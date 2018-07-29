package br.com.osm.enuns;

public enum UnidadeMedida {

	UN("Unidade"),
	M2("Metro Quadrado"),
	MT("Metro"),
	CM("Centímetro"),
	MM("Milimetro"),
	KG("Quilograma"),
	G("Grama"),
	MG("Miligrama"),
	M3("Metro Cúbico"),
	L("Litros"),
	ML("Mililitros");

	private String descricao;

	UnidadeMedida(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
