package com.xerocry.bankservice.service;

import com.xerocry.bankservice.entity.Account;
import com.xerocry.bankservice.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepo accountRepo;

    public Account loadAccountByName(String cardNumber) {
        return this.accountRepo.findAccountByCardNumber(cardNumber);
    }

}
