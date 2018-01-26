package br.com.osm.test;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.osm.dao.MedicoDAO;
import br.com.osm.entidades.Medico;
import br.com.osm.utils.EntityManagerProducer;

public class TesteMedicoDAO {

	public TesteMedicoDAO() {
	}

	private static EntityManager entityManager;
	public MedicoDAO medicoDAO;

	@BeforeClass
	public static void beforeClass() {
		entityManager = EntityManagerProducer.criarEntityManagerTeste();
	}

	@Before
	public void before() {
		entityManager.getTransaction().begin();
	}

	@After
	public void after() {
		entityManager.getTransaction().rollback();
	}

	@Test
	public void selecionarMedicos() throws Exception {
		medicoDAO = new MedicoDAO(entityManager);
		List<Medico> listMedicos = medicoDAO.listMedicos();
		System.out.println(listMedicos);
	}

}
