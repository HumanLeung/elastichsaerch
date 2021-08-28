package com.example.elasticsearch.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.util.Date;

@Data
@Setter
@Getter
@Document(indexName = "ems")
public class Emp {

    @Id
    private String id;

    @Field(type = FieldType.Text,analyzer = "ik_max_word")

    private String name;

    @Field(type = FieldType.Integer)
    private Integer age;

    @Field(type = FieldType.Date)
    private Date bir;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Text)
    private String address;
}
