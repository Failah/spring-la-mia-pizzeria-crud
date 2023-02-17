package com.lessons.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lessons.java.model.Pizza;
import com.lessons.java.repository.PizzaRepository;

import jakarta.validation.Valid;

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

	@GetMapping("/pizzas")
	public String getPizzasByTitle(@RequestParam(required = false) String title, Model model) {
		List<Pizza> pizzas;
		if (title != null && !title.isEmpty()) {
			pizzas = pizzaRepository.findByNameContainingIgnoreCase(title);
		} else {
			pizzas = pizzaRepository.findAll();
		}
		model.addAttribute("pizzas", pizzas);
		return "pizzaIndex";
	}

	@GetMapping("/pizzas/{id}")
	public String showPizzaDetails(@PathVariable("id") Integer id, Model model) {
		Pizza pizza = getPizzaById(id);
		if (pizza != null) {
			model.addAttribute("pizza", pizza);
			return "pizzaDetails";
		} else {
			return "redirect:/";
		}
	}

	private Pizza getPizzaById(int id) {
		List<Pizza> pizzas = pizzaRepository.findAll();
		for (Pizza pizza : pizzas) {
			if (pizza.getId() == id) {
				return pizza;
			}
		}
		return null;
	}

	@GetMapping("/pizzas/new-pizza")
	public String create(Model model) {
		model.addAttribute("pizza", new Pizza());
		return "new-pizza";
	}

	@PostMapping("/pizzas/new-pizza")
	public String store(@Valid @ModelAttribute("pizza") Pizza pizzaForm, BindingResult bindingResult, Model model) {
		System.out.println(pizzaForm.toString());
		System.out.println(bindingResult.toString());
		if (bindingResult.hasErrors()) {
			return "new-pizza";
		}
		pizzaRepository.save(pizzaForm);
		return "redirect:/pizzas";
	}

	@GetMapping("/pizzas/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		Pizza pizza = pizzaRepository.getReferenceById(id);
		model.addAttribute("pizza", pizza);
		return "edit-pizza";
	}

	@PostMapping("/pizzas/edit/{id}")
	public String update(@Valid @ModelAttribute("pizza") Pizza pizzaForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "edit-pizza";
		}
		pizzaRepository.save(pizzaForm);
		return "redirect:/pizzas";
	}

	@PostMapping("pizzas/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		pizzaRepository.deleteById(id);
		return "redirect:/pizzas";
	}

}
