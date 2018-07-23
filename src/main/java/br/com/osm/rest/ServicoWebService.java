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

import br.com.osm.dao.ServicoDAO;
import br.com.osm.entidades.Servico;
import br.com.osm.exception.OSMException;
import br.com.osm.security.Restricao;

@Path("servico")
public class ServicoWebService extends OSMServiceBase<Long, Servico> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	public ServicoWebService(ServicoDAO servicoDAO) {
		super(servicoDAO);
	}

	@Override
	protected void validacaoSalvar(Servico servico) throws OSMException {
		try {
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
	@Restricao({ "cadastrar-servico", "editar-servico" })
	public Response salvar(Servico servico) {
		return super.salvar(servico);
	}

	@Override
	@Path("{id}")
	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	@Produces({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	@Transactional
	@Restricao({ "excluir-servico" })
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
