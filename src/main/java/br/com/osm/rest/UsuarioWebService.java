package br.com.osm.rest;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.deltaspike.jpa.api.transaction.Transactional;

import br.com.osm.dao.UsuarioDAO;
import br.com.osm.entidades.Usuario;
import br.com.osm.exception.OSMException;
import br.com.osm.security.Restricao;

@Path("usuario")
public class UsuarioWebService extends OSMServiceBase<Long, Usuario> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	public UsuarioWebService(UsuarioDAO usuarioDAO) {
		super(usuarioDAO);
	}

	@Override
	protected void validacaoSalvar(Usuario usuario) throws OSMException {
		try {
			if(usuario.getDataNascimento().after(new Date())) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data nascimento posterior à atual.", "Data de nascimento posterior à atual."));
				throw new OSMException("data.posterior.atual");
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new OSMException(e);
		}
	}

//	@Override
//	@Path("complete/{descricao}")
//	@GET
//	@Produces({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
//	@Restricao({ "visualizar-medico" })
//	public Response autoComplete(@PathParam("descricao") String descricao) throws OSMException {
//		return super.autoComplete(descricao);
//	}

	@Override
	@POST
	@Consumes({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	@Produces({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	@Transactional
	@Restricao({ "cadastrar-usuarios", "editar-usuarios" })
	public Response salvar(Usuario usuario) {
		return super.salvar(usuario);
	}

	@Override
	@Path("{id}")
	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	@Produces({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	@Transactional
	@Restricao({ "excluir-usuarios" })
	public Response excluir(@PathParam("id") Long id) {
		return super.excluir(id);
	}

	@Override
	@Path("reativar/{id}")
	@POST
	@Consumes({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	@Produces({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	@Transactional
	@Restricao({ "administrador" })
	public Response reativar(@PathParam("id") Long id) {
		return super.reativar(id);
	}

}
