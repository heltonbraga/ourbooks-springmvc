package br.com.braga.ourbooks.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import br.com.braga.ourbooks.event.OnRegistrationCompleteEvent;
import br.com.braga.ourbooks.model.User;
import br.com.braga.ourbooks.model.UserDto;
import br.com.braga.ourbooks.model.VerificationToken;
import br.com.braga.ourbooks.repository.UserRepository;
import br.com.braga.ourbooks.service.MyUserDetailsService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository repository;

	@Autowired
	private MyUserDetailsService userDetailsService;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@GetMapping
	@RequestMapping("/login")
	public String showLogin() {
		return "/user/login";
	}

	@GetMapping
	@RequestMapping("/registration")
	public String showRegistration(WebRequest request, Model model) {
		UserDto userDto = new UserDto();
		model.addAttribute("user", userDto);
		return "/user/registration";
	}

	@PostMapping("/registration")
	public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserDto userDto, BindingResult bindingResult,
			HttpServletRequest request, Errors errors) {

		if (bindingResult.hasErrors()) {
			return new ModelAndView("/user/registration", "user", userDto);
		}

		if (!userDto.getPassword().equals(userDto.getConfirmPass())) {
			bindingResult.rejectValue("password", null, "passwords don't match");
			return new ModelAndView("/user/registration");
		}

		Optional<User> nomeEmUso = repository.findById(userDto.getUsername());
		if (nomeEmUso.isPresent()) {
			bindingResult.rejectValue("username", null, "username in use");
			return new ModelAndView("/user/registration");
		}

		Optional<User> emailEmUso = repository.findByEmail(userDto.getEmail());
		if (emailEmUso.isPresent()) {
			bindingResult.rejectValue("email", null, "email in use");
			return new ModelAndView("/user/registration");
		}

		User user = new User();
		user.setUsername(userDto.getUsername());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDetailsService.getEncoder().encode(userDto.getPassword()));
		user.setEnabled(false);
		repository.save(user);

		String appUrl = request.getContextPath();
		eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl));

		return new ModelAndView("/user/login", "user", userDto);
	}

	@GetMapping("/confirmation")
	public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {

		VerificationToken verificationToken = userDetailsService.getVerificationToken(token);
		if (verificationToken == null) {
			model.addAttribute("message", "Confirmation not possible, verification token not found.");
			return "redirect:/user/invalidConfirmation";
		}
		if (LocalDateTime.now().isAfter(verificationToken.getExpiryDate())) {
			model.addAttribute("message", "Confirmation not possible, verification token expired.");
			return "redirect:/user/invalidConfirmation";
		}

		User user = verificationToken.getUser();
		user.setEnabled(true);
		repository.save(user);

		return "redirect:/user/login";
	}

}
