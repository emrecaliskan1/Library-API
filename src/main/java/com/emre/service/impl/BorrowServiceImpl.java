package com.emre.service.impl;

import com.emre.dto.DtoBorrow;
import com.emre.dto.DtoBorrowIU;
import com.emre.enums.BookStatusType;
import com.emre.exception.BaseException;
import com.emre.exception.ErrorMessage;
import com.emre.exception.MessageType;
import com.emre.model.Book;
import com.emre.model.Borrow;
import com.emre.model.User;
import com.emre.repository.BookRepository;
import com.emre.repository.BorrowRepository;
import com.emre.repository.UserRepository;
import com.emre.service.IBorrowService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowServiceImpl implements IBorrowService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BorrowRepository borrowRepository;

    @Override
    @Transactional
    public DtoBorrow borrowBook(DtoBorrowIU dtoBorrowIU) {

        User user = userRepository.findById(dtoBorrowIU.getUserId()).orElseThrow(() -> new BaseException(
                new ErrorMessage(MessageType.USERNAME_NOT_FOUND, dtoBorrowIU.getUserId().toString())));

        Book book = bookRepository.findById(dtoBorrowIU.getBookId()).orElseThrow(() -> new BaseException(
                new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoBorrowIU.getBookId().toString())));

        if (!book.getStatus().equals(BookStatusType.AVAILABLE)) {
            throw new BaseException(
                    new ErrorMessage(MessageType.BOOK_NOT_AVAILABLE, dtoBorrowIU.getBookId().toString()));
        }

        Borrow borrow = new Borrow();
        borrow.setUser(user);
        borrow.setBook(book);
        borrow.setBorrowDate(new Date());
        borrow.setReturnDate(dtoBorrowIU.getReturnDate());

        book.setStatus(BookStatusType.NONAVAILABLE);

        bookRepository.save(book);
        Borrow savedBorrow = borrowRepository.save(borrow);

        DtoBorrow dto = new DtoBorrow();
        dto.setId(savedBorrow.getId());
        dto.setUserId(dtoBorrowIU.getUserId());
        dto.setUsername(user.getUsername());
        dto.setBookId(dtoBorrowIU.getBookId());
        dto.setBookName(book.getName());
        dto.setWriter(book.getWriter());
        dto.setBorrowDate(savedBorrow.getBorrowDate());
        dto.setReturnDate(savedBorrow.getReturnDate());
        return dto;
    }

    @Override
    public List<DtoBorrow> getUserBorrowHistory(Long userId) {
        List<Borrow> borrows = borrowRepository.findByUserId(userId);

        return borrows.stream().map(borrow -> {
            DtoBorrow dto = new DtoBorrow();
            dto.setId(borrow.getId());
            dto.setUserId(borrow.getUser().getId());
            dto.setBookId(borrow.getBook().getId());
            dto.setBorrowDate(borrow.getBorrowDate());
            dto.setReturnDate(borrow.getReturnDate());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DtoBorrow returnBook(Long borrowId, Long userId) {
        Borrow borrow = borrowRepository.findById(borrowId)
                .orElseThrow(
                        () -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, borrowId.toString())));
        if (!borrow.getUser().getId().equals(userId)) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION,
                    "Bu kitabı sadece ödünç alan kullanıcı iade edebilir."));
        }
        if (borrow.isReturned()) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Kitap zaten iade edilmiş."));
        }
        borrow.setReturned(true);
        borrow.setReturnDate(new Date());
        Book book = borrow.getBook();
        book.setStatus(BookStatusType.AVAILABLE);
        bookRepository.save(book);
        Borrow savedBorrow = borrowRepository.save(borrow);
        DtoBorrow dto = new DtoBorrow();
        dto.setId(savedBorrow.getId());
        dto.setUserId(savedBorrow.getUser().getId());
        dto.setUsername(savedBorrow.getUser().getUsername());
        dto.setBookId(savedBorrow.getBook().getId());
        dto.setBookName(savedBorrow.getBook().getName());
        dto.setWriter(savedBorrow.getBook().getWriter());
        dto.setBorrowDate(savedBorrow.getBorrowDate());
        dto.setReturnDate(savedBorrow.getReturnDate());
        return dto;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void autoReturnExpiredBorrows() {
        Date now = new Date();
        List<Borrow> expiredBorrows = borrowRepository.findAll().stream()
                .filter(b -> !b.isReturned() && b.getReturnDate() != null && b.getReturnDate().before(now))
                .collect(Collectors.toList());

        for (Borrow borrow : expiredBorrows) {
            borrow.setReturned(true);
            Book book = borrow.getBook();
            book.setStatus(BookStatusType.AVAILABLE);
            bookRepository.save(book);
            borrowRepository.save(borrow);
        }
    }
}
