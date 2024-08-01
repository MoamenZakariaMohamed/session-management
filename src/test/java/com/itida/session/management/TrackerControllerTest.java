package com.itida.session.management;

import com.itida.session.management.auth.domain.User;
import com.itida.session.management.infrastructure.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
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

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@ContextConfiguration(classes = TestSessionApplication.class)
@Import(TestContainerConfig.class)
@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/sql/clean.sql" })
@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/sql/init.sql" })
public class TrackerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtTokenProvider;

    private String token;


    @BeforeEach
    public void init () {
        User user = User.builder().name("userName").mobileNumber("01001237892").email("user@gmail.com").build();
        token = jwtTokenProvider.generateToken(user);
    }


    @Test
    public void testGetUserHistory() throws Exception {
        mockMvc.perform(
                        get("/api/v1/trackers")
                                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUserHistoryInValidToken() throws Exception {
        mockMvc.perform(
                        get("/api/v1/trackers")
                                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYW9hbWVuemFrYXJpYTk5QGdtYWlsLmNvbSIsImlhdCI6MTcyMjQ5NjMyNiwiZXhwIjoxNzIyNDk2NjI2fQ.cyvcCnBWMI9UGXtv4Jkx4vuYQsX9LJHdm2kb7iVR5zU"))
                .andExpect(status().isUnauthorized());
    }
    @Test
    public void testGetUserNotFoundHistory() throws Exception {
        mockMvc.perform(
                        get("/api/v1/trackers/1")
                                .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetUserHistoryById() throws Exception {
        mockMvc.perform(
                        get("/api/v1/trackers/100")
                                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
    @Test
    public void successfullyAdded()throws Exception{
        mockMvc.perform(
                        post("/api/v1/trackers")
                                .header("Authorization", "Bearer " + token)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(validRequest()))
                .andExpect(status().isCreated());

    }


    @Test
    public void failedAdded()throws Exception{
        mockMvc.perform(
                        post("/api/v1/trackers")
                                .header("Authorization", "Bearer " + token)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(inValidRequest()))
                .andExpect(status().isBadRequest());

    }

    private String inValidRequest(){
        return "{ \"date\": \"" + LocalDate.now() + "\", \"loginTime\": \"" + "12:00:00" + "\", \"logoutTime\": \"" + "10:00:00" + "\"}";
    }

    private String validRequest(){
        return "{ \"date\": \"" + LocalDate.now() + "\", \"loginTime\": \"" + LocalTime.now().minusHours(1) + "\", \"logoutTime\": \"" + LocalTime.now().plusHours(1) + "\"}";
    }
}
