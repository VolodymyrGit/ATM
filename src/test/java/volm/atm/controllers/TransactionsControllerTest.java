package volm.atm.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
//@RunWith(SpringRunner.class)
//@WebMvcTest(value = TransactionsController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(value = {"classpath:create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"classpath:create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(value = {"classpath:create-user-transactions-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"classpath:create-user-transactions-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class TransactionsControllerTest {

    private static String token;

    @Autowired
    private MockMvc mockMvc;


    private void getToken() throws Exception {
        String strLoginRequestJSON = "{\"cardNumber\":\"2222333344445555\",\"pinCode\":\"1111\"}";

        MockHttpServletRequestBuilder post = post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(strLoginRequestJSON);

        token = ("Bearer " + mockMvc.perform(post)
                .andReturn().getResponse().getContentAsString());
    }


    @Test
    void successGetBalance() throws Exception {
        getToken();
        MockHttpServletRequestBuilder get = get("/get-balance").header("Authorization", token);

        mockMvc.perform(get)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void successGetTransactions() throws Exception {
        getToken();
        MockHttpServletRequestBuilder get = get("/get-transactions").header("Authorization", token);

        mockMvc.perform(get)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void successTopUpMy() throws Exception {
        getToken();

        String TopUpMyRequestJSON = "{\"amount\":\"50\"}";

        MockHttpServletRequestBuilder post = post("/top-up-my").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TopUpMyRequestJSON);

        mockMvc.perform(post)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void successTopUpSomeones() throws Exception {
        getToken();

        String TopUpSomeonesRequestJSON = "{\"cardNumber\":\"3333444455556666\",\"amount\":\"50\"}";

        MockHttpServletRequestBuilder post = post("/top-up-someones").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TopUpSomeonesRequestJSON);

        mockMvc.perform(post)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void successMoneyTransfer() throws Exception {
        getToken();

        String MoneyTransferRequestJSON = "{\"cardNumber\":\"1111222233334444\",\"amount\":\"10\"}";

        MockHttpServletRequestBuilder post = post("/money-transfer").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MoneyTransferRequestJSON);

        mockMvc.perform(post)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void successWithdrawMoney() throws Exception {
        getToken();

        String MoneyTransferRequestJSON = "{\"amount\":\"99\"}";

        MockHttpServletRequestBuilder post = post("/withdraw-money").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MoneyTransferRequestJSON);

        mockMvc.perform(post)
                .andDo(print())
                .andExpect(status().isOk());
    }
}