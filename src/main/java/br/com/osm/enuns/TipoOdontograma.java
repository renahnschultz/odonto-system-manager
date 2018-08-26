package br.com.osm.enuns;

public enum TipoOdontograma {

	ADULTO("adulto"),
	INFANTIL("infantil");
	
	private String descricao;
	
	TipoOdontograma(String descricao){
		this.setDescricao(descricao);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
