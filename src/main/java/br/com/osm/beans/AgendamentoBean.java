package br.com.osm.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.osm.annotations.LazyModel;
import br.com.osm.dao.AgendamentoDAO;
import br.com.osm.entidades.Agendamento;
import br.com.osm.entidades.Servico;
import br.com.osm.entidades.Usuario;
import br.com.osm.enuns.SituacaoAgendamento;
import br.com.osm.model.AbstractLazyModel;
import br.com.osm.rest.AgendamentoWebService;
import br.com.osm.rest.ServicoWebService;

/**
 * Classe responsável pelo controle da tela de cadastro de Agendamento.
 *
 * @author Renahn 20-07-2018
 *
 */
@Named
@ViewScoped
public class AgendamentoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	transient private AgendamentoDAO agendamentoDAO;
	private Servico servico = new Servico();
	private Usuario odontologo;
	private Date data;
	@Inject
	@LazyModel
	private AbstractLazyModel<Long, Servico> servicoLazy;
	
	private List<Agendamento> agendamentosAprovar;
	private boolean carregandoAgendamentos = true;

	public AgendamentoBean() {
	}

	public void buscarHorarios() {
		System.out.println("erta");
	}
	
	public void buscarAgendamentosAprovar() {
		try {
			agendamentosAprovar = agendamentoDAO.buscarAgendamentosPendentes();
			carregandoAgendamentos = false;
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao carregar agendamentos para aprovar", e);
		}
	}
	
	public void aprovarReprovar(Agendamento agendamento, Integer valor) {
		try {
			if(valor == 1) {
				agendamento.setSituacao(SituacaoAgendamento.APROVADO);
			}else if(valor ==2) {
				agendamento.setSituacao(SituacaoAgendamento.REPROVADO);
			}
			new AgendamentoWebService(agendamentoDAO).salvar(agendamento);
			agendamentosAprovar.remove(agendamento);
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao aprovar/reprovar agendamento", e);
		}
	}
	
	public void salvar() {
		cancelar();
	}

	public void cancelar() {
		servico = new Servico();
	}

	public void excluir() {
		servico = new Servico();
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

	public Usuario getOdontologo() {
		return odontologo;
	}

	public void setOdontologo(Usuario odontologo) {
		this.odontologo = odontologo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public List<Agendamento> getAgendamentosAprovar() {
		return agendamentosAprovar;
	}

	public void setAgendamentosAprovar(List<Agendamento> agendamentosAprovar) {
		this.agendamentosAprovar = agendamentosAprovar;
	}

	public boolean isCarregandoAgendamentos() {
		return carregandoAgendamentos;
	}

	public void setCarregandoAgendamentos(boolean carregandoAgendamentos) {
		this.carregandoAgendamentos = carregandoAgendamentos;
	}

}
