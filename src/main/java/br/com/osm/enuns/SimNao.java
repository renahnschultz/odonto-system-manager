package br.com.osm.enuns;

public enum SimNao {

	NAO("nao"),
	SIM("sim");
	
	private String descricao;
	
	SimNao(String descricao){
		this.setDescricao(descricao);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
