package com.emre.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonFormat;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoBorrowIU {
    private Long userId;
    private Long bookId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date returnDate;
}
