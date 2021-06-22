package volm.atm.service;

import org.springframework.stereotype.Service;
import volm.atm.models.User;

import java.math.BigDecimal;
import java.util.Optional;


@Service
public interface UserService {

    Optional<User> findByLoginAndPassword(String login, String password);

    boolean checkIfEnoughMoney(User user, BigDecimal amountToSubtract);
}
