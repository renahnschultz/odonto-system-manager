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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.osm.annotations.OrdenacaoPadrao;
@Entity
@Table(name = "atendimento")
public class Atendimento implements Entidade<Long> {

	/**
	 * @author Renahn 28-07-2018
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@OrdenacaoPadrao
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_agendamento")
	private Agendamento agendamento;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_inicio", nullable = false)
	private Date dataInicio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_fim", nullable = false)
	private Date dataFim;
	
	@Column(name = "valor_total", nullable = false)
	private Double valorTotal = 0.0;
	
	@OneToMany(mappedBy = "atendimento", cascade = CascadeType.ALL)
	private List<AcaoServico> servicos = new ArrayList<AcaoServico>();

	public Atendimento() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Agendamento getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
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
		Atendimento other = (Atendimento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public List<AcaoServico> getServicos() {
		return servicos;
	}

	public void setServicos(List<AcaoServico> servicos) {
		this.servicos = servicos;
	}
	
	public void adicionarServico(AcaoServico servico) {
		if(!servicos.contains(servico)) {
			servicos.add(servico);
			this.valorTotal += servico.getPreco();
		}
	}

	public void removerServico(AcaoServico servico) {
		if(servicos.contains(servico)) {
			servicos.remove(servico);
			this.valorTotal -= servico.getPreco();
		}
	}
	
	public Double totalServicos() {
		Double total = 0.0;
		for (AcaoServico servico : servicos) {
			total += servico.getPreco();
		}
		return total;
	}
	

}
