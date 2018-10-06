/**
 *
 */
package br.com.osm.producer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.PreDestroyApplicationEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.deltaspike.core.api.message.MessageContext;

import br.com.osm.dao.ServicoDAO;
import br.com.osm.dao.UsuarioDAO;
import br.com.osm.entidades.Usuario;
import br.com.osm.enuns.SimNao;
import br.com.osm.enuns.SimNaoOutro;
import br.com.osm.enuns.TipoPagamento;
import br.com.osm.enuns.TipoResposta;
import br.com.osm.enuns.TipoServico;
import br.com.osm.enuns.TipoUsuario;
import br.com.osm.enuns.UnidadeMedida;
import br.com.osm.exception.OSMException;

/**
 *
 * @author Renahn 22/07/2018
 *
 */
@Named
@ApplicationScoped
public class OSMProducer implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private MessageContext mensagens;
	
	@Inject
	private transient UsuarioDAO usuarioDAO;
	@Inject
	private transient ServicoDAO servicoDAO;

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
	@Produces
	public List<TipoServico> tiposServico() {
		List<TipoServico> tipoServicos = new ArrayList<TipoServico>();
		for (TipoServico tipo : TipoServico.values()) {
			tipoServicos.add(tipo);
		}
		return tipoServicos;
	}

	@Named
	@Produces
	public List<TipoUsuario> tiposUsuario() {
		List<TipoUsuario> tipoUsuario = new ArrayList<TipoUsuario>();
		for (TipoUsuario tipo : TipoUsuario.values()) {
			tipoUsuario.add(tipo);
		}
		return tipoUsuario;
	}

	@Named
	@Produces
	public List<UnidadeMedida> unidadesMedida() {
		List<UnidadeMedida> unidadesMedida = new ArrayList<UnidadeMedida>();
		for (UnidadeMedida medida : UnidadeMedida.values()) {
			unidadesMedida.add(medida);
		}
		return unidadesMedida;
	}

	@Named
	@Produces
	public List<SimNao> simNaoProducer() {
		List<SimNao> lista = new ArrayList<SimNao>();
		for (SimNao object : SimNao.values()) {
			lista.add(object);
		}
		return lista;
	}

	@Named
	@Produces
	public List<SimNaoOutro> simNaoOutroProducer() {
		List<SimNaoOutro> lista = new ArrayList<SimNaoOutro>();
		for (SimNaoOutro object : SimNaoOutro.values()) {
			lista.add(object);
		}
		return lista;
	}

	@Named
	@Produces
	public List<TipoPagamento> tiposPagamento() {
		List<TipoPagamento> lista = new ArrayList<TipoPagamento>();
		for (TipoPagamento object : TipoPagamento.values()) {
			lista.add(object);
		}
		return lista;
	}

	@Named
	@Produces
	public List<TipoResposta> tipoRespostaProducer() {
		List<TipoResposta> lista = new ArrayList<TipoResposta>();
		for (TipoResposta object : TipoResposta.values()) {
			lista.add(object);
		}
		return lista;
	}

	@Named
	@Produces
	public List<Usuario> odontologosSelect() {
		try {
			List<Usuario> buscarOdontologos = usuarioDAO.buscarOdontologos();
			for (Usuario usuario : buscarOdontologos) {
				
			}
			return buscarOdontologos;
		} catch (OSMException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Named
	@Produces
	public TimeZone timeZoneUser() {
		return TimeZone.getTimeZone("GMT");
	}

	public String currencyCode() {
		Locale browserLocale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		return "R$ ";
	}

}
