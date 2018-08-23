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

import br.com.osm.dao.PerguntaDAO;
import br.com.osm.entidades.Pergunta;
import br.com.osm.exception.OSMException;
import br.com.osm.security.Restricao;

@Path("pergunta")
public class PerguntaWebService extends OSMServiceBase<Long, Pergunta> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	public PerguntaWebService(PerguntaDAO perguntaDAO) {
		super(perguntaDAO);
	}

	@Override
	protected void validacaoSalvar(Pergunta pergunta) throws OSMException {
		try {
		}catch(Exception e) {
			e.printStackTrace();
			throw new OSMException(e);
		}
	}

	@Override
	@POST
	@Consumes({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	@Produces({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	@Transactional
	@Restricao({ "cadastrar-perguntas", "editar-perguntas" })
	public Response salvar(Pergunta pergunta) {
		return super.salvar(pergunta);
	}

	@Override
	@Path("{id}")
	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	@Produces({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
	@Transactional
	@Restricao({ "excluir-perguntas" })
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
