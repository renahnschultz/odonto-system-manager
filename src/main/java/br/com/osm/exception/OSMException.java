package br.com.osm.exception;

import java.io.Serializable;

/**
 *
 * @author geraldo
 *
 */
public class OSMException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;

	private String idMensagem;
	private String parametros[];

	/**
	 * Construtor que recebe como parâmetro o id da mensagem no message bundle que deverá ser apresentada.
	 *
	 * @param idMensagem
	 *            O id da mensagem que será apresentada.
	 * @param parametros
	 *            Os parâmetros que serão utilizados para montar a mensagem.
	 */
	public OSMException(String idMensagem, String... parametros) {
		this.idMensagem = idMensagem;
		this.parametros = parametros;
	}

	/**
	 * Construtor que recebe como parâmetro o id da mensagem no message bundle que deverá ser apresentada.
	 *
	 * @param throwable
	 *            A {@link Exception} que originou o erro.
	 * @param idMensagem
	 *            O id da mensagem que será apresentada.
	 * @param parametros
	 *            Os parâmetros que serão utilizados para montar a mensagem.
	 */
	public OSMException(Throwable throwable, String idMensagem, String... parametros) {
		super(throwable);
		this.idMensagem = idMensagem;
		this.parametros = parametros;
	}

	public OSMException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Obtem o id da mensagem que será exibida.
	 *
	 * @return O id da mensagem.
	 */
	public String getIdMensagem() {
		return idMensagem;
	}

	/**
	 * Obtem os parametros que serão usados para compor a mensagem.
	 *
	 * @return Os parametros utilizados na mensagem.
	 */
	public String[] getParametros() {
		return parametros;
	}

}
