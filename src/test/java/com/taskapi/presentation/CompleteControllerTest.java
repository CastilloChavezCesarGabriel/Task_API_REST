package com.taskapi.presentation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = com.taskapi.TaskApiApplication.class)
@AutoConfigureMockMvc
public final class CompleteControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testCompleteChangesStatus() throws Exception {
        MvcResult createResult = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Test task\", \"description\": \"For completion\"}"))
                .andExpect(status().isCreated())
                .andReturn();

        String identifier = extractIdentifier(createResult);

        mockMvc.perform(put("/tasks/" + identifier + "/complete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    @Test
    void testCompleteNonExistentReturns404() throws Exception {
        mockMvc.perform(put("/tasks/nonexistent/complete"))
                .andExpect(status().isNotFound());
    }

    private String extractIdentifier(MvcResult result) throws Exception {
        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode node = objectMapper.readTree(jsonResponse);
        return node.path("identifier").asText();
    }
}