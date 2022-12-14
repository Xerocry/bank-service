package com.xerocry.bankservice.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class AuthenticationResponse implements Serializable {
    private final String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }
}
