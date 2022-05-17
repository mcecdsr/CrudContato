package br.com.crud.contato.api.exceptionHandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem Incompreensível."),
	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de Sistema."),
	METODO_ENTRADA_INVÁLIDO("/metodo-entrada-invalido" , "Método de Entrada Inválido"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado" , "Recurso não encontrado"),
	ENTIDADE_EM_USO("/entidade-em-uso" , "Entidade em uso"),
	ENTIDADE_NÃO_USADA("/entidade-não-usada-no-relacionamento" , "Entidade não é utilizada no relacionamento das bases, precisa ser incluida.");

	private String uri;
	private String title;
	
	private ProblemType(String path, String title) {
		this.uri = "https://cecifoods.com.br" + path;
		this.title = title;
	}
	
	
	

}
