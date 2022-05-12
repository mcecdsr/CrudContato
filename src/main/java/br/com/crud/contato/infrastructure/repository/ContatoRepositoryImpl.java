package br.com.crud.contato.infrastructure.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import br.com.crud.contato.domain.model.Contato;
import br.com.crud.contato.domain.repository.ContatoRepositoryQueries;

@Repository
public class ContatoRepositoryImpl implements ContatoRepositoryQueries {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Contato> find(String nome , BigDecimal telefoneIni , BigDecimal telefoneFin){
		
		//primeira declaração tem que ser do criteria builder p usar o criteria query
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		
		//criteria query usando o buuilder (fábrica do criteria)
		CriteriaQuery<Contato> criteria = builder.createQuery(Contato.class);
		
		//root é a raiz do criteria
		Root<Contato> root = criteria.from(Contato.class); // select * from Contato
		
		//para transformar em uma consulta dinamica eu preciso jogar dentro de uma lista e varre-la
		var predicates = new ArrayList<Predicate>();
		
		// ele precisa declarar o predicate para conseguir usar no where do criteria
		
		if (StringUtils.hasLength(nome)) {
			predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
			//Predicate nomePredicate = builder.like(root.get("nome"), "%" + nome + "%");
		}
		
		
		if(telefoneIni != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("telefone"), telefoneIni));
			//Predicate telefoneIniPredicate = builder.greaterThanOrEqualTo(root.get("telefone"), telefoneIni);
		}
		
		
		if(telefoneFin != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("telefone"), telefoneFin));
			//Predicate telefoneFinPredicate = builder.lessThanOrEqualTo(root.get("telefone"), telefoneFin);
		}
		
		
		criteria.where(predicates.toArray(new Predicate[0]));
		
		TypedQuery<Contato> query = manager.createQuery(criteria);
		return query.getResultList();
		
		
		
		
		
		/*
		//consulta dinâmica sem criteria query
		//monta um corpo de string com as informações concatenadas
		var jpql = new StringBuilder(); 
		//começa a cocatenar as informações (append)
		*	jpql.append("from Contato where 0 = 0 " );
		*
		*	var parametros = new HashMap<String , Object>();
		*	
		*	
		*	//essa função verifica se a string é nula e se n esta vazio
		*	if (StringUtils.hasLength(nome)) {
		*		jpql.append("and nome like :nome  ");
		*		parametros.put("nome", "%" + nome + "%");	
		*	}
		*	
		*	if ( telefoneIni != null) {
		*		jpql.append("and telefone >= :telefoneIni ");
		*		parametros.put("telefoneIni", telefoneIni);
		*	}
		*	
		*	if ( telefoneFin != null) {
		*		jpql.append("and telefone <= :telefoneFin ");
		*		parametros.put("telefoneFin", telefoneFin);
		*	}
		*	
		*	//tipando a variável query para poder mapear o retorno
		*	//como a variavel jpql virou um stringBuilder e o createquery recebe um string , ele precisa ser declarado dessa forma
		*	TypedQuery<Contato> query = manager.createQuery(jpql.toString()	, Contato.class);
		*	
		*	
		*	//mapeado os parametros para devolver os valores
		*	parametros.forEach((chave,valor) -> query.setParameter(chave, valor));
		*	
		*	
		*	return query.getResultList();
		*/	
			
	}
	

}
