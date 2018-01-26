package br.com.osm.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.osm.entidades.Medico;

public class MedicoDAO extends GenericoDAO<Long, Medico> {

	public MedicoDAO() {
		super(Medico.class, null);
	}

	@Inject
	public MedicoDAO(EntityManager entityManager) {
		super(Medico.class, entityManager);
	}

	/**
	 * TODO COMENTARIO
	 *
	 * @param descricao
	 * @return
	 * @throws MagicTradeException
	 */
	public List<Medico> listMedicos() throws Exception {
		try {
			StringBuilder jpql = new StringBuilder(" SELECT m FROM Medico m ");
			return entityManager.createQuery(jpql.toString(), Medico.class).getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
		}
		return null;
	}

}
