package com.javaDemos.esJavaClientWebfluxDemo.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaDemos.esJavaClientWebfluxDemo.config.AppConfig;
import com.javaDemos.esJavaClientWebfluxDemo.domain.Task;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Repository
public class TaskRepository {
    private final RestHighLevelClient client;
    private final ObjectMapper objectMapper;
    private final String tasksIndex;

    public TaskRepository(AppConfig appConfig, ObjectMapper objectMapper) {
        this.client = appConfig.getESClient();
        this.objectMapper = objectMapper;
        this.tasksIndex = appConfig.getEsTasksIndex();
    }

    public Flux<Task> getAllTasks() throws IOException {
        SearchRequest request = new SearchRequest(tasksIndex);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        request.source(sourceBuilder);

        return Flux.fromStream(
                Arrays.stream(client.search(request, RequestOptions.DEFAULT).getHits().getHits())
                        .map(SearchHit::getSourceAsString)
                        .map(Task::getTaskFromString));
    }

    public Mono<Task> getTaskById(String id) throws IOException {
        GetRequest request = new GetRequest(tasksIndex, id);
        String response = client.get(request, RequestOptions.DEFAULT).getSourceAsString();
        return Mono.justOrEmpty(Task.getTaskFromString(response));
    }

    public HttpStatus createTask(Map<String, Object> task) throws IOException {
        IndexRequest request = new IndexRequest(tasksIndex);
        request.id((String) task.get("id"));
        request.source(task);

        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        return HttpStatus.valueOf(response.status().getStatus());
    }

    public HttpStatus deleteTaskById(String id) throws IOException {
        DeleteRequest request = new DeleteRequest(tasksIndex);
        request.id(id);

        int status = client.delete(request, RequestOptions.DEFAULT).status().getStatus();
        return HttpStatus.valueOf(status);
    }

    public HttpStatus updateTaskById(String id, Map<String, Object> task) throws IOException {
        UpdateRequest request = new UpdateRequest(tasksIndex, id);
        request.doc(task);

        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        return HttpStatus.valueOf(response.status().getStatus());
    }
}
