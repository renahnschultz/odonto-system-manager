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

import br.com.osm.dao.DebitoDAO;
import br.com.osm.entidades.Debito;
import br.com.osm.exception.OSMException;
import br.com.osm.security.Restricao;

@Path("debito")
public class DebitoWebService extends OSMServiceBase<Long, Debito> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	public DebitoWebService(DebitoDAO entidadeDAO) {
		super(entidadeDAO);
	}

	@Override
	protected void validacaoSalvar(Debito entidade) throws OSMException {
		try {
		} catch (Exception e) {
			e.printStackTrace();
			throw new OSMException(e);
		}
	}

	@Override
	@POST
	@Consumes({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	@Produces({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	@Transactional
	@Restricao({ "odontologo" })
	public Response salvar(Debito entidade) {
		return super.salvar(entidade);
	}

	@Override
	@Path("{id}")
	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	@Produces({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	@Transactional
	@Restricao({ "odontologo" })
	public Response excluir(@PathParam("id") Long id) {
		return null;
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
