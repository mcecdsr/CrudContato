package br.com.crud.contato.domain.exception;

public class NegocioException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	
	//public EntidadeNaoEncontradaException(HttpStatus status, String mensagem) {
	//	super(status, mensagem);
	//}

	public NegocioException(String mensagem) {
		super (mensagem);
	}
	
	public NegocioException(String mensagem , Throwable causa) {
		super (mensagem , causa);
	}

}
