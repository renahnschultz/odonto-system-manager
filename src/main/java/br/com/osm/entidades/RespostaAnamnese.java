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
@Table(name = "pergunta_has_anamnese")
public class RespostaAnamnese implements Entidade<Long> {

	/**
	 * @author Renahn 28-07-2018
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@OrdenacaoPadrao
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "anamnese_id")
	private Anamnese anamnese;

	@JoinColumn(name = "pergunta_id")
	private Pergunta pergunta;
	
	@Enumerated
	@Column(name = "resposta_boolean")
	private SimNaoOutro respostaBoolean;
	
	@Column(name = "resposta_texto", length = 500)
	private String respostaTexto;
	
	@Column(name = "resposta_complemento", length = 500)
	private String complemento;

	public RespostaAnamnese() {
	}
	
	public RespostaAnamnese(Anamnese anamnese, Pergunta pergunta) {
		super();
		this.setPergunta(pergunta);
		this.setAnamnese(anamnese);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getRespostaTexto() {
		return respostaTexto;
	}

	public void setRespostaTexto(String respostaTexto) {
		this.respostaTexto = respostaTexto;
	}

	public SimNaoOutro getRespostaBoolean() {
		return respostaBoolean;
	}

	public void setRespostaBoolean(SimNaoOutro respostaBoolean) {
		this.respostaBoolean = respostaBoolean;
	}

	public Pergunta getPergunta() {
		return pergunta;
	}

	public void setPergunta(Pergunta pergunta) {
		this.pergunta = pergunta;
	}

	public Anamnese getAnamnese() {
		return anamnese;
	}

	public void setAnamnese(Anamnese anamnese) {
		this.anamnese = anamnese;
	}

}
