package br.com.osm.enuns;

public enum TipoResposta {

	TEXTO("texto"),
	SIM_NAO_OUTRO("sim.nao.outro");
	
	private String descricao;
	
	TipoResposta(String descricao){
		this.setDescricao(descricao);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
