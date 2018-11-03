package br.com.osm.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.osm.entidades.Auditoria;

public class AuditoriaDAO extends GenericoDAO<Long, Auditoria> {

	public AuditoriaDAO() {
		super(Auditoria.class, null);
	}

	@Inject
	public AuditoriaDAO(EntityManager entityManager) {
		super(Auditoria.class, entityManager);
	}

}
