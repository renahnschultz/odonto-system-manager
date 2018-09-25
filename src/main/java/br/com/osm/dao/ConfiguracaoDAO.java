package br.com.osm.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.osm.entidades.Configuracao;

public class ConfiguracaoDAO extends GenericoDAO<Long, Configuracao> {

	public ConfiguracaoDAO() {
		super(Configuracao.class, null);
	}

	@Inject
	public ConfiguracaoDAO(EntityManager entityManager) {
		super(Configuracao.class, entityManager);
	}
	
}
