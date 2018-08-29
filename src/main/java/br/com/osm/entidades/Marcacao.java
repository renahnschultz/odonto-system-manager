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
import javax.persistence.Transient;

import br.com.generico.AbstractAtivo;
import br.com.osm.annotations.OrdenacaoPadrao;
import br.com.osm.enuns.UnidadeMedida;
@Entity
@Table(name = "marcacao")
public class Marcacao implements Entidade<Long> {

	/**
	 * @author Renahn 28-07-2018
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@OrdenacaoPadrao
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome", nullable = false, length = 45)
	private String nome;

	@Column(name = "descricao", nullable = true)
	private String descricao;
	
	@Column(name = "posX", nullable = false)
	private Double posX;

	@Column(name = "posY", nullable = false)
	private Double posY;
	
	@Column(name = "cor", nullable = false)
	private String cor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_dente_has_odontograma")
	private DenteOdontograma dente;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_odontograma")
	private Odontograma odontograma;

	@Transient
	private Date dataHora;

	public Marcacao() {
	}

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getPosX() {
		return posX;
	}

	public void setPosX(Double posX) {
		this.posX = posX;
	}

	public Double getPosY() {
		return posY;
	}

	public void setPosY(Double posY) {
		this.posY = posY;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public DenteOdontograma getDente() {
		return dente;
	}

	public void setDente(DenteOdontograma dente) {
		this.dente = dente;
	}

	public Odontograma getOdontograma() {
		return odontograma;
	}

	public void setOdontograma(Odontograma odontograma) {
		this.odontograma = odontograma;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dente == null) ? 0 : dente.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((posX == null) ? 0 : posX.hashCode());
		result = prime * result + ((posY == null) ? 0 : posY.hashCode());
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
		Marcacao other = (Marcacao) obj;
		if (dente == null) {
			if (other.dente != null)
				return false;
		} else if (!dente.equals(other.dente))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (posX == null) {
			if (other.posX != null)
				return false;
		} else if (!posX.equals(other.posX))
			return false;
		if (posY == null) {
			if (other.posY != null)
				return false;
		} else if (!posY.equals(other.posY))
			return false;
		return true;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	@Override
	public String toString() {
		return "Marcacao [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", posX=" + posX + ", posY=" + posY + ", cor=" + cor
				+ ", dataHora=" + dataHora + "]";
	}
	
	

}
