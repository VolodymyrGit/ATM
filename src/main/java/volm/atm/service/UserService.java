package volm.atm.service;

import org.springframework.stereotype.Service;
import volm.atm.models.User;

import java.math.BigDecimal;


@Service
public interface UserService {

    boolean checkIfEnoughMoney(User user, BigDecimal amountToSubtract);

    User getUser(String cardNumber) throws RuntimeException;
}
