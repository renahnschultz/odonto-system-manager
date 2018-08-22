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

	@Restricao(value = {"visualizar-servicos", "cadastrar-servicos", "excluir-servicos"})
	public class cadServicos implements ViewConfig {
	}

	@Restricao(value = {"visualizar-materiais", "cadastrar-materiais", "excluir-materiais"})
	public class cadMaterial implements ViewConfig {
	}

	@Restricao(value = {"visualizar-usuarios", "cadastrar-usuarios", "excluir-usuarios"})
	public class cadUsuario implements ViewConfig {
	}
	
	public class registroPaciente implements ViewConfig {
	}

	public class DashboardPaciente implements ViewConfig {

	}

	public class DashboardOdontologo implements ViewConfig {

	}

	public class DashboardSecretario implements ViewConfig {

	}

	public class DashboardAdministrador implements ViewConfig {

	}

}
