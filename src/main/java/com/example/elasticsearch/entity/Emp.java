package com.example.elasticsearch.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Data
@Document(indexName = "ems")
public class Emp {
    private String id;
    private String name;
    private Integer age;
    private Date bir;
    private String content;
    private String address;
}
