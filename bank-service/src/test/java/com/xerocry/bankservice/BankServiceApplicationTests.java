package com.xerocry.bankservice;

import com.xerocry.bankservice.controller.TransactionController;
import com.xerocry.bankservice.entity.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class BankServiceApplicationTests {

    @Autowired
    private TransactionController userEntityController;


    @Test
    void contextLoads() {
        assertThat(userEntityController).isNotNull();
    }
}
