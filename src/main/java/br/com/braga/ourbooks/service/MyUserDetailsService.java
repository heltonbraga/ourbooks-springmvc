package br.com.braga.ourbooks.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.braga.ourbooks.model.User;
import br.com.braga.ourbooks.model.VerificationToken;
import br.com.braga.ourbooks.repository.TokenRepository;
import br.com.braga.ourbooks.repository.UserRepository;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenRepository tokenRepository;

	public PasswordEncoder getEncoder() {
		return encoder;
	}

	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> opt = userRepository.findByEmail(email);
		if (opt.isEmpty()) {
			throw new UsernameNotFoundException("No user found with username: " + email);
		}
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		User user = opt.get();

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword().toLowerCase(),
				enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, getAuthorities(user));
	}

	private static List<GrantedAuthority> getAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("leitor"));
		if (user.getUsername().equalsIgnoreCase("admin")) {
			authorities.add(new SimpleGrantedAuthority("admin"));
		}
		return authorities;
	}

	public VerificationToken getVerificationToken(String VerificationToken) {
		return tokenRepository.findByToken(VerificationToken);
	}

	public void createVerificationToken(User user, String token) {
		VerificationToken myToken = new VerificationToken(token, user);
		tokenRepository.save(myToken);
	}

}