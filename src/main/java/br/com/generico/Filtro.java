/**
 *
 */
package br.com.generico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Renahn 07-02-2018
 *
 */
public class Filtro {

	public static final String ABRE_PARENTESE = "abreParentese";
	public static final String ABRE_PARENTESE_VALOR = "(";
	public static final String FECHA_PARENTESE = "fechaParentese";
	public static final String FECHA_PARENTESE_VALOR = ")";
	private final String nome;
	private final Object valor;
	private final TipoClausula tipoClausula;
	private final OperandoClausula operandoClausula;
	private List<Filtro> filtros = new ArrayList<Filtro>();

	/**
	 * Cria um novo {@link Filtro}.
	 *
	 * @return O {@link Filtro} que será manipulado.
	 */
	public static Filtro criarNovoFiltro() {
		return new Filtro();
	}

	private Filtro() {
		this.nome = null;
		this.valor = null;
		this.tipoClausula = null;
		this.operandoClausula = null;
	}

	/**
	 * Construtor padrão da classe.
	 *
	 * @param tipoClausula
	 *            O tipo de clausula que será adicionada, caso seja somente um filtro esse valor não será utilizado.
	 * @param nome
	 *            O nome da clausula que será adicionada. <b> Ex: estado.nome </b>
	 * @param operandoClausula
	 *            O operando que será utilizado. <b>Ex: =, !=, <=, etc.</b>
	 * @param valor
	 *            O valor que será passado para a clausula. <b>Ex: pará </b>, também pode ser passado o objeto, nesse caso o id será
	 *            utilizado na consulta.
	 */
	private Filtro(TipoClausula tipoClausula, String nome, OperandoClausula operandoClausula, Object valor) {
		this.nome = nome;
		this.valor = valor;
		this.tipoClausula = tipoClausula;
		this.operandoClausula = operandoClausula;
		filtros.add(this);
	}

	/**
	 * Obtem o valor do atributo nome.
	 *
	 * @return O valor de nome.
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Obtem o valor do atributo valor.
	 *
	 * @return O valor de valor.
	 */
	public Object getValor() {
		return valor;
	}

	/**
	 * Obtem o valor do atributo tipoClausula.
	 *
	 * @return O valor de tipoClausula.
	 */
	public TipoClausula getTipoClausula() {
		return tipoClausula;
	}

	/**
	 * Obtem o valor do atributo operandoClausula.
	 *
	 * @return O valor de operandoClausula.
	 */
	public OperandoClausula getOperandoClausula() {
		return operandoClausula;
	}

	/**
	 *
	 * @param nome
	 *            O nome da clausula que será adicionada. <b> Ex: estado.nome </b>
	 * @param operandoClausula
	 *            O operando que será utilizado. <b>Ex: =, !=, <=, etc.</b>
	 * @param valor
	 *            O valor que será passado para a clausula. <b>Ex: pará </b>, também pode ser passado o objeto, nesse caso o id será
	 *            utilizado na consulta.
	 * @return O {@link Filtro} que foi inicialmente criado.
	 */
	public Filtro append(String nome, OperandoClausula operandoClausula, Object valor) {
		Filtro filtro = new Filtro(null, nome, operandoClausula, valor);
		filtros.add(filtro);
		return this;
	}

	public Filtro abreParentese() {
		Filtro filtro = new Filtro(null, ABRE_PARENTESE, null, ABRE_PARENTESE_VALOR);
		filtros.add(filtro);
		return this;
	}

	public Filtro fechaParentese() {
		Filtro filtro = new Filtro(null, FECHA_PARENTESE, null, FECHA_PARENTESE_VALOR);
		filtros.add(filtro);
		return this;
	}

	/**
	 * Adiciona um clasula AND na consulta.
	 *
	 * @return O {@link Filtro} que está sendo manipulado.
	 */
	public Filtro and() {
		Filtro filtro = new Filtro(TipoClausula.AND, null, null, null);
		filtros.add(filtro);
		return this;
	}

	/**
	 * Adiciona um clasula AND na consulta.
	 *
	 * @return O {@link Filtro} que está sendo manipulado.
	 */
	public Filtro or() {
		Filtro filtro = new Filtro(TipoClausula.OR, null, null, null);
		filtros.add(filtro);
		return this;
	}

	/**
	 * Retorna todos os {@link Filtro} que foram criados.
	 *
	 * @return Os {@link Filtro} que foram criados.
	 */
	public List<Filtro> build() {
		return Collections.unmodifiableList(filtros);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Filtro [nome=").append(nome).append(", valor=").append(valor).append(", tipoClausula=").append(tipoClausula)
				.append(", operandoClausula=").append(operandoClausula).append("]");
		return builder.toString();
	}
}
