package br.com.osm.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.osm.dao.HorarioOdontologoDAO;
import br.com.osm.dao.UsuarioDAO;
import br.com.osm.entidades.HorarioOdontologo;
import br.com.osm.entidades.Usuario;
import br.com.osm.enuns.DiaSemana;
import br.com.osm.rest.UsuarioWebService;
import br.com.osm.util.FacesUtil;
import br.com.osm.util.OrdenaHorariosOdontologo;

/**
 * Classe responsável pelo controle da tela de cadastro de Paciente.
 *
 * @author Renahn 20-07-2018
 *
 */
@Named
@ViewScoped
public class HorarioOdontologoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	transient private HorarioOdontologoDAO horarioOdontologoDAO;
	@Inject
	transient private UsuarioDAO usuarioDAO;

	private Usuario usuarioLogado = FacesUtil.getUsuarioLogado();
	private List<HorarioOdontologo> horariosOdontologo = new ArrayList<HorarioOdontologo>();

	public HorarioOdontologoBean() {
	}

	@PostConstruct
	public void init() {
		horariosOdontologo = usuarioLogado.getHorariosOdontologo();
		if (horariosOdontologo.isEmpty()) {
			novoHorarioOdontologo();
		}
		Collections.sort(horariosOdontologo, new OrdenaHorariosOdontologo());
	}

	public void novoHorarioOdontologo() {
		horariosOdontologo = new ArrayList<HorarioOdontologo>();
		for (DiaSemana dia : DiaSemana.values()) {
			HorarioOdontologo horario = new HorarioOdontologo();
			horario.setDiaSemana(dia);
			horario.setPeriodo((short) 1);
			horario.setOdontologo(usuarioLogado);
			horario.setInicio(Timestamp.valueOf(LocalDateTime.now().withHour(8).withMinute(30).withSecond(0)));
			horario.setFim(Timestamp.valueOf(LocalDateTime.now().withHour(12).withMinute(0).withSecond(0)));
			HorarioOdontologo horario2 = new HorarioOdontologo();
			horario2.setDiaSemana(dia);
			horario2.setPeriodo((short) 2);
			horario2.setOdontologo(usuarioLogado);
			horario2.setInicio(Timestamp.valueOf(LocalDateTime.now().withHour(13).withMinute(30).withSecond(0)));
			horario2.setFim(Timestamp.valueOf(LocalDateTime.now().withHour(18).withMinute(0).withSecond(0)));
			horariosOdontologo.add(horario);
			horariosOdontologo.add(horario2);
		}
		usuarioLogado.setHorariosOdontologo(horariosOdontologo);
	}

	public void salvar() {
		usuarioLogado.setHorariosOdontologo(horariosOdontologo);
		new UsuarioWebService(usuarioDAO).salvar(usuarioLogado);
		cancelar();
	}

	public void cancelar() {
		horariosOdontologo = new ArrayList<HorarioOdontologo>();
	}

	public List<HorarioOdontologo> getHorariosOdontologo() {
		return horariosOdontologo;
	}

	public void setHorariosOdontologo(List<HorarioOdontologo> horariosOdontologo) {
		this.horariosOdontologo = horariosOdontologo;
	}

}
