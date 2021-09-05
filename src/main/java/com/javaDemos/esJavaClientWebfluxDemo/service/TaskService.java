package com.javaDemos.esJavaClientWebfluxDemo.service;

import com.javaDemos.esJavaClientWebfluxDemo.domain.Task;
import com.javaDemos.esJavaClientWebfluxDemo.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Service
public class TaskService {
    private final Date date;
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.date = new Date();
    }

    public Flux<Task> getAllTasks() throws IOException {
        return taskRepository.getAllTasks();
    }

    public Mono<Task> getTaskById(String id) throws IOException {
        return taskRepository.getTaskById(id);
    }

    public ResponseEntity<Mono<Task>> createTask(Task task) throws IOException {
        task.setCreatedAt(date.getTime());
        Map<String, Object> taskMap = Task.getTaskMap(task, false);
        HttpStatus responseStatus = taskRepository.createTask(taskMap);
        return getCreateOrUpdateResponse(responseStatus, task);
    }

    public ResponseEntity<String> deleteTaskById(String id) throws IOException {
        HttpStatus responseStatus = taskRepository.deleteTaskById(id);
        return new ResponseEntity<>(id, responseStatus);
    }

    public ResponseEntity<Mono<Task>> updateTaskById(String id, Task task) throws IOException {
        if (!Objects.equals(id, task.getId())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            Map<String, Object> taskMap = Task.getTaskMap(task, true);
            HttpStatus responseStatus = taskRepository.updateTaskById(id, taskMap);
            return getCreateOrUpdateResponse(responseStatus, task);
        }
    }

    private ResponseEntity<Mono<Task>> getCreateOrUpdateResponse(HttpStatus status, Task task) throws IOException {
        if (status.value() / 100 == 2) {
            return new ResponseEntity<>(getTaskById(task.getId()), status);
        } else {
            return new ResponseEntity<>(Mono.empty(), status);
        }
    }
}
