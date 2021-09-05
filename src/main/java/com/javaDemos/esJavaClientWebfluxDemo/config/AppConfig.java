package com.javaDemos.esJavaClientWebfluxDemo.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppConfig {

    @Value("${es.url}")
    private String esURL;

    @Value("${es.protocol}")
    private String esProtocol;

    @Value("${es.tasks.index}")
    private String esTasksIndex;

    public RestHighLevelClient getESClient() {
        String[] hostPort = esURL.split(":");

        return new RestHighLevelClient(
                RestClient.builder(new HttpHost(hostPort[0], Integer.parseInt(hostPort[1]), esProtocol))
        );
    }
}
