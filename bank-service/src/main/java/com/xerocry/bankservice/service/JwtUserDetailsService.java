package com.xerocry.bankservice.service;

import com.xerocry.bankservice.dto.AuthMethod;
import com.xerocry.bankservice.entity.Account;
import com.xerocry.bankservice.repository.AccountRepo;
import com.xerocry.bankservice.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String cardNumber) throws UsernameNotFoundException {
        Optional<Account> account = accountRepo.findAccountByCardNumber(cardNumber);
        if (account.isPresent()) {
            log.debug("Card '" + cardNumber + "' not found");
            throw new UsernameNotFoundException("Card number " + cardNumber + " not found");
        }
        else if (account.get().getAuthMethod().equals(AuthMethod.PIN)) {
            return new User(cardNumber, account.get().getPin(), Collections.emptyList());
        } else
            return new User(cardNumber, userRepo.findUserById(account.get().getId()).getFingerprint(), Collections.emptyList());
    }
}
