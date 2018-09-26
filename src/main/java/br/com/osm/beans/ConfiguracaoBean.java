package br.com.osm.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
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
	
	@PostConstruct
	public void init() {
		try {
			configuracao = configuracaoDAO.pesquisarPor(1L);
			if(getConfiguracao() == null) {
				Configuracao configuracao2 = new Configuracao();
				configuracao2.setId(1L);
				configuracao =  configuracao2;
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao iniciar bean de configuração.", e);
		}
	}

	public void salvar() {
		new ConfiguracaoWebService(configuracaoDAO).salvar(getConfiguracao());
	}

	public void cancelar() {
	}

	public Configuracao getConfiguracao() {
		return configuracao;
	}

	public void setConfiguracao(Configuracao configuracao) {
		this.configuracao = configuracao;
	}


}
