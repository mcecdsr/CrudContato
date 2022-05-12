package br.com.crud.contato.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.crud.contato.domain.model.Pessoa;
import br.com.crud.contato.domain.repository.PessoaRepository;
import br.com.crud.contato.domain.service.PessoaService;

@RestController
@RequestMapping(value = "/pessoas",consumes = MediaType.APPLICATION_JSON_VALUE)
public class PessoaController {


	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private PessoaService pessoaService;


	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping
	public List<Pessoa> listar(){
		return pessoaRepository.findAll();
	}

	@GetMapping("/por-idade")
	public List<Pessoa> listarPorIdade(int idadeIni, int idadeFin){
		return pessoaRepository.findByIdadeBetween(idadeIni, idadeFin);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(path = "/{id}")
	public Pessoa buscarPorId(@PathVariable Long id) {
		return pessoaService.buscaPorId(id);

	}

	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping
	public Pessoa adicionar(@RequestBody Pessoa pessoa) {
		return pessoaService.salvar(pessoa);

	}


	@PutMapping(path = "/{id}")
	public Pessoa atualizar(@PathVariable Long id ,@RequestBody Pessoa pessoa) {
		
		Pessoa pessoaAtual = pessoaService.buscaPorId(id);
		BeanUtils.copyProperties(pessoa, pessoaAtual, "id");
		return pessoaService.salvar(pessoaAtual);

	}


	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id){
		pessoaService.excluir(id);
	}



}
