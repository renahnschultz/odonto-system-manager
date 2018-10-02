package br.com.osm.beans;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.osm.dao.AtendimentoDAO;
import br.com.osm.entidades.AcaoServico;
import br.com.osm.entidades.Agendamento;
import br.com.osm.entidades.Atendimento;
import br.com.osm.enuns.SituacaoAgendamento;
import br.com.osm.rest.AtendimentoWebService;

/**
 * Classe responsável pelo controle da tela de cadastro de Paciente.
 *
 * @author Renahn 28-07-2018
 *
 */
@Named
@SessionScoped
public class AtendimentoBean implements Serializable {
	
	@Inject
	transient private AtendimentoDAO atendimentoDAO;

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
	
	public void adicionarAcaoServico(AcaoServico servico) {
		atendimento.adicionarServico(servico);
	}
	
	public void removerAcaoServico(AcaoServico servico) {
		atendimento.removerServico(servico);
	}
	
	public void finalizarAtendimento() {
		try {
			atendimento.setDataFim(new Date());
			atendimento.getAgendamento().setSituacao(SituacaoAgendamento.FINALIZADO);
			new AtendimentoWebService(atendimentoDAO).salvar(atendimento);
			atendimento = null;
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao iniciar atendimento.",e);
		}
	}


}
