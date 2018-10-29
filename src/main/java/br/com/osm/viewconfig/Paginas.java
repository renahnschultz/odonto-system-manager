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

	@Restricao(value = {"visualizar-servico", "cadastrar-servico", "excluir-servico"})
	public class cadServicos implements ViewConfig {
	}

	@Restricao(value = {"visualizar-material", "cadastrar-material", "excluir-material"})
	public class cadMaterial implements ViewConfig {
	}

	@Restricao(value = {"visualizar-usuarios", "cadastrar-usuarios", "excluir-usuarios"})
	public class cadUsuario implements ViewConfig {
	}

	@Restricao(value = {"visualizar-perguntas", "cadastrar-perguntas", "excluir-perguntas"})
	public class cadPergunta implements ViewConfig {
	}

	@Restricao(value = {"cadastrar-anamnese", "editar-anamnese"})
	public class Anamnese implements ViewConfig {
	}

	@Restricao(value = {"odontologo"})
	public class HorarioOdontologo implements ViewConfig {
	}

	@Restricao(value = {"paciente", "odontologo", "secretario"})
	public class Agendamento implements ViewConfig {
	}

	@Restricao(value = {"aprovar-agendamento"})
	public class AprovarAgendamento implements ViewConfig {
	}

	@Restricao(value = {"odontologo"})
	public class Agenda implements ViewConfig {
	}
	@Restricao(value = {"administrador"})
	public class Configuracao implements ViewConfig {
	}
	
	public class registroPaciente implements ViewConfig {
	}

	@Restricao(value = {"paciente"})
	public class DashboardPaciente implements ViewConfig {

	}

	@Restricao(value = {"odontologo"})
	public class DashboardOdontologo implements ViewConfig {

	}

	@Restricao(value = {"secretario"})
	public class DashboardSecretario implements ViewConfig {

	}

	@Restricao(value = {"administrador-clinica"})
	public class DashboardAdministrador implements ViewConfig {

	}

	@Restricao(value = {"odontologo"})
	public class Odontograma implements ViewConfig {
		
	}

	@Restricao(value = {"receber"})
	public class Debitos implements ViewConfig {
		
	}

}
