package br.com.osm.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.osm.entidades.Marcacao;

public class MarcacaoDAO extends GenericoDAO<Long, Marcacao> {

	public MarcacaoDAO() {
		super(Marcacao.class, null);
	}

	@Inject
	public MarcacaoDAO(EntityManager entityManager) {
		super(Marcacao.class, entityManager);
	}
	
}
