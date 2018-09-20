package br.com.osm.beans;

import java.io.Serializable;
import java.util.Date;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.osm.annotations.LazyModel;
import br.com.osm.dao.ServicoDAO;
import br.com.osm.entidades.Servico;
import br.com.osm.entidades.Usuario;
import br.com.osm.model.AbstractLazyModel;
import br.com.osm.rest.ServicoWebService;

/**
 * Classe responsável pelo controle da tela de cadastro de Agendamento.
 *
 * @author Renahn 20-07-2018
 *
 */
@Named
@ViewScoped
public class AgendamentoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	transient private ServicoDAO servicoDAO;
	private Servico servico = new Servico();
	private Usuario odontologo;
	private Date data;
	@Inject
	@LazyModel
	private AbstractLazyModel<Long, Servico> servicoLazy;

	public AgendamentoBean() {
	}

	public void buscarHorarios() {
		System.out.println("erta");
	}
	
	public void salvar() {
		new ServicoWebService(servicoDAO).salvar(servico);
		cancelar();
	}

	public void cancelar() {
		servico = new Servico();
	}

	public void excluir() {
		new ServicoWebService(servicoDAO).excluir(servico.getId());
		servico = new Servico();
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public AbstractLazyModel<Long, Servico> getServicoLazy() {
		return servicoLazy;
	}

	public void setServicoLazy(AbstractLazyModel<Long, Servico> servicoLazy) {
		this.servicoLazy = servicoLazy;
	}

	public Usuario getOdontologo() {
		return odontologo;
	}

	public void setOdontologo(Usuario odontologo) {
		this.odontologo = odontologo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}
