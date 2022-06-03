package br.com.braga.ourbooks.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.braga.ourbooks.model.Situacao;
import br.com.braga.ourbooks.repository.LeitorRepository;
import br.com.braga.ourbooks.repository.LivroRepository;
import br.com.braga.ourbooks.repository.MovimentoRepository;

@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
	private LeitorRepository leitorRepository;

	@Autowired
	private LivroRepository livroRepository;

	@Autowired
	private MovimentoRepository movimentoRepository;

	@GetMapping
	public String home(Model model, Principal principal) {
		long leitores = leitorRepository.count();
		long livros = livroRepository.count();
		long movimentosFinalizados = movimentoRepository.countBySituacao(Situacao.FINALIZADA);
		model.addAttribute("leitores", leitores);
		model.addAttribute("livros", livros);
		model.addAttribute("movimentos", movimentosFinalizados);
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
