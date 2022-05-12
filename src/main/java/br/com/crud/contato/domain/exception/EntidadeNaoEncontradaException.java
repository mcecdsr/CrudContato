package br.com.crud.contato.domain.exception;

public abstract class EntidadeNaoEncontradaException extends NegocioException {
	private static final long serialVersionUID = 1L;
	
	
	//public EntidadeNaoEncontradaException(HttpStatus status, String mensagem) {
	//	super(status, mensagem);
	//}

	public EntidadeNaoEncontradaException(String mensagem) {
		super (mensagem);
	}

}
