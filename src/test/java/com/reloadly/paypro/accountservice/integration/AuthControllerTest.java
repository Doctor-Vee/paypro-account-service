package com.reloadly.paypro.accountservice.integration;

import com.reloadly.paypro.accountservice.payload.request.LoginRequest;
import com.reloadly.paypro.accountservice.payload.request.SignupRequest;
import com.reloadly.paypro.accountservice.persistence.model.User;
import com.reloadly.paypro.accountservice.persistence.repository.UserRepository;
import com.reloadly.paypro.accountservice.utils.JsonUtil;
import com.reloadly.paypro.accountservice.utils.TestModels;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    private String signupPath = "/auth/signup";
    private String loginPath = "/auth/login";

    private HttpHeaders headers;

    private HttpEntity<String> request;

    @BeforeAll
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        this.seedData();
    }

    @BeforeEach
    public void runBeforeAllTestMethods() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void shouldSignupSuccessfully() throws Exception {
        SignupRequest signupRequest = TestModels.createSignupRequest();
        this.mockMvc
                .perform(MockMvcRequestBuilders.post(signupPath).content(JsonUtil.toJsonString(signupRequest))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201)).andExpect(content().string("Congratulations, your account number is " + signupRequest.getPhoneNumber().substring(1)));
    }

    @Test
    void allowUserLoginWithValidCredentials() throws Exception {
        LoginRequest loginRequest = new LoginRequest("newuser1234", "newuser1234");

        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.post(loginPath).content(JsonUtil.toJsonString(loginRequest))
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.token").isString());
    }

    @Test
    void shouldThrowErrorWhenUserTriesToLoginWithInvalidCredentials() throws Exception {
        LoginRequest loginRequest = new LoginRequest("newuser1234", "fakepass1234");

        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.post(loginPath).content(JsonUtil.toJsonString(loginRequest))
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andExpect(content().string("Invalid username or password"));
    }


    void seedData() {

        User user = new User("newuser1234@gmail.com", "newuser1234", passwordEncoder.encode("newuser1234"), "08012341234", "0001234567");
        this.userRepository.save(user);

    }


}
