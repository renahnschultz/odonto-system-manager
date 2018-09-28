package br.com.osm.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.osm.entidades.Odontograma;
import br.com.osm.entidades.Servico;
import br.com.osm.enuns.StatusAtivo;
import br.com.osm.exception.OSMException;

public class ServicoDAO extends GenericoDAO<Long, Servico> {

	public ServicoDAO() {
		super(Servico.class, null);
	}

	@Inject
	public ServicoDAO(EntityManager entityManager) {
		super(Servico.class, entityManager);
	}
	
	public List<Servico> autoComplete(String nome) throws OSMException {
		try {
			StringBuilder sql = new StringBuilder("SELECT p FROM ")
					.append(tipo.getSimpleName())
					.append(" AS p ")
					.append(" WHERE p.nome LIKE :nome ")
					.append(" AND p.ativo = :ativo ")
					.append(" ORDER BY p.nome ASC ");
			TypedQuery<Servico> query = entityManager.createQuery(sql.toString(), Servico.class);
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
