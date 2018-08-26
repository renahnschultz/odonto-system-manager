package br.com.osm.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.osm.entidades.Dente;

public class DenteDAO extends GenericoDAO<Long, Dente> {

	public DenteDAO() {
		super(Dente.class, null);
	}

	@Inject
	public DenteDAO(EntityManager entityManager) {
		super(Dente.class, entityManager);
	}
	
}
