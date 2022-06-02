package br.com.braga.ourbooks.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/leitor")
public class LeitorController {
	@GetMapping(value = {"/home", "/"})
	public String home(Model model, Principal principal) {
		return "leitor/home";
	}
}
