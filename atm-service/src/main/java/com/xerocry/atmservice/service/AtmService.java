package com.xerocry.atmservice.service;

import com.xerocry.atmservice.dto.*;

import com.xerocry.atmservice.security.SecurityContext;
import com.xerocry.atmservice.validator.CardValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class AtmService {
    @Value("${bankServiceUrl}")
    private static String url;
    private static final String CHECK_BALANCE = "/transactions/balance/";
    private static final String DEPOSIT_TRANSACTION = "/transactions/deposit";
    private static final String WITHDRAW_TRANSACTION = "/transactions/withdraw";
    private static final String LOGIN = "/authenticate";
    private static final String CHANGE_AUTH = "/account/changeauth/";
//    private static final String GET_TRANSACTIONS = "statement";
    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<TransactionResponse> withdraw(TransactionRequest transactionRequest){
        log.info("ATMController::balance");
        return restTemplate.postForEntity(url + WITHDRAW_TRANSACTION, transactionRequest, TransactionResponse.class);
    }
    public ResponseEntity<TransactionResponse> deposit(TransactionRequest transactionRequest){
        return restTemplate.postForEntity(url + DEPOSIT_TRANSACTION, transactionRequest, TransactionResponse.class);

    }
    public ResponseEntity<TransactionResponse> checkBalance(String cardNumber) {
        return restTemplate.getForEntity(url + CHECK_BALANCE + cardNumber,TransactionResponse.class);
    }
    public ResponseEntity<?> changeAuthMethod(String cardNumber, Boolean method) {
        return restTemplate.exchange(url + CHANGE_AUTH + cardNumber + "/" + (method ? 1 : 0),
                HttpMethod.POST,
                new HttpEntity<>(cardNumber),
                String.class);
    }
    public ResponseEntity<AuthenticationResponse> login(AuthenticationRequest authRequest) {
        validateCardNumber(authRequest.getCardNumber());
        ResponseEntity<AuthenticationResponse> response = restTemplate.postForEntity(
                url + LOGIN,
                new HttpEntity<>(authRequest),
                AuthenticationResponse.class);
        String authorization = response.getBody().getToken();
        SecurityContext.getInstance().setToken(!authorization.isEmpty() ? authorization : null);
        SecurityContext.getInstance().setCardNumber(authRequest.getCardNumber());
        return response;
    }
    public String validateCardNumber(String cardNo) {
        if (CardValidator.isValid(cardNo)) {
            SecurityContext.getInstance()
                    .setCardNumber(cardNo);
        }
        return SecurityContext.getInstance().getCardNumber();
    }
}
