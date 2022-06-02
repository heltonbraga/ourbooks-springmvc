package br.com.braga.ourbooks;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/*").permitAll();
		http.authorizeRequests().anyRequest().authenticated();
//			.antMatchers("/admin").hasAuthority("admin")
//			.antMatchers("/user").hasAuthority("leitor")
		http.formLogin(form -> {
			form.loginPage("/login").defaultSuccessUrl("/leitor/", true).permitAll();
		}).logout(logout -> {
			logout.logoutUrl("/logout").logoutSuccessUrl("/");
		}).csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("admin"));
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select username, password, enabled from leitores where username = ?")
				.authoritiesByUsernameQuery(
						"select username, case when username = 'admin' then 'admin' else 'leitor' end authority from leitores where username = ?")
				.passwordEncoder(encoder);

//		insert into leitores (created_at, email, enabled, username, password) values (
//				NOW(), 'admin@admin', true, 'admin',
//				'$2a$10$/HWbOyNvflko8jQ6px6t7eN2cU7JEzm5lGJkN7zNSHctr4AZLX3NK')
	}

}