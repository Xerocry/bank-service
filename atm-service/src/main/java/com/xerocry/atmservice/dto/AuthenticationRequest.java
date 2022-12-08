package com.xerocry.atmservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class AuthenticationRequest implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;
    private String cardNumber;
    private String password;
}
