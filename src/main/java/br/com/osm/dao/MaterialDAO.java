package br.com.osm.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.osm.entidades.Material;

public class MaterialDAO extends GenericoDAO<Long, Material> {

	public MaterialDAO() {
		super(Material.class, null);
	}

	@Inject
	public MaterialDAO(EntityManager entityManager) {
		super(Material.class, entityManager);
	}

}
