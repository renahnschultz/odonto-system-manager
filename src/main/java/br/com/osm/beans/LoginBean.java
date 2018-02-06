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

import br.com.mobilesys.magictrade.dao.ColaboradorDAO;
import br.com.mobilesys.magictrade.dao.generico.Filtro;
import br.com.mobilesys.magictrade.dao.generico.OperandoClausula;
import br.com.mobilesys.magictrade.entity.Colaborador;
import br.com.mobilesys.magictrade.exception.MagicTradeException;
import br.com.mobilesys.magictrade.message.Mensagens;
import br.com.mobilesys.magictrade.security.VerificaPermissao;
import br.com.mobilesys.magictrade.util.Constantes;
import br.com.mobilesys.magictrade.viewconfig.Paginas;

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

	private Colaborador colaborador;

	@Inject
	transient private ColaboradorDAO colaboradorDAO;

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
			colaborador = colaboradorDAO.pesquisar(Filtro.criarNovoFiltro()
					.append("login", OperandoClausula.IGUAL, login).build(), false);
			colaborador = null;
			request.get().login(login, senha);
			colaborador = colaboradorDAO.pesquisar(Filtro.criarNovoFiltro()
					.append("login", OperandoClausula.IGUAL, login).build(), false);
			request.get().getSession().setAttribute(Constantes.COLABORADOR_SESSAO, colaborador);
			idUsuario = colaborador.getId();
			nomeUsuario = colaborador.getNome();
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
		return Paginas.Login.class;
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
	 * Verifica se existe algum {@link Usuario} autenticado no sistema.
	 *
	 * @return Retorna se existe algum {@link Usuario} autenticado no sistema.
	 */
	public boolean isAutenticado() {
		return idUsuario != null;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

}
