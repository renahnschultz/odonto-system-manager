package br.com.osm.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.generico.AbstractAtivo;
import br.com.oms.enuns.TipoServico;
import br.com.oms.enuns.TipoUsuario;
import br.com.osm.annotations.OrdenacaoPadrao;

@Entity
@Table(name = "servico")
public class Servico extends AbstractAtivo implements Entidade<Long> {

	/**
	 * @author Renahn 07-22-2018
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@OrdenacaoPadrao
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome", nullable = false, length = 45)
	private String nome;

	@Column(name = "descricao", nullable = false, length = 200)
	private String descricao;

	@Enumerated
	@Column(name = "tipo", nullable = false)
	private TipoServico tipo;

	@Column(name = "preco_sugerido", nullable = true)
	private Double precoSugerido;

	public Servico() {
	}

	@Override
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Servico other = (Servico) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Servico [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", tipo=" + tipo + "]";
	}

	public TipoServico getTipo() {
		return tipo;
	}

	public void setTipo(TipoServico tipo) {
		this.tipo = tipo;
	}

	public Double getPrecoSugerido() {
		return precoSugerido;
	}

	public void setPrecoSugerido(Double precoSugerido) {
		this.precoSugerido = precoSugerido;
	}

}
