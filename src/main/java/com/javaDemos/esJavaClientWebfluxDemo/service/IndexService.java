package com.javaDemos.esJavaClientWebfluxDemo.service;

import com.javaDemos.esJavaClientWebfluxDemo.domain.CreateIndexRequestBody;
import com.javaDemos.esJavaClientWebfluxDemo.domain.DeleteIndexRequestBody;
import com.javaDemos.esJavaClientWebfluxDemo.repository.IndexRepository;
import org.elasticsearch.ElasticsearchStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class IndexService {
    private final IndexRepository esRepository;

    public IndexService(IndexRepository indexRepository) {
        this.esRepository = indexRepository;
    }

    public ResponseEntity<String> createIndex(CreateIndexRequestBody requestBody) {
        try {
            esRepository.createIndex(requestBody);
            return new ResponseEntity<>("acknowledged", HttpStatus.OK);
        } catch (ElasticsearchStatusException e) {
            return new ResponseEntity<>(e.getDetailedMessage(), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteIndex(DeleteIndexRequestBody requestBody) {
        try {
            esRepository.deleteIndex(requestBody);
            return new ResponseEntity<>("acknowledged", HttpStatus.OK);
        } catch (ElasticsearchStatusException e) {
            return new ResponseEntity<>(e.getDetailedMessage(), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
