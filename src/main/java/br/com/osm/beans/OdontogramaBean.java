package br.com.osm.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.context.PrimeFacesContext;
import org.primefaces.context.RequestContext;

import br.com.osm.dao.DenteDAO;
import br.com.osm.dao.OdontogramaDAO;
import br.com.osm.entidades.Dente;
import br.com.osm.entidades.DenteOdontograma;
import br.com.osm.entidades.Marcacao;
import br.com.osm.entidades.Odontograma;
import br.com.osm.entidades.Usuario;
import br.com.osm.exception.OSMException;
import br.com.osm.rest.OdontogramaWebService;
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
	
	
	private Marcacao marcacao = new Marcacao();
	private Long denteId;

	public OdontogramaBean() {
	}

	@PostConstruct
	public void init() {
		try {
			odontograma = odontogramaDAO.odontogramaDoPaciente(usuarioLogado);
			Collections.sort(odontograma.getDentes(), new Comparator<DenteOdontograma>() {

				@Override
				public int compare(DenteOdontograma o1, DenteOdontograma o2) {
					if(o1.getDente().getOrdem() > o2.getDente().getOrdem()) {
						return 1;
					} else if(o1.getDente().getOrdem() < o2.getDente().getOrdem()) {
						return -1;
					}
					return 0;
				}
			});
		} catch (Exception e) {
			throw new RuntimeException("Erro ao iniciar anamnese.", e);
		}
	}

	public void imprimirMarcacoes() {
		for(Marcacao marcacao : odontograma.getMarcacoes()) {
			PrimeFaces.current().executeScript("drawCoordinates(" + marcacao.getPosX().toString() +", " + marcacao.getPosY().toString() + ", #canvas" + marcacao.getDente().getDente().getId().toString() + ", false)");
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
			salvar();
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
			for(DenteOdontograma dente : odontograma.getDentes()) {
				if(dente.getDente().getId() == denteId) {
					marcacao.setDente(dente);
					break;
				}
			}
			marcacao.setNome("Nome da marcação");
			marcacao.getDente().adicionarMarcacao(marcacao);
			marcacao.setOdontograma(odontograma);
			marcacao.setDataHora(new Date());
			odontograma.adicionarMarcacao(marcacao);
			marcacao = new Marcacao();
			salvar();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao iniciar anamnese.", e);
		}
	}

	public void salvar() {
		try {
			new OdontogramaWebService(odontogramaDAO).salvar(odontograma);
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

	public Long getDenteId() {
		return denteId;
	}

	public void setDenteId(Long denteId) {
		this.denteId = denteId;
	}

	public Marcacao getMarcacao() {
		return marcacao;
	}

	public void setMarcacao(Marcacao marcacao) {
		this.marcacao = marcacao;
	}

}
