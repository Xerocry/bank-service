package com.xerocry.atmservice.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SecurityContext {
    private static SecurityContext ourInstance = new SecurityContext();
    private String token;
    private String cardNumber;

    public static SecurityContext getInstance() {
        return ourInstance;
    }

}
