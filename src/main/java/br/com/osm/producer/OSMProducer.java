/**
 *
 */
package br.com.osm.producer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.faces.context.FacesContext;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.PreDestroyApplicationEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.deltaspike.core.api.message.MessageContext;

import br.com.osm.enuns.TipoServico;

/**
 *
 * @author Renahn 22/07/2018
 *
 */
@Named
@ApplicationScoped
public class OSMProducer {

	@Inject
	private MessageContext mensagens;

	@PostConstruct
	public void postConstruct() {
	}

	@PreDestroy
	public void preDestroy() {
	}

	public void destruir(@Observes PreDestroyApplicationEvent event) {
	}

	public void inicializar(@Observes PostConstructApplicationEvent event) {
	}

	@Named
	public List<TipoServico> tiposServico() {
		List<TipoServico> tipoServicos = new ArrayList<TipoServico>();
		for (TipoServico tipo : TipoServico.values()) {
			tipoServicos.add(tipo);
		}
		return tipoServicos;
	}

	public String currencyCode() {
		Locale browserLocale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		return "R$ ";
	}

}
