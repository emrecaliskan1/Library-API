package com.emre.controller;

import com.emre.controller.RootEntity;
import com.emre.dto.DtoBook;
import com.emre.dto.DtoBookIU;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IRestBookController {

    @PostMapping("/books")
    RootEntity<DtoBook> createBook(@RequestBody DtoBookIU dtoBookIU);

    @GetMapping("/books")
    RootEntity<List<DtoBook>> getAllBooks();

    @GetMapping("/books/{id}")
    RootEntity<DtoBook> getBookById(@PathVariable Long id);
}