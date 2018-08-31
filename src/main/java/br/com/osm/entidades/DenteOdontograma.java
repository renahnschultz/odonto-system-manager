package br.com.osm.entidades;

import java.util.ArrayList;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.osm.annotations.OrdenacaoPadrao;
import br.com.osm.enuns.EstadoDente;
import br.com.osm.enuns.SimNaoOutro;

@Entity
@Table(name = "dente_has_odontograma")
public class DenteOdontograma implements Entidade<Long> {

	/**
	 * @author Renahn 28-07-2018
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@OrdenacaoPadrao
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_odontograma")
	private Odontograma odontograma;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_dente")
	private Dente dente;
	
	@Enumerated
	@Column(name = "estado", nullable = false)
	private EstadoDente estado = EstadoDente.NORMAL;
	
	@OneToMany(mappedBy = "dente", cascade = CascadeType.ALL)
	private List<Marcacao> marcacoes;

	public DenteOdontograma() {
	}
	
	public DenteOdontograma(Odontograma odontograma, Dente dente) {
		super();
		this.odontograma = odontograma;
		this.dente = dente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Odontograma getOdontograma() {
		return odontograma;
	}

	public void setOdontograma(Odontograma odontograma) {
		this.odontograma = odontograma;
	}

	public Dente getDente() {
		return dente;
	}

	public void setDente(Dente dente) {
		this.dente = dente;
	}

	public EstadoDente getEstado() {
		return estado;
	}

	public void setEstado(EstadoDente estado) {
		this.estado = estado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dente == null) ? 0 : dente.hashCode());
		result = prime * result + ((odontograma == null) ? 0 : odontograma.hashCode());
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
		DenteOdontograma other = (DenteOdontograma) obj;
		if (dente == null) {
			if (other.dente != null)
				return false;
		} else if (!dente.equals(other.dente))
			return false;
		if (odontograma == null) {
			if (other.odontograma != null)
				return false;
		} else if (!odontograma.equals(other.odontograma))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DenteOdontograma [id=" + id + ", dente=" + dente + ", estado=" + estado + "]";
	}
	
	public void adicionarMarcacao(Marcacao marcacao) {
		if(marcacoes == null) {
			marcacoes = new ArrayList<Marcacao>();
		}
		marcacoes.add(marcacao);
	}

	public List<Marcacao> getMarcacoes() {
		return marcacoes;
	}

	public void setMarcacoes(List<Marcacao> marcacoes) {
		this.marcacoes = marcacoes;
	}

}
