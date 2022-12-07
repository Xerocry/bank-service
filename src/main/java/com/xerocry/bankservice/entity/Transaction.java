package com.xerocry.bankservice.entity;

import com.xerocry.bankservice.dto.TransactionStatus;
import com.xerocry.bankservice.dto.TransactionType;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
@Entity
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transactions_generator")
    @SequenceGenerator(name = "transactions_generator", sequenceName = "transactions_seq", allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    private double transactionAmount;
    private TransactionStatus transactionStatus;
    private TransactionType transactionType;
    private LocalDate timestamp;

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", account=" + account +
                ", transactionAmount=" + transactionAmount +
                ", transactionStatus='" + transactionStatus + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
