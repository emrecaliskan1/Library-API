package com.emre.controller.impl;

import com.emre.controller.IRestBookController;
import com.emre.controller.RestBaseController;
import com.emre.controller.RootEntity;
import com.emre.dto.DtoBook;
import com.emre.dto.DtoBookIU;
import com.emre.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api")
public class RestBookControllerImpl extends RestBaseController implements IRestBookController {

    @Autowired
    private IBookService bookService;

    @PostMapping("/save-book")
    @Override
    public RootEntity<DtoBook> createBook(@RequestBody DtoBookIU dtoBookIU) {
        return ok(bookService.saveBook(dtoBookIU));
    }

    @GetMapping("/books/all")
    @Override
    public RootEntity<List<DtoBook>> getAllBooks() {
        return ok(bookService.getAllBooks());
    }

    @GetMapping("/books/{id}")
    @Override
    public RootEntity<DtoBook> getBookById(@PathVariable Long id) {
        return ok(bookService.getBookById(id));
    }
}