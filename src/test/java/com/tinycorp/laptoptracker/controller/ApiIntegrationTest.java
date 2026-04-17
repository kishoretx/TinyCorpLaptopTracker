package com.tinycorp.laptoptracker.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class ApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRejectProtectedEndpointWithoutToken() throws Exception {
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    void managerShouldAccessReports() throws Exception {
        String token = loginAndGetToken("manager", "manager123");

        mockMvc.perform(get("/api/v1/reports/average-device-age")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void employeeShouldNotAccessReports() throws Exception {
        String token = loginAndGetToken("employee", "employee123");

        mockMvc.perform(get("/api/v1/reports/average-device-age")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    private String loginAndGetToken(String username, String password) throws Exception {
        String requestBody = objectMapper.writeValueAsString(Map.of("username", username, "password", password));
        MvcResult result = mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();
        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        return jsonNode.get("token").asText();
    }
}
