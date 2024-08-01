package com.itida.session.management;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itida.session.management.auth.domain.AuthRequest;
import com.itida.session.management.auth.domain.SignUpRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@ContextConfiguration(classes = TestSessionApplication.class)
@Import(TestContainerConfig.class)
@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/sql/clean.sql" })
@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/sql/init.sql" })
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void register() throws Exception {
        mockMvc.perform(
                        post("/api/v1/auth/register")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body("test user",
                                        "dummymail@gmail.com","password","01211339434")))
                .andExpect(status().isOk());
    }

    @Test
    public void registerWithExistingEmail() throws Exception {
        mockMvc.perform(
                        post("/api/v1/auth/register")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body("test user",
                                        "user@gmail.com","password","01211339434")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void registerWithExistingMobile() throws Exception {
        mockMvc.perform(
                        post("/api/v1/auth/register")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body("dummy",
                                        "test@yahoo.com","dummy","01001237892")))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void registerWithInvalidRequest() throws Exception {
        mockMvc.perform(
                        post("/api/v1/auth/register")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body("dummy",
                                        null,"dummy",null)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void loginTest() throws Exception {
        mockMvc.perform(
                        post("/api/v1/auth/login")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(login("dummyUser@hotmail.com","testUser@0091" )))
                .andExpect(status().isOk());
    }


    @Test
    public void inValidloginTest() throws Exception {
        mockMvc.perform(
                        post("/api/v1/auth/login")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(login("dummyUser@hotmail.com","testUse091" )))
                .andExpect(status().isUnauthorized());
    }
    private String body(String name , String mail , String password , String mobile) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(new SignUpRequest(name, mail,password,mobile));
    }
    private String login ( String mail , String password ) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(new AuthRequest(mail,password));
    }
}
