package com.example.repositories;

import com.example.pojo.Poem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoemRepository extends JpaRepository<Poem, Long> {
}