package br.com.osm.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.osm.entidades.Anamnese;
import br.com.osm.entidades.Pergunta;
import br.com.osm.exception.OSMException;

public class PerguntaDAO extends GenericoDAO<Long, Pergunta> {

	public PerguntaDAO() {
		super(Pergunta.class, null);
	}

	@Inject
	public PerguntaDAO(EntityManager entityManager) {
		super(Pergunta.class, entityManager);
	}
	
	public List<Pergunta> perguntasForaDaAnamnese(Anamnese anamnese) throws OSMException {
		try {
			StringBuilder sql = new StringBuilder("SELECT pha.pergunta FROM ")
					.append(" RespostaAnamnese ")
					.append("  AS pha ")
					.append(" WHERE pha.anamnese = :anamnese  ");
			TypedQuery<Pergunta> queryPergunta = entityManager.createQuery(sql.toString(), Pergunta.class);
			queryPergunta.setParameter("anamnese", anamnese);
			List<Pergunta> resultList = queryPergunta.getResultList();
			
			sql = new StringBuilder("SELECT p FROM ")
					.append(tipo.getSimpleName())
					.append(" AS p ");
			TypedQuery<Pergunta> query = entityManager.createQuery(sql.toString(), Pergunta.class);
			List<Pergunta> perguntas = query.getResultList();
			perguntas.removeAll(resultList);
			return perguntas;
		} catch (Exception e) {
			throw new OSMException(e, "erro.dao.generico.listar", tipo.getSimpleName());
		}
	}

}
