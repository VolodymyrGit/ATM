package volm.atm.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(value = {"classpath:create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"classpath:create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void okRegistration() throws Exception {
        String strRegistrationRequest = "{\"cardNumber\":\"4444555566667777\",\"pinCode\":\"1111\"}";
        MockHttpServletRequestBuilder post = post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(strRegistrationRequest);
        mockMvc.perform(post)
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void CardNumberAlreadyExist() throws Exception {
        String strRegistrationRequest = "{\"cardNumber\":\"2222333344445555\",\"pinCode\":\"1111\"}";
        MockHttpServletRequestBuilder post = post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(strRegistrationRequest);
        mockMvc.perform(post)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}