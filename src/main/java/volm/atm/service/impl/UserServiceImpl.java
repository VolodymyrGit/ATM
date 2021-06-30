package volm.atm.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import volm.atm.exceptions.UserNotFoundException;
import volm.atm.models.User;
import volm.atm.repos.UserRepo;
import volm.atm.service.UserService;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;


    @Override
    public boolean checkIfEnoughMoney(User user, BigDecimal amountToSubtract) {

        return user.getBalance().subtract(amountToSubtract).compareTo(BigDecimal.valueOf(0)) >= 0;
    }


    public User getUser(String cardNumber) throws RuntimeException {
        return userRepo.findByCardNumberEquals(cardNumber)
                .orElseThrow(() -> new UserNotFoundException(cardNumber));
    }
}
