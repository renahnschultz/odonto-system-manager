package br.com.osm.test;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.osm.dao.UsuarioDAO;
import br.com.osm.producer.EntityManagerProducer;

public class TesteMedicoDAO {

	public TesteMedicoDAO() {
	}

	private static EntityManager entityManager;
	public UsuarioDAO medicoDAO;

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
		medicoDAO = new UsuarioDAO(entityManager);
//		List<Medico> listMedicos = medicoDAO.listMedicos();
//		System.out.println(listMedicos);
	}

}
