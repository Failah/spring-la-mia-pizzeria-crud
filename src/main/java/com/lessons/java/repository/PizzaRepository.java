package com.lessons.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lessons.java.model.Pizza;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

	public List<Pizza> findByNameContainingIgnoreCase(String name);

}
