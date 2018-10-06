package br.com.osm.entidades;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.osm.annotations.OrdenacaoPadrao;
import br.com.osm.enuns.SimNao;

@Entity
@Table(name = "debito")
public class Debito implements Entidade<Long> {

	/**
	 * @author Renahn 28-07-2018
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@OrdenacaoPadrao
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "motivo", nullable = false, length = 100)
	private String motivo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data", nullable = false)
	private Date data = new Date();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_agendamento")
	private Agendamento agendamento;

	@Column(name = "valor", nullable = false)
	private Double valor;
	
	@OneToMany(mappedBy = "debito", cascade = CascadeType.ALL, 
			fetch = FetchType.LAZY)
	private List<Pagamento> pagamentos;

	@Enumerated
	@Column(name = "quitado", nullable = false)
	private SimNao quitado;

	public Debito() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Agendamento getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
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
		Debito other = (Debito) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public List<Pagamento> getPagamentos() {
		return pagamentos;
	}

	public void setPagamentos(List<Pagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}
	
	public Double totalPago() {
		Double total = 0.0;
		for (Pagamento pagamento : pagamentos) {
			total += pagamento.getValor();
		}
		return total;
	}

	public SimNao getQuitado() {
		return quitado;
	}

	public void setQuitado(SimNao quitado) {
		this.quitado = quitado;
	}

	public Double valorRestante() {
		Double total = valor - totalPago();
		return total;
	}

}
