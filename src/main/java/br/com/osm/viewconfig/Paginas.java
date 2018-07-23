/**
 *
 */
package br.com.osm.viewconfig;

import org.apache.deltaspike.core.api.config.view.ViewConfig;

import br.com.osm.security.Restricao;

/**
 *
 * @author Renahn 06-02-2018
 *
 */
public interface Paginas extends ViewConfig {

	// Use interface para pastas e class para paginas.
	@Restricao(value = "DASHBOARD")
	public class aaaa implements ViewConfig {
		
	}
	@Restricao(value = "CADASTRAR_SERVICO")
	public class servicos implements ViewConfig {
		
	}
	public class registroPaciente implements ViewConfig {
	}
}
