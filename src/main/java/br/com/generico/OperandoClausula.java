/**
 *
 */
package br.com.generico;

import br.com.oms.enuns.EnumGenerico;

/**
 *
 * @author Renahn 07-02-2018
 *
 */
public enum OperandoClausula implements EnumGenerico {
	/**
	 * Para indicar que será adicionado uma operação = para pesquisar qualquer coisa igual ao valor passado.
	 */
	IGUAL(" = "),
	/**
	 * Para indicar que será adicionado uma operação != para pesquisar qualquer coisa diferente do valor passado.
	 */
	DIFERENTE(" != "),
	/**
	 * Para indicar que será adicionado uma operação < para pesquisar qualquer coisa menor que o valor passado.
	 */
	MENOR_QUE(" < "),
	/**
	 * Para indicar que será adicionado uma operação > para pesquisar qualquer coisa maior que o valor passado.
	 */
	MAIOR_QUE(" > "),
	/**
	 * Para indicar que será adicionado uma operação <= para pesquisar qualquer coisa menor ou igual ao valor passado.
	 */
	MENOR_OU_IGUAL(" <= "),
	/**
	 * Para indicar que será adicionado uma operação >= para pesquisar qualquer coisa maior ou igual ao valor passado.
	 */
	MAIOR_OU_IGUAL(" >= "),
	/**
	 * Para indicar que será adicionado uma operação IS NULL para pesquisar qualquer coisa com valor nulo.
	 */
	NULO(" IS NULL "),
	/**
	 * Para indicar que será adicionado uma operação IS NOT NULL para pesquisar qualquer coisa que não seja nulo.
	 */
	NAO_NULO(" IS NOT NULL "),
	/**
	 * Para indicar que será adicionado uma operação LIKE para pesquisar qualquer coisa que inicie com o valor passado.
	 */
	LIKE_INICIO(" LIKE "),
	/**
	 * Para indicar que será adicionado uma operação LIKE para pesquisar qualquer coisa que termine com o valor passado.
	 */
	LIKE_FINAL(" LIKE "),
	/**
	 * Para indicar que será adicionado uma operação LIKE para pesquisar qualquer coisa que contenha o valor passado.
	 */
	LIKE_TODOS(" LIKE "),
	/**
	 * Para indicar que será adicionado uma operação IN para pesquisar qualquer coisa que contenha o(s) valor(es) passado(s).
	 */
	IN(" IN "),
	/**
	 * Para indicar que será adicionado uma operação LIKE para pesquisar qualquer coisa que NÃO contenha o(s) valor(es) passado(s).
	 */
	NOT_IN(" NOT IN ");

	private final String descricao;

	/**
	 * Construtor padrão da classe.
	 *
	 * @param descricao
	 */
	private OperandoClausula(String descricao) {
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
