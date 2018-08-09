package br.com.osm.entidades;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.osm.annotations.OrdenacaoPadrao;

@Entity
@Table(name = "permissao")
public class Permissao {

	@Id
	@OrdenacaoPadrao
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "descricao", nullable = false)
	private String descricao;
	
	@Column(name = "permissao", nullable = false)
	private String permissao;
	
	@Column(name = "grupo", nullable = false)
	private String grupo;
}
