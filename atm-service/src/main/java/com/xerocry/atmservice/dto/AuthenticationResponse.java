package com.xerocry.atmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationResponse implements Serializable {
    private String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }
}
