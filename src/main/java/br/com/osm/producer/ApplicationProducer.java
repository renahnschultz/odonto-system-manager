/**
 *
 */
package br.com.osm.producer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.PreDestroyApplicationEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.deltaspike.core.api.message.MessageContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.osm.entidades.Usuario;

/**
 *
 * @author Renahn 22/07/2018
 *
 */
@Named
@ApplicationScoped
public class ApplicationProducer {

	@Inject
	private MessageContext mensagens;
	private Map<Long, List<String>> cachePermissao = new HashMap<Long, List<String>>();

	@PostConstruct
	public void postConstruct() {
	}

	@PreDestroy
	public void preDestroy() {
	}

	public void destruir(@Observes PreDestroyApplicationEvent event) {
	}

	public void inicializar(@Observes PostConstructApplicationEvent event) {
	}

	/**
	 * Método utilizado para montar mensagens dinâmicas, quando for utilizar mensagens estáticas utilize o Mensagens.java.
	 *
	 * @param chave
	 *            Identificação da mensagem que deverá estar contida no arquivo ValidationMessages.properties
	 * @param argumentos
	 *            Os argumentos opcionais que poderão ser utilizados para compor a mensagem.
	 * @return A mensagem interpolada que foi produzida.
	 */
	public String getLabel(String chave, String... argumentos) {
		if (argumentos != null && argumentos.length > 0) {
			return mensagens.message().template("{" + chave + "}").argumentArray(argumentos).toString();
		}
		return mensagens.message().template("{" + chave + "}").toString();
	}

	@Produces
	public Gson getGson() {
		Gson gson = new GsonBuilder()
				.setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		return gson;
	}

	public boolean estaNoCache(Usuario usuario) {
		return cachePermissao.containsKey(usuario.getId());
	}

	public boolean possuiPermissao(Usuario usuario, List<String> permissoes) {
		if (!estaNoCache(usuario)) {
			return false;
		}
		List<String> permissoesCacheUsuario = cachePermissao.get(usuario.getId());
		for (String permissao : permissoes) {
			if (permissoesCacheUsuario.contains(permissao)) {
				return true;
			}
		}
		return false;
	}
}
