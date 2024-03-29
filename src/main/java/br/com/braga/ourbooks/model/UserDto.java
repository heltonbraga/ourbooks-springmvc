package br.com.braga.ourbooks.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDto {
	@NotNull
	@NotEmpty
	@Size(min = 3, max = 50)
	private String username;

	@NotNull
	@NotEmpty
	@Size(min = 8, max = 20)
	private String password;
	private String confirmPass;

	@NotNull
	@NotEmpty
	@Email
	private String email;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPass() {
		return confirmPass;
	}

	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
