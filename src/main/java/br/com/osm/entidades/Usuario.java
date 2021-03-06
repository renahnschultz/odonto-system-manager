package br.com.osm.entidades;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.generico.AbstractAtivo;
import br.com.osm.annotations.OrdenacaoPadrao;
import br.com.osm.enuns.Genero;
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

	@Column(name = "sobrenome", nullable = false, length = 45)
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

	@Column(name = "telefone_whats", nullable = true, length = 16)
	private String telefoneWhats;

	@Column(name = "cro", nullable = true, length = 16)
	private String cro;

	@Enumerated
	@Column(name = "tipo", nullable = false, length = 16)
	private TipoUsuario tipo;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_has_permissao", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_permissao"))
	private List<Permissao> permissoes = new ArrayList<Permissao>();

	@OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Anamnese anamnese;

	@OneToMany(mappedBy = "odontologo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<HorarioOdontologo> horariosOdontologo;

	@OneToMany(mappedBy = "odontologo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Agendamento> agendamentos;
	@OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Agendamento> agendamentosPaciente;

	@Enumerated
	@Column(name = "genero", nullable = false, length = 4)
	private Genero genero;

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

	public void adicionarPermissao(Permissao permissao) {
		if (!permissoes.contains(permissao)) {
			permissoes.add(permissao);
		}
	}

	public String getCro() {
		return cro;
	}

	public void setCro(String cro) {
		this.cro = cro;
	}

	public Anamnese getAnamnese() {
		return anamnese;
	}

	public void setAnamnese(Anamnese anamnese) {
		this.anamnese = anamnese;
	}

	public int getIdade() {
		return calculaIdade(dataNascimento);
	}

	private int calculaIdade(Date dataNasc) {

		Calendar dataNascimento = Calendar.getInstance();
		dataNascimento.setTime(dataNasc);
		Calendar hoje = Calendar.getInstance();

		int idade = hoje.get(Calendar.YEAR) - dataNascimento.get(Calendar.YEAR);

		if (hoje.get(Calendar.MONTH) < dataNascimento.get(Calendar.MONTH)) {
			idade--;
		} else {
			if (hoje.get(Calendar.MONTH) == dataNascimento.get(Calendar.MONTH)
					&& hoje.get(Calendar.DAY_OF_MONTH) < dataNascimento.get(Calendar.DAY_OF_MONTH)) {
				idade--;
			}
		}

		return idade;
	}

	public String getNomeCompleto() {
		return nome + " " + sobrenome;
	}

	public List<HorarioOdontologo> getHorariosOdontologo() {
		return horariosOdontologo;
	}

	public void setHorariosOdontologo(List<HorarioOdontologo> horariosOdontologo) {
		this.horariosOdontologo = horariosOdontologo;
	}

	public List<Agendamento> getAgendamentos() {
		return agendamentos;
	}

	public void setAgendamentos(List<Agendamento> agendamentos) {
		this.agendamentos = agendamentos;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public List<Agendamento> getAgendamentosPaciente() {
		return agendamentosPaciente;
	}

	public void setAgendamentosPaciente(List<Agendamento> agendamentosPaciente) {
		this.agendamentosPaciente = agendamentosPaciente;
	}

}
