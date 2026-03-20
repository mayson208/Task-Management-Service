package com.example.task.api.integration;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TaskValidationIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldRejectBlankTitle() throws Exception {
        mockMvc.perform(
                        post("/api/v1/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"title\": \"\"}")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void createTask_returnsAllValidationErrors() throws Exception {
        String longTitle = "a".repeat(101);
        mockMvc.perform(
                        post("/api/v1/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"title\": \"" + longTitle + "\"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(greaterThan(0))));
    }

    @Test
    void getTasks_withNegativePage_returns400() throws Exception {
        mockMvc.perform(get("/api/v1/tasks").param("page", "-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getTasks_withOversizedPage_returns400() throws Exception {
        mockMvc.perform(get("/api/v1/tasks").param("size", "200"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getTasks_withZeroSize_returns400() throws Exception {
        mockMvc.perform(get("/api/v1/tasks").param("size", "0"))
                .andExpect(status().isBadRequest());
    }
}
