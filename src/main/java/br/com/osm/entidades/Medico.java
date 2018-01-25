package br.com.osm.entidades;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "medico")
public class Medico extends Usuario {

	/**
	 * @author Renahn 23-01-2018
	 */
	private static final long serialVersionUID = 1L;

	private String nome;

	@OneToOne(mappedBy = "medico")
	private Autenticacao autenticacao;

	public Medico() {
	}

	@Override
	public String getNome() {
		return nome;
	}

	@Override
	public void setNome(String nome) {
		this.nome = nome;
	}

}
