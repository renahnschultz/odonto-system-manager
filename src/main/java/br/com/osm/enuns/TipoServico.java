package br.com.osm.enuns;

public enum TipoServico {

	CIRURGIA("cirurgia"),
	TRATAMENTO("tratamento"),
	OUTRO("outro");

	private String descricao;

	TipoServico(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
