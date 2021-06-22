package volm.atm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import volm.atm.exceptions.EntityNotFoundException;
import volm.atm.models.OperationType;
import volm.atm.models.User;
import volm.atm.models.UserTransactions;
import volm.atm.repos.UserRepo;
import volm.atm.repos.UserTransactionsRepo;
import volm.atm.service.UserService;
import volm.atm.service.UserTransactionService;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class UserTransactionServiceImpl implements UserTransactionService {

    private final UserRepo userRepo;
    private final UserTransactionsRepo userTransactionsRepo;
    private final UserService userService;


    @Override
    public ResponseEntity<HttpStatus> doTopUp(User user, String cardNumber, BigDecimal amount) {

        User authUserFromDB = userRepo.findByCardNumberEquals(user.getCardNumber())
                .orElseThrow(() -> new EntityNotFoundException(User.class));

        if (cardNumber == null || user.getCardNumber().equals(cardNumber)) {

            authUserFromDB.setBalance(authUserFromDB.getBalance().add(amount));
            saveTransaction(authUserFromDB, authUserFromDB, amount, OperationType.TOP_UP_MY);

        } else {
            User userForTransfer = userRepo.findByCardNumberEquals(cardNumber)
                    .orElseThrow(() -> new EntityNotFoundException(User.class));

            userForTransfer.setBalance(userForTransfer.getBalance().add(amount));
            saveTransaction(authUserFromDB, userForTransfer, amount, OperationType.TOP_UP_SOMEONES);
        }
        return ResponseEntity.ok().build();
    }


    @Override
    public ResponseEntity<HttpStatus> doWithdraw(User user, BigDecimal amount) {

        User authUserFromDB = userRepo.findByCardNumberEquals(user.getCardNumber())
                .orElseThrow(() -> new EntityNotFoundException(User.class));

        if (authUserFromDB.getBalance().subtract(amount).compareTo(BigDecimal.valueOf(0)) >= 0) {

            authUserFromDB.setBalance(authUserFromDB.getBalance().subtract(amount));
            saveTransaction(authUserFromDB, null, amount, OperationType.WITHDRAW_MONEY);

            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


    @Override
    public ResponseEntity<HttpStatus> doMoneyTransfer(User user, String cardNumber, BigDecimal amount) {

        User authUserFromDB = userRepo.findByCardNumberEquals(user.getCardNumber())
                .orElseThrow(() -> new EntityNotFoundException(User.class));
        User userForTransfer = userRepo.findByCardNumberEquals(cardNumber)
                .orElseThrow(() -> new EntityNotFoundException(User.class));

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
                .userFrom(authUserFromDB)
                .userTo(userForTransfer)
                .transactionTime(LocalDateTime.now())
                .amount(amount)
                .operationType(operationType)
                .build();

        userRepo.save(authUserFromDB);
        userTransactionsRepo.save(transaction);

        if (userForTransfer != null && !authUserFromDB.getCardNumber().equals(userForTransfer.getCardNumber())) {
            userRepo.save(userForTransfer);
        }
    }
}
