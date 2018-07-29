/**
 *
 */
package br.com.generico;

import br.com.osm.enuns.EnumGenerico;

/**
 * Enum que mapeia algumas clausulas JPQL para serem utilizada na geração das consultas.
 * 
 * @author Renahn 07-02-2018
 *
 */
public enum TipoClausula implements EnumGenerico {
	/**
	 * Para indicar que será adicionado uma clausula AND na consulta.
	 */
	AND(" AND "),
	/**
	 * Para indicar que será adicionado uma clausula OR na consulta.
	 */
	OR(" OR ");

	private final String descricao;

	private TipoClausula(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String getDescricao() {
		return descricao;
	}

	@Override
	public String toString() {
		return descricao;
	}

}
