package com.xerocry.bankservice.repository;

import com.xerocry.bankservice.entity.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerminalRepo extends JpaRepository<Terminal, Long> {
}
