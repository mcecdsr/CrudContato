package br.com.crud.contato.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.crud.contato.domain.model.Contato;

public interface ContatoRepository extends JpaRepository<Contato, Long>, ContatoRepositoryQueries {
	
	//@Query("from Contato where nome like %:nome% and id = :id" )
	List<Contato> BuscarPorNome(String nome, @Param("id")Long id);
	

}