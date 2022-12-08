package com.xerocry.atmservice.controller;

import com.xerocry.atmservice.dto.*;
import com.xerocry.atmservice.entity.Account;
import com.xerocry.atmservice.service.AtmService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(path = "api/v1/atm")
public class AtmController {
    @Autowired
    private AtmService atmService;

    @PostMapping("/cards/login/{method}")
    public ResponseEntity<AuthenticationResponse> login(@PathVariable @NotNull Boolean method, @RequestBody AuthenticationRequest authRequest) {
        log.info("ATMController::login");
        return atmService.login(method ? AuthMethod.FINGERPRINT : AuthMethod.PIN, authRequest);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(@RequestBody TransactionRequest transactionRequest){
        log.info("AtmController::withdraw");
        return atmService.withdraw(transactionRequest);
    }
    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> deposit(@RequestBody TransactionRequest transactionRequest){
        log.info("AtmController::deposit");
        return atmService.deposit(transactionRequest);
    }
    @GetMapping("/balance/{cardNumber}")
    public ResponseEntity<TransactionResponse> balance(@NotNull @PathVariable String cardNumber) {
        log.info("AtmController::balance");
        return atmService.checkBalance(cardNumber);
    }

    @PostMapping("/changeauth/{cardNumber}/{method}")
    public ResponseEntity<?> changeAuth(@NotNull @PathVariable("cardNumber") String cardNumber,
                                        @NotNull @PathVariable("method") Boolean method) throws Exception {
        log.info("AtmController::changeAuth");
        return atmService.changeAuthMethod(cardNumber, method);
    }

    @GetMapping("/cards/validate/{cardNo}")
//    @CircuitBreaker(name = "default", fallbackMethod = "validateCardFallBack")
    // @RateLimiter(name="default")
    public String validateCardNumber(@PathVariable @NotNull String cardNo) {
        return atmService.validateCardNumber(cardNo);
    }

}
