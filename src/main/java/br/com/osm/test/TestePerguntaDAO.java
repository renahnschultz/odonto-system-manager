package br.com.osm.test;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.osm.dao.PerguntaDAO;
import br.com.osm.dao.UsuarioDAO;
import br.com.osm.producer.EntityManagerProducer;

public class TestePerguntaDAO {

	public TestePerguntaDAO() {
	}

	private static EntityManager entityManager;
	public PerguntaDAO dao;

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
	}

}
