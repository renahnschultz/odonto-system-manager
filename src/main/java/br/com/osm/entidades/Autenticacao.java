package br.com.osm.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "autenticacao")
public class Autenticacao implements Entidade<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "nome", nullable = false, length = 45)
	private String login;
	@Column(name = "nome", nullable = false, length = 45)
	private String senha;

	@OneToOne
	@JoinColumn(name = "id_autenticacao", unique = true, nullable = false, updatable = false)
	private Medico medico;

	@Override
	public Long getId() {
		return this.id;
	}

}
