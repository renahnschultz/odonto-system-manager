package br.com.osm.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.deltaspike.jpa.api.transaction.Transactional;

import br.com.mobilesys.magictrade.exception.ValidacaoException;
import br.com.osm.dao.MedicoDAO;
import br.com.osm.entidades.Medico;
import br.com.osm.exception.OSMException;
import br.com.osm.security.Restricao;

@Path("medico")
public class MedicoWebService extends OSMServiceBase<Long, Medico> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	public MedicoWebService(MedicoDAO medicoDAO) {
		super(medicoDAO);
	}

	@Override
	protected void validacaoSalvar(Medico medico) throws ValidacaoException {
	}

	@Override
	@Path("complete/{descricao}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	@Restricao({ "visualizar-medico" })
	public Response autoComplete(@PathParam("descricao") String descricao) throws OSMException {
		return super.autoComplete(descricao);
	}

	@Path("buscar/{descricao}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	@Restricao({ "visualizar-medico" })
	public Response buscarMedicoPorDescricao(@PathParam("descricao") String descricao) throws OSMException {
		Medico medico = ((MedicoDAO) dao).buscarMedicoAtivoPorDescricao(descricao);
		if (medico == null) {
			return Response.status(Status.NO_CONTENT).build();
		}
		return Response.ok(medico).build();
	}

	@Override
	@POST
	@Consumes({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	@Produces({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	@Transactional
	@Restricao({ "cadastrar-medico", "editar-medico" })
	public Response salvar(Medico medico) {
		return super.salvar(medico);
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
