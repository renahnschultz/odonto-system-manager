/**
 *
 */
package br.com.osm.security;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.deltaspike.core.api.common.DeltaSpike;
import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.core.api.config.view.metadata.ViewConfigResolver;
import org.apache.deltaspike.security.api.authorization.AbstractAccessDecisionVoter;
import org.apache.deltaspike.security.api.authorization.AccessDecisionVoterContext;
import org.apache.deltaspike.security.api.authorization.SecurityViolation;
import org.primefaces.util.Base64;

import br.com.osm.beans.LoginBean;
import br.com.osm.dao.UsuarioDAO;
import br.com.osm.message.Mensagens;
import br.com.osm.util.Constantes;
import br.com.osm.viewconfig.Paginas;

@Named
@SessionScoped
@Provider
public class VerificaPermissao extends AbstractAccessDecisionVoter implements ContainerRequestFilter {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(VerificaPermissao.class.getName());

	@Inject
	private transient UsuarioDAO usuarioDAO;

	@Inject
	@DeltaSpike
	private Instance<HttpServletRequest> request;
	@Inject
	private Mensagens mensagens;

	@Inject
	private LoginBean loginBean;

	@Inject
	private ViewConfigResolver viewConfigResolver;

	/**
	 * Armazena a página que o usuário tentou acessar mas ainda não estava autenticado, para que ele seja redireconado para a página que
	 * tentou acessar após fazer o login. A página padrão de redirecionamento é a Dashboard
	 */
	private Class<? extends ViewConfig> paginaNegada = Paginas.aaaa.class;

	@Override
	protected void checkPermission(AccessDecisionVoterContext context, Set<SecurityViolation> violations) {
		//se existe o colaborador no request, quer dizer que é uma chamada via REST e não é necessário executar essa validação, pois já foi
		//validado no método filter(ContainerRequestContext requestContext) desta mesma classe
		if (request.get().getAttribute(Constantes.COLABORADOR_SESSAO) != null) {
			return;
		}
		if (!loginBean.isAutenticado()) {
			logger.warning("Usuário não autenticado");
			violations.add(new SemPermissaoSecurityViolation(mensagens.usuarioNaoAutenticado()));

			paginaNegada = viewConfigResolver
					.getViewConfigDescriptor(FacesContext.getCurrentInstance().getViewRoot().getViewId())
					.getConfigClass();
		} else {
			Restricao restricao = context.getMetaDataFor(Restricao.class.getName(), Restricao.class);
			if (restricao == null) {
				restricao = getRestricao(context);
			}
			Boolean possuiPermissao = Boolean.FALSE;
			for (String role : restricao.value()) {
				if (temPermissao(role)) {
					possuiPermissao = Boolean.TRUE;
					break;
				}
			}
			if (!possuiPermissao) {
				logger.warning("Sem permissão " + Arrays.asList(restricao.value()));
				violations.add(new SemPermissaoSecurityViolation(mensagens.acessoNegado()));
			} else {
				logger.info("Possui permissão " + Arrays.asList(restricao.value()));
			}
		}
	}

	/**
	 * Obtem a anotação {@link Restricao} do recurso que esta sendo acessado.
	 *
	 * @param context
	 *            O contexto que será utilizado para se obter a anotação {@link Restricao}
	 * @return A anotação {@link Restricao} do recurso que esta sendo acessado.
	 */
	private Restricao getRestricao(AccessDecisionVoterContext context) {
		Class<?> viewConfig = (Class<?>) context.getMetaData().get("org.apache.deltaspike.core.api.config.view.ViewConfig");
		return viewConfig.getAnnotation(Restricao.class);
	}

	/**
	 * Verifica se o {@link Colaborador} autenticado na sessão da aplicação possui determinada permissão.
	 *
	 * @param role
	 *            A permissão que será validada.
	 * @return
	 *
	 *         <li>
	 *         <b>true</b> - Se o colaborador tem a permissão
	 *         </li>
	 *         <li>
	 *         <b>false</b> - Se o colaborador NÃO tem a permissão
	 *         </li>
	 */
	public boolean temPermissao(String role) {
		if (role.equals("mobilesys")) {
			return request.get().isUserInRole(role);
		}
		return request.get().isUserInRole(role)
				|| request.get().isUserInRole("administrador");
	}

	/**
	 * Valida se o usuário tem alguma das permissões passada no parâmetro. As permissões devem ser passadas como uma String separando cada
	 * permissão por ',' (vírgula).
	 *
	 * @param roles
	 *            As permissões que serão avaliadas no formato 'cadastrar,excluir,editar';
	 * @return Retorna se o usuário tem alguma permissão ou não.
	 */
	public boolean temPermissoes(String roles) {
		if (StringUtils.isBlank(roles)) {
			return false;
		}
		boolean retorno = false;
		String[] split = roles.split("[,]");
		for (String role : split) {
			retorno = temPermissao(role.trim());
			if (retorno) {
				return retorno;
			}
		}
		return retorno;
	}

	//==============================================================//
	//		INICIO DA VALIDAÇÃO DE AUTORIZAÇÃO POR CHAMADA REST		//
	//==============================================================//

	@Context
	private ResourceInfo resourceInfo;

	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String AUTHENTICATION_SCHEME = "Basic";
	private static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED)
			.entity("You cannot access this resource").build();
	private static final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN)
			.entity("Access blocked !!").build();

	@Override
	public void filter(ContainerRequestContext requestContext) {
		Method method = resourceInfo.getResourceMethod();
		if (method.isAnnotationPresent(Restricao.class)) {
			final MultivaluedMap<String, String> headers = requestContext.getHeaders();

			final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

			if (CollectionUtils.isEmpty(authorization)) {
				requestContext.abortWith(ACCESS_FORBIDDEN);
				return;
			}

			final String usuarioSenhaCodificado = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

			String usuarioSenhaDecodificado = new String(Base64.decode(usuarioSenhaCodificado.getBytes()));

			String[] usuarioSenha = usuarioSenhaDecodificado.split("[:]");
			if (usuarioSenha.length != 2) {
				requestContext.abortWith(ACCESS_FORBIDDEN);
				return;
			}

			Restricao restricao = method.getAnnotation(Restricao.class);
			List<String> rolesSet = new ArrayList<String>(Arrays.asList(restricao.value()));

//			try {
//				if (!usuarioDAO.possuiPermissao(usuarioSenha[0], usuarioSenha[1], rolesSet)) {
//					requestContext.abortWith(ACCESS_DENIED);
//					return;
//				}
//				request.get().setAttribute(Constantes.COLABORADOR_SESSAO, usuarioDAO.pesquisarPorLogin(usuarioSenha[0]));
//			} catch (Exception e) {
//				Response erro = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
//						.entity("Erro ao validar usuário! " + e.getMessage()).build();
//				requestContext.abortWith(erro);
//				return;
//			}
		}
	}

	/**
	 * Retorna a página que o usuário tentou acessar antes de fazer o login na aplicação. Por padrão a página é a Dashboard.
	 *
	 * @return
	 */
	public Class<? extends ViewConfig> getPaginaNegada() {
		return paginaNegada;
	}

	public boolean isAdministrador() {
		return temPermissao("administrador");
	}

	public boolean isMobilesys() {
		return temPermissao("mobilesys");
	}
}
