package br.com.osm.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.deltaspike.core.api.config.view.ViewConfig;

import br.com.osm.annotations.LazyModel;
import br.com.osm.dao.UsuarioDAO;
import br.com.osm.entidades.Usuario;
import br.com.osm.enuns.TipoUsuario;
import br.com.osm.model.AbstractLazyModel;
import br.com.osm.rest.PacienteWebService;
import br.com.osm.rest.UsuarioWebService;
import br.com.osm.viewconfig.Paginas.aaaa;

/**
 * Classe responsável pelo controle da tela de cadastro de Usuario.
 *
 * @author Lucas 29-07-2018
 *
 */
@Named
@ViewScoped
public class UsuarioBean implements Serializable {

	@Inject
	transient private UsuarioDAO usuarioDAO;
	private Usuario usuario = new Usuario();
	@Inject
	@LazyModel
	private AbstractLazyModel<Long, Usuario> usuarioLazy;

	public UsuarioBean() {
	}

	public Class<? extends ViewConfig> salvar() {
		usuario.setTipo(TipoUsuario.ADMINISTRADOR);
		new PacienteWebService(usuarioDAO).salvar(usuario);
		return aaaa.class;
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
	
	@Named
	public List<TipoUsuario> tiposUsuario() {
		List<TipoUsuario> tipoUsuario = new ArrayList<TipoUsuario>();
		Usuario usuarioLogado = new LoginBean().getUsuario();
		
		for (TipoUsuario tipo : TipoUsuario.values()) {
			if (TipoUsuario.ADMINISTRADOR.equals(tipo)) {
				continue;
			}
			tipoUsuario.add(tipo);
		}
		return tipoUsuario;
	}

}
