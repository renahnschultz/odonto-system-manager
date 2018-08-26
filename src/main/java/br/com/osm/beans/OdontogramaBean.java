package br.com.osm.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
 * Classe responsável pelo controle da tela de cadastro de Anamnese.
 *
 * @author Renahn 28-07-2018
 *
 */
@Named
@ViewScoped
public class OdontogramaBean implements Serializable {

	@Inject
	transient private AnamneseDAO anamneseDAO;
	@Inject
	transient private PerguntaDAO perguntaDAO;
	private Anamnese anamnese;
	
	private Usuario usuarioLogado = FacesUtil.getUsuarioLogado();

	public OdontogramaBean() {
	}
	
	@PostConstruct
	public void init() {
		try {
			anamnese = anamneseDAO.anamneseDoUsuario(usuarioLogado);
			if(getAnamnese() == null) {
				criarNovaAnamnese();
			}else {
				novasRespostas(perguntaDAO.perguntasForaDaAnamnese(anamnese));
			}
		}catch(Exception e) {
			throw new RuntimeException("Erro ao iniciar anamnese.", e);
		}
	}

	private void criarNovaAnamnese() throws OSMException {
		try {
			List<Pergunta> perguntas = perguntaDAO.listarTudo();
			anamnese = new Anamnese();
			anamnese.setUsuario(usuarioLogado);
			getAnamnese().setRespostas(new ArrayList<RespostaAnamnese>());
			novasRespostas(perguntas);
		}catch(Exception e) {
			throw new OSMException(e, "Erro ao iniciar anamnese.");
		}
	}

	private void novasRespostas(List<Pergunta> perguntas) {
		for (Pergunta pergunta : perguntas) {
			getAnamnese().adicionarResposta(new RespostaAnamnese(getAnamnese(), pergunta));
		}
	}

	public void salvar() {
		try {
			anamnese.setDataPreenchimento(new Date());
			new AnamneseWebService(anamneseDAO).salvar(anamnese);
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
