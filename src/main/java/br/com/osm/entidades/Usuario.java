package br.com.osm.entidades;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.generico.AbstractAtivo;
import br.com.osm.annotations.OrdenacaoPadrao;
import br.com.osm.enuns.TipoUsuario;

@Entity
@Table(name = "usuario")
public class Usuario extends AbstractAtivo implements Entidade<Long> {

	/**
	 * @author Renahn 06-02-2018
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@OrdenacaoPadrao
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome", nullable = false, length = 100)
	private String nome;

	private String sobrenome;

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

	@Column(name = "telefone_whats", nullable = false, length = 16)
	private String telefoneWhats;

	@Enumerated
	@Column(name = "tipo", nullable = false, length = 16)
	private TipoUsuario tipo;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usuario_has_permissao", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_permissao"))
	private List<Permissao> permissoes;

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

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder().append("Usuario [id=").append(id).append(", nome=")
				.append(nome).append(", sobrenome=").append(sobrenome).append(", email=").append(email)
				.append(", dataNascimento=").append(dataNascimento).append(", cpf=").append(cpf)
				.append(", senha=").append(senha).append(", telefone=").append(telefone)
				.append("]");
		return stringBuilder.toString();
	}

	public TipoUsuario getTipo() {
		return tipo;
	}

	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
	}

	public String getTelefoneWhats() {
		return telefoneWhats;
	}

	public void setTelefoneWhats(String telefoneWhats) {
		this.telefoneWhats = telefoneWhats;
	}

	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

}
