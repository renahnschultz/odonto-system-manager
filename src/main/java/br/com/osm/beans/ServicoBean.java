package br.com.osm.beans;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.deltaspike.core.api.config.view.ViewConfig;

import br.com.oms.enuns.TipoUsuario;
import br.com.osm.annotations.LazyModel;
import br.com.osm.dao.ServicoDAO;
import br.com.osm.entidades.Servico;
import br.com.osm.model.AbstractLazyModel;
import br.com.osm.rest.ServicoWebService;
import br.com.osm.viewconfig.Paginas.aaaa;

/**
 * Classe responsável pelo controle da tela de cadastro de Paciente.
 *
 * @author Renahn 20-07-2018
 *
 */
@Named
@ViewScoped
public class ServicoBean implements Serializable {

	@Inject
	transient private ServicoDAO servicoDAO;
	private Servico servico = new Servico();
	@Inject
	@LazyModel
	private AbstractLazyModel<Long, Servico> servicoLazy;

	public ServicoBean() {
	}

	public Class<? extends ViewConfig> salvar() {
		new ServicoWebService(servicoDAO).salvar(servico);
		return aaaa.class;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public AbstractLazyModel<Long, Servico> getServicoLazy() {
		return servicoLazy;
	}

	public void setServicoLazy(AbstractLazyModel<Long, Servico> servicoLazy) {
		this.servicoLazy = servicoLazy;
	}

	
	
}
