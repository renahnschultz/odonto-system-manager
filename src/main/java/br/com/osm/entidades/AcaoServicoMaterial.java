package br.com.osm.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.osm.annotations.OrdenacaoPadrao;
import br.com.osm.enuns.SimNaoOutro;

@Entity
@Table(name = "acao_servico_has_material")
public class AcaoServicoMaterial implements Entidade<Long> {

	/**
	 * @author Renahn 28-07-2018
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@OrdenacaoPadrao
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "acao_servico_id")
	private AcaoServico acaoServico;

	@JoinColumn(name = "material_id")
	private Material material;

	@Column(name = "quantidade", nullable = false)
	private Double quantidade;

	public AcaoServicoMaterial() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AcaoServico getAcaoServico() {
		return acaoServico;
	}

	public void setAcaoServico(AcaoServico acaoServico) {
		this.acaoServico = acaoServico;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acaoServico == null) ? 0 : acaoServico.hashCode());
		result = prime * result + ((material == null) ? 0 : material.hashCode());
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
		AcaoServicoMaterial other = (AcaoServicoMaterial) obj;
		if (acaoServico == null) {
			if (other.acaoServico != null)
				return false;
		} else if (!acaoServico.equals(other.acaoServico))
			return false;
		if (material == null) {
			if (other.material != null)
				return false;
		} else if (!material.equals(other.material))
			return false;
		return true;
	}


}
