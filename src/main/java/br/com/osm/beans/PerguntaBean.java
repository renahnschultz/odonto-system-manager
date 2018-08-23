package br.com.osm.beans;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.osm.annotations.LazyModel;
import br.com.osm.dao.PerguntaDAO;
import br.com.osm.entidades.Material;
import br.com.osm.entidades.Pergunta;
import br.com.osm.model.AbstractLazyModel;
import br.com.osm.rest.PerguntaWebService;

/**
 * Classe responsável pelo controle da tela de cadastro de Perguntas da anamnese .
 *
 * @author Renahn 28-07-2018
 *
 */
@Named
@ViewScoped
public class PerguntaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	transient private PerguntaDAO perguntaDAO;
	private Pergunta pergunta = new Pergunta();
	@Inject
	@LazyModel
	private AbstractLazyModel<Long, Pergunta> perguntaLazy;

	public PerguntaBean() {
	}

	public void salvar() {
		new PerguntaWebService(perguntaDAO).salvar(pergunta);
		cancelar();
	}

	public void cancelar() {
		pergunta = new Pergunta();
	}

	public void excluir() {
		new PerguntaWebService(perguntaDAO).excluir(pergunta.getId());
		pergunta = new Pergunta();
	}

	public Pergunta getPergunta() {
		return pergunta;
	}

	public void setPergunta(Pergunta pergunta) {
		this.pergunta = pergunta;
	}

	public AbstractLazyModel<Long, Pergunta> getPerguntaLazy() {
		return perguntaLazy;
	}

	public void setPerguntaLazy(AbstractLazyModel<Long, Pergunta> perguntaLazy) {
		this.perguntaLazy = perguntaLazy;
	}

}
