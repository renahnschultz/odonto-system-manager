package br.com.osm.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.osm.dao.ConfiguracaoDAO;
import br.com.osm.entidades.Atendimento;
import br.com.osm.entidades.Configuracao;
import br.com.osm.rest.ConfiguracaoWebService;

/**
 * Classe responsável pelo controle da tela de cadastro de Paciente.
 *
 * @author Renahn 28-07-2018
 *
 */
@Named
@SessionScoped
public class AtendimentoBean implements Serializable {

	private Atendimento atendimento;

	public AtendimentoBean() {
	}
	
	public void salvar() {
	}

	public void cancelar() {
	}

	public Atendimento getAtendimento() {
		return atendimento;
	}

	public void setAtendimento(Atendimento atendimento) {
		this.atendimento = atendimento;
	}


}
