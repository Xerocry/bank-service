package com.xerocry.bankservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionResponse that = (TransactionResponse) o;
        return Double.compare(that.amount, amount) == 0 && cardNumber.equals(that.cardNumber) && status == that.status && timestamp.equals(that.timestamp) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, amount, status, timestamp, type);
    }
}
