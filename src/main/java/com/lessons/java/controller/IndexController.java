package com.lessons.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lessons.java.model.Pizza;
import com.lessons.java.repository.PizzaRepository;

@Controller
@RequestMapping("/")
public class IndexController {
	@Autowired
	private PizzaRepository pizzaRepository;

	@GetMapping("/")
	public String index(Model model) {
		List<Pizza> pizzaList = pizzaRepository.findAll();
		model.addAttribute("pizzas", pizzaList);
		return "index";
	}
}
