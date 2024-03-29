package br.com.braga.ourbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.braga.ourbooks.model.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

}
