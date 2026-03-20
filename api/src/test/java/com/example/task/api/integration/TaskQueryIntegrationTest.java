package com.example.task.api.integration;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TaskQueryIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldReturnPagedTasks() throws Exception {
        mockMvc.perform(
                        post("/api/v1/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{ \"title\": \"Task A\" }")
                )
                .andExpect(status().isCreated());

        mockMvc.perform(
                        get("/api/v1/tasks")
                                .param("page", "0")
                                .param("size", "5")
                )
                .andExpect(status().isOk());
    }
}
