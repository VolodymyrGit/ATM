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
import volm.atm.service.UserTransactionService;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class TransactionsController {

    private final UserRepo userRepo;
    private final UserTransactionsRepo userTransactionsRepo;
    private final UserTransactionService userTransactionService;


    @GetMapping("/get-balance")
    public ResponseEntity<BalanceResponseDto> getBalance(@AuthenticationPrincipal User user) {

        User userFromDB = userRepo.findByCardNumberEquals(user.getCardNumber())
                .orElseThrow(() -> new EntityNotFoundException(User.class));

        return ResponseEntity.ok(new BalanceResponseDto(userFromDB.getCardNumber(), userFromDB.getBalance()));
    }


    @GetMapping("/get-transactions")
    public ResponseEntity<List<UserTransactions>> getTransactions(@AuthenticationPrincipal User user) {

        List<UserTransactions> allUserTransactions =
                userTransactionsRepo.findAllByUserFromEqualsOrUserToEquals(user, user);

        return ResponseEntity.ok(allUserTransactions);
    }


    @PostMapping("/top-up-my")
    public ResponseEntity<HttpStatus> topUpMy(@AuthenticationPrincipal User user,
                                              @RequestBody TransactionsRequestDto requestDto) {

        return userTransactionService.doTopUp(user, null, requestDto.getAmount());
    }


    @PostMapping("/top-up-someones")
    public ResponseEntity<HttpStatus> topUpSomeones(@AuthenticationPrincipal User user,
                                                          @RequestBody TransactionsRequestDto requestDto) {

        return userTransactionService.doTopUp(user, requestDto.getCardNumber(), requestDto.getAmount());
    }


    @PostMapping("/money-transfer")
    public ResponseEntity<HttpStatus> moneyTransfer(@AuthenticationPrincipal User user,
                                                    @RequestBody TransactionsRequestDto requestDto) {

        return userTransactionService.doMoneyTransfer(user, requestDto.getCardNumber(), requestDto.getAmount());
    }


    @PostMapping("/withdraw-money")
    public ResponseEntity<HttpStatus> withdrawMoney(@AuthenticationPrincipal User user,
                                                    @RequestBody TransactionsRequestDto requestDto) {

        return userTransactionService.doWithdraw(user, requestDto.getAmount());
    }
}