package com.xerocry.atmservice.controller;

import com.xerocry.atmservice.dto.*;
import com.xerocry.atmservice.entity.Account;
import com.xerocry.atmservice.service.AtmService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Receive user demands for login, change auth method, deposit, withdraw, check balance requests.
 */
@RestController
@Slf4j
@RequestMapping(path = "api/v1/atm")
public class AtmController {
    @Autowired
    private AtmService atmService;

    /**
     * Login request to create userr session for other bank requests
     * @param authRequest - JSON containing 'cardNumber' and PIN\Fingerprint 'password' string
     * @return
     */
    @PostMapping("/cards/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authRequest) {
        log.info("ATMController::login");
        return atmService.login(authRequest);
    }

    /**
     * Withdraw money from the user banking card
     * @param transactionRequest - JSON containing 'cardNumber' and 'amount'
     * @return
     */
    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(@RequestBody TransactionRequest transactionRequest){
        log.info("AtmController::withdraw");
        return atmService.withdraw(transactionRequest);
    }

    /**
     * Deposit money into the user banking card
     * @param transactionRequest - JSON containing 'cardNumber' and 'amount'
     * @return
     */
    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> deposit(@RequestBody TransactionRequest transactionRequest){
        log.info("AtmController::deposit");
        return atmService.deposit(transactionRequest);
    }
    /**
     * Request to get balance of user banking card
     * @param cardNumber - valid card number
     * @return
     */
    @GetMapping("/balance/{cardNumber}")
    public ResponseEntity<TransactionResponse> balance(@NotNull @PathVariable String cardNumber) {
        log.info("AtmController::balance");
        return atmService.checkBalance(cardNumber);
    }
    @PostMapping("/changeauth/{cardNumber}/{method}")
    public ResponseEntity<?> changeAuth(@NotNull @PathVariable("cardNumber") String cardNumber,
                                        @NotNull @PathVariable("method") Boolean method){
        log.info("AtmController::changeAuth");
        return atmService.changeAuthMethod(cardNumber, method);
    }
    @GetMapping("/cards/validate/{cardNo}")
    @CircuitBreaker(name = "atm-controller", fallbackMethod = "fallback")
    public String validateCardNumber(@PathVariable @NotNull String cardNo) {
        return atmService.validateCardNumber(cardNo);
    }
    public ResponseEntity<String> fallback(Throwable e) {
        return new ResponseEntity <>(e.getMessage(), HttpStatus.BAD_GATEWAY);
    }

}
