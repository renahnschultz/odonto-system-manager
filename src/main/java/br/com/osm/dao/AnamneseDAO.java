package br.com.osm.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.osm.entidades.Anamnese;
import br.com.osm.entidades.Usuario;
import br.com.osm.exception.OSMException;

public class AnamneseDAO extends GenericoDAO<Long, Anamnese> {

	public AnamneseDAO() {
		super(Anamnese.class, null);
	}

	@Inject
	public AnamneseDAO(EntityManager entityManager) {
		super(Anamnese.class, entityManager);
	}
	
	public Anamnese anamneseDoUsuario(Usuario usuario) throws OSMException {
		try {
			StringBuilder sql = new StringBuilder("SELECT p FROM ")
					.append(tipo.getSimpleName())
					.append(" AS p ")
					.append(" WHERE p.usuario = :usuario");
			TypedQuery<Anamnese> query = entityManager.createQuery(sql.toString(), Anamnese.class);
			query.setParameter("usuario", usuario);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new OSMException(e, "erro.dao.generico.listar", tipo.getSimpleName());
		}
	}

}
