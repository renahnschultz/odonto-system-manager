package br.com.osm.enuns;

public enum SituacaoAgendamento {

	PENDENTE("pendente"),
	APROVADO("aprovado"),
	REPROVADO("reprovado"),
	EXECUCAO("em.execucao"),
	FINALIZADO("finalizado");
	
	private String descricao;
	
	SituacaoAgendamento(String descricao){
		this.setDescricao(descricao);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
