package com.IOteiza.taskmanager.service;

import org.springframework.stereotype.Service;
import com.IOteiza.taskmanager.dto.TaskDTO;
import com.IOteiza.taskmanager.entity.Task;
import com.IOteiza.taskmanager.exception.ResourceNotFoundException;
import com.IOteiza.taskmanager.mapper.TaskMapper;
import com.IOteiza.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    private TaskRepository repo;

    public List<TaskDTO> getAllTasks() {
        return repo.findAll()
                .stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO getTaskById(Long id) {
        Task task = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with ID: " + id + " not found :/"));
        return TaskMapper.toDTO(task);
    }

    public TaskDTO createTask(TaskDTO dto) {
        Task task = TaskMapper.toEntity(dto);
        task.setCreatedAt(LocalDateTime.now());
        Task saved = repo.save(task);
        return TaskMapper.toDTO(saved);
    }

    public TaskDTO updateTask(Long id, TaskDTO dto) {
        Task task = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with ID: " + id + " not found :/"));
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCompleted(dto.getCompleted());
        task.setUpdatedAt(LocalDateTime.now());

        Task updated = repo.save(task);
        return TaskMapper.toDTO(updated);
    }

    public void deleteTask(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Task with ID: " + id + " not found :/");
        }
        repo.deleteById(id);
    }
}
