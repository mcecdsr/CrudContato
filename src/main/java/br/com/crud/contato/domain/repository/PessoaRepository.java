package br.com.crud.contato.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.crud.contato.domain.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	
	
	List<Pessoa> findByIdadeBetween (int idadeIni, int idadeFin);
	
}
