package com.example.elasticsearch;

import com.example.elasticsearch.entity.Emp;
import com.example.elasticsearch.repository.ESRepository;
import org.assertj.core.internal.bytebuddy.dynamic.scaffold.MethodGraph;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
public class TestEmpRepository {

    @Autowired
    private ESRepository esRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Qualifier("elasticsearchClient")
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void testSave(){
        Emp s =new Emp();
        s.setId(UUID.randomUUID().toString());
        s.setName("john");
        s.setBir(new Date());
        s.setAge(19);
        s.setAddress("中华民国");
        s.setContent("三权分立,自由民主");
        esRepository.save(s);
    }

    @Test
    public void testDelete(){
        esRepository.deleteById("6ce09106-7fa4-43c0-b345-d810c327a56b");
    }

    @Test
    public void testDeleteAll(){
        esRepository.deleteAll();
    }

    @Test
    public void testFindOne(){
        System.out.println(esRepository.findById("6ce09106-7fa4-43c0-b345-d810c327a56b").get());
    }

    @Test
    public void testFindAll(){
        Iterable<Emp> all = esRepository.findAll(Sort.by(Sort.Order.desc("age")));
        all.forEach(System.out::println);
    }

    @Test
    public void testFindPage() throws IOException, ParseException {
        List<Emp> empList = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest("ems");
        SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
        searchRequestBuilder.query(QueryBuilders.termQuery("content","自"))
                .sort("age", SortOrder.DESC)
                .from(0)
                .size(20)
                .highlighter(new HighlightBuilder().field("*")
                        .preTags("<>span style='color:red>'").postTags("</span"));
        searchRequest.source(searchRequestBuilder);
        SearchResponse searchResponse = restHighLevelClient
                .search(searchRequest, RequestOptions.DEFAULT);

        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits){
//            System.out.println(hit.getSourceAsMap());
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            Emp emp = new Emp(hit.getId(),
                    sourceAsMap.get("name").toString(),
                    Integer.parseInt(sourceAsMap.get("age").toString()),
                    new SimpleDateFormat("yyyy-MM-dd").parse(sourceAsMap.get("bir").toString()),
                    sourceAsMap.get("content").toString(),
                    sourceAsMap.get("address").toString());

            Map<String, HighlightField> highlightFields = hit.getHighlightFields();

            if (highlightFields.containsKey("content")){
                emp.setContent(highlightFields.get("content").fragments()[0].toString());
            }
            if (highlightFields.containsKey("name")){
                emp.setContent(highlightFields.get("name").fragments()[0].toString());
            }
            if (highlightFields.containsKey("address")){
                emp.setContent(highlightFields.get("address").fragments()[0].toString());
            }
            empList.add(emp);
        }
        empList.forEach(System.out::println);

    }

}
