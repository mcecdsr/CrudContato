package br.com.crud.contato.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import br.com.crud.contato.domain.model.Contato;

public interface ContatoRepositoryQueries {

	List<Contato> find(String nome, BigDecimal telefoneIni, BigDecimal telefoneFin);

}