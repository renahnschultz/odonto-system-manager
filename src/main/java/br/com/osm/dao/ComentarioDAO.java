package br.com.osm.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.osm.entidades.Comentario;

public class ComentarioDAO extends GenericoDAO<Long, Comentario> {

	public ComentarioDAO() {
		super(Comentario.class, null);
	}

	@Inject
	public ComentarioDAO(EntityManager entityManager) {
		super(Comentario.class, entityManager);
	}
	
}
