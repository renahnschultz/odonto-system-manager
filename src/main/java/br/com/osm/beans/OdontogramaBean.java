package br.com.osm.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.osm.dao.DenteDAO;
import br.com.osm.dao.OdontogramaDAO;
import br.com.osm.entidades.Dente;
import br.com.osm.entidades.DenteOdontograma;
import br.com.osm.entidades.Odontograma;
import br.com.osm.entidades.Usuario;
import br.com.osm.exception.OSMException;
import br.com.osm.util.FacesUtil;

/**
 * Classe responsável pelo controle da tela de cadastro de Anamnese.
 *
 * @author Renahn 28-07-2018
 *
 */
@Named
@ViewScoped
public class OdontogramaBean implements Serializable {

	@Inject
	transient private OdontogramaDAO odontogramaDAO;
	@Inject
	transient private DenteDAO denteDAO;
	private Odontograma odontograma;

	private Usuario usuarioLogado = FacesUtil.getUsuarioLogado();
	
	private Double coorX;
	private Double coorY;
	private Long denteId;

	public OdontogramaBean() {
	}

	@PostConstruct
	public void init() {
		try {
			odontograma = odontogramaDAO.odontogramaDoPaciente(usuarioLogado);
			criarNovoOdontograma();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao iniciar anamnese.", e);
		}
	}

	public void criarNovoOdontograma() throws OSMException {
		try {
			List<Dente> dentes = denteDAO.listarTudo();
			odontograma = new Odontograma();
			odontograma.setPaciente(usuarioLogado);
			odontograma.setDentes(new ArrayList<DenteOdontograma>());
			inicializarDentes(dentes);
			Collections.sort(odontograma.getDentes(), new Comparator<DenteOdontograma>() {

				@Override
				public int compare(DenteOdontograma o1, DenteOdontograma o2) {
					if(o1.getDente().getOrdem() > o2.getDente().getOrdem()) {
						return -1;
					} else if(o1.getDente().getOrdem() > o2.getDente().getOrdem()) {
						return 1;
					}
					return 0;
				}
			});
		} catch (Exception e) {
			throw new OSMException(e, "Erro ao iniciar anamnese.");
		}
	}

	private void inicializarDentes(List<Dente> dentes) {
		for (Dente dente : dentes) {
			odontograma.adicionarDente(new DenteOdontograma(odontograma, dente));
		}
	}
	
	public void processarMarcacao() {
		try {
			Marcacao marcacao = new Marcacao();
			odontograma.getDentes();
			
		} catch (Exception e) {
			throw new RuntimeException("Erro ao iniciar anamnese.", e);
		}
	}

	public void salvar() {
		try {
//			odontograma.setDataPreenchimento(new Date());
//			new AnamneseWebService(odontogramaDAO).salvar(odontograma);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao iniciar anamnese.", e);
		}
	}

	public Odontograma getOdontograma() {
		return odontograma;
	}

	public void setOdontograma(Odontograma odontograma) {
		this.odontograma = odontograma;
	}

	public Double getCoorY() {
		return coorY;
	}

	public void setCoorY(Double coorY) {
		this.coorY = coorY;
	}

	public Double getCoorX() {
		return coorX;
	}

	public void setCoorX(Double coorX) {
		this.coorX = coorX;
	}

	public Long getDenteId() {
		return denteId;
	}

	public void setDenteId(Long denteId) {
		this.denteId = denteId;
	}

}
