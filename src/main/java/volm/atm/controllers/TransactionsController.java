package volm.atm.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import volm.atm.dto.UserBalanceResponseDto;
import volm.atm.dto.TransactionDto;
import volm.atm.dto.TransactionsRequestDto;
import volm.atm.mappers.UserMapper;
import volm.atm.mappers.UserTransactionsMapper;
import volm.atm.models.User;
import volm.atm.models.UserTransactions;
import volm.atm.repos.UserTransactionsRepo;
import volm.atm.service.UserService;
import volm.atm.service.UserTransactionService;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class TransactionsController {

    private final UserService userService;
    private final UserTransactionsRepo userTransactionsRepo;
    private final UserTransactionService userTransactionService;
    private final UserTransactionsMapper userTransactionsMapper;
    private final UserMapper userMapper;


    @GetMapping("/get-balance")
    public ResponseEntity<UserBalanceResponseDto> getBalance(@AuthenticationPrincipal User user) {

        User userFromDB = userService.getUser(user.getCardNumber());

        return ResponseEntity.ok(userMapper.toUserBalanceResponseDto(userFromDB));
    }


    @GetMapping("/get-transactions")
    public ResponseEntity<List<TransactionDto>> getTransactions(@AuthenticationPrincipal User user) {

        List<UserTransactions> allUserTransactions =
                userTransactionsRepo.findAllByUserFromEqualsOrUserToEquals(user, user);

        List<TransactionDto> allTransactionsDto = userTransactionsMapper.toListTransactionDto(allUserTransactions);

        return ResponseEntity.ok(allTransactionsDto);
    }


    @PostMapping("/top-up-my")
    public ResponseEntity<HttpStatus> topUpMy(@AuthenticationPrincipal User user,
                                              @RequestBody @Valid TransactionsRequestDto requestDto) {

        userTransactionService.doTopUp(user, null, requestDto.getAmount());

        return ResponseEntity.ok().build();
    }


    @PostMapping("/top-up-someones")
    public ResponseEntity<HttpStatus> topUpSomeones(@AuthenticationPrincipal User user,
                                                    @RequestBody @Valid TransactionsRequestDto requestDto) {

        userTransactionService.doTopUp(user, requestDto.getCardNumber(), requestDto.getAmount());

        return ResponseEntity.ok().build();
    }


    @PostMapping("/money-transfer")
    public ResponseEntity<HttpStatus> moneyTransfer(@AuthenticationPrincipal User user,
                                                    @RequestBody() @Valid TransactionsRequestDto requestDto) {

        return userTransactionService.doMoneyTransfer(user, requestDto.getCardNumber(), requestDto.getAmount());
    }


    @PostMapping("/withdraw-money")
    public ResponseEntity<HttpStatus> withdrawMoney(@AuthenticationPrincipal User user,
                                                    @RequestBody @Valid TransactionsRequestDto requestDto) {

        return userTransactionService.doWithdraw(user, requestDto.getAmount());
    }
}