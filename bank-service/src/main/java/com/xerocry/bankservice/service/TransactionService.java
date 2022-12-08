package com.xerocry.bankservice.service;

import com.xerocry.bankservice.dto.*;
import com.xerocry.bankservice.entity.Account;
import com.xerocry.bankservice.entity.Transaction;
import com.xerocry.bankservice.repository.AccountRepo;
import com.xerocry.bankservice.repository.TransactionRepo;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Slf4j
public class TransactionService {
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private AccountRepo accountRepo;

    public ResponseEntity<TransactionResponse> deposit(TransactionRequest transactionRequest) {
        String cardNumber = transactionRequest.getCardNumber();
        Account account = accountRepo.findAccountByCardNumber(cardNumber).get();
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

    public ResponseEntity<TransactionResponse> withdraw(TransactionRequest transactionRequest) {
        TransactionStatus STATUS;
        String cardNumber = transactionRequest.getCardNumber();
        Account account = accountRepo.findAccountByCardNumber(cardNumber).get();

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

    public ResponseEntity<TransactionResponse> checkBalance(@NotNull String cardNumber) {
        Account account = this.accountRepo.findAccountByCardNumber(cardNumber).get();

        TransactionResponse response = new TransactionResponse();
        response.setTimestamp(LocalDate.now());
        response.setType(TransactionType.CHECK_BALANCE);
        response.setStatus(TransactionStatus.SUCCESS);
        response.setCardNumber(account.getCardNumber());
        response.setAmount(account.getBalance());
        return ResponseEntity.ok(response);
    }


    public ResponseEntity<?> changeAuthMethod(@NotNull String cardNumber, @NotNull Boolean method) {
        Account account = accountRepo.findAccountByCardNumber(cardNumber).get();

        if (account == null) {
            log.error("No card found!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        account.setAuthMethod(method ? AuthMethod.FINGERPRINT : AuthMethod.PIN);
        this.accountRepo.save(account);

        return ResponseEntity.ok("Auth method changed!");
    }

    public Boolean validateCard(String cardNumber) {
        try {
            Optional<Account> account = accountRepo.findAccountByCardNumber(cardNumber);
            if (account.isPresent()) {
                return true;
            } else {
                throw new NotFoundException("Card " .concat(cardNumber).concat(" not found"));
            }
        } catch (NotFoundException e) {
            log.error("validateCard service not found error");
            log.error(e.getLocalizedMessage());
            return false;
        } catch (Exception e) {
            log.error("validateCard service error");
            log.error(e.getLocalizedMessage());
            return false;
        }
    }
}
