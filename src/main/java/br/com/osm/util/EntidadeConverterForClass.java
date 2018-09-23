/**
 *
 */
package br.com.osm.util;

import java.io.Serializable;
import java.lang.reflect.Method;

import javax.el.ValueExpression;
import javax.enterprise.inject.Instance;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.osm.entidades.Entidade;

/**
 * @author Renahn
 *
 */
@FacesConverter(forClass = Entidade.class)
public class EntidadeConverterForClass implements Converter {

	@Inject
	private Instance<EntityManager> instanciaEntityManager;

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
		if (value != null && !value.equals("null") && !value.isEmpty()) {
			Object object = component.getAttributes().get(value);
			if (object == null) {
				object = carregaObjetoDoBancoDeDados(facesContext, component, value);
			}
			return object;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
		if (value != null && value instanceof Entidade) {
			Entidade<Serializable> entidadeInfralog = (Entidade<Serializable>) value;
			if (entidadeInfralog.getId() != null) {
//				return String.valueOf(entidadeInfralog.getId());
				String codigo = String.valueOf(entidadeInfralog.getId());
				component.getAttributes().put(codigo, value);
				return codigo;
			}
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object carregaObjetoDoBancoDeDados(FacesContext context, UIComponent component, String value) {
		try {
			if (value == null
					|| value.trim().isEmpty()) {
				return null;
			}
			ValueExpression valueExpression = component.getValueExpression("value");
			if (valueExpression == null) {
				return null;
			}
			Class classe = valueExpression.getType(context.getELContext());
			if (classe == null) {
				return null;
			}
			Method method = classe.getDeclaredMethod("getId");
			Class classeId = method.getReturnType();

			if (classeId == null) {
				return null;
			}
			// o valor do id da entidade
			Serializable id = null;
			// verifica o tipo do id da entidade
			if (Integer.class.isAssignableFrom(classeId)) {
				id = Integer.parseInt(value);
			} else if (Long.class.isAssignableFrom(classeId)) {
				id = Long.parseLong(value);
			}
			return instanciaEntityManager.get().find(classe, id);
		} catch (Exception e) {
			return null;
		}
	}
}
