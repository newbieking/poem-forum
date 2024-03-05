package com.example.pojo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "poem_collect")
@AllArgsConstructor
@NoArgsConstructor
public class UserPoemCollect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String userId;

    private Long poemId;
}
