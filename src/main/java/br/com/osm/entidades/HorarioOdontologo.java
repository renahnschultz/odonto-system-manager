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

import br.com.osm.enuns.DiaSemana;

@Entity
@Table(name = "horario_odontologo")
public class HorarioOdontologo implements Entidade<Long> {

	/**
	 * @author Renahn 28-07-2018
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_odontologo")
	private Usuario odontologo;

	@Enumerated
	@Column(name = "dia_semana", nullable = false)
	private DiaSemana diaSemana;

	@Column(name = "periodo", nullable = false)
	private Short periodo;

	@Temporal(TemporalType.TIME)
	@Column(name = "inicio", nullable = false)
	private Date inicio;

	@Temporal(TemporalType.TIME)
	@Column(name = "fim", nullable = false)
	private Date fim;

	public HorarioOdontologo() {
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

	public DiaSemana getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(DiaSemana diaSemana) {
		this.diaSemana = diaSemana;
	}

	public Short getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Short periodo) {
		this.periodo = periodo;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((diaSemana == null) ? 0 : diaSemana.hashCode());
		result = prime * result + ((odontologo == null) ? 0 : odontologo.hashCode());
		result = prime * result + ((periodo == null) ? 0 : periodo.hashCode());
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
		HorarioOdontologo other = (HorarioOdontologo) obj;
		if (diaSemana != other.diaSemana)
			return false;
		if (odontologo == null) {
			if (other.odontologo != null)
				return false;
		} else if (!odontologo.equals(other.odontologo))
			return false;
		if (periodo == null) {
			if (other.periodo != null)
				return false;
		} else if (!periodo.equals(other.periodo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HorarioOdontologo [id=" + id + ", diaSemana=" + diaSemana + ", periodo=" + periodo + ", inicio=" + inicio + ", fim=" + fim
				+ "]";
	}
	
	


}
