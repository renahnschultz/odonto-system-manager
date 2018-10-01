package br.com.osm.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.osm.entidades.Atendimento;

public class AtendimentoDAO extends GenericoDAO<Long, Atendimento> {

	public AtendimentoDAO() {
		super(Atendimento.class, null);
	}

	@Inject
	public AtendimentoDAO(EntityManager entityManager) {
		super(Atendimento.class, entityManager);
	}
	
}
