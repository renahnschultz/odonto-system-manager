package br.com.osm.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.osm.entidades.Pergunta;

public class PerguntaDAO extends GenericoDAO<Long, Pergunta> {

	public PerguntaDAO() {
		super(Pergunta.class, null);
	}

	@Inject
	public PerguntaDAO(EntityManager entityManager) {
		super(Pergunta.class, entityManager);
	}

}
