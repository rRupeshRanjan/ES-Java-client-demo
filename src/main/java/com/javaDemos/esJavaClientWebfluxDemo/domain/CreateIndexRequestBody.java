package com.javaDemos.esJavaClientWebfluxDemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIndexRequestBody {
    private String index;
    private int replicas;
    private int shards;
}
