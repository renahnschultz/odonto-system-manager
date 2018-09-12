package br.com.osm.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.osm.entidades.HorarioOdontologo;
import br.com.osm.entidades.Servico;

public class HorarioOdontologoDAO extends GenericoDAO<Long, HorarioOdontologo> {

	public HorarioOdontologoDAO() {
		super(HorarioOdontologo.class, null);
	}

	@Inject
	public HorarioOdontologoDAO(EntityManager entityManager) {
		super(HorarioOdontologo.class, entityManager);
	}

}
