package br.com.osm.util;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Renahn
 *
 */
public class FacesUtil {
	/**
	 * Obtem o valor de um parâmetro passado por request.
	 *
	 * @param name
	 *            O nome do parâmetro.
	 * @return O valor do parâmetro.
	 */
	public static String getRequestParameter(String name) {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
	}

	/**
	 * Adiciona mensagem de sucesso para ser exibida na tela.
	 *
	 * @param mensagem
	 *            A mensagem que será adicionada.
	 */
	public static void exibirMensagemSucesso(String mensagem) {
		exibirMensagem(FacesMessage.SEVERITY_INFO, mensagem);
	}

	/**
	 * Adiciona mensagem de alerta para ser exibida na tela.
	 *
	 * @param mensagem
	 *            A mensagem que será exibida.
	 */
	public static void exibirMensagemAlerta(String mensagem) {
		exibirMensagem(FacesMessage.SEVERITY_WARN, mensagem);
	}

	/**
	 * Adiciona mensagem de erro para ser exibida na tela.
	 *
	 * @param mensagem
	 *            A mensagem que será exibida.
	 */
	public static void exibirMensagemErro(String mensagem) {
		exibirMensagem(FacesMessage.SEVERITY_ERROR, mensagem);
	}

	/**
	 * Adiciona mensagem que será exibida na tela.
	 *
	 * @param severity
	 *            O tipo de mensagem que será exibida.
	 * @param mensagem
	 *            A mensagem que será exibida.
	 */
	private static void exibirMensagem(FacesMessage.Severity severity, String mensagem) {
		FacesMessage facesMessage = new FacesMessage(severity, mensagem, mensagem);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}

	/**
	 *
	 * @return O contexto exterdo da aplicação.
	 */
	public static ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	/**
	 *
	 * @return Um map com todos os objetos em sessão no momento.
	 */
	@SuppressWarnings("rawtypes")
	public static Map getSessionMap() {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	}

	/**
	 *
	 * @return O contexto do servlet.
	 */
	public static ServletContext getServletContext() {
		return (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	}

	/**
	 *
	 * @return O objeto request da requisição atual.
	 */
	public static HttpServletRequest getServletRequest() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (facesContext == null) {
			return null;
		}
		return (HttpServletRequest) facesContext.getExternalContext().getRequest();
	}

	/**
	 *
	 * @return O objeto session atual.
	 */
	public static HttpServletResponse getServletResponse() {
		return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	}

	/**
	 * Produtor para o {@link ResourceBundle}.
	 *
	 * @return O {@link ResourceBundle} que foi produzido.
	 */
	public static ResourceBundle getResourceBundle() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if (FacesContext.getCurrentInstance() == null) {
			return null;
		}
		String userBundleName = FacesContext.getCurrentInstance().getApplication().getMessageBundle();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Locale locale = null;
		if (facesContext != null && facesContext.getViewRoot() != null) {
			locale = facesContext.getViewRoot().getLocale();

			if (locale == null) {
				locale = Locale.getDefault();
			}
		} else {
			locale = Locale.getDefault();
		}
		return ResourceBundle.getBundle(userBundleName, locale, loader);
	}

	private static BeanManager getBeanManager() {
		try {
			InitialContext initialContext = new InitialContext();
			return (BeanManager) initialContext.lookup("java:comp/BeanManager");
		} catch (NamingException e) {
			throw new IllegalStateException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBeanByName(final Class<T> clazz) {
		final BeanManager bm = getBeanManager();
		final Iterator<Bean<?>> iter = bm.getBeans(clazz).iterator();
		if (!iter.hasNext()) {
			return null;
		}
		final Bean<T> bean = (Bean<T>) iter.next();
		final CreationalContext<T> ctx = bm.createCreationalContext(bean);
		return (T) bm.getReference(bean, clazz, ctx);
	}

	public static String getPath(String string) {
		return getServletContext().getRealPath(string);
	}
}
