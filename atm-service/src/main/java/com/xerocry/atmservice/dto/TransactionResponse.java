package com.xerocry.atmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private String cardNumber;
    private double amount;
    private TransactionStatus status;
    private LocalDate timestamp;
    private TransactionType type;
}
