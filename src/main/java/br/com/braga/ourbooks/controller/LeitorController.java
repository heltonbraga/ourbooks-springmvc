package br.com.braga.ourbooks.controller;

import java.security.Principal;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.braga.ourbooks.model.Leitor;
import br.com.braga.ourbooks.model.View;
import br.com.braga.ourbooks.model.View.LeitorNovo;
import br.com.braga.ourbooks.repository.LeitorRepository;

@Controller
@RequestMapping("/leitor")
public class LeitorController {

	@Autowired
	private LeitorRepository repository;

	@GetMapping(path = "/{id}")
	public String home(@PathVariable("id") Long id, Model model, Principal principal) {
		Optional<Leitor> opt = repository.findById(id);
		if (opt.isPresent()) {
			ObjectMapper mapper = new ObjectMapper();
			View.LeitorDetalhe leitor = mapper.convertValue(opt.get(), View.LeitorDetalhe.class);
			model.addAttribute("leitor", leitor);
			return "leitor/home";
		}
		return "redirect:/";
	}

	@PostMapping("novo")
	public String novo(@Valid LeitorNovo requisicao, BindingResult result) {
		if (result.hasErrors()) {
			return "/signup";
		}

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		Leitor leitor = mapper.convertValue(requisicao, Leitor.class);

		repository.save(leitor);

		return "redirect:/leitor/" + leitor.getId();
	}

}
