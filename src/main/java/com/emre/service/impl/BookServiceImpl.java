package com.emre.service.impl;

import com.emre.dto.DtoBook;
import com.emre.dto.DtoBookIU;
import com.emre.enums.BookStatusType;
import com.emre.exception.BaseException;
import com.emre.exception.ErrorMessage;
import com.emre.exception.MessageType;
import com.emre.model.Book;
import com.emre.repository.BookRepository;
import com.emre.service.IBookService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    private Book createBook(DtoBookIU dtoBookIU) {
        Book book = new Book();
        BeanUtils.copyProperties(dtoBookIU, book);
        book.setStatus(BookStatusType.AVAILABLE);
        return book;
    }

    @Override
    public DtoBook saveBook(DtoBookIU dtoBookIU) {
        DtoBook dtoBook = new DtoBook();
        Book savedBook = bookRepository.save(createBook(dtoBookIU));
        BeanUtils.copyProperties(savedBook, dtoBook);
        return dtoBook;
    }

    private DtoBook convertToDto(Book book) {
        DtoBook dtoBook = new DtoBook();
        BeanUtils.copyProperties(book, dtoBook);
        return dtoBook;
    }

    @Override
    public List<DtoBook> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoBook getBookById(Long id) {
        Optional<Book> optBook = bookRepository.findById(id);
        if (optBook.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }

        return convertToDto(optBook.get());
    }

}
