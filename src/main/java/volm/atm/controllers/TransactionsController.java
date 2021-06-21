package volm.atm.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import volm.atm.controllers.dto.BalanceResponseDto;
import volm.atm.controllers.dto.TransactionsRequestDto;
import volm.atm.exceptions.EntityNotFoundException;
import volm.atm.models.User;
import volm.atm.models.UserTransactions;
import volm.atm.repos.UserRepo;
import volm.atm.repos.UserTransactionsRepo;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;


@RestController
//@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TransactionsController {

    private final UserRepo userRepo;
    private final UserTransactionsRepo userTransactionsRepo;


    @GetMapping("/get-balance")
    public ResponseEntity<BalanceResponseDto> getBalance(@AuthenticationPrincipal User user) {

        User userFromDB = userRepo.findByCardNumberEquals(user.getCardNumber())
                .orElseThrow(() -> new EntityNotFoundException(User.class));

        return ResponseEntity.ok(new BalanceResponseDto(userFromDB.getCardNumber(), userFromDB.getBalance()));
    }


    @PostMapping("/top-up-my-account")
    public ResponseEntity<UserTransactions> topUpMyAccount(@AuthenticationPrincipal User user,
                                                           @RequestBody TransactionsRequestDto requestDto) {

        User userFromDB = userRepo.findByCardNumberEquals(user.getCardNumber())
                .orElseThrow(() -> new EntityNotFoundException(User.class));

        userFromDB.setBalance(userFromDB.getBalance().add(requestDto.getAmount()));

        UserTransactions transaction = UserTransactions.builder()
                .userFrom(user)
                .userTo(user)
                .transactionTime(LocalDateTime.now())
                .amount(requestDto.getAmount())
                .build();

        userRepo.save(userFromDB);
        userTransactionsRepo.save(transaction);

        return ResponseEntity.ok(transaction);
    }


    @PostMapping("/top-up-someones-account")
    public ResponseEntity<UserTransactions> topUpSomeonesAccount(@AuthenticationPrincipal User user,
                                                                 String cardNumberToTopUp,
                                                                 BigDecimal amount) {
        User userToTopUp = userRepo.findByCardNumberEquals(cardNumberToTopUp)
                .orElseThrow(() -> new EntityNotFoundException(User.class));

        userToTopUp.setBalance(userToTopUp.getBalance().add(amount));

        UserTransactions transaction = UserTransactions.builder()
                .userFrom(user)
                .userTo(userToTopUp)
                .transactionTime(LocalDateTime.now())
                .amount(amount)
                .build();

        userRepo.save(userToTopUp);
        userTransactionsRepo.save(transaction);

        return ResponseEntity.ok(transaction);
    }


    @PostMapping("/money-transfer")
    public ResponseEntity<UserTransactions> moneyTransfer(@AuthenticationPrincipal User user,
                                                          String cardNumberToWhichTransfer,
                                                          BigDecimal amount) {

        User authUser = userRepo.findByCardNumberEquals(user.getCardNumber())
                .orElseThrow(() -> new EntityNotFoundException(User.class));
        User userToWhomTransfer = userRepo.findByCardNumberEquals(cardNumberToWhichTransfer)
                .orElseThrow(() -> new EntityNotFoundException(User.class));

        authUser.setBalance(authUser.getBalance().subtract(amount));
        userToWhomTransfer.setBalance(userToWhomTransfer.getBalance().add(amount));

        UserTransactions transaction = UserTransactions.builder()
                .userFrom(authUser)
                .userTo(userToWhomTransfer)
                .transactionTime(LocalDateTime.now())
                .amount(amount)
                .build();

        userRepo.save(authUser);
        userRepo.save(userToWhomTransfer);
        userTransactionsRepo.save(transaction);

        return ResponseEntity.ok(transaction);
    }


    @PostMapping("/withdrawal-of-funds")
    public ResponseEntity<UserTransactions> withdrawFunds(@AuthenticationPrincipal User user, BigDecimal amount) {

        User userFromDB = userRepo.findByCardNumberEquals(user.getCardNumber())
                .orElseThrow(() -> new EntityNotFoundException(User.class));

        if (userFromDB.getBalance().subtract(amount).compareTo(BigDecimal.valueOf(0)) >= 0) {

            userFromDB.setBalance(userFromDB.getBalance().subtract(amount));
            userRepo.save(userFromDB);

            UserTransactions transaction = UserTransactions.builder()
                    .userFrom(userFromDB)
                    .userTo(null)
                    .transactionTime(LocalDateTime.now())
                    .amount(amount)
                    .build();

            return ResponseEntity.ok(transaction);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}