package br.com.osm.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.osm.dao.AnamneseDAO;
import br.com.osm.dao.PerguntaDAO;
import br.com.osm.entidades.Anamnese;
import br.com.osm.entidades.Pergunta;
import br.com.osm.entidades.RespostaAnamnese;
import br.com.osm.entidades.Usuario;
import br.com.osm.exception.OSMException;
import br.com.osm.rest.AnamneseWebService;
import br.com.osm.util.FacesUtil;

/**
 * Classe responsável pelo controle da tela de cadastro de Paciente.
 *
 * @author Lucas 28-07-2018
 *
 */
@Named
@ViewScoped
public class AnamneseBean implements Serializable {

	@Inject
	transient private AnamneseDAO anamneseDAO;
	@Inject
	transient private PerguntaDAO perguntaDAO;
	private Anamnese anamnese;
	
	private Usuario usuarioLogado = FacesUtil.getUsuarioLogado();

	public AnamneseBean() {
	}
	
	@PostConstruct
	public void init() {
		try {
			setAnamnese(anamneseDAO.anamneseDoUsuario(usuarioLogado));
			if(getAnamnese() == null) {
				criarNovaAnamnese();
			}
		}catch(Exception e) {
			throw new RuntimeException("Erro ao iniciar anamnese.", e);
		}
	}

	private void criarNovaAnamnese() throws OSMException {
		try {
			List<Pergunta> perguntas = perguntaDAO.listarTudo();
			setAnamnese(new Anamnese());
			getAnamnese().setRespostas(new ArrayList<RespostaAnamnese>());
			for (Pergunta pergunta : perguntas) {
				getAnamnese().adicionarResposta(new RespostaAnamnese(getAnamnese(), pergunta));
			}
		}catch(Exception e) {
			throw new OSMException(e, "Erro ao iniciar anamnese.");
		}
	}

	public void salvar() {
		try {
			new AnamneseWebService(anamneseDAO).salvar(getAnamnese());
		}catch(Exception e) {
			throw new RuntimeException("Erro ao iniciar anamnese.", e);
		}
	}

	public Anamnese getAnamnese() {
		return anamnese;
	}

	public void setAnamnese(Anamnese anamnese) {
		this.anamnese = anamnese;
	}

}
