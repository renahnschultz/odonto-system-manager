package br.com.osm.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.osm.entidades.Permissao;
import br.com.osm.entidades.Usuario;
import br.com.osm.exception.OSMException;

public class PermissaoDAO extends GenericoDAO<Long, Permissao>  {

	public PermissaoDAO() {
		super(Permissao.class, null);
	}

	@Inject
	public PermissaoDAO(EntityManager entityManager) {
		super(Permissao.class, entityManager);
	}

	public List<Permissao> listarTudo() throws OSMException {
		try {
			StringBuilder sql = new StringBuilder("SELECT p FROM ")
					.append(tipo.getSimpleName())
					.append(" AS p ");
			TypedQuery<Permissao> query = entityManager.createQuery(sql.toString(), Permissao.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new OSMException(e, "erro.dao.generico.listar", tipo.getSimpleName());
		}
	}

}
