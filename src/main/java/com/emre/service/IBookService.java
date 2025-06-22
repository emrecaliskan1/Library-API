package com.emre.service;

import com.emre.dto.DtoBook;
import com.emre.dto.DtoBookIU;
import com.emre.model.Book;

import java.util.List;

public interface IBookService {

    public DtoBook saveBook(DtoBookIU dtoBookIU);

    public List<DtoBook> getAllBooks();

    public DtoBook getBookById(Long id);

}
