/**
 *
 */
package br.com.osm.producer;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.TimeZone;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;

import br.com.osm.annotations.LazyModel;
import br.com.osm.dao.GenericoDAO;
import br.com.osm.entidades.Entidade;
import br.com.osm.model.AbstractLazyModel;

/**
 *
 * @author Renahn 04-07-2018
 *
 */
@Named
@ViewScoped
public class LazyModelProducer implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings({ "unchecked" })
	@Produces
	@LazyModel
	@Dependent
	public <PK extends Serializable, T extends Entidade<?>> AbstractLazyModel<PK, T> criarAbstractLazyModel(InjectionPoint injectionPoint,
			EntityManager entityManager) {
		try {
			ParameterizedType type = (ParameterizedType) injectionPoint.getType();
			Class<PK> classeId = (Class<PK>) type.getActualTypeArguments()[0];
			Class<T> classe = (Class<T>) type.getActualTypeArguments()[1];
			return new AbstractLazyModel<PK, T>(new GenericoDAO<PK, T>(classe, entityManager), classeId, classe);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao injetar lazy model", e);
		}
	}

	@Produces
	@Named
	public TimeZone getTimeZone() {
		return TimeZone.getTimeZone("America/Sao_Paulo");
	}

	@Produces
	@RequestScoped
	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();

	}

	public static <T> T getInstanciaProgramaticamente(Class<T> classe) {
		try {
			return CDI.current().select(classe).get();
		} catch (Exception e) {
			return null;
		}
	}
}
