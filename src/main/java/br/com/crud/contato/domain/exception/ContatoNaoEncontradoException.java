package br.com.crud.contato.domain.exception;

public class ContatoNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;
	
	
	//public EntidadeNaoEncontradaException(HttpStatus status, String mensagem) {
	//	super(status, mensagem);
	//}

	public ContatoNaoEncontradoException(String mensagem) {
		super (mensagem);
	}
	
	public ContatoNaoEncontradoException( Long id) {
		this(String.format("Contato %d nao encontrado na bases",id ));
	}

}
