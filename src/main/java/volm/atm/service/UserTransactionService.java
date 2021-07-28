package volm.atm.service;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import volm.atm.models.User;

import java.math.BigDecimal;


@Service
public interface UserTransactionService {

    void doTopUp(User user, String cardNumber, BigDecimal amount);

    ResponseEntity<HttpStatus> doWithdraw(User user, BigDecimal amount);

    ResponseEntity<HttpStatus> doMoneyTransfer(User user, String cardNumber, BigDecimal amount);
}
