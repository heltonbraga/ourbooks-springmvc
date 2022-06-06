package br.com.braga.ourbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.braga.ourbooks.model.VerificationToken;

@Repository
public interface TokenRepository extends JpaRepository<VerificationToken, Long> {

	VerificationToken findByToken(String verificationToken);

}
