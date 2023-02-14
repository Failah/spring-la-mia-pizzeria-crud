package com.lessons.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lessons.java.model.Pizza;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

}
