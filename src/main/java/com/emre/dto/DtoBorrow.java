package com.emre.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoBorrow {

    private Long id;
    private Long userId;
    private Long bookId;
    private Date borrowDate;
    private Date returnDate;
}
