package br.com.crud.contato.api.exceptionHandler;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.crud.contato.domain.exception.EntidadeNaoEncontradaException;
import br.com.crud.contato.domain.exception.NegocioException;
import br.com.crud.contato.domain.exception.PessoaEmUsoException;

@ControllerAdvice
public class ApiControllerAdvice extends ResponseEntityExceptionHandler{
	
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> tratarPessoaNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request){
		return handleExceptionInternal(ex,ex.getMessage() , new HttpHeaders() , HttpStatus.NOT_FOUND,request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> tratarNegocioException(NegocioException ex , WebRequest request){
		return handleExceptionInternal(ex,ex.getMessage() , new HttpHeaders() , HttpStatus.BAD_REQUEST,request);
	}
	
	
	
	@ExceptionHandler(PessoaEmUsoException.class)
	public ResponseEntity<?> tratarPessoaEmUso(PessoaEmUsoException  ex , WebRequest request){
		return handleExceptionInternal(ex,ex.getMessage() , new HttpHeaders() , HttpStatus.CONFLICT,request);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
	
		
		if (body == null) {
			 body = Problema.builder().
					 dataHora(LocalDateTime.now()).
					 mensagem(status.getReasonPhrase()).build();
		} else if (body instanceof String) {
			 body = Problema.builder().
					 dataHora(LocalDateTime.now()).
					 mensagem((String) body).build();
		}
		
			
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

}