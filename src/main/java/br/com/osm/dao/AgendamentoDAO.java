package br.com.osm.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.osm.entidades.Agendamento;

public class AgendamentoDAO extends GenericoDAO<Long, Agendamento> {

	public AgendamentoDAO() {
		super(Agendamento.class, null);
	}

	@Inject
	public AgendamentoDAO(EntityManager entityManager) {
		super(Agendamento.class, entityManager);
	}
	
}
