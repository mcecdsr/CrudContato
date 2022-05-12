package br.com.crud.contato.api.controller;


import java.math.BigDecimal;
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

import br.com.crud.contato.domain.exception.NegocioException;
import br.com.crud.contato.domain.exception.PessoaNaoEncontradaException;
import br.com.crud.contato.domain.model.Contato;
import br.com.crud.contato.domain.repository.ContatoRepository;
import br.com.crud.contato.domain.service.ContatoService;

@RestController
@RequestMapping(value = "/contatos",consumes = MediaType.APPLICATION_JSON_VALUE)
public class ContatoController {

	@Autowired
	private ContatoRepository contatoRepository;

	@Autowired
	private ContatoService contatoService;


	@GetMapping 
	public List<Contato> listar(){
		return contatoRepository.findAll();
	}


	@GetMapping ( "/por-nome")
	public List<Contato> listarPorNome(String nome,Long id){
		return contatoRepository.BuscarPorNome(nome,id);
	}


	@GetMapping ( "/por-nome-telefone")
	public List<Contato> find(String nome, BigDecimal telefoneIni , BigDecimal telefoneFin){
		return contatoRepository.find(nome,telefoneIni,telefoneFin);
	}


	@ResponseStatus(HttpStatus.OK)
	@GetMapping( "/{contatoId}")
	public Contato buscarPorId(@PathVariable Long contatoId) {
		return contatoService.buscarId(contatoId);

		//	Optional<Contato> contato = contatoRepository.findById(contatoId);
		//	if (contato.isPresent()) {
		//			return ResponseEntity.ok(contato.get());
		//		}
		//		return ResponseEntity.notFound().build();
	}

	@PostMapping 
	@ResponseStatus(HttpStatus.CREATED)
	public Contato adicionar(@RequestBody Contato contato) {
		try {
		return contatoService.salvar(contato);
		}catch (PessoaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping( "/{contatoId}")
	public Contato atualizar (@PathVariable Long contatoId , @RequestBody Contato contato ){

		Contato contatoAtual = contatoService.buscarId(contatoId);
		BeanUtils.copyProperties(contato, contatoAtual, "id");
		
		try {
			return contatoService.salvar(contatoAtual);
		}catch (PessoaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage() , e);
		}
	}

	@DeleteMapping( "/{contatoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover (@PathVariable Long contatoId){
		contatoService.excluir(contatoId);	
	}

}










//public ResponseEntity<Contato> buscarPorId(@PathVariable Long contatoId) {

//Contato contato = contatoRepository.buscar(contatoId);
//return ResponseEntity.ok(contato);

//	HttpHeaders headers = new HttpHeaders();
//	headers.add(HttpHeaders.LOCATION, "http://localhost:8080/contatos/1");
//	return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();		










