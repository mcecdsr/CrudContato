package br.com.crud.contato.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.crud.contato.domain.exception.ContatoNaoEncontradoException;
import br.com.crud.contato.domain.exception.PessoaEmUsoException;
import br.com.crud.contato.domain.model.Contato;
import br.com.crud.contato.domain.model.Pessoa;
import br.com.crud.contato.domain.repository.ContatoRepository;

@Service
public class ContatoService {


	@Autowired
	private ContatoRepository contatoRepository;

	@Autowired
	private PessoaService pessoaService;


	public Contato buscarId(Long contatoId) {
		return contatoRepository.findById(contatoId).orElseThrow
				(() -> new ContatoNaoEncontradoException(contatoId));
	}


	public Contato salvar(Contato contato) {
		Long pessoaId = contato.getPessoa().getId();
		Pessoa pessoa = pessoaService.buscaPorId(pessoaId);
		contato.setPessoa(pessoa);
		return contatoRepository.save(contato);

	}


	public void excluir(Long id) {
		try {
			contatoRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new PessoaEmUsoException(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ContatoNaoEncontradoException(id);

		}




	}
}