package br.com.osm.beans;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.osm.dao.ConfiguracaoDAO;
import br.com.osm.entidades.Configuracao;
import br.com.osm.rest.ConfiguracaoWebService;

/**
 * Classe responsável pelo controle da tela de cadastro de Paciente.
 *
 * @author Lucas 28-07-2018
 *
 */
@Named
@ViewScoped
public class ConfiguracaoBean implements Serializable {

	@Inject
	transient private ConfiguracaoDAO configuracaoDAO;
	private Configuracao configuracao = new Configuracao();

	public ConfiguracaoBean() {
	}
	
	public void init() {
		try {
			setConfiguracao(configuracaoDAO.pesquisarPor(1L));
			if(getConfiguracao() == null) {
				setConfiguracao(new Configuracao());
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao iniciar bean de configuração.", e);
		}
	}

	public void salvar() {
		new ConfiguracaoWebService(configuracaoDAO).salvar(getConfiguracao());
		cancelar();
	}

	public void cancelar() {
		setConfiguracao(new Configuracao());
	}

	public Configuracao getConfiguracao() {
		return configuracao;
	}

	public void setConfiguracao(Configuracao configuracao) {
		this.configuracao = configuracao;
	}


}
