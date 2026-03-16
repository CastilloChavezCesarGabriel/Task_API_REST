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
public final class RemoveControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testRemoveReturnsSuccess() throws Exception {
        MvcResult createResult = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"To remove\", \"description\": \"Will be removed\"}"))
                .andExpect(status().isCreated())
                .andReturn();

        String identifier = extractIdentifier(createResult);

        mockMvc.perform(delete("/tasks/" + identifier))
                .andExpect(status().isOk());
    }

    @Test
    void testRemoveNonExistentReturns404() throws Exception {
        mockMvc.perform(delete("/tasks/nonexistent"))
                .andExpect(status().isNotFound());
    }

    private String extractIdentifier(MvcResult result) throws Exception {
        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode node = objectMapper.readTree(jsonResponse);
        return node.path("identifier").asText();
    }
}
