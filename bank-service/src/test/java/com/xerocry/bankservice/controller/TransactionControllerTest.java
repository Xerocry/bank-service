package com.xerocry.bankservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xerocry.bankservice.dto.*;
import com.xerocry.bankservice.entity.Account;
import com.xerocry.bankservice.entity.Bank;
import com.xerocry.bankservice.repository.AccountRepo;
import com.xerocry.bankservice.service.AccountService;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountRepo accountRepo;
    private Account account = new Account();

    @BeforeEach
    void setup() throws IOException {
        account.setCardNumber("7164531182231184");
        account.setBalance(1111);
        account.setAuthMethod(AuthMethod.PIN);
        account.setPin("1234");
        account.setBank(new Bank("BOG", "Georgia, Tbilisi", "110"));
    }

    @After
    public void cleanUp() {
        accountRepo.delete(account);
    }

    @Test
    public void whenCheckBalance() throws Exception {
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setCardNumber("7164531182231184");
        transactionResponse.setAmount(10000);
        transactionResponse.setStatus(TransactionStatus.SUCCESS);
        transactionResponse.setType(TransactionType.DEPOSIT);
        transactionResponse.setTimestamp(LocalDate.now());

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/transactions/balance/7164531182231184")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        TransactionResponse response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TransactionResponse.class);
        assertEquals(response.getAmount(), account.getBalance());
    }

}
