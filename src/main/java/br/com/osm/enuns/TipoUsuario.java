package br.com.osm.enuns;

public enum TipoUsuario {

	ADMINISTRADOR("administrador.odonto.system"),
	ADMINISTRADOR_CLINICA("administrador.clinicaconsultorio"),
	ODONTOLOGO("odontologo"),
	SECRETARIO("secretario"),
	PACIENTE("paciente");

	private String descricao;

	TipoUsuario(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
