package com.xerocry.bankservice.service;

import com.xerocry.bankservice.dto.*;
import com.xerocry.bankservice.entity.Account;
import com.xerocry.bankservice.entity.Bank;
import com.xerocry.bankservice.entity.Transaction;
import com.xerocry.bankservice.repository.AccountRepo;
import com.xerocry.bankservice.security.jwt.JwtTokenUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceTest {
    Account account = new Account();
    @MockBean
    private JwtTokenUtil jwtUtil;
    @MockBean
    private AccountRepo accountRepository;
    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private TransactionService transactionService;

    @Before
    public void init() {
        account.setCardNumber("7164531182231187");
        account.setBalance(1111);
        account.setAuthMethod(AuthMethod.PIN);
        account.setPin("$2a$12$G44/zLcMW.LicOx232/opO3sTH73kKPpQS0XmaEBBBnpeCzBASENS");
        account.setBank(new Bank("BOG", "Georgia, Tbilisi", "110"));

        when(accountRepository.findAccountByCardNumber("7164531182231187")).thenReturn(Optional.of(account));
    }

    @BeforeEach
    void setUp() throws IOException {
        account.setCardNumber("7164531182231187");
        account.setBalance(1111);
        account.setAuthMethod(AuthMethod.PIN);
        account.setPin("$2a$12$G44/zLcMW.LicOx232/opO3sTH73kKPpQS0XmaEBBBnpeCzBASENS");
        account.setBank(new Bank("BOG", "Georgia, Tbilisi", "110"));

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(100);
        transactionRequest.setCardNumber(account.getCardNumber());

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setCardNumber("7164531182231187");
        transactionResponse.setAmount(100);
        transactionResponse.setStatus(TransactionStatus.SUCCESS);
        transactionResponse.setType(TransactionType.DEPOSIT);
        transactionResponse.setTimestamp(LocalDate.now());

        Mockito.when(accountRepository.findAccountByCardNumber("7164531182231187")).thenReturn(Optional.ofNullable(account));

        Mockito.when(accountRepository.findById(account.getId()))
                .thenReturn(Optional.ofNullable(account));

        account.setBalance(account.getBalance() + 100);
        Mockito.when(transactionService.deposit(transactionRequest))
                .thenReturn(ResponseEntity.ok(transactionResponse));

        transactionResponse.setType(TransactionType.WITHDRAW);
        account.setBalance(account.getBalance() - 100);
        Mockito.when(transactionService.withdraw(transactionRequest))
                .thenReturn(ResponseEntity.ok(transactionResponse));

    }


    @Test
    public void testBalance() {
        TransactionRequest queryRequest = new TransactionRequest();
        queryRequest.setCardNumber("7164531182231187");
        Account account = new Account();
        account.setCardNumber("7164531182231187");
        account.setAuthMethod(AuthMethod.PIN);
        account.setBalance(1000);
        account.setPin("1234");
        when(accountRepository.findAccountByCardNumber(queryRequest.getCardNumber())).thenReturn(Optional.of(account));
        when(jwtUtil.validateToken("token", jwtUserDetailsService.loadUserByUsername(queryRequest.getCardNumber()))).thenReturn(true);

        ResponseEntity<TransactionResponse> responseEntity = transactionService.checkBalance(queryRequest.getCardNumber());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testDeposit() throws Exception {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiIsImtpZCI6ImEwZWFkM2U2ZDg3ZjE0MzAxYWY4ZjE1OGFjMDRlMTM0In0.eyJjYXJkTnVtYmVyIjoiNzE2NDUzMTE4MjIzMTE4NCIsInBhc3N3b3JkIjoiZmluZ2VyMTIzIn0.JyvEvMWgVAa3YDVgG7R4xKGTh3AE7_XKrrprL5PM4ylAZl8S5RngRF9z5HvQvQge0kGu-FTgfd-ME5OVYA6A1A";
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setCardNumber("7164531182231187");
        transactionRequest.setAmount(100);

        Account account = new Account();
        account.setCardNumber("7164531182231187");
        account.setBalance(100);
        account.setAuthMethod(AuthMethod.PIN);
        account.setPin("1234");
        account.setBank(new Bank("BOG", "Georgia, Tbilisi", "110"));

        Transaction transaction = new Transaction();
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setTransactionAmount(100);
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setAccount(account);
        transaction.setTimestamp(LocalDate.now());

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setCardNumber("7164531182231187");
        transactionResponse.setAmount(100);
        transactionResponse.setStatus(TransactionStatus.SUCCESS);
        transactionResponse.setType(TransactionType.DEPOSIT);
        transactionResponse.setTimestamp(LocalDate.now());

        when(accountRepository.findAccountByCardNumber(transactionRequest.getCardNumber())).thenReturn(Optional.of(account));
        when(jwtUtil.validateToken(token, jwtUserDetailsService.loadUserByUsername(transactionRequest.getCardNumber()))).thenReturn(true);

        ResponseEntity<TransactionResponse> responseEntity = transactionService.deposit(transactionRequest);

        assertEquals(transactionResponse, responseEntity.getBody());
    }

    @Test
    public void testWithdraw() throws Exception {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiIsImtpZCI6ImEwZWFkM2U2ZDg3ZjE0MzAxYWY4ZjE1OGFjMDRlMTM0In0.eyJjYXJkTnVtYmVyIjoiNzE2NDUzMTE4MjIzMTE4NCIsInBhc3N3b3JkIjoiZmluZ2VyMTIzIn0.JyvEvMWgVAa3YDVgG7R4xKGTh3AE7_XKrrprL5PM4ylAZl8S5RngRF9z5HvQvQge0kGu-FTgfd-ME5OVYA6A1A";
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setCardNumber("7164531182231187");
        transactionRequest.setAmount(100);

        Transaction transaction = new Transaction();
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setTransactionAmount(100);
        transaction.setTransactionType(TransactionType.WITHDRAW);
        transaction.setAccount(account);
        transaction.setTimestamp(LocalDate.now());

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setCardNumber("7164531182231187");
        transactionResponse.setAmount(100);
        transactionResponse.setStatus(TransactionStatus.SUCCESS);
        transactionResponse.setType(TransactionType.WITHDRAW);
        transactionResponse.setTimestamp(LocalDate.now());

        when(jwtUtil.validateToken(token, jwtUserDetailsService.loadUserByUsername(transactionRequest.getCardNumber()))).thenReturn(true);

        ResponseEntity<TransactionResponse> responseEntity = transactionService.withdraw(transactionRequest);

        assertEquals(transactionResponse, responseEntity.getBody());

    }
}
