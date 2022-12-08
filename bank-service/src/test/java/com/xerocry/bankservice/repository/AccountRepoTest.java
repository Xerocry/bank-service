package com.xerocry.bankservice.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xerocry.bankservice.dto.AuthMethod;
import com.xerocry.bankservice.entity.Account;
import com.xerocry.bankservice.entity.Bank;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class AccountRepoTest {
    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setup()  {
        Account account = new Account();
        account.setCardNumber("200");
        account.setBalance(10000);
        account.setAuthMethod(AuthMethod.PIN);
        account.setPin("1234");
        account.setBank(new Bank("BOG", "Georgia, Tbilisi", "110"));

        entityManager.persist(account);
    }


    @Test
    public void whenFindByCardNumber(){
//        Optional<Account> account = accountRepo.findAccountByCardNumber("200");
//        assertEquals(account.get().getPin(),"$2a$12$G44/zLcMW.LicOx232/opO3sTH73kKPpQS0XmaEBBBnpeCzBASENS");
    }

}
