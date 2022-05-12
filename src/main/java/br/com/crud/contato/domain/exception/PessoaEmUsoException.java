package br.com.crud.contato.domain.exception;

public class PessoaEmUsoException extends EntidadeEmUsoException {
	private static final long serialVersionUID = 1L;
	
	
	//public EntidadeNaoEncontradaException(HttpStatus status, String mensagem) {
	//	super(status, mensagem);
	//}

	public PessoaEmUsoException(String mensagem) {
		super (mensagem);
	}
	
	public PessoaEmUsoException( Long id) {
		this(String.format("Pessoa %d nao pode ser removido,esta em uso",id ));
	}

}
