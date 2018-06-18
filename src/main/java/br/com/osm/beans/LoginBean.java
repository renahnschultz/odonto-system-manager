/**
 *
 */
package br.com.osm.beans;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.deltaspike.core.api.common.DeltaSpike;
import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.core.api.config.view.navigation.ViewNavigationHandler;
import org.apache.deltaspike.jsf.api.message.JsfMessage;

import br.com.generico.Filtro;
import br.com.generico.OperandoClausula;
import br.com.osm.dao.UsuarioDAO;
import br.com.osm.entidades.Usuario;
import br.com.osm.exception.OSMException;
import br.com.osm.message.Mensagens;
import br.com.osm.security.VerificaPermissao;
import br.com.osm.util.Constantes;
import br.com.osm.viewconfig.Paginas;

/**
 *
 * @author Renahn 9 de jun de 2017
 *
 */
@Named
@SessionScoped
public class LoginBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(LoginBean.class.getName());
	public static final String HOME = "/paginas/dashboard";
	private String login;
	private String senha;
	@Inject
	private JsfMessage<Mensagens> mensagens;
	@Inject
	@DeltaSpike
	private Instance<HttpServletRequest> request;

	@Named
	@Produces
	private Long idUsuario;

	private String nomeUsuario;

	private Usuario usuario;

	@Inject
	transient private UsuarioDAO usuarioDAO;

	@Inject
	private ViewNavigationHandler viewNavigationHandler;

	@Inject
	private VerificaPermissao verificaPermissao;

	/**
	 * Coordenadores que o {@link UsuarioSistema} logado tem acesso.
	 */
	@PostConstruct
	public void init() {

	}

	public void autenticar() {
		try {
			//executa essa query apenas paga garantir que as tabelas serão criadas pelo JPA
			usuario = usuarioDAO.pesquisar(Filtro.criarNovoFiltro()
					.append("login", OperandoClausula.IGUAL, login).build(), false);
			usuario = null;
			request.get().login(login, senha);
			usuario = usuarioDAO.pesquisar(Filtro.criarNovoFiltro()
					.append("login", OperandoClausula.IGUAL, login).build(), false);
			request.get().getSession().setAttribute(Constantes.COLABORADOR_SESSAO, usuario);
			idUsuario = usuario.getId();
			nomeUsuario = usuario.getNome();
			viewNavigationHandler.navigateTo(verificaPermissao.getPaginaNegada());
		} catch (ServletException e) {
			logger.warning("Usuário ou senha inválidos: " + login + ", " + senha);
			mensagens.addError().erroAoRealizarLoginUsuarioSenhaInvalidos();
		} catch (OSMException e) {
			logger.log(Level.SEVERE, "Erro ao pesquisar Usuário com login: " + login, e);
		}
	}

	public Class<? extends ViewConfig> logout() {
		limparSessao();
		return Paginas.aaaa.class;
	}

	public void logoutIdle() {
		limparSessao();
	}

	private void limparSessao() {
		request.get().getSession().invalidate();
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		idUsuario = null;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	/**
	 * Verifica se existe algum {@link Autenticacao} autenticado no sistema.
	 *
	 * @return Retorna se existe algum {@link Autenticacao} autenticado no sistema.
	 */
	public boolean isAutenticado() {
		return idUsuario != null;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}
}
