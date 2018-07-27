/**
 *
 */
package br.com.osm.viewconfig;

import org.apache.deltaspike.core.api.config.view.DefaultErrorView;
import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.jsf.api.config.view.View;
import org.apache.deltaspike.jsf.api.config.view.View.NavigationMode;

import br.com.osm.security.Restricao;

/**
 *
 * @author Renahn 06-02-2018
 *
 */
public interface Paginas extends ViewConfig {

	@View(navigation = NavigationMode.REDIRECT)
	class AcessoNegado extends DefaultErrorView {
	}

	class Login implements ViewConfig {
	}

	// Use interface para pastas e class para paginas.
	@Restricao(value = "DASHBOARD")
	public class aaaa implements ViewConfig {

	}

	public class cadServicos implements ViewConfig {

	}

	public class registroPaciente implements ViewConfig {
	}
}
