package com.IOteiza.taskmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.IOteiza.taskmanager.dto.TaskDTO;
import com.IOteiza.taskmanager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    @Test
    void shouldCreateTask() throws Exception {

        TaskDTO dto = new TaskDTO(
                1L,
                "Test task",
                "Test description",
                false,
                null,
                null
        );

        when(taskService.createTask(any(TaskDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test task"));
    }

    @Test
    void shouldFailValidation() throws Exception {

        TaskDTO dto = new TaskDTO(
                null,
                "",
                "",
                null,
                null,
                null
        );

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.completed").exists());
    }

    @Test
    void shouldReturnAllTasks() throws Exception {

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnTaskById() throws Exception {

        TaskDTO dto = new TaskDTO(
                1L,
                "Test task",
                "Test description",
                false,
                null,
                null
        );

        when(taskService.getTaskById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldDeleteTask() throws Exception {

        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateTask() throws Exception {

        TaskDTO dto = new TaskDTO(
                1L,
                "Updated task",
                "Updated description",
                true,
                null,
                null
        );

        when(taskService.updateTask(any(Long.class), any(TaskDTO.class))).thenReturn(dto);

        mockMvc.perform(put("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated task"));
    }
}