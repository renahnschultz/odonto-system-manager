package br.com.osm.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.osm.entidades.Debito;

public class DebitoDAO extends GenericoDAO<Long, Debito> {

	public DebitoDAO() {
		super(Debito.class, null);
	}

	@Inject
	public DebitoDAO(EntityManager entityManager) {
		super(Debito.class, entityManager);
	}

}
