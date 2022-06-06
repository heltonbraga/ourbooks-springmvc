package br.com.braga.ourbooks.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "leitores")
public class Leitor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(View.LeitorDetalhe.class)
	private Long id;
	
	@Column(unique = true, length = 50)
	@NotEmpty
	@Size(min = 3, max = 50, message = "O campo nome deve ter entre 3 e 50 caracteres")
	@JsonView(View.LeitorNovo.class)
	private String username;

	@Column(unique = true, length = 100, nullable = false)
	@Size(min = 10, max = 100, message = "O campo email deve ter entre 10 e 100 caracteres")
	@Pattern(regexp = ".+@.+\\..+", message = "O email informado não parece válido")
	@JsonView(View.LeitorNovo.class)
	private String email;

	@Column(length = 500, nullable = false)
	@JsonView(View.LeitorNovo.class)
	@NotEmpty(message = "É necessário informar uma senha")
	private String password;

	@Column(nullable = false)
	@JsonView(View.LeitorDetalhe.class)
	private boolean enabled;
	
	@Column(updatable = false)
	@JsonView(View.LeitorDetalhe.class)
	private LocalDateTime createdAt;
	
	@ManyToMany
	@JoinTable(
	  name = "livros_disponiveis", 
	  joinColumns = @JoinColumn(name = "leitor_id"), 
	  inverseJoinColumns = @JoinColumn(name = "livro_id"))
	@JsonView(View.LeitorDetalhe.class)
	private Set<Livro> disponiveis;
	
	@ManyToMany
	@JoinTable(
	  name = "livros_desejados", 
	  joinColumns = @JoinColumn(name = "leitor_id"), 
	  inverseJoinColumns = @JoinColumn(name = "livro_id"))
	@JsonView(View.LeitorDetalhe.class)
	private Set<Livro> desejados;
	
	public Leitor() {
		//
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Set<Livro> getDisponiveis() {
		return disponiveis;
	}

	public void setDisponiveis(Set<Livro> disponiveis) {
		this.disponiveis = disponiveis;
	}

	public Set<Livro> getDesejados() {
		return desejados;
	}

	public void setDesejados(Set<Livro> desejados) {
		this.desejados = desejados;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
