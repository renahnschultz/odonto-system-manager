/**
 *
 */
package br.com.osm.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

/**
 *
 * @author Renahn 20-06-2018
 *
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ TYPE, FIELD, PARAMETER, METHOD })
public @interface LazyModel {

}
