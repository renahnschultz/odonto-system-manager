package br.com.osm.entidades;

import java.util.ArrayList;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.osm.annotations.OrdenacaoPadrao;
@Entity
@Table(name = "acao_servico")
public class AcaoServico implements Entidade<Long> {

	/**
	 * @author Renahn 28-07-2018
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@OrdenacaoPadrao
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_servico")
	private Servico servico;

	@Column(name = "preco", nullable = false)
	private Double preco;

	@Temporal(TemporalType.DATE)
	@Column(name = "data", nullable = false)
	private Date data;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_atendimento")
	private Atendimento atendimento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_marcacao")
	private Marcacao marcacao;
	
	@OneToMany(mappedBy = "acaoServico", cascade = CascadeType.ALL)
	private List<AcaoServicoMaterial> materiais = new ArrayList<AcaoServicoMaterial>();
	

	public AcaoServico() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Atendimento getAtendimento() {
		return atendimento;
	}

	public void setAtendimento(Atendimento atendimento) {
		this.atendimento = atendimento;
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
		AcaoServico other = (AcaoServico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public List<AcaoServicoMaterial> getMateriais() {
		return materiais;
	}

	public void setMateriais(List<AcaoServicoMaterial> materiais) {
		this.materiais = materiais;
	}

	public Marcacao getMarcacao() {
		return marcacao;
	}

	public void setMarcacao(Marcacao marcacao) {
		this.marcacao = marcacao;
	}

	

}
