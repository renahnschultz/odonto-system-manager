package br.com.osm.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;

import br.com.osm.annotations.LazyModel;
import br.com.osm.dao.PermissaoDAO;
import br.com.osm.dao.UsuarioDAO;
import br.com.osm.entidades.Permissao;
import br.com.osm.entidades.Usuario;
import br.com.osm.enuns.TipoUsuario;
import br.com.osm.exception.OSMException;
import br.com.osm.model.AbstractLazyModel;
import br.com.osm.rest.PacienteWebService;

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

	@Inject
	transient private PermissaoDAO permissaoDAO;

	private TreeNode permissoes;
	private TreeNode[] permissoesSelecionadas;

	public UsuarioBean() {
	}

	@PostConstruct
	public void init() {
		criarTreePermissoes();
	}
	
	public void salvar() {
		for (TreeNode treeNode : permissoesSelecionadas) {
			if(treeNode.getType().equals("permissao")) {
				usuario.adicionarPermissao((Permissao) treeNode.getData());
			}
		}
		new PacienteWebService(usuarioDAO).salvar(usuario);
	}
	
	public void excluir() {
		new PacienteWebService(usuarioDAO).excluir(usuario.getId());
	}
	
	public void cancelar() {
		usuario = new Usuario();
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

	public void alteracaoTipoUsuario() {
	}

	public TreeNode getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(TreeNode permissoes) {
		this.permissoes = permissoes;
	}

	public void criarTreePermissoes() {
		try {
			List<Permissao> permissoes;
			this.permissoes = new CheckboxTreeNode("Root", null);
			permissoes = permissaoDAO.listarTudo();
			Map<String, TreeNode> grupos = new HashMap<String, TreeNode>();
			for (Permissao permissao : permissoes) {
				if (!grupos.containsKey(permissao.getGrupo())) {
					grupos.put(permissao.getGrupo(), new CheckboxTreeNode("grupo", permissao.getGrupo(), this.permissoes));
				}
				new CheckboxTreeNode("permissao", permissao, grupos.get(permissao.getGrupo()));
			}
		} catch (OSMException e) {
			e.printStackTrace();
		}
	}

	public TreeNode[] getPermissoesSelecionadas() {
		return permissoesSelecionadas;
	}

	public void setPermissoesSelecionadas(TreeNode[] permissoesSelecionadas) {
		this.permissoesSelecionadas = permissoesSelecionadas;
	}

}
