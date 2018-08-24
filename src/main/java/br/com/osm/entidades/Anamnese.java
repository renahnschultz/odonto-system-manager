package br.com.osm.entidades;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.osm.annotations.OrdenacaoPadrao;

@Entity
@Table(name = "anamnese")
public class Anamnese implements Entidade<Long> {

	/**
	 * @author Renahn 28-07-2018
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@OrdenacaoPadrao
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_preenchimento", nullable = false)
	private Date dataPreenchimento;

	@OneToMany(mappedBy = "anamnese", cascade = CascadeType.ALL)
	private List<RespostaAnamnese> respostas;

	public Anamnese() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getDataPreenchimento() {
		return dataPreenchimento;
	}

	public void setDataPreenchimento(Date dataPreenchimento) {
		this.dataPreenchimento = dataPreenchimento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataPreenchimento == null) ? 0 : dataPreenchimento.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		Anamnese other = (Anamnese) obj;
		if (dataPreenchimento == null) {
			if (other.dataPreenchimento != null)
				return false;
		} else if (!dataPreenchimento.equals(other.dataPreenchimento))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	public List<RespostaAnamnese> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<RespostaAnamnese> respostas) {
		this.respostas = respostas;
	}
	
	public void adicionarResposta(RespostaAnamnese resposta) {
		respostas.add(resposta);
	}

}
