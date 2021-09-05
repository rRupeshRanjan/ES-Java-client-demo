package com.javaDemos.esJavaClientWebfluxDemo.controller;

import com.javaDemos.esJavaClientWebfluxDemo.domain.CreateIndexRequestBody;
import com.javaDemos.esJavaClientWebfluxDemo.domain.DeleteIndexRequestBody;
import com.javaDemos.esJavaClientWebfluxDemo.service.IndexService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    private IndexService indexService;

    public IndexController(IndexService indexService) {
        this.indexService = indexService;
    }

    @PostMapping("/create-index")
    public ResponseEntity<String> createIndex(@RequestBody CreateIndexRequestBody requestBody) {
        return indexService.createIndex(requestBody);
    }

    @DeleteMapping("/delete-index")
    public ResponseEntity<String> delete(@RequestBody DeleteIndexRequestBody requestBody) {
        return indexService.deleteIndex(requestBody);
    }
}
