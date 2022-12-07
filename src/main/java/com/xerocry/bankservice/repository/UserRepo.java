package com.xerocry.bankservice.repository;

import com.xerocry.bankservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = "SELECT * from users WHERE account_id = ?1")
    User findUserById(Long id);
}
