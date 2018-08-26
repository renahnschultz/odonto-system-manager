package br.com.osm.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.osm.entidades.Odontograma;
import br.com.osm.entidades.Usuario;
import br.com.osm.exception.OSMException;

public class OdontogramaDAO extends GenericoDAO<Long, Odontograma> {

	public OdontogramaDAO() {
		super(Odontograma.class, null);
	}

	@Inject
	public OdontogramaDAO(EntityManager entityManager) {
		super(Odontograma.class, entityManager);
	}
	
	public Odontograma odontogramaDoPaciente(Usuario paciente) throws OSMException {
		try {
			StringBuilder sql = new StringBuilder("SELECT p FROM ")
					.append(tipo.getSimpleName())
					.append(" AS p ")
					.append(" WHERE p.paciente = :usuario");
			TypedQuery<Odontograma> query = entityManager.createQuery(sql.toString(), Odontograma.class);
			query.setParameter("usuario", paciente);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new OSMException(e, "erro.dao.generico.listar", tipo.getSimpleName());
		}
	}

}
