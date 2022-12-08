package com.xerocry.atmservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable {
    private String cardNumber;
//    private int remainingAttempts = 3;
    private String pin;
/*
    public Boolean isLocked(){
        return remainingAttempts == 0;
    }*/
}