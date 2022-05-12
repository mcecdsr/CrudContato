package br.com.crud.contato.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.crud.contato.domain.exception.PessoaEmUsoException;
import br.com.crud.contato.domain.exception.PessoaNaoEncontradaException;
import br.com.crud.contato.domain.model.Pessoa;
import br.com.crud.contato.domain.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	
	public Pessoa buscaPorId(Long id) {
		return pessoaRepository.findById(id)
				.orElseThrow(()-> new PessoaNaoEncontradaException(id));
	}
	
	
	public Pessoa salvar (Pessoa pessoa) {
		return pessoaRepository.save(pessoa);
	}
	
	
	public void excluir(Long id) {
		try {
			pessoaRepository.deleteById(id);	
		} catch (DataIntegrityViolationException e) {
			throw new PessoaEmUsoException(id);
		} catch (EmptyResultDataAccessException e) {
			throw new PessoaNaoEncontradaException(id);
		}
	}
	
	
	
}
