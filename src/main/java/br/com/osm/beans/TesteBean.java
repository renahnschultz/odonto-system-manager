package br.com.osm.beans;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.osm.dao.UsuarioDAO;
import br.com.osm.entidades.Usuario;
import br.com.osm.rest.UsuarioWebService;

@Named
@ViewScoped
public class TesteBean implements Serializable {

	/**
	 * @author Renahn 24-01-2018
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	transient private UsuarioDAO usuarioDAO;
	private Usuario usuario = new Usuario();
	
	private String teste;

	public TesteBean() {
	}
	
	public void salvar() {
		new UsuarioWebService(usuarioDAO).salvar(usuario);
	}

	public String getTeste() {
		return teste;
	}

	public void setTeste(String teste) {
		this.teste = teste;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
