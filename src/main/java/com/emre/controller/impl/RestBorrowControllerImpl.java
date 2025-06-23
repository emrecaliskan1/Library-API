package com.emre.controller.impl;

import com.emre.controller.IRestBorrowController;
import com.emre.controller.RestBaseController;
import com.emre.controller.RootEntity;
import com.emre.dto.DtoBorrow;
import com.emre.dto.DtoBorrowIU;
import com.emre.dto.UserIdRequest;
import com.emre.service.IBorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/borrow")
public class RestBorrowControllerImpl extends RestBaseController implements IRestBorrowController {

    @Autowired
    private IBorrowService borrowService;

    @PostMapping("/take")
    @Override
    public RootEntity<DtoBorrow> borrowBook(@RequestBody DtoBorrowIU dtoBorrowIU) {
        return ok(borrowService.borrowBook(dtoBorrowIU));
    }

    @GetMapping("/history/{userId}")
    @Override
    public RootEntity<List<DtoBorrow>> getUserBorrowHistory(@PathVariable Long userId) {
        return ok(borrowService.getUserBorrowHistory(userId));
    }

    @PutMapping("/return/{borrowId}")
    @Override
    public RootEntity<DtoBorrow> returnBook(@PathVariable Long borrowId, @RequestBody UserIdRequest userIdRequest) {
        return ok(borrowService.returnBook(borrowId, userIdRequest.getUserId()));
    }
}
