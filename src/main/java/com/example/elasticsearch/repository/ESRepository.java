package com.example.elasticsearch.repository;

import com.example.elasticsearch.entity.Emp;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ESRepository extends ElasticsearchRepository<Emp,String> {

    Page<Emp> search(QueryBuilder var1, Pageable var2);
}
