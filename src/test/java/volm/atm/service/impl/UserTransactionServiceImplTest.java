package volm.atm.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import volm.atm.models.User;
import volm.atm.repos.UserRepo;
import volm.atm.repos.UserTransactionsRepo;
import volm.atm.security.Role;
import volm.atm.service.UserService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


//@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(value = {"classpath:create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"classpath:create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(value = {"classpath:create-user-transactions-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"classpath:create-user-transactions-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserTransactionServiceImplTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private UserTransactionsRepo userTransactionsRepo;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserTransactionServiceImpl userTransactionService;


    @Test
    void doTopUp() {

    }

    @Test
    void doWithdraw() {
        User us = new User(20L,
                "5555777788889999",
                "$2a$08$9u.wbQjYVqS4BhwTrihhq.sVrYkTVOq132rm1dacUaA7N0a8r/uXW",
                Collections.singletonList(Role.BANK_USER),
                BigDecimal.valueOf(777L));

        BigDecimal amount = BigDecimal.valueOf(100L);

        when(userRepo.findByCardNumberEquals(us.getCardNumber())).thenReturn(Optional.of(us));
//        doNothing().when(userRepo).save(us);
//        doNothing().when(userTransactionsRepo).save(any());

        ResponseEntity<HttpStatus> actual = userTransactionService.doWithdraw(us, amount);

        Assertions.assertEquals(HttpStatus.OK, actual.getStatusCode());

        verify(userRepo, times(1)).save(any());
        verify(userTransactionsRepo).save(any());
    }

    @Test
    void doMoneyTransfer() {
    }
}