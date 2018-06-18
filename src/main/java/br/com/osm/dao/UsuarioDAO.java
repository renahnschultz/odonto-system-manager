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

//	/**
//	 * TODO COMENTARIO
//	 *
//	 * @param descricao
//	 * @return
//	 * @throws OSMException
//	 */
//	public List<Medico> listMedicos() throws Exception {
//		try {
//			StringBuilder jpql = new StringBuilder(" SELECT m FROM Medico m ");
//			return entityManager.createQuery(jpql.toString(), Medico.class).getResultList();
//		} catch (NoResultException e) {
//			return null;
//		} catch (Exception e) {
//		}
//		return null;
//	}

}
