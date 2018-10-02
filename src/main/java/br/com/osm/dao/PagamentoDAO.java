package br.com.osm.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.osm.entidades.Pagamento;

public class PagamentoDAO extends GenericoDAO<Long, Pagamento> {

	public PagamentoDAO() {
		super(Pagamento.class, null);
	}

	@Inject
	public PagamentoDAO(EntityManager entityManager) {
		super(Pagamento.class, entityManager);
	}
	
}
