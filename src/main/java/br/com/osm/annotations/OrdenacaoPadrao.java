/**
 *
 */
package br.com.osm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação utilizada para identificar o campo que será utilizado como ordenação padrão na listagem dos cadastros.
 *
 * @author Renahn 20-06-2018
 *
 */
@Target(value = { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface OrdenacaoPadrao {

	/**
	 * Para utilizar uma ordenação de um campo de outra entidade.
	 *
	 * @return O campo que será usado na ordenação.
	 */
	String campoEntidade() default "";

	/**
	 * Para indicar se a ondenação é ascendente ou descendente.
	 *
	 * @return Indica o tipo de ordenação, default é true (ascendente).
	 */
	boolean asc() default true;
}
