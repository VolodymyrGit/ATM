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

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(value = {"classpath:create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"classpath:create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void correctLogin() throws Exception {
        String strLoginRequest = "{\"cardNumber\":\"2222333344445555\",\"pinCode\":\"1111\"}";

        MockHttpServletRequestBuilder post = post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(strLoginRequest);

        MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8);

        mockMvc.perform(post)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(textPlainUtf8));
    }


    @Test
    void accessDeniedTest() throws Exception {
        mockMvc.perform(get("/get-balance"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


    @Test
    void notValidCardNumber() throws Exception {
        String strLoginRequest = "{\"cardNumber\":\"22223333444455557\",\"pinCode\":\"1111\"}";
        MockHttpServletRequestBuilder post = post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(strLoginRequest);
        mockMvc.perform(post)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    void wrongPinCode() throws Exception {
        String strLoginRequest = "{\"cardNumber\":\"2222333344445555\",\"pinCode\":\"7111\"}";
        MockHttpServletRequestBuilder post = post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(strLoginRequest);
        mockMvc.perform(post)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}