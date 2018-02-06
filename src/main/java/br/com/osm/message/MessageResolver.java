/**
 *
 */
package br.com.osm.message;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Specializes;
import javax.faces.context.FacesContext;

import org.apache.deltaspike.core.api.message.MessageContext;
import org.apache.deltaspike.jsf.impl.message.JsfMessageResolver;

/**
 *
 * @author Renahn 06-02-2018
 *
 */
@Specializes
public class MessageResolver extends JsfMessageResolver {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected List<String> getMessageSources(MessageContext messageContext) {
		List<String> retorno = new ArrayList<String>();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (facesContext == null) {
			retorno.add("ValidationMessages");
			return retorno;
		}
		return super.getMessageSources(messageContext);
	}
}
