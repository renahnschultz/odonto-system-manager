package br.com.osm.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.osm.entidades.Debito;
import br.com.osm.entidades.Usuario;
import br.com.osm.enuns.TipoUsuario;
import br.com.osm.exception.OSMException;

public class DebitoDAO extends GenericoDAO<Long, Debito> {

	public DebitoDAO() {
		super(Debito.class, null);
	}

	@Inject
	public DebitoDAO(EntityManager entityManager) {
		super(Debito.class, entityManager);
	}
	
	public List<Debito> buscarDebitos(Usuario paciente) throws OSMException {
		try {
			StringBuilder sql = new StringBuilder("SELECT p FROM ")
					.append(tipo.getSimpleName())
					.append(" AS p ")
					.append(" WHERE p.agendamento.paciente = :paciente");
			TypedQuery<Debito> query = entityManager.createQuery(sql.toString(), Debito.class);
			query.setParameter("paciente", paciente);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new OSMException(e, "erro.dao.generico.listar", tipo.getSimpleName());
		}
	}

}
