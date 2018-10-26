package br.com.osm.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.osm.dao.AcaoServicoDAO;
import br.com.osm.dao.ComentarioDAO;
import br.com.osm.dao.DenteDAO;
import br.com.osm.dao.MarcacaoDAO;
import br.com.osm.dao.MaterialDAO;
import br.com.osm.dao.OdontogramaDAO;
import br.com.osm.dao.ServicoDAO;
import br.com.osm.entidades.AcaoServico;
import br.com.osm.entidades.AcaoServicoMaterial;
import br.com.osm.entidades.Comentario;
import br.com.osm.entidades.Dente;
import br.com.osm.entidades.DenteOdontograma;
import br.com.osm.entidades.Marcacao;
import br.com.osm.entidades.Material;
import br.com.osm.entidades.Odontograma;
import br.com.osm.entidades.Servico;
import br.com.osm.entidades.Usuario;
import br.com.osm.enuns.EstadoDente;
import br.com.osm.exception.OSMException;
import br.com.osm.rest.AcaoServicoWebService;
import br.com.osm.rest.ComentarioWebService;
import br.com.osm.rest.MarcacaoWebService;
import br.com.osm.rest.OdontogramaWebService;
import br.com.osm.util.FacesUtil;

/**
 * Classe responsável pelo controle da tela de Odontograma.
 *
 * @author Renahn 28-07-2018
 *
 */
@Named
@ViewScoped
public class OdontogramaBean implements Serializable {

	@Inject
	private AtendimentoBean atendimentoBean;
	@Inject
	transient private OdontogramaDAO odontogramaDAO;
	@Inject
	transient private DenteDAO denteDAO;
	@Inject
	transient private ServicoDAO servicoDAO;
	@Inject
	transient private MaterialDAO materialDAO;
	@Inject
	transient private MarcacaoDAO marcacaoDAO;
	@Inject
	transient private ComentarioDAO comentarioDAO;
	@Inject
	transient private AcaoServicoDAO acaoServicoDAO;

	private Odontograma odontograma;

	private Usuario usuarioLogado = FacesUtil.getUsuarioLogado();

	private Marcacao marcacao = new Marcacao();
	private Long denteId;
	private Comentario comentarioAdicionar = new Comentario();

	private Double posX;
	private Double posY;
	private String cor;

	private AcaoServico acaoServico;

	private AcaoServicoMaterial novoMaterial;

	private String ferramenta = "MARCACAO";

	public OdontogramaBean() {
	}

	@PostConstruct
	public void init() {
		try {
			odontograma = odontogramaDAO.odontogramaDoPaciente(atendimentoBean.getAtendimento().getAgendamento().getPaciente());
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
			marcacao = new Marcacao();
			for (DenteOdontograma dente : odontograma.getDentes()) {
				if (dente.getDente().getId() == denteId) {
					marcacao.setDente(dente);
					break;
				}
			}
			if(marcacao.getDente().getEstado().equals(EstadoDente.AUSENTE)) {
				return;
			}
			marcacao.setNome("Nome da marcação");
			marcacao.getDente().adicionarMarcacao(marcacao);
			marcacao.setOdontograma(odontograma);
			marcacao.setDataHora(new Date());
			marcacao.setPosX(posX);
			marcacao.setPosY(posY);
			marcacao.setCor(cor);
			odontograma.adicionarMarcacao(marcacao);
			marcacao = new Marcacao();
			salvar();
			odontograma = odontogramaDAO.recuperar(odontograma);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao iniciar anamnese.", e);
		}
	}

	public void ausentarDente() {
		try {
			for (DenteOdontograma dente : odontograma.getDentes()) {
				if (dente.getDente().getId() == denteId) {
					dente.setEstado(EstadoDente.AUSENTE);
					break;
				}
			}
			salvar();
			odontograma = odontogramaDAO.recuperar(odontograma);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao ausentar dente.", e);
		}
	}
	public void proteseDente() {
		try {
			for (DenteOdontograma dente : odontograma.getDentes()) {
				if (dente.getDente().getId() == denteId) {
					dente.setEstado(EstadoDente.PROTESE);
					break;
				}
			}
			salvar();
			odontograma = odontogramaDAO.recuperar(odontograma);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao ausentar dente.", e);
		}
	}

	public void comentarMarcacao() {
		try {
			comentarioAdicionar.setOdontologo(usuarioLogado);
			comentarioAdicionar.setMarcacao(marcacao);
			marcacao.adicionarComentario(comentarioAdicionar);
			new ComentarioWebService(comentarioDAO).salvar(comentarioAdicionar);
			comentarioAdicionar = new Comentario();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao comentar marcacao.", e);
		}
	}

	public void excluirComentario(Comentario comentario) {
		try {
			marcacao.getComentarios().remove(comentario);
			new ComentarioWebService(comentarioDAO).excluir(comentario.getId());
		} catch (Exception e) {
			throw new RuntimeException("Erro ao remover comentario.", e);
		}
	}

	public void editarMarcacao(Marcacao marcacao) {
		try {
			this.marcacao = marcacaoDAO.recuperar(marcacao);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao recuperar marcacao.", e);
		}
	}

	public void removerMarcacao(Marcacao marcacao) {
		try {
			for (Comentario comentario : marcacao.getComentarios()) {
				new ComentarioWebService(comentarioDAO).excluir(comentario.getId());
			}
			new MarcacaoWebService(marcacaoDAO).excluir(marcacao.getId());
			marcacao = new Marcacao();
			odontograma = odontogramaDAO.recuperar(odontograma);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao remover marcacao.", e);
		}
	}

	public void salvar() {
		try {
			if (marcacao != null && marcacao.getId() != null) {
				int index = odontograma.getMarcacoes().indexOf(marcacao);
				if (index != -1) {
					odontograma.getMarcacoes().remove(index);
					odontograma.getMarcacoes().add(index, marcacao);
				}
			}
			new OdontogramaWebService(odontogramaDAO).salvar(odontograma);
			marcacao = new Marcacao();
			comentarioAdicionar = new Comentario();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao iniciar anamnese.", e);
		}
	}

	public void novoServico() {
		acaoServico = new AcaoServico();
		novoMaterial = new AcaoServicoMaterial();
	}

	public List<Servico> completeServico(String complete) {
		try {
			List<Servico> entidades = servicoDAO.autoComplete(complete);
			return entidades;
		} catch (OSMException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Material> completeMaterial(String complete) {
		try {
			List<Material> entidades = materialDAO.autoComplete(complete);
			return entidades;
		} catch (OSMException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void adicionarMaterial() {
		if (novoMaterial.getMaterial() != null && novoMaterial.getQuantidade() != null) {
			if (acaoServico.getMateriais().contains(novoMaterial)) {
				acaoServico.getMateriais().remove(novoMaterial);
			}
			novoMaterial.setAcaoServico(acaoServico);
			acaoServico.getMateriais().add(novoMaterial);
			novoMaterial = new AcaoServicoMaterial();
		}
	}

	public void salvarAcaoServico() {
		try {
			acaoServico.setData(new Date());
			acaoServico.setMarcacao(marcacao);
			acaoServico.setAtendimento(atendimentoBean.getAtendimento());
			atendimentoBean.adicionarAcaoServico(acaoServico);
			new AcaoServicoWebService(acaoServicoDAO).salvar(acaoServico);
			marcacao.getServicos().add(acaoServico);
			cancelarAcaoServico();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao comentar marcacao.", e);
		}
	}

	public void cancelarAcaoServico() {
		acaoServico = new AcaoServico();
	}

	public void removerMaterial(AcaoServicoMaterial material) {
		acaoServico.getMateriais().remove(material);
	}

	public void servicoSelecionado() {
		acaoServico.setPreco(acaoServico.getServico().getPrecoSugerido());
	}

	public void excluirAcaoServico(AcaoServico servico) {
		try {
			marcacao.getServicos().remove(servico);
			atendimentoBean.removerAcaoServico(servico);
			new AcaoServicoWebService(acaoServicoDAO).excluir(servico.getId());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao excluir Ação de Serviço.", e);
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

	public Comentario getComentarioAdicionar() {
		return comentarioAdicionar;
	}

	public void setComentarioAdicionar(Comentario comentarioAdicionar) {
		this.comentarioAdicionar = comentarioAdicionar;
	}

	public Double getPosX() {
		return posX;
	}

	public void setPosX(Double posX) {
		this.posX = posX;
	}

	public Double getPosY() {
		return posY;
	}

	public void setPosY(Double posY) {
		this.posY = posY;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public AcaoServico getAcaoServico() {
		return acaoServico;
	}

	public void setAcaoServico(AcaoServico acaoServico) {
		this.acaoServico = acaoServico;
	}

	public AcaoServicoMaterial getNovoMaterial() {
		return novoMaterial;
	}

	public void setNovoMaterial(AcaoServicoMaterial novoMaterial) {
		this.novoMaterial = novoMaterial;
	}

	public String getFerramenta() {
		return ferramenta;
	}

	public void setFerramenta(String ferramenta) {
		this.ferramenta = ferramenta;
	}

}
