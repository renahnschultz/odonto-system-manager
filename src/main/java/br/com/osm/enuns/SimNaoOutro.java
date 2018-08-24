package br.com.osm.enuns;

public enum SimNaoOutro {

	NAO("nao"),
	SIM("sim"),
	OUTRO("outro");
	
	private String descricao;
	
	SimNaoOutro(String descricao){
		this.setDescricao(descricao);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
