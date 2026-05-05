package com.IOteiza.taskmanager.mapper;

import com.IOteiza.taskmanager.dto.TaskDTO;
import com.IOteiza.taskmanager.entity.Task;

import java.time.LocalDateTime;

public class TaskMapper {
    public static TaskDTO toDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setCompleted(task.getCompleted());
        String createdAt = null;
        String updatedAt = null;
        if (task.getCreatedAt() != null) {
            createdAt = task.getCreatedAt().toString();
        }
        if (task.getUpdatedAt() != null) {
            updatedAt = task.getUpdatedAt().toString();
        }
        dto.setCreatedAt(createdAt);
        dto.setUpdatedAt(updatedAt);
        return dto;
    }

    public static Task toEntity(TaskDTO dto) {
        Task task = new Task();

        if (dto.getId() != null) {
            task.setId(dto.getId());
        }

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCompleted(dto.getCompleted());

        task.setCreatedAt(LocalDateTime.now());
        return task;
    }
}