<<<<<<< HEAD
package br.com.braga.ourbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.braga.ourbooks.model.Leitor;

@Repository
public interface LeitorRepository extends JpaRepository<Leitor, Long> {

}
