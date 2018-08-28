package br.com.osm.entidades;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.generico.AbstractAtivo;
import br.com.osm.annotations.OrdenacaoPadrao;
import br.com.osm.enuns.TipoOdontograma;

@Entity
@Table(name = "odontograma")
public class Odontograma extends AbstractAtivo implements Entidade<Long> {

	/**
	 * @author Renahn 28-07-2018
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@OrdenacaoPadrao
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_paciente")
	private Usuario paciente;
	
	@Enumerated
	@Column(name = "tipo", nullable = false)
	private TipoOdontograma tipo;
	
	@OneToMany(mappedBy = "odontograma", cascade = CascadeType.ALL)
	private List<DenteOdontograma> dentes;

	public Odontograma() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getPaciente() {
		return paciente;
	}

	public void setPaciente(Usuario paciente) {
		this.paciente = paciente;
	}

	public TipoOdontograma getTipo() {
		return tipo;
	}

	public void setTipo(TipoOdontograma tipo) {
		this.tipo = tipo;
	}

	public List<DenteOdontograma> getDentes() {
		return dentes;
	}

	public void setDentes(List<DenteOdontograma> dentes) {
		this.dentes = dentes;
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
		Odontograma other = (Odontograma) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Odontograma [id=" + id + ", paciente=" + paciente + ", tipo=" + tipo + ", dentes=" + dentes + "]";
	}
	
	
	public void adicionarDente(DenteOdontograma dente) {
		dentes.add(dente);
	}
	

}
