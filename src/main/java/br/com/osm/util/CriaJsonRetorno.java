package br.com.osm.util;

public class CriaJsonRetorno {

	public static final String MENSAGEM = "mensagem";
	
	public Throwable criarRetornoValidacao(Exception e) {
		return criarRetornoIndefinido(e);
	}
	
	public Throwable criarRetornoIndefinido(Exception e) {
		return e.getCause();
	}
}
