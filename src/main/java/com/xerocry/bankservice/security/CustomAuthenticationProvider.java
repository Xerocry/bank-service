package com.xerocry.bankservice.security;

import com.xerocry.bankservice.entity.Account;
import com.xerocry.bankservice.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private AccountRepo accountRepo;

    @Override
    public Authentication authenticate(Authentication authentication) {

        String cardNumber = authentication.getName();
        String auth = authentication.getCredentials().toString();

        if (Objects.nonNull(cardNumber) && Objects.nonNull(auth)) {
            Account account = accountRepo.findAccountByCardNumber(cardNumber);
            if (account.getRemainingAttempts() == 0 || !account.isAccountNonLocked()) {
                throw new LockedException("Account is Blocked!");
            }
//            if (!account.getAuthValue().equals(auth)) {
//                card.setNumberOfLoginTries((byte) (card.getNumberOfLoginTries() + 1));
//                if (card.getNumberOfLoginTries() == 3)
//                    card.setCardStatus(CardStatus.BLOCKED);
//                accountRepo.save(account);
//                throw new BadCredentialsException("Wrong authentication parameter. You have " + (3 - account.getRemainingAttempts()) + " more tries left.");
//            }
            return new UsernamePasswordAuthenticationToken(
                    cardNumber, auth, new ArrayList<>());
        } else {
            throw new BadCredentialsException("Card number or authentication parameter not provided.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
