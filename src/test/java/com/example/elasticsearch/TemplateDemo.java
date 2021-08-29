package com.example.elasticsearch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

@SpringBootTest
public class TemplateDemo {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    public void createIndex(){
//       elasticsearchRestTemplate.index();
        System.out.println("create index");
    }

    @Test
    public void deleteIndex(){
//      elasticsearchRestTemplate.delete();
    }
}
