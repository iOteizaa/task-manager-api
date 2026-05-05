package com.IOteiza.taskmanager.controller;

import com.IOteiza.taskmanager.dto.TaskDTO;
import com.IOteiza.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {
    @Autowired
    private TaskService service;

    @GetMapping
    public List<TaskDTO> getAll() {
        return service.getAllTasks();
    }

    @GetMapping("/{id}")
    public TaskDTO getById(@PathVariable Long id) {
        return service.getTaskById(id);
    }

    @PostMapping
    public TaskDTO create(@Valid @RequestBody TaskDTO dto) {
        return service.createTask(dto);
    }

    @PutMapping("/{id}")
    public TaskDTO update(@PathVariable Long id, @Valid @RequestBody TaskDTO dto) {
        return service.updateTask(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteTask(id);
        return "Task deleted successfully";
    }
}
