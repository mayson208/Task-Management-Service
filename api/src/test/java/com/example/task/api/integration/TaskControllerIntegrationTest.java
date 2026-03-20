package com.example.task.api.integration;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TaskControllerIntegrationTest extends BaseIntegrationTest {

    private String createTask(String title) throws Exception {
        String body = mockMvc.perform(
                        post("/api/v1/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"title\": \"" + title + "\"}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        JsonNode node = objectMapper.readTree(body);
        return node.get("id").asText();
    }

    @Test
    void shouldCreateAndCompleteTask() throws Exception {
        String taskId = createTask("Integration Test Task");

        mockMvc.perform(post("/api/v1/tasks/{id}/complete", taskId))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateTask_withValidBody_returns204() throws Exception {
        String taskId = createTask("Original Title");

        mockMvc.perform(patch("/api/v1/tasks/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Updated Title\"}"))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateTask_withEmptyBody_returns400() throws Exception {
        String taskId = createTask("Some Task");

        mockMvc.perform(patch("/api/v1/tasks/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteTask_returns204() throws Exception {
        String taskId = createTask("Task to Delete");

        mockMvc.perform(delete("/api/v1/tasks/{id}", taskId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTask_whenNotFound_returns404() throws Exception {
        mockMvc.perform(delete("/api/v1/tasks/{id}", "00000000-0000-0000-0000-000000000000"))
                .andExpect(status().isNotFound());
    }

    @Test
    void startTask_returns204() throws Exception {
        String taskId = createTask("Task to Start");

        mockMvc.perform(post("/api/v1/tasks/{id}/start", taskId))
                .andExpect(status().isNoContent());
    }

    @Test
    void startTask_whenAlreadyCompleted_returns409() throws Exception {
        String taskId = createTask("Task to Complete then Start");

        mockMvc.perform(post("/api/v1/tasks/{id}/complete", taskId))
                .andExpect(status().isNoContent());

        mockMvc.perform(post("/api/v1/tasks/{id}/start", taskId))
                .andExpect(status().isConflict());
    }

    @Test
    void completeTask_returns204() throws Exception {
        String taskId = createTask("Task to Complete");

        mockMvc.perform(post("/api/v1/tasks/{id}/complete", taskId))
                .andExpect(status().isNoContent());
    }
}
