package br.com.osm.producer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

import org.eclipse.persistence.config.PersistenceUnitProperties;

@ApplicationScoped
public class EntityManagerProducer implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	final String PROVIDER = "odonto-system-manager";

	private static EntityManagerFactory factoryTeste;

	EntityManagerFactory emf = Persistence.createEntityManagerFactory(PROVIDER);

	@Produces
	@Default
	@RequestScoped
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public static EntityManager criarEntityManagerTeste() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.CREATE_OR_EXTEND);
		if (factoryTeste == null) {
			factoryTeste = Persistence.createEntityManagerFactory("odonto-system-manager-teste", parametros);
		}
		return factoryTeste.createEntityManager();
	}

	public void fecharEntityManager(@Disposes EntityManager entityManager) {
		if (entityManager != null) {
			entityManager.close();
		}
	}

}
