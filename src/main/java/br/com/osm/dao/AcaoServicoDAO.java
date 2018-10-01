package br.com.osm.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.osm.entidades.AcaoServico;

public class AcaoServicoDAO extends GenericoDAO<Long, AcaoServico> {

	public AcaoServicoDAO() {
		super(AcaoServico.class, null);
	}

	@Inject
	public AcaoServicoDAO(EntityManager entityManager) {
		super(AcaoServico.class, entityManager);
	}
	
}
