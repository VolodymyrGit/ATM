package volm.atm.service;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import volm.atm.models.User;
import volm.atm.models.UserTransactions;

import java.math.BigDecimal;


@Service
public interface UserTransactionService {

    ResponseEntity<HttpStatus> doTopUp(User user, String cardNumber, BigDecimal amount);

    ResponseEntity<HttpStatus> doWithdraw(User user, BigDecimal amount);

    ResponseEntity<HttpStatus> doMoneyTransfer(User user, String cardNumber, BigDecimal amount);
}
