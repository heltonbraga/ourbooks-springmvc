package br.com.braga.ourbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.braga.ourbooks.model.Movimento;
import br.com.braga.ourbooks.model.Situacao;

@Repository
public interface MovimentoRepository extends JpaRepository<Movimento, Long> {

	long countBySituacao(Situacao situacao);

}
