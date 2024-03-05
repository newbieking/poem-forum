package com.example.pojo;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Entity
@Table(name = "poem")
public class Poem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    @Field(type = FieldType.Text)
    private String title;

    //    @Field(type = FieldType.Keyword)
    private String author;

    //    @Field(type = FieldType.Text)
    private String content;
}
