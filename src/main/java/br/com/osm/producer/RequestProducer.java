/**
 *
 */
package br.com.osm.producer;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Renahn 15-06-2018
 *
 */
@Named
@RequestScoped
public class RequestProducer {

	@Context
	@Produces
	@RequestScoped
	private UriInfo uriInfo;

}
