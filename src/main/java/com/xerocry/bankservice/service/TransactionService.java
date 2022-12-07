package com.xerocry.bankservice.service;

import com.xerocry.bankservice.dto.TransactionStatus;
import com.xerocry.bankservice.dto.TransactionRequest;
import com.xerocry.bankservice.dto.TransactionResponse;
import com.xerocry.bankservice.dto.TransactionType;
import com.xerocry.bankservice.entity.Account;
import com.xerocry.bankservice.entity.Transaction;
import com.xerocry.bankservice.repository.AccountRepo;
import com.xerocry.bankservice.repository.TransactionRepo;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;

@Service
@Slf4j
public class TransactionService {
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private AccountRepo accountRepo;

    public ResponseEntity<TransactionResponse> deposit(String token, TransactionRequest transactionRequest) {
        String cardNumber = transactionRequest.getCardNumber();
        Account account = accountRepo.findAccountByCardNumber(cardNumber);
        account.setBalance(account.getBalance() + transactionRequest.getAmount());
        log.debug("Successfully deposited!");

        Transaction transaction = new Transaction();
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setTransactionAmount(transactionRequest.getAmount());
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setTimestamp(LocalDate.now());
        transaction.setAccount(account);
        this.transactionRepo.save(transaction);

        TransactionResponse response = new TransactionResponse();
        response.setTimestamp(LocalDate.now());
        response.setType(TransactionType.DEPOSIT);
        response.setStatus(TransactionStatus.SUCCESS);
        response.setCardNumber(account.getCardNumber());
        response.setAmount(transactionRequest.getAmount());

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<TransactionResponse> withdraw(String token, TransactionRequest transactionRequest) {
        TransactionStatus STATUS;
        String cardNumber = transactionRequest.getCardNumber();
        Account account = accountRepo.findAccountByCardNumber(cardNumber);

        if (account.getBalance() > transactionRequest.getAmount()) {
            account.setBalance(account.getBalance() - transactionRequest.getAmount());
            log.debug("Successfully withdrawn!");
            STATUS = TransactionStatus.SUCCESS;
        } else {
            STATUS = TransactionStatus.FAILED;
            log.debug("Not enough money on balance!");
        }

        Transaction transaction = new Transaction();
        transaction.setTransactionStatus(STATUS);
        transaction.setTransactionAmount(transactionRequest.getAmount());
        transaction.setTransactionType(TransactionType.WITHDRAW);
        transaction.setTimestamp(LocalDate.now());
        transaction.setAccount(account);
        this.transactionRepo.save(transaction);

        TransactionResponse response = new TransactionResponse();
        response.setTimestamp(LocalDate.now());
        response.setType(TransactionType.WITHDRAW);
        response.setStatus(STATUS);
        response.setCardNumber(account.getCardNumber());
        response.setAmount(transactionRequest.getAmount());

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<TransactionResponse> checkBalance(String token, @PathVariable @NotNull String cardNumber) {
        Account account = this.accountRepo.findAccountByCardNumber(cardNumber);

        TransactionResponse response = new TransactionResponse();
        response.setTimestamp(LocalDate.now());
        response.setType(TransactionType.CHECK_BALANCE);
        response.setStatus(TransactionStatus.SUCCESS);
        response.setCardNumber(account.getCardNumber());
        response.setAmount(account.getBalance());
        return ResponseEntity.ok(response);
    }


}