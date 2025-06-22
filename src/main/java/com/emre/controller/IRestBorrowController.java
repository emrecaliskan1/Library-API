package com.emre.controller;

import com.emre.dto.DtoBook;
import com.emre.dto.DtoBorrow;
import jakarta.persistence.criteria.Root;

import java.util.List;

public interface IRestBorrowController {

    public RootEntity<DtoBorrow> borrowBook(Long userId, Long bookId);

    public RootEntity<List<DtoBorrow>> getUserBorrowHistory(Long userId);
}
