/**
 *
 */
package br.com.osm.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;

import br.com.osm.entidades.Entidade;

public class GenericoDAO<PK extends Serializable, TipoClasse extends Entidade<?>> {

	protected EntityManager entityManager;

	protected Class<TipoClasse> tipo;

	protected final String pacoteEntidade;

	public GenericoDAO(Class<TipoClasse> tipoClasse, EntityManager entityManager) {
		this.tipo = tipoClasse;
		this.entityManager = entityManager;
		this.pacoteEntidade = this.tipo.getPackage().getName();
	}

	public CriteriaBuilder getCriteriaBuilder() {
		return entityManager.getCriteriaBuilder();
	}

}
