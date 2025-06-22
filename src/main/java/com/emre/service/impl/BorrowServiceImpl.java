package com.emre.service.impl;

import com.emre.dto.DtoBorrow;
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
    public DtoBorrow borrowBook(Long userId, Long bookId) {
        // Kullanıcı kontrolü
        User user = userRepository.findById(userId).orElseThrow(() ->
                new BaseException(new ErrorMessage(MessageType.USERNAME_NOT_FOUND, userId.toString())));

        // Kitap kontrolü
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, bookId.toString())));

        if (!book.getStatus().equals(BookStatusType.AVAILABLE)) {
            throw new BaseException(new ErrorMessage(MessageType.BOOK_NOT_AVAILABLE, bookId.toString()));
        }

        // Borrow kaydı oluştur
        Borrow borrow = new Borrow();
        borrow.setUser(user);
        borrow.setBook(book);
        borrow.setBorrowDate(new Date());
        borrow.setReturnDate(null); // Kitap henüz iade edilmedi

        // Kitap durumunu değiştir
        book.setStatus(BookStatusType.NONAVAILABLE);

        // Kaydet
        bookRepository.save(book);
        Borrow savedBorrow = borrowRepository.save(borrow);

        // DTO dönüşü
        DtoBorrow dto = new DtoBorrow();
        dto.setId(savedBorrow.getId());
        dto.setUserId(userId);
        dto.setBookId(bookId);
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
}
