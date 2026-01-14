package com.example.task.api.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskValidationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRejectBlankTitle() throws Exception {
        mockMvc.perform(
                        post("/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                    {
                                      "title": ""
                                    }
                                """)
                )
                .andExpect(status().isBadRequest());
    }
}
