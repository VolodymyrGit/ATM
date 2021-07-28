package volm.atm.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import volm.atm.models.OperationType;
import volm.atm.models.User;
import volm.atm.models.UserTransactions;
import volm.atm.repos.UserRepo;
import volm.atm.repos.UserTransactionsRepo;
import volm.atm.service.UserService;
import volm.atm.service.UserTransactionService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserTransactionServiceImpl implements UserTransactionService {

    private final UserRepo userRepo;
    private final UserTransactionsRepo userTransactionsRepo;
    private final UserService userService;


    @Override
    public void doTopUp(User user, String cardNumber, BigDecimal amount) {

        User authUserFromDB = userService.getUser(user.getCardNumber());

        if (cardNumber == null || user.getCardNumber().equals(cardNumber)) {

            authUserFromDB.setBalance(authUserFromDB.getBalance().add(amount));
            saveTransaction(authUserFromDB, authUserFromDB, amount, OperationType.TOP_UP_MY);

        } else {
            User userForTransfer = userService.getUser(cardNumber);
            userForTransfer.setBalance(userForTransfer.getBalance().add(amount));
            saveTransaction(authUserFromDB, userForTransfer, amount, OperationType.TOP_UP_SOMEONES);
        }
    }


    @Override
    public ResponseEntity<HttpStatus> doWithdraw(User user, BigDecimal amount) {

        User authUserFromDB = userService.getUser(user.getCardNumber());

        if (authUserFromDB.getBalance().subtract(amount).compareTo(BigDecimal.valueOf(0)) >= 0) {

            authUserFromDB.setBalance(authUserFromDB.getBalance().subtract(amount));
            saveTransaction(authUserFromDB, null, amount, OperationType.WITHDRAW_MONEY);

            return ResponseEntity.ok().build();
        }
        //        Повертати воід або Exception
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


    @Override
    public ResponseEntity<HttpStatus> doMoneyTransfer(User user, String cardNumber, BigDecimal amount) {

        User authUserFromDB = userService.getUser(user.getCardNumber());
        User userForTransfer = userService.getUser(cardNumber);

        if (userService.checkIfEnoughMoney(authUserFromDB, amount)) {

            authUserFromDB.setBalance(authUserFromDB.getBalance().subtract(amount));
        } else {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        userForTransfer.setBalance(userForTransfer.getBalance().add(amount));

        saveTransaction(authUserFromDB, userForTransfer, amount, OperationType.MONEY_TRANSFER);

        return ResponseEntity.ok().build();
    }


    private void saveTransaction(User authUserFromDB,
                                 User userForTransfer,
                                 BigDecimal amount,
                                 OperationType operationType) {

        UserTransactions transaction = UserTransactions.builder()
                .operationType(operationType)
                .amount(amount)
                .userFrom(authUserFromDB)
                .userTo(userForTransfer)
                .transactionTime(LocalDateTime.now())
                .build();

        userRepo.save(authUserFromDB);
        userTransactionsRepo.save(transaction);

        if (userForTransfer != null && !authUserFromDB.getCardNumber().equals(userForTransfer.getCardNumber())) {
            userRepo.save(userForTransfer);
        }
    }
}
