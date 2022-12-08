package com.xerocry.bankservice.repository;

import com.xerocry.bankservice.entity.Account;
import com.xerocry.bankservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    @Query(value = "SELECT * FROM transactions WHERE account_id = ?1", nativeQuery = true)
    Transaction findTransactionById(Account account);

}

