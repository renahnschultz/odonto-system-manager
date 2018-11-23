package br.com.osm.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.osm.dao.AgendamentoDAO;
import br.com.osm.entidades.Agendamento;
import br.com.osm.exception.OSMException;
import br.com.osm.util.FacesUtil;

/**
 * Classe responsável pelo controle da dashboard de odontologo e administrador
 *
 * @author Renahn 28-07-2018
 *
 */
@Named
@ViewScoped
public class DashboardPacienteBean implements Serializable {

	@Inject
	private LoginBean loginBean;
	private List<Agendamento> agendamentosPaciente;
	@Inject
	private AgendamentoDAO agendamentoDAO;

	@PostConstruct
	public void init() {
		try {
			agendamentosPaciente = agendamentoDAO.buscarAgendamentosPaciente(FacesUtil.getUsuarioLogado());
		} catch (OSMException e) {
			e.printStackTrace();
		}
	}

	public List<Agendamento> getAgendamentosPaciente() {
		return agendamentosPaciente;
	}

	public void setAgendamentosPaciente(List<Agendamento> agendamentosPaciente) {
		this.agendamentosPaciente = agendamentosPaciente;
	}

}
