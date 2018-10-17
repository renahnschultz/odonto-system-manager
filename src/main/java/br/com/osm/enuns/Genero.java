package br.com.osm.enuns;

public enum Genero {

	FEMININO("feminino"),
	MASCULINO("masculino");
	
	private String descricao;
	
	Genero(String descricao){
		this.setDescricao(descricao);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
