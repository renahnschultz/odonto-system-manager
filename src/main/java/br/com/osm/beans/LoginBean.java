/**
 *
 */
package br.com.osm.beans;

import java.io.Serializable;
import java.util.Date;
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
import br.com.osm.dao.AuditoriaDAO;
import br.com.osm.dao.UsuarioDAO;
import br.com.osm.entidades.Auditoria;
import br.com.osm.entidades.Usuario;
import br.com.osm.enuns.TipoUsuario;
import br.com.osm.exception.OSMException;
import br.com.osm.message.Mensagens;
import br.com.osm.rest.AuditoriaWebService;
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
	private Boolean habilitarBotao = true;
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
	transient private AuditoriaDAO auditoriaDAO;

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

	public Class<? extends ViewConfig> verificarAutenticacao() {
		if (isAutenticado()) {
			if (TipoUsuario.PACIENTE.equals(usuario.getTipo())) {
				return Paginas.DashboardPaciente.class;
			} else if (TipoUsuario.ODONTOLOGO.equals(usuario.getTipo())) {
				return Paginas.DashboardOdontologo.class;
			} else if (TipoUsuario.SECRETARIO.equals(usuario.getTipo())) {
				return Paginas.DashboardSecretario.class;
			} else if (TipoUsuario.ADMINISTRADOR.equals(usuario.getTipo()) || TipoUsuario.ADMINISTRADOR_CLINICA.equals(usuario.getTipo())) {
				return Paginas.DashboardAdministrador.class;
			}
			return null;
		}
		return null;
	}

	public Class<? extends ViewConfig> autenticar() {
		try {
			//executa essa query apenas paga garantir que as tabelas serão criadas pelo JPA
			usuario = usuarioDAO.pesquisar(Filtro.criarNovoFiltro()
					.append("email", OperandoClausula.IGUAL, login).build(), false);
			usuario = null;
			request.get().login(login, senha);
			usuario = usuarioDAO.pesquisar(Filtro.criarNovoFiltro()
					.append("email", OperandoClausula.IGUAL, login)
					.or()
					.append("cpf", OperandoClausula.IGUAL, login).build(), false);
			request.get().getSession().setAttribute(Constantes.COLABORADOR_SESSAO, usuario);
			idUsuario = usuario.getId();
			nomeUsuario = usuario.getNome();
			usuario.getPermissoes();

			String ip = verificaIpMaquina();
			new AuditoriaWebService(auditoriaDAO).salvar(new Auditoria("Login", new Date(), usuario, ip));
			if (TipoUsuario.PACIENTE.equals(usuario.getTipo())) {
				return Paginas.DashboardPaciente.class;
			} else if (TipoUsuario.ODONTOLOGO.equals(usuario.getTipo())) {
				return Paginas.DashboardOdontologo.class;
			} else if (TipoUsuario.SECRETARIO.equals(usuario.getTipo())) {
				return Paginas.DashboardSecretario.class;
			} else if (TipoUsuario.ADMINISTRADOR.equals(usuario.getTipo()) || TipoUsuario.ADMINISTRADOR_CLINICA.equals(usuario.getTipo())) {
				return Paginas.DashboardAdministrador.class;
			}
			return null;
		} catch (ServletException e) {
			logger.warning("Usuário ou senha inválidos: " + login + ", " + senha);
			mensagens.addError().erroAoRealizarLoginUsuarioSenhaInvalidos();
		} catch (OSMException e) {
			logger.log(Level.SEVERE, "Erro ao pesquisar Usuário com login: " + login, e);
		}
		return null;
	}

	private String verificaIpMaquina() {
		String ipAddress = request.get().getHeader("x-forwarded-for");
		if (ipAddress == null) {
		    ipAddress = request.get().getHeader("X_FORWARDED_FOR");
		    if (ipAddress == null){
		        ipAddress = request.get().getRemoteAddr();
		    }
		}
		return ipAddress;
	}

	public Class<? extends ViewConfig> logout() {
		limparSessao();
		return Paginas.Login.class;
	}

	public void logoutIdle() {
		limparSessao();
	}

	private void limparSessao() {
		String ip = verificaIpMaquina();
		new AuditoriaWebService(auditoriaDAO).salvar(new Auditoria("Logout", new Date(), usuario, ip));
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Boolean getHabilitarBotao() {
		return habilitarBotao;
	}

	public void setHabilitarBotao(Boolean habilitarBotao) {
		this.habilitarBotao = habilitarBotao;
	}
}
