package br.com.braga.ourbooks;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import br.com.braga.ourbooks.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private MyUserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/*", "/user/**").permitAll();
		http.authorizeRequests().antMatchers("/signup").permitAll();
		http.authorizeRequests().antMatchers("/leitor/novo").permitAll();
		http.authorizeRequests().anyRequest().authenticated();
		http.formLogin(form -> {
			form.loginPage("/user/login").defaultSuccessUrl("/leitor/1", true).permitAll();
		}).logout(logout -> {
			logout.logoutUrl("/logout").logoutSuccessUrl("/");
		}).csrf().disable();
		
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select username, password, enabled from leitores where username = ?")
				.authoritiesByUsernameQuery(
						"select username, case when username = 'admin' then 'admin' else 'leitor' end authority from leitores where username = ?")
				.passwordEncoder(userDetailsService.getEncoder());

//		insert into leitores (created_at, email, enabled, username, password) values (
//				NOW(), 'admin@admin', true, 'admin',
//				'$2a$10$/HWbOyNvflko8jQ6px6t7eN2cU7JEzm5lGJkN7zNSHctr4AZLX3NK')
	}

}