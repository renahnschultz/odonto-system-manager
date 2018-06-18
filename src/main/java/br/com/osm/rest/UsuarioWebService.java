package br.com.osm.rest;

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
	@Restricao({ "cadastrar-medico", "editar-medico" })
	public Response salvar(Usuario usuario) {
		return super.salvar(usuario);
	}

	@Override
	@Path("{id}")
	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	@Produces({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	@Transactional
	@Restricao({ "excluir-medico" })
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
