package com.xerocry.bankservice.controller;

import com.xerocry.bankservice.dto.*;
import com.xerocry.bankservice.entity.Account;
import com.xerocry.bankservice.entity.User;
import com.xerocry.bankservice.repository.AccountRepo;
import com.xerocry.bankservice.repository.UserRepo;
import com.xerocry.bankservice.service.AccountService;
import com.xerocry.bankservice.service.JwtUserDetailsService;
import com.xerocry.bankservice.service.TransactionService;
import com.xerocry.bankservice.security.jwt.JwtTokenUtil;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@OpenAPIDefinition(servers = {@Server(url = "http://localhost:8100")}, info = @Info(title = "Bank Service API", version = "v1", description = "Part of ATM Emulator microservice architecture, handling transactions"))
@RequestMapping(path = "/api/v1")
@Slf4j
public class TransactionController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authRequest) {
        try {
            Account account = accountService.loadAccountByName(authRequest.getCardNumber());

            if (account == null) {
                return ResponseEntity.badRequest().body("Card not exist.");
            }

            if (account.getRemainingAttempts() > 0) {
                try {
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(authRequest.getCardNumber(), authRequest.getPassword())
                    );
                    account.setRemainingAttempts(3);
                } catch (BadCredentialsException exception) {
                    account.setRemainingAttempts(account.getRemainingAttempts() - 1);
                    accountRepo.save(account);
//                    if(account.getAuthMethod().equals())
                    throw new BadCredentialsException("Wrong authentication parameter. You have "
                            + account.getRemainingAttempts()
                            + " more tries left.");
                }

                final String token = jwtTokenUtil.generateToken(account);

                return ResponseEntity.ok(new AuthenticationResponse(token));
            } else {
                return ResponseEntity.badRequest().body("Card Blocked");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Authentication error: " + e.getMessage());
        }
    }

    @PostMapping(value = "/transactions/withdraw")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<TransactionResponse> withdraw(@RequestBody TransactionRequest transactionRequest) throws Exception {
        return this.transactionService.withdraw(transactionRequest);
    }

    @PostMapping(value = "/transactions/deposit")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<TransactionResponse> deposit(@RequestBody TransactionRequest transactionRequest) throws Exception {
        return this.transactionService.deposit(transactionRequest);
    }

    @GetMapping("/transactions/balance/{cardNumber}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<TransactionResponse> balance(@NotNull @PathVariable String cardNumber) throws Exception {
        return this.transactionService.checkBalance(cardNumber);
    }

    @PostMapping("/account/changeauth/{cardNumber}/{method}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> changeAuth(@NotNull @PathVariable("cardNumber") String cardNumber,
                                                          @NotNull @PathVariable("method") Boolean method) throws Exception {
        return this.transactionService.changeAuthMethod(cardNumber, method);
    }

    @GetMapping("/account/validateCard/{cardNumber}")
    public ResponseEntity<Account> validateCard(@NotNull @PathVariable String cardNumber) {
        return this.transactionService.validateCard(cardNumber);
    }
}
