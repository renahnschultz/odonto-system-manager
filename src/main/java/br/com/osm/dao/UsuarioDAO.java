package br.com.osm.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.osm.entidades.Usuario;
import br.com.osm.enuns.TipoUsuario;
import br.com.osm.exception.OSMException;

public class UsuarioDAO extends GenericoDAO<Long, Usuario> {

	public UsuarioDAO() {
		super(Usuario.class, null);
	}

	@Inject
	public UsuarioDAO(EntityManager entityManager) {
		super(Usuario.class, entityManager);
	}

	public List<Usuario> buscarOdontologos() throws OSMException {
		try {
			StringBuilder sql = new StringBuilder("SELECT p FROM ")
					.append(tipo.getSimpleName())
					.append(" AS p ")
					.append(" WHERE p.tipo = :tipo");
			TypedQuery<Usuario> query = entityManager.createQuery(sql.toString(), Usuario.class);
			query.setParameter("tipo", TipoUsuario.ODONTOLOGO);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new OSMException(e, "erro.dao.generico.listar", tipo.getSimpleName());
		}
	}

}
