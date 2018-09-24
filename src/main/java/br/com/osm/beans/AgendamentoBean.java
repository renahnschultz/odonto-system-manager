package br.com.osm.beans;

import java.beans.Beans;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.osm.annotations.LazyModel;
import br.com.osm.dao.AgendamentoDAO;
import br.com.osm.entidades.Agendamento;
import br.com.osm.entidades.HorarioOdontologo;
import br.com.osm.entidades.Servico;
import br.com.osm.entidades.Usuario;
import br.com.osm.enuns.SituacaoAgendamento;
import br.com.osm.enuns.TipoUsuario;
import br.com.osm.model.AbstractLazyModel;
import br.com.osm.rest.AgendamentoWebService;
import br.com.osm.util.FacesUtil;

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
	private List<Agendamento> agendamentosDisponiveis = new ArrayList<Agendamento>();

	private List<Agendamento> agendamentosAprovar;
	private boolean carregandoAgendamentos = true;
	private Agendamento agendamentoNovo;
	private Agendamento agendamentoAprovar;

	public AgendamentoBean() {
	}

	public void buscarHorarios() {
		try {
			if(data.before(new Date()) || data.equals(new Date())) {
				return;
			}
			agendamentosDisponiveis = new ArrayList<Agendamento>();
			List<Agendamento> agendamentosDoDia = agendamentoDAO.buscarAgendamentosAprovadosEPendentes(odontologo, data);
			HorarioOdontologo primeiroPeriodo = null;
			HorarioOdontologo segundoPeriodo = null;
			LocalDate dia = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			for (HorarioOdontologo horario : odontologo.getHorariosOdontologo()) {
				if (horario.getDiaSemana().ordinal() == dia.getDayOfWeek().ordinal()) {
					if (horario.getPeriodo() == 1) {
						primeiroPeriodo = horario;
					} else if (horario.getPeriodo() == 2) {
						segundoPeriodo = horario;
					}
				}
			}
			LocalDateTime inicio1 = LocalDateTime.ofInstant(primeiroPeriodo.getInicio().toInstant(), ZoneId.systemDefault());
			LocalDateTime fim1 = LocalDateTime.ofInstant(primeiroPeriodo.getFim().toInstant(), ZoneId.systemDefault());
			LocalDateTime inicio2 = LocalDateTime.ofInstant(segundoPeriodo.getInicio().toInstant(), ZoneId.systemDefault());
			LocalDateTime fim2 = LocalDateTime.ofInstant(segundoPeriodo.getFim().toInstant(), ZoneId.systemDefault());

			LocalDateTime horaAdd = LocalDateTime.of(inicio1.getYear(), inicio1.getMonth(), inicio1.getDayOfMonth(), inicio1.getHour(),
					inicio1.getMinute(), inicio1.getSecond());

			LocalDateTime diaSelecionado = LocalDateTime.ofInstant(data.toInstant(), ZoneId.systemDefault());

			while (1 == 1) {
				if (((horaAdd.isAfter(inicio1) || horaAdd.isEqual(inicio1))
						&& horaAdd.isBefore(fim1))
						||
						((horaAdd.isAfter(inicio2) || horaAdd.isEqual(inicio2))
								&& horaAdd.isBefore(fim2))) {
					Agendamento agendamento = new Agendamento(odontologo, Timestamp.valueOf(horaAdd.withYear(diaSelecionado.getYear())
											.withMonth(diaSelecionado.getMonthValue()).withDayOfMonth(diaSelecionado.getDayOfMonth())));
					if(agendamentosDoDia.contains(agendamento)) {
						horaAdd = horaAdd.plusMinutes(30);
						continue;
					}
					agendamentosDisponiveis.add(agendamento);
				}
				if (horaAdd.isAfter(fim2) || horaAdd.isEqual(fim2)) {
					break;
				}
				horaAdd = horaAdd.plusMinutes(30);
			}
			agendamentosDisponiveis.removeAll(agendamentosDoDia);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao carregar agendamentos para aprovar", e);
		}
	}
	
	public void confirmarAgendamento() {
		try {
			agendamentoNovo.setSituacao(SituacaoAgendamento.PENDENTE);
			new AgendamentoWebService(agendamentoDAO).salvar(agendamentoNovo);
			agendamentosDisponiveis.remove(agendamentoNovo);
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao confirmar agendamento", e);
		}
	}
	
	public void configNovoAgendamento() {
		Usuario usuarioLogado = FacesUtil.getUsuarioLogado();
		if(usuarioLogado.getTipo().equals(TipoUsuario.PACIENTE)) {
			this.agendamentoNovo.setPaciente(usuarioLogado);
		}
	}

	public void buscarAgendamentosAprovar() {
		try {
			agendamentosAprovar = agendamentoDAO.buscarAgendamentosPendentes();
			carregandoAgendamentos = false;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao carregar agendamentos para aprovar", e);
		}
	}

	public void aprovarReprovar(Integer valor) {
		try {
			if (valor == 1) {
				agendamentoAprovar.setSituacao(SituacaoAgendamento.APROVADO);
			} else if (valor == 2) {
				agendamentoAprovar.setSituacao(SituacaoAgendamento.REPROVADO);
			}
			new AgendamentoWebService(agendamentoDAO).salvar(agendamentoAprovar);
			agendamentosAprovar.remove(agendamentoAprovar);
		} catch (Exception e) {
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

	public List<Agendamento> getAgendamentosDisponiveis() {
		return agendamentosDisponiveis;
	}

	public void setAgendamentosDisponiveis(List<Agendamento> agendamentosDisponiveis) {
		this.agendamentosDisponiveis = agendamentosDisponiveis;
	}

	public Agendamento getAgendamentoNovo() {
		return agendamentoNovo;
	}

	public void setAgendamentoNovo(Agendamento agendamentoNovo) {
		this.agendamentoNovo = agendamentoNovo;
	}

	public Agendamento getAgendamentoAprovar() {
		return agendamentoAprovar;
	}

	public void setAgendamentoAprovar(Agendamento agendamentoAprovar) {
		this.agendamentoAprovar = agendamentoAprovar;
	}

}
