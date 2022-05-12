package br.com.crud.contato.domain.exception;

public class PessoaNaoEncontradaException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;
	
	
	//public EntidadeNaoEncontradaException(HttpStatus status, String mensagem) {
	//	super(status, mensagem);
	//}

	public PessoaNaoEncontradaException(String mensagem) {
		super (mensagem);
	}
	
	public PessoaNaoEncontradaException( Long id) {
		this(String.format("Pessoa %d nao encontrado na bases",id ));
	}

}
