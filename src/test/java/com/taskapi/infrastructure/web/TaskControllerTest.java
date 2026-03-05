package com.taskapi.infrastructure.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = com.taskapi.infrastructure.TaskApiApplication.class)
@AutoConfigureMockMvc
public final class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createTaskReturns201() throws Exception {
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Learn REST\", \"description\": \"Build API\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Learn REST"))
                .andExpect(jsonPath("$.description").value("Build API"))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.identifier").exists());
    }

    @Test
    void createTaskWithBlankTitleReturns400() throws Exception {
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"\", \"description\": \"Build API\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void listTasksReturnsEmptyListInitially() throws Exception {
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk());
    }

    @Test
    void startTaskChangesStatus() throws Exception {
        MvcResult createResult = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Test task\", \"description\": \"For starting\"}"))
                .andExpect(status().isCreated())
                .andReturn();

        String identifier = extractIdentifier(createResult.getResponse().getContentAsString());

        mockMvc.perform(put("/tasks/" + identifier + "/start"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    }

    @Test
    void startNonExistentTaskReturns404() throws Exception {
        mockMvc.perform(put("/tasks/nonexistent/start"))
                .andExpect(status().isNotFound());
    }

    @Test
    void completeTaskChangesStatus() throws Exception {
        MvcResult createResult = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Test task\", \"description\": \"For completion\"}"))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = createResult.getResponse().getContentAsString();
        String identifier = extractIdentifier(responseBody);

        mockMvc.perform(put("/tasks/" + identifier + "/complete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    @Test
    void completeNonExistentTaskReturns404() throws Exception {
        mockMvc.perform(put("/tasks/nonexistent/complete"))
                .andExpect(status().isNotFound());
    }

    @Test
    void removeTaskReturnsSuccess() throws Exception {
        MvcResult createResult = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"To remove\", \"description\": \"Will be removed\"}"))
                .andExpect(status().isCreated())
                .andReturn();

        String identifier = extractIdentifier(createResult.getResponse().getContentAsString());

        mockMvc.perform(delete("/tasks/" + identifier))
                .andExpect(status().isOk());
    }

    @Test
    void removeNonExistentTaskReturns404() throws Exception {
        mockMvc.perform(delete("/tasks/nonexistent"))
                .andExpect(status().isNotFound());
    }

    private String extractIdentifier(String jsonResponse) {
        int startIndex = jsonResponse.indexOf("\"identifier\":\"") + 14;
        int endIndex = jsonResponse.indexOf("\"", startIndex);
        return jsonResponse.substring(startIndex, endIndex);
    }
}