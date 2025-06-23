package com.emre.service;

import com.emre.dto.DtoBorrow;
import com.emre.dto.DtoBorrowIU;

import java.util.List;

public interface IBorrowService {

    DtoBorrow borrowBook(DtoBorrowIU dtoBorrowIU);

    List<DtoBorrow> getUserBorrowHistory(Long userId);

    DtoBorrow returnBook(Long borrowId, Long userId);

}
