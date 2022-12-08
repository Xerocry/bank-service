package com.xerocry.bankservice.repository;

import com.xerocry.bankservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {
    @Query(nativeQuery = true, value = "SELECT * from accounts WHERE card_number = ?1")
    Optional<Account> findAccountByCardNumber(String cardNumber);
}
