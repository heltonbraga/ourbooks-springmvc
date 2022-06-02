package br.com.braga.ourbooks.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
	@GetMapping
	public String home(Model model, Principal principal) {
		return "publica";
	}

	@GetMapping
	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping
	@RequestMapping("/signup")
	public String signup() {
		return "novo_leitor";
	}
}
