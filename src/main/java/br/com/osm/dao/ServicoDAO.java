package br.com.osm.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.osm.entidades.Servico;

public class ServicoDAO extends GenericoDAO<Long, Servico> {

	public ServicoDAO() {
		super(Servico.class, null);
	}

	@Inject
	public ServicoDAO(EntityManager entityManager) {
		super(Servico.class, entityManager);
	}

}
