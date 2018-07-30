package br.com.osm.enuns;

public enum UnidadeMedida {

	UN("unidade"),
	M2("metro.quadrado"),
	MT("metro"),
	CM("centimetro"),
	MM("milimetro"),
	KG("quilograma"),
	G("grama"),
	MG("miligrama"),
	M3("metro.cubico"),
	L("litro"),
	ML("mililitro");

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
