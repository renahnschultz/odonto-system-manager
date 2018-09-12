package br.com.osm.enuns;

public enum DiaSemana {

	DOMINGO("domingo"),
	SEGUNDA("segunda"),
	TERCA("terca"),
	QUARTA("quarta"),
	QUINTA("quinta"),
	SEXTA("sexta"),
	SABADO("sabado");
	
	private String descricao;
	
	DiaSemana(String descricao){
		this.setDescricao(descricao);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
