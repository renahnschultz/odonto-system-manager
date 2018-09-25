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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.generico.AbstractAtivo;
import br.com.osm.annotations.OrdenacaoPadrao;
import br.com.osm.enuns.SimNao;
import br.com.osm.enuns.SituacaoAgendamento;

@Entity
@Table(name = "configuracao")
public class Configuracao extends AbstractAtivo implements Entidade<Long> {

	/**
	 * @author Renahn 28-07-2018
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@OrdenacaoPadrao
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "razao_social", nullable = false, length = 100)
	private String razaoSocial;

	@Column(name = "fantasia", nullable = false, length = 100)
	private String fantasia;
	
	@Column(name = "endereco", nullable = false, length = 100)
	private String endereco;
	
	@Column(name = "bairro", nullable = false, length = 100)
	private String bairro;
	
	@Column(name = "cidade", nullable = false, length = 100)
	private String cidade;
	
	@Column(name = "estado", nullable = false, length = 50)
	private String estado;

	@Column(name = "cnpj", nullable = false, length = 14)
	private String cnpj;

	@Column(name = "telefone_1", nullable = false, length = 15)
	private String telefone1;

	@Column(name = "telefone_2", nullable = true, length = 15)
	private String telefone2;
	
	@Column(name = "email", nullable = false, length = 120)
	private String email;
	
	@Column(name = "valor_multa", nullable = true)
	private Double valorMulta;
	
	@Enumerated
	@Column(name = "multa", nullable = false)
	private SimNao multa;

	public Configuracao() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getFantasia() {
		return fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Double getValorMulta() {
		return valorMulta;
	}

	public void setValorMulta(Double valorMulta) {
		this.valorMulta = valorMulta;
	}

	public SimNao getMulta() {
		return multa;
	}

	public void setMulta(SimNao multa) {
		this.multa = multa;
	}

	
	
}
