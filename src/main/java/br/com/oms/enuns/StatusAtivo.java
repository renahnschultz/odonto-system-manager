package br.com.oms.enuns;

public enum StatusAtivo {

	INATIVO("inativo"),
	ATIVO("ativo"),
	EXCLUIDO("excluido");
	
	private String descricao;
	
	StatusAtivo(String descricao){
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	
}
