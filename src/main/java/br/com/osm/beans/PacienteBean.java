package br.com.osm.beans;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.deltaspike.core.api.config.view.ViewConfig;

import br.com.osm.annotations.LazyModel;
import br.com.osm.dao.PermissaoDAO;
import br.com.osm.dao.UsuarioDAO;
import br.com.osm.entidades.Usuario;
import br.com.osm.enuns.TipoUsuario;
import br.com.osm.exception.OSMException;
import br.com.osm.model.AbstractLazyModel;
import br.com.osm.rest.PacienteWebService;
import br.com.osm.viewconfig.Paginas;

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
	@Inject
	transient private PermissaoDAO permissaoDAO;
	private Usuario usuario = new Usuario();
	@Inject
	@LazyModel
	private AbstractLazyModel<Long, Usuario> usuarioLazy;

	public PacienteBean() {
	}

	public Class<? extends ViewConfig> salvar() {
		try {
			usuario.setTipo(TipoUsuario.PACIENTE);
			usuario.setPermissoes(permissaoDAO.permissoesPadrao("paciente"));
			new PacienteWebService(usuarioDAO).salvar(usuario);
			return Paginas.Login.class;
		} catch (OSMException e) {
			throw new RuntimeException("Erro ao salvar paciente", e);
		}
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
