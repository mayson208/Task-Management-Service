package com.example.task.api.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskQueryIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnPagedTasks() throws Exception {
        mockMvc.perform(
                        post("/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                    { "title": "Task A" }
                                """)
                )
                .andExpect(status().isOk());

        mockMvc.perform(
                        get("/tasks")
                                .param("page", "0")
                                .param("size", "5")
                )
                .andExpect(status().isOk());
    }
}
