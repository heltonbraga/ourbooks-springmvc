package br.com.braga.ourbooks.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.braga.ourbooks.repository.LeitorRepository;

@Controller
@RequestMapping("/leitor")
public class LeitorController {
	
	@Autowired
	private LeitorRepository repository;
	
	@GetMapping(value = {"/home", "/"})
	public String home(Model model, Principal principal) {
		return "leitor/home";
	}
	

	@PostMapping("novo")
	public String novo() {
		
		return "leitor/home";
	}
	
}
