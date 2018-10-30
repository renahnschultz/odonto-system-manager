package br.com.osm.beans;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.osm.dao.AtendimentoDAO;
import br.com.osm.dao.DebitoDAO;
import br.com.osm.entidades.AcaoServico;
import br.com.osm.entidades.Atendimento;
import br.com.osm.entidades.Debito;
import br.com.osm.enuns.SituacaoAgendamento;
import br.com.osm.exception.OSMException;
import br.com.osm.rest.AtendimentoWebService;
import br.com.osm.rest.DebitoWebService;
import br.com.osm.util.FacesUtil;

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
	@Inject
	transient private DebitoDAO debitoDAO;

	private Atendimento atendimento;

	public AtendimentoBean() {
	}

	@PostConstruct
	public void init() {
		try {
			atendimento = atendimentoDAO.atendimentoEmExecucao(FacesUtil.getUsuarioLogado());
		} catch (OSMException e) {
			e.printStackTrace();
		}
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
			atendimento.getAgendamento().setAtendimento(atendimento);
			new AtendimentoWebService(atendimentoDAO).salvar(atendimento);
			if (atendimento.getValorTotal() > 0.0) {
				Debito debito = new Debito();
				debito.setAgendamento(atendimento.getAgendamento());
				debito.setValor(atendimento.getValorTotal());
				debito.setMotivo("Débito de atendimento finalizado.");
				debito.setData(new Date());
				new DebitoWebService(debitoDAO).salvar(debito);
			}
			atendimento = null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao iniciar atendimento.", e);
		}
	}

}
