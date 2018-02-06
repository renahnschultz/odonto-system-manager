package br.com.osm.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.osm.entidades.Usuario;

public class UsuarioDAO extends GenericoDAO<Long, Usuario> {

	public UsuarioDAO() {
		super(Usuario.class, null);
	}

	@Inject
	public UsuarioDAO(EntityManager entityManager) {
		super(Usuario.class, entityManager);
	}

}
