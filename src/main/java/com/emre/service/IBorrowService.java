package com.emre.service;

import com.emre.dto.DtoBorrow;

import java.util.List;

public interface IBorrowService {

    DtoBorrow borrowBook(Long userId, Long bookId);
    List<DtoBorrow> getUserBorrowHistory(Long userId);

}
