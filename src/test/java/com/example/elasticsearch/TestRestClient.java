package com.example.elasticsearch;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class TestRestClient {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void test() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("ems","emp","1");
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(deleteResponse.status());
    }

    @Test
    public void testAddIndex() throws IOException {
        IndexRequest indexRequest = new IndexRequest("ems","emp","12");
        indexRequest.source("{\"name\":\"nice\",\"age\":23}", XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest,RequestOptions.DEFAULT);
        System.out.println(indexResponse.status());
    }
    @Test
    public void testSearch() throws IOException {
        SearchRequest searchRequest = new SearchRequest("ems");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery())
                .from(0)
                .size(20)
                .postFilter(QueryBuilders.matchAllQuery())
                .sort("age", SortOrder.DESC)
                .highlighter(new HighlightBuilder().field("*").requireFieldMatch(false));

        searchRequest.types("emp").source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        System.out.println("matched result: " +searchResponse.status());
        System.out.println("Document points: "+searchResponse.getHits().getMaxScore());
        SearchHit [] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsMap());
        }

    }

    @Test
    public void testUpdate() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest("ems","emp","12");
        updateRequest.doc("{\"name\":\"john\",\"age\":21}", XContentType.JSON);
       UpdateResponse updateResponse = restHighLevelClient.update(updateRequest,RequestOptions.DEFAULT);
        System.out.println(updateResponse.status());
    }

    @Test
    public void testBulk(){
        BulkRequest bulkRequest = new BulkRequest();
        IndexRequest indexRequest = new IndexRequest("ems","emp","11");
        indexRequest.source("{\"name\":\"Jane\",\"age\":25}", XContentType.JSON);

        bulkRequest.add(indexRequest);
    }

}
