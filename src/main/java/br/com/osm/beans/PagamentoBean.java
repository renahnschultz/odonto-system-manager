package br.com.osm.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.osm.dao.DebitoDAO;
import br.com.osm.dao.PagamentoDAO;
import br.com.osm.dao.UsuarioDAO;
import br.com.osm.entidades.Debito;
import br.com.osm.entidades.Pagamento;
import br.com.osm.entidades.Usuario;
import br.com.osm.exception.OSMException;
import br.com.osm.rest.PagamentoWebService;
import br.com.osm.util.FacesUtil;

/**
 * Classe responsável pelo controle da tela de cadastro de Paciente.
 *
 * @author Renahn 28-07-2018
 *
 */
@Named
@ViewScoped
public class PagamentoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	transient private UsuarioDAO usuarioDAO;
	@Inject
	transient private DebitoDAO debitoDAO;
	@Inject
	transient private PagamentoDAO pagamentoDAO;

	private Usuario paciente;
	private Debito debito;

	private List<Debito> debitosPaciente;

	public PagamentoBean() {
	}

	@PostConstruct
	public void init() {
		try {
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao iniciar bean de configuração.", e);
		}
	}

	public void salvar() {
	}

	public void cancelar() {
	}

	public void buscarDebitos() {
		try {
			debitosPaciente = debitoDAO.buscarDebitos(paciente);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao buscar débitos do paciente.", e);
		}
	}

	public List<Usuario> completePaciente(String complete) {
		try {
			List<Usuario> entidades = usuarioDAO.autoCompletePaciente(complete);
			return entidades;
		} catch (OSMException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void infoDebito() {
		try {
			debito = debitoDAO.recuperar(debito);
		} catch (OSMException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao recuperar débito do paciente.", e);
		}
	}

	public void quitarDebito() {
		try {
			Pagamento pagamento = new Pagamento();
			pagamento.setData(new Date());
			pagamento.setRecebedor(FacesUtil.getUsuarioLogado());
			pagamento.setValor(debito.getValor());
			pagamento.setDebito(debito);
			for (Pagamento pagamento1 : debito.getPagamentos()) {
				pagamento.setValor(pagamento.getValor() - pagamento1.getValor());
			}
			debito.getPagamentos().add(pagamento);
			new PagamentoWebService(pagamentoDAO).salvar(pagamento);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao quitar débito do paciente.", e);
		}
	}

	public Usuario getPaciente() {
		return paciente;
	}

	public void setPaciente(Usuario paciente) {
		this.paciente = paciente;
	}

	public Debito getDebito() {
		return debito;
	}

	public void setDebito(Debito debito) {
		this.debito = debito;
	}

	public List<Debito> getDebitosPaciente() {
		return debitosPaciente;
	}

	public void setDebitosPaciente(List<Debito> debitosPaciente) {
		this.debitosPaciente = debitosPaciente;
	}

}
