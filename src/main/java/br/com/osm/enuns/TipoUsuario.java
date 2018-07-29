package br.com.osm.enuns;

public enum TipoUsuario {

	ADMINISTRADOR("Administrador Odonto System"),
	ADMINISTRADOR_CLINICA("Administrador Clinica/Consultorio"),
	ODONTOLOGO("Odontólogo"),
	SECRETARIO("Secretário"),
	PACIENTE("Paciente");

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
