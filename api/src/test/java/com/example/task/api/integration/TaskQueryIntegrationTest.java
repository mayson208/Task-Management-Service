package com.example.task.api.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TaskQueryIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnPagedTasks() throws Exception {
        mockMvc.perform(
                        post("/api/v1/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                    { "title": "Task A" }
                                """)
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
