package br.com.osm.security;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.inject.Stereotype;

import org.apache.deltaspike.security.api.authorization.Secured;

import br.com.osm.viewconfig.Paginas;

/**
 * Anotação que será utilizada para controle de permissão aos recursos do sistema.
 *
 * @author Geraldo - 05/04/2017
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Documented
@Stereotype
@Secured(value = VerificaPermissao.class, errorView = Paginas.aaaa.class)
public @interface Restricao {

	/**
	 * As permissões necessárias para ter acesso ao recurso.
	 *
	 * @return As permissões.
	 */
	String[] value();

}
