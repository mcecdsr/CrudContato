package br.com.crud.contato.api.exceptionHandler;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(Include.NON_NULL)
public class Problem {
	
	private  Integer status;
	private  String type;
	private String title;
	private String detail;
	
	
	private String userMessage;
	private LocalDateTime timestamp;

}
