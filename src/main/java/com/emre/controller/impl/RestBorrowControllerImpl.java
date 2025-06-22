package com.emre.controller.impl;

import com.emre.controller.IRestBorrowController;
import com.emre.controller.RestBaseController;
import com.emre.controller.RootEntity;
import com.emre.dto.DtoBorrow;
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
    public RootEntity<DtoBorrow> borrowBook(@RequestParam Long userId,@RequestParam Long bookId) {
        return ok(borrowService.borrowBook(userId,bookId));
    }

    @GetMapping("/history/{userId}")
    @Override
    public RootEntity<List<DtoBorrow>> getUserBorrowHistory(@PathVariable Long userId) {
        return ok(borrowService.getUserBorrowHistory(userId));
    }
}
