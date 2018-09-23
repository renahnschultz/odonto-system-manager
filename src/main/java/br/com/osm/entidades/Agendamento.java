package br.com.osm.entidades;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.generico.AbstractAtivo;
import br.com.osm.annotations.OrdenacaoPadrao;
import br.com.osm.enuns.SituacaoAgendamento;

@Entity
@Table(name = "agendamento")
public class Agendamento extends AbstractAtivo implements Entidade<Long> {

	/**
	 * @author Renahn 28-07-2018
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@OrdenacaoPadrao
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_odontologo")
	private Usuario odontologo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_paciente")
	private Usuario paciente;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_hora", nullable = false)
	private Date dataHora;

	@Enumerated
	@Column(name = "situacao", nullable = false)
	private SituacaoAgendamento situacao;

	public Agendamento(Usuario odontologo, Date dataHora) {
		super();
		this.odontologo = odontologo;
		this.dataHora = dataHora;
	}

	public Agendamento() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getOdontologo() {
		return odontologo;
	}

	public void setOdontologo(Usuario odontologo) {
		this.odontologo = odontologo;
	}

	public Usuario getPaciente() {
		return paciente;
	}

	public void setPaciente(Usuario paciente) {
		this.paciente = paciente;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public SituacaoAgendamento getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoAgendamento situacao) {
		this.situacao = situacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataHora == null) ? 0 : dataHora.hashCode());
		result = prime * result + ((odontologo == null) ? 0 : odontologo.hashCode());
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
		Agendamento other = (Agendamento) obj;
		if (dataHora == null) {
			if (other.dataHora != null)
				return false;
		} else if (!dataHora.equals(other.dataHora))
			return false;
		if (odontologo == null) {
			if (other.odontologo != null)
				return false;
		} else if (!odontologo.equals(other.odontologo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Agendamento [id=" + id + ", dataHora=" + dataHora + "]";
	}

}
