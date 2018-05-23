package br.com.generico;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import br.com.oms.enuns.StatusAtivo;

@MappedSuperclass
public abstract class AbstractAtivo {

	@Enumerated
	@Column(name = "ativo", nullable = false)
	private StatusAtivo ativo = StatusAtivo.ATIVO;

	public StatusAtivo getAtivo() {
		return ativo;
	}

	public void setAtivo(StatusAtivo ativo) {
		this.ativo = ativo;
	}
}
