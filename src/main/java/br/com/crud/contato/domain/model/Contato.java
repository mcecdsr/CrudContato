package br.com.crud.contato.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;



@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity

public class Contato {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="nome_contato" , length = 30 , nullable = false)
	private String nome;
	
	@Column(name="tel_contato")
	private BigDecimal telefone;
	
	
	@ManyToOne
	@JoinColumn (name= "pessoa_id" , nullable = false)
	private Pessoa pessoa;

}
