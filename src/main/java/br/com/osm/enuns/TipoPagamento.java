package br.com.osm.enuns;

public enum TipoPagamento {

	DINHEIRO("dinheiro"),
	CARTAO_CREDITO("cartao.credito"),
	CARTAO_DEBITO("cartao.debito"),
	CHEQUE("cheque"),
	CONVENIO("convenio");
	
	private String descricao;
	
	TipoPagamento(String descricao){
		this.setDescricao(descricao);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
