package com.emre.controller;

import com.emre.dto.DtoBook;
import com.emre.dto.DtoBorrow;
import com.emre.dto.DtoBorrowIU;
import com.emre.dto.UserIdRequest;

import jakarta.persistence.criteria.Root;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

public interface IRestBorrowController {

    public RootEntity<DtoBorrow> borrowBook(DtoBorrowIU dtoBorrowIU);;

    public RootEntity<List<DtoBorrow>> getUserBorrowHistory(Long userId);

    public RootEntity<DtoBorrow> returnBook(Long borrowId, UserIdRequest userIdRequest);
}
