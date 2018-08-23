package br.com.osm.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.generico.AbstractAtivo;
import br.com.osm.annotations.OrdenacaoPadrao;
import br.com.osm.enuns.SimNao;
import br.com.osm.enuns.TipoResposta;

@Entity
@Table(name = "pergunta")
public class Pergunta extends AbstractAtivo implements Entidade<Long> {
	/**
	 * @author Renahn Schultz
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@OrdenacaoPadrao
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "descricao", nullable = false, length = 45)
	private String descricao;

	@Enumerated
	@Column(name = "tipo_resposta", nullable = false)
	private TipoResposta tipoResposta;

	@Enumerated
	@Column(name = "complemento", nullable = false)
	private SimNao complemento;

	public Pergunta() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoResposta getTipoResposta() {
		return tipoResposta;
	}

	public void setTipoResposta(TipoResposta tipoResposta) {
		this.tipoResposta = tipoResposta;
	}

	public SimNao getComplemento() {
		return complemento;
	}

	public void setComplemento(SimNao complemento) {
		this.complemento = complemento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((complemento == null) ? 0 : complemento.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((tipoResposta == null) ? 0 : tipoResposta.hashCode());
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
		Pergunta other = (Pergunta) obj;
		if (complemento != other.complemento)
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (tipoResposta == null) {
			if (other.tipoResposta != null)
				return false;
		} else if (!tipoResposta.equals(other.tipoResposta))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pergunta [id=" + id + ", descricao=" + descricao + ", tipoResposta=" + tipoResposta + ", complemento=" + complemento + "]";
	}

}
