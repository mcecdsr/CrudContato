package br.com.crud.contato.api.exceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

import br.com.crud.contato.domain.exception.EntidadeEmUsoException;
import br.com.crud.contato.domain.exception.EntidadeNaoEncontradaException;
import br.com.crud.contato.domain.exception.NegocioException;
import br.com.crud.contato.domain.exception.PessoaEmUsoException;

@ControllerAdvice
public class ApiControllerAdvice extends ResponseEntityExceptionHandler{

	private static final String ERRO_GENERICO = 
			"Ocorreu um erro interno inesperado no sistema.Tente novamente e se o problema persistir, entre em contato com o administrador do sistema.";

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Throwable rootCause = ExceptionUtils.getRootCause(ex);

		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException ((InvalidFormatException)rootCause, headers,status, request);
		} else if (rootCause instanceof PropertyBindingException) {
			return handlePropertyBindingException ((PropertyBindingException)rootCause, headers,status, request);
		}

		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = ("Json com informações não aceitas.Verifique sintaxe de envio.");

		Problem problem = createProblemBuilder(status, problemType,detail)
				.userMessage(detail).build();	
		return handleExceptionInternal(ex,problem , new HttpHeaders() , status ,request);
	} 

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = String.format("O recurso '%s' , que você tentou acessar, é inexistente." , ex.getRequestURL());

		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(ERRO_GENERICO).build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
	
		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException)ex, headers,status, request);
		}
	
		
		ProblemType problemType = ProblemType.METODO_ENTRADA_INVÁLIDO;
		String detail ="Parâmetros não aceitos.";  

		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(ERRO_GENERICO).build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(
			MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		
		ProblemType problemType = ProblemType.METODO_ENTRADA_INVÁLIDO;
		String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', que é de um tipo inválido.Favor informar um valor compatível a %s." , 
				   ex.getName(),ex.getValue() , ex.getRequiredType().getSimpleName());

		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(ERRO_GENERICO).build();
		return handleExceptionInternal(ex, problem, headers, status, request);
		
	}


	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String path = joinPath(ex.getPath());

		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format("A propriedade '%s' não existe na classe ou não está visível. Favor corrigir o json.", path);


		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(ERRO_GENERICO).build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	} 

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {


		String path = joinPath(ex.getPath());

		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format("A propriedade '%s' recebeu o valor '%s' que é do tipo inválido.Favor mandar um valor compatível com '%s'.", 
				path, ex.getValue(), ex.getTargetType().getSimpleName());


		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(ERRO_GENERICO).build();
		return handleExceptionInternal(ex, problem, headers, status, request) ;
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> tratarPessoaNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request){

		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = ex.getMessage();

		Problem problem = createProblemBuilder(status, problemType,detail).userMessage(detail).build();	
		return handleExceptionInternal(ex,problem , new HttpHeaders() , status ,request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> tratarNegocioException(NegocioException ex , WebRequest request){

		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.ENTIDADE_NÃO_USADA;
		String detail = ex.getMessage();

		Problem problem = createProblemBuilder(status, problemType,detail).userMessage(detail).build();	
		return handleExceptionInternal(ex,problem  , new HttpHeaders() , status ,request);
	}



	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> tratarPessoaEmUso(PessoaEmUsoException  ex , WebRequest request){
		HttpStatus status = HttpStatus.CONFLICT;
		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
		String detail = ex.getMessage();

		Problem problem = createProblemBuilder(status, problemType,detail).userMessage(detail).build();	
		return handleExceptionInternal(ex,problem , new HttpHeaders() , status,request);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> tratarTodasAsExceçoesNaoTratadas(Exception ex , WebRequest request ){
		
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
		String detail =ERRO_GENERICO;
				 

		ex.printStackTrace();
		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(ERRO_GENERICO).build();
		return handleExceptionInternal(ex, problem,  new HttpHeaders(), status, request);
		
	}
	


	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {


		if (body == null) {
			body = Problem.builder().
					title(status.getReasonPhrase())
					.status(status.value()).timestamp(LocalDateTime.now())
					.build();
		} else if (body instanceof String) {
			body = Problem.builder().
					title((String) body).
					status(status.value()).timestamp(LocalDateTime.now())
					.build();
		}


		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status , ProblemType problemType , String detail){
		return Problem.builder().status(status.value()).type(problemType.getUri()).title(problemType.getTitle()).detail(detail).timestamp(LocalDateTime.now());
	}

	private String joinPath(List<Reference> references) {
		return references.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
	}


}
