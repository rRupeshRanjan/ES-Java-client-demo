package com.javaDemos.esJavaClientWebfluxDemo.repository;

import com.javaDemos.esJavaClientWebfluxDemo.config.AppConfig;
import com.javaDemos.esJavaClientWebfluxDemo.domain.CreateIndexRequestBody;
import com.javaDemos.esJavaClientWebfluxDemo.domain.DeleteIndexRequestBody;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public class IndexRepository {
    private final RestHighLevelClient client;

    public IndexRepository(AppConfig appConfig) {
        this.client = appConfig.getESClient();
    }

    public void createIndex(CreateIndexRequestBody requestBody) throws IOException {
        if (!isIndexPresent(requestBody.getIndex())) {
            CreateIndexRequest request = new CreateIndexRequest(requestBody.getIndex());
            request.settings(Settings.builder()
                    .put("index.number_of_shards", requestBody.getShards())
                    .put("index.number_of_replicas", requestBody.getReplicas())
            );

            client.indices().create(request, RequestOptions.DEFAULT);
        }
    }

    public void deleteIndex(DeleteIndexRequestBody requestBody) throws IOException {
        if (isIndexPresent(requestBody.getIndex())) {
            DeleteIndexRequest request = new DeleteIndexRequest(requestBody.getIndex());
            client.indices().delete(request, RequestOptions.DEFAULT);
        }
    }

    private boolean isIndexPresent(String index) throws IOException {
        GetIndexRequest request = new GetIndexRequest(index);
        return client.indices().exists(request, RequestOptions.DEFAULT);
    }
}
