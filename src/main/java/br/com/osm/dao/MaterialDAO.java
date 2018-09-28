package br.com.osm.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.osm.entidades.Material;
import br.com.osm.entidades.Servico;
import br.com.osm.enuns.StatusAtivo;
import br.com.osm.exception.OSMException;

public class MaterialDAO extends GenericoDAO<Long, Material> {

	public MaterialDAO() {
		super(Material.class, null);
	}

	@Inject
	public MaterialDAO(EntityManager entityManager) {
		super(Material.class, entityManager);
	}
	
	public List<Material> autoComplete(String nome) throws OSMException {
		try {
			StringBuilder sql = new StringBuilder("SELECT p FROM ")
					.append(tipo.getSimpleName())
					.append(" AS p ")
					.append(" WHERE p.nome LIKE :nome ")
					.append(" AND p.ativo = :ativo ")
					.append(" ORDER BY p.nome ASC ");
			TypedQuery<Material> query = entityManager.createQuery(sql.toString(), Material.class);
			query.setParameter("nome", "%" + nome + "%");
			query.setParameter("ativo", StatusAtivo.ATIVO);
			query.setMaxResults(15);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new OSMException(e, "erro.dao.generico.listar", tipo.getSimpleName());
		}
	}

}
