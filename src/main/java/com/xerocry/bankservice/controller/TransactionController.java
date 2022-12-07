package com.xerocry.bankservice.controller;

import com.xerocry.bankservice.dto.AuthenticationRequest;
import com.xerocry.bankservice.dto.AuthenticationResponse;
import com.xerocry.bankservice.dto.TransactionRequest;
import com.xerocry.bankservice.dto.TransactionResponse;
import com.xerocry.bankservice.entity.Account;
import com.xerocry.bankservice.repository.AccountRepo;
import com.xerocry.bankservice.repository.TransactionRepo;
import com.xerocry.bankservice.service.AccountService;
import com.xerocry.bankservice.service.JwtUserDetailsService;
import com.xerocry.bankservice.service.TransactionService;
import com.xerocry.bankservice.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/transactions")
@Slf4j
public class TransactionController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authRequest) throws Exception {

        Account account = this.accountRepo.findAccountByCardNumber(authRequest.getCardNumber());
        Account account1 = new Account();

        if (account.getRemainingAttempts() > 0) {
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(authRequest.getCardNumber(), authRequest.getPassword())
                );
                account1.setRemainingAttempts(3);
            } catch (BadCredentialsException exception) {
                account1.setRemainingAttempts(account.getRemainingAttempts() - 1);
                throw new Exception("Incorrect credentials", exception);
            }
            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(authRequest.getCardNumber());

            final String token = jwtTokenUtil.generateToken(userDetails);

            return ResponseEntity.ok(new AuthenticationResponse(token));
        } else {
            return ResponseEntity.badRequest().body("Card Blocked");
        }
    }

    @PostMapping(value = "/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(@RequestHeader("Authorization") String token,
                                                        @RequestBody TransactionRequest transactionRequest) throws Exception {
        return this.transactionService.withdraw(token, transactionRequest);
    }

    @PostMapping(value = "/deposit")
    public ResponseEntity<TransactionResponse> deposit(@RequestHeader("Authorization") String token,
                                                       @RequestBody TransactionRequest transactionRequest) throws Exception {
        return this.transactionService.deposit(token, transactionRequest);
    }

    @GetMapping("/balance/{cardNumber}")
    public ResponseEntity<TransactionResponse> balance(@RequestHeader("Authorization") String token,
                                                       @NotNull @PathVariable String cardNumber) throws Exception {
        return this.transactionService.checkBalance(token, cardNumber);
    }

}