package com.xerocry.bankservice.service;

import com.xerocry.bankservice.entity.Account;
import com.xerocry.bankservice.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepo accountRepo;

    public Account loadAccountByName(String cardNumber) {
        Optional<Account> account = this.accountRepo.findAccountByCardNumber(cardNumber);
        return account.isPresent() ? this.accountRepo.findAccountByCardNumber(cardNumber).get() : null;
    }

}
