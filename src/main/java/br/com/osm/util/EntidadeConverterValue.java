/**
 *
 */
package br.com.osm.util;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.osm.entidades.Entidade;

/**
 * @author Renahn
 *
 */
@FacesConverter(value = "entidadeConverterValue")
public class EntidadeConverterValue implements Converter {

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
		if (value != null && !value.equals("null") && !value.isEmpty()) {
			return component.getAttributes().get(value);
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
}
