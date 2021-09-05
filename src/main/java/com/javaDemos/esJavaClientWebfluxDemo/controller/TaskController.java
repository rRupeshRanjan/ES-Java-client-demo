package com.javaDemos.esJavaClientWebfluxDemo.controller;

import com.javaDemos.esJavaClientWebfluxDemo.domain.Task;
import com.javaDemos.esJavaClientWebfluxDemo.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
public class TaskController {

    public TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/task/{id}")
    public Mono<Task> getTaskById(@PathVariable String id) throws IOException {
        return taskService.getTaskById(id);
    }

    @GetMapping("/tasks")
    public Flux<Task> getAllTasks() throws IOException {
        return taskService.getAllTasks();
    }

    @PostMapping("/task")
    public ResponseEntity<Mono<Task>> createTask(@RequestBody Task task) throws IOException {
        return taskService.createTask(task);
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<String> deleteTaskById(@PathVariable String id) throws IOException {
        return taskService.deleteTaskById(id);
    }

    @PutMapping("/task/{id}")
    public ResponseEntity<Mono<Task>> updateTaskById(@RequestBody Task task,
                                                 @PathVariable String id) throws IOException {
        return taskService.updateTaskById(id, task);
    }
}
