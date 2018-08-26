package br.com.osm.enuns;

public enum EstadoDente {

	NORMAL("normal"),
	PROTESE("protese"),
	AUSENTE("ausente");
	
	private String descricao;
	
	EstadoDente(String descricao){
		this.setDescricao(descricao);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
