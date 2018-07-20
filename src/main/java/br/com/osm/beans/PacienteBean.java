package br.com.osm.beans;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.osm.annotations.LazyModel;
import br.com.osm.dao.UsuarioDAO;
import br.com.osm.entidades.Usuario;
import br.com.osm.model.AbstractLazyModel;
import br.com.osm.rest.UsuarioWebService;

/**
 * Classe responsável pelo controle da tela de cadastro de Paciente.
 *
 * @author Renahn 20-07-2018
 *
 */
@Named
@ViewScoped
public class PacienteBean implements Serializable {

	@Inject
	transient private UsuarioDAO usuarioDAO;
	private Usuario usuario = new Usuario();
	@Inject
	@LazyModel
	private AbstractLazyModel<Long, Usuario> usuarioLazy;

	public PacienteBean() {
	}

	public void salvar() {
		new UsuarioWebService(usuarioDAO).salvar(usuario);
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public AbstractLazyModel<Long, Usuario> getUsuarioLazy() {
		return usuarioLazy;
	}

	public void setUsuarioLazy(AbstractLazyModel<Long, Usuario> usuarioLazy) {
		this.usuarioLazy = usuarioLazy;
	}

}
