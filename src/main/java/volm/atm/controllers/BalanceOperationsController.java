//package volm.atm.controllers;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//import volm.atm.exceptions.EntityNotFoundException;
//import volm.atm.models.User;
//import volm.atm.models.UserTransactions;
//import volm.atm.repos.UserRepo;
//import volm.atm.repos.UserTransactionsRepo;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//
//@RestController
//@RequiredArgsConstructor
//public class BalanceOperationsController {
//
//    private final UserRepo userRepo;
//    private final UserTransactionsRepo userTransactionsRepo;
//
//    @PostMapping("/top-up-my-account")
//    public ResponseEntity<UserTransactions> topUpMyAccount(@AuthenticationPrincipal User user, BigDecimal amount) {
//
//        User userFromDB = userRepo.findByCardNumberEquals(user.getCardNumber())
//                .orElseThrow(() -> new EntityNotFoundException(User.class));
//
//        userFromDB.setBalance(userFromDB.getBalance().add(amount));
//
//        UserTransactions transaction = UserTransactions.builder()
//                .userFrom(user)
//                .userTo(user)
//                .transactionTime(LocalDateTime.now())
//                .amount(amount)
//                .build();
//
//        userRepo.save(userFromDB);
//        userTransactionsRepo.save(transaction);
//
//        return ResponseEntity.ok(transaction);
//    }
//}
