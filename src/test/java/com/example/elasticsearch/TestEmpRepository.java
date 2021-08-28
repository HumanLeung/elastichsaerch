package com.example.elasticsearch;

import com.example.elasticsearch.entity.Emp;
import com.example.elasticsearch.repository.ESRepository;
import org.assertj.core.internal.bytebuddy.dynamic.scaffold.MethodGraph;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.UUID;

@SpringBootTest
public class TestEmpRepository {

    @Autowired
    private ESRepository esRepository;

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
    public void testFindPage(){
    }

}
