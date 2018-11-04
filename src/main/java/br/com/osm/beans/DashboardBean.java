package br.com.osm.beans;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.osm.dao.DashboardDAO;
import br.com.osm.entidades.Usuario;
import br.com.osm.enuns.TipoUsuario;
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
public class DashboardBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	transient private DashboardDAO dashboardDAO;
	private Long qtdAgendamentos = 0L;
	private Long qtdAtendimentos = 0L;
	private Double debitoFaturado = 0.0;
	private Double debitoAberto= 0.0;
	private Usuario usuarioLogado = FacesUtil.getUsuarioLogado();

	public DashboardBean() {
	}

	@PostConstruct
	public void init() {
		try {
			boolean odontologo = TipoUsuario.ODONTOLOGO.equals(usuarioLogado.getTipo());
			qtdAgendamentos = (Long) dashboardDAO.quantidadeAgendamentos(odontologo ? usuarioLogado : null);
			qtdAtendimentos = (Long) dashboardDAO.quantidadeAtendimentos(odontologo ? usuarioLogado : null);
			debitoFaturado = ((BigDecimal) dashboardDAO.debitoFaturado(odontologo ? usuarioLogado : null)).doubleValue();
			debitoAberto = ((BigDecimal) dashboardDAO.debitoAberto(odontologo ? usuarioLogado : null)).doubleValue();
		} catch (OSMException e) {
			e.printStackTrace();
		}
	}

	public Long getQtdAgendamentos() {
		return qtdAgendamentos;
	}

	public void setQtdAgendamentos(Long qtdAgendamentos) {
		this.qtdAgendamentos = qtdAgendamentos;
	}

	public Long getQtdAtendimentos() {
		return qtdAtendimentos;
	}

	public void setQtdAtendimentos(Long qtdAtendimentos) {
		this.qtdAtendimentos = qtdAtendimentos;
	}

	public Double getDebitoFaturado() {
		return debitoFaturado;
	}

	public void setDebitoFaturado(Double debitoFaturado) {
		this.debitoFaturado = debitoFaturado;
	}

	public Double getDebitoAberto() {
		return debitoAberto;
	}

	public void setDebitoAberto(Double debitoAberto) {
		this.debitoAberto = debitoAberto;
	}

}
