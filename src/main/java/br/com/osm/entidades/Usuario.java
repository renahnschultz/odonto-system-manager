package br.com.osm.entidades;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.generico.AbstractAtivo;

@Entity
@Table(name = "usuario")
public class Usuario extends AbstractAtivo implements Entidade<Long> {

	/**
	 * @author Renahn 06-02-2018
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome", nullable = false, length = 45)
	private String nome;

	@Column(name = "email", nullable = false, length = 45)
	private String email;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_nascimento", nullable = false)
	private Date dataNascimento;

	@Column(name = "cpf", nullable = false, length = 15)
	private String cpf;

	@Column(name = "senha", nullable = false, length = 16)
	private String senha;

	@Column(name = "telefone", nullable = false, length = 16)
	private String telefone;

	@Column(name = "tipo", nullable = false, length = 16)
	private Integer tipo;

	public Usuario() {
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder().append("Usuario [id=").append(id).append(", nome=")
				.append(nome).append(", email=").append(email).append(", dataNascimento=").append(dataNascimento)
				.append(", cpf=").append(cpf).append(", senha=").append(senha).append(", telefone=").append(telefone)
				.append("]");
		return stringBuilder.toString();
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

}
