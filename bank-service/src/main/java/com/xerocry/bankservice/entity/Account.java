package com.xerocry.bankservice.entity;

import javax.persistence.*;

import com.xerocry.bankservice.dto.AuthMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;
    @OneToOne(mappedBy = "account")
    private User user;
    @OneToMany(mappedBy = "account")
    private Set<Transaction> transactions;
    private String cardNumber;
    private LocalDate expireDate;
    private double balance;
    private int remainingAttempts = 3;
    private String pin;
    private AuthMethod authMethod;

    public Boolean isLocked(){
        return remainingAttempts == 0;
    }
}
