package br.com.crud.contato.domain.exception;

public abstract class EntidadeEmUsoException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public EntidadeEmUsoException(String mensagem) {
		super (mensagem);
	}

}
