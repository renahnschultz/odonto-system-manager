package br.com.osm.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import br.com.osm.dao.AgendamentoDAO;
import br.com.osm.dao.AtendimentoDAO;
import br.com.osm.entidades.Agendamento;
import br.com.osm.entidades.Atendimento;
import br.com.osm.entidades.Usuario;
import br.com.osm.enuns.SituacaoAgendamento;
import br.com.osm.rest.AtendimentoWebService;
import br.com.osm.util.FacesUtil;

/**
 * Classe responsável pelo controle da tela de cadastro de Agendamento.
 *
 * @author Renahn 20-07-2018
 *
 */
@Named
@ViewScoped
public class AgendaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	transient private AtendimentoDAO atendimentoDAO;
	@Inject
	transient private AgendamentoDAO agendamentoDAO;
	private Usuario usuarioLogado = FacesUtil.getUsuarioLogado();
	
	@Inject
	private AtendimentoBean atendimentoBean;

	private ScheduleModel agendaModel;
	private Agendamento agendamento;

	public AgendaBean() {
	}
	
	

	@PostConstruct
	public void init() {
		try {
			Date diaInicio = new Date();
			Date diaFim = Date.from(diaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(7).atStartOfDay()
					.atZone(ZoneId.systemDefault())
					.toInstant());
			List<Agendamento> agendamentos = agendamentoDAO.buscarAgendamentosAprovados(usuarioLogado, diaInicio, diaFim);
			agendaModel = new DefaultScheduleModel();
			for (Agendamento agendamento : agendamentos) {
				agendaModel.addEvent(new DefaultScheduleEvent(agendamento.getPaciente().getNomeCompleto(),
						agendamento.getDataHora(),
						Timestamp.valueOf(agendamento.getDataHora().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusMinutes(30)),
						agendamento));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao montar agenda do Odontólogo", e);
		}
	}
	
	public void iniciarAtendimento() {
		try {
			agendamento = agendamentoDAO.recuperar(agendamento);
			agendamento.setSituacao(SituacaoAgendamento.EXECUCAO);
			Atendimento atendimento = new Atendimento();
			atendimento.setAgendamento(agendamento);
			atendimento.setDataInicio(new Date());
			atendimento.setDataFim(new Date());
			new AtendimentoWebService(atendimentoDAO).salvar(atendimento);
			atendimentoBean.setAtendimento(atendimento);
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao iniciar atendimento.",e);
		}
	}
	
	public void onEventSelect(SelectEvent selectEvent) {
        setAgendamento((Agendamento)((ScheduleEvent) selectEvent.getObject()).getData());
    }

	public void salvar() {
		cancelar();
	}

	public void cancelar() {
	}

	public void excluir() {
	}

	public ScheduleModel getAgendaModel() {
		return agendaModel;
	}

	public void setAgendaModel(ScheduleModel agendaModel) {
		this.agendaModel = agendaModel;
	}



	public Agendamento getAgendamento() {
		return agendamento;
	}



	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}

}
