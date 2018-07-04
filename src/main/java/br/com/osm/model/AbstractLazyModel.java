/**
 *
 */
package br.com.osm.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import br.com.oms.enuns.StatusAtivo;
import br.com.osm.annotations.OrdenacaoPadrao;
import br.com.osm.dao.GenericoDAO;
import br.com.osm.entidades.Entidade;
import br.com.osm.exception.OSMException;
import br.com.osm.util.ConversorGenerico;

public class AbstractLazyModel<PK extends Serializable, TipoClasse extends Entidade<?>> extends LazyDataModel<TipoClasse> {

	private static final long serialVersionUID = 1L;
	protected CriteriaBuilder builder;
	protected String ordenacaoPadrao;
	protected GenericoDAO<PK, TipoClasse> dao;
	protected CriteriaQuery<TipoClasse> query;
	protected Root<TipoClasse> from;
	protected Class<TipoClasse> tipoClasse;
	protected Class<PK> tipoId;
	private Map<String, Object> filters;
	/**
	 * Lista de objetos que não devem ser retornados na lista do lazy.
	 */
	private List<TipoClasse> entidadesSelecionadas;

	public AbstractLazyModel() {
	}

	public AbstractLazyModel(GenericoDAO<PK, TipoClasse> genericoDAO, Class<PK> tipoId, Class<TipoClasse> tipoClasse) {
		this.dao = genericoDAO;
		this.tipoClasse = tipoClasse;
		this.tipoId = tipoId;
		inicializar();
	}

	protected void inicializar() {
		Field[] atributos = this.tipoClasse.getDeclaredFields();
		int contadorOrdenacaoPadrao = 0;
		for (Field atributo : atributos) {
			OrdenacaoPadrao ordenacaoPadrao = atributo.getAnnotation(OrdenacaoPadrao.class);
			if (ordenacaoPadrao != null) {
				this.ordenacaoPadrao = atributo.getName();
				if (!ordenacaoPadrao.campoEntidade().trim().isEmpty()) {
					this.ordenacaoPadrao += "." + ordenacaoPadrao.campoEntidade().trim();
				}
				contadorOrdenacaoPadrao++;
			}
		}
		if (contadorOrdenacaoPadrao > 1) {
			throw new IllegalArgumentException(
					"Anotação @OrdenacaoPadrao encontrada mais de uma vez na classe " + this.tipoClasse.getName());
		}

		if (ordenacaoPadrao == null) {
			throw new IllegalArgumentException("Anotação @OrdenacaoPadrao não encontrada na classe " + this.tipoClasse.getName());
		}
		this.builder = dao.getCriteriaBuilder();
		this.query = builder.createQuery(tipoClasse);
		this.from = query.from(tipoClasse);
		this.entidadesSelecionadas = new ArrayList<TipoClasse>();
		this.filters = new HashMap<String, Object>();
	}

	@Override
	public List<TipoClasse> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		try {
			if (!this.filters.isEmpty()) {
				filters.putAll(this.filters);
			}
			Predicate predicate = builder.and();
			Predicate predicatePadrao = criarPredicatePadrao(from, filters);
			if (predicatePadrao != null) {
				predicate = builder.and(predicate, predicatePadrao);
			}
			Predicate predicateEspecifico = criarPredicateEspecifico(from, filters);
			if (predicateEspecifico != null) {
				predicate = builder.and(predicate, predicateEspecifico);
			}
			if (CollectionUtils.isNotEmpty(getEntidadesSelecionadas())) {
				predicate = builder.and(predicate, builder.not(from.in(getEntidadesSelecionadas())));
			}
			Order order = criarOrderBy(sortField, sortOrder, from);
			setRowCount(dao.getTotalRegistros(from, predicate).intValue());
			return dao.listar(first, pageSize, query, from, predicate, order);
		} catch (OSMException e) {
			e.printStackTrace();
			return new ArrayList<TipoClasse>();
		}
	}

	@Override
	public List<TipoClasse> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
		return load(first, pageSize, null, filters);
	}

	/**
	 * Para criar os {@link Predicate} padrão, ou seja, os que serão criados a partir do valor dos <code>filterBy</code> das colunas do
	 * dataTable.
	 *
	 * @param from
	 * @param filters
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Predicate criarPredicatePadrao(Root<TipoClasse> from, Map<String, Object> filters) {
		Predicate predicate = null;
		try {
			if (filters != null && !filters.containsKey("ativo")) {
				predicate = builder.and(builder.equal(from.get("ativo"), StatusAtivo.ATIVO));
			}
		} catch (IllegalArgumentException e) {

		}

		if (filters != null && !filters.keySet().isEmpty()) {
			if (predicate == null) {
				predicate = builder.and();
			}
			for (String key : filters.keySet()) {
				if ("globalFilter".equals(key)) {
					continue;
				}
				Object valor = filters.get(key);
				String[] atributos = key.split("[.]");
				Path<Object> path = from.get(atributos[0]);
				if (path.getJavaType().isEnum()) {
					predicate = adicionarPredicateEnum(predicate, path, valor);
				} else {
					for (int i = 1; i < atributos.length; i++) {
						if (i + 1 == atributos.length) {
							path = path.get(atributos[i]);
						} else {
							path = path.get(atributos[i]);
						}
					}
					if (path.getJavaType().isEnum()) {
						predicate = adicionarPredicateEnum(predicate, path, valor);
					} else {
						predicate = builder.and(predicate,
								builder.like(path.as(String.class), "%" + String.valueOf(filters.get(key)) + "%"));
					}
				}
			}
		}
		return predicate;
	}

	/**
	 * Este método é responsável por adicionar ao predicate padrão um predicate de Enum.
	 *
	 * @param predicate
	 * @param path
	 * @param valor
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Predicate adicionarPredicateEnum(Predicate predicate, Path<Object> path, Object valor) {
		Class<? extends Enum> classeEnum = (Class<? extends Enum>) path.getJavaType();
		try {
			return builder.and(predicate, builder.equal(path, Enum.valueOf(classeEnum, valor.toString())));
		} catch (IllegalArgumentException e) {
			return predicate;
		}
	}

	protected Predicate criarPredicateEspecifico(Root<TipoClasse> from, Map<String, Object> filters) {
		return null;
	}

	/**
	 * @param sortField
	 * @param sortOrder
	 * @param from
	 * @return
	 */
	protected Order criarOrderBy(String sortField, SortOrder sortOrder, Root<TipoClasse> from) {
		String orderBy = sortField == null ? ordenacaoPadrao : sortField;
		String[] atributos = orderBy.split("[.]");
		Path<Object> path = from.get(atributos[0]);
		for (int i = 1; i < atributos.length; i++) {
			path = path.get(atributos[i]);
		}
		Order order = null;
		if (sortOrder == null || sortOrder.equals(SortOrder.DESCENDING)) {
			order = builder.desc(path);
		} else {
			order = builder.asc(path);
		}
		return order;
	}

	@Override
	public void setRowIndex(int rowIndex) {
		if (getPageSize() != 0 || rowIndex == -1) {
			super.setRowIndex(rowIndex);
		} else {
			super.setRowIndex(-1);
		}
	}

	@Override
	public Object getRowKey(TipoClasse entidade) {
		return String.valueOf(entidade.getId());
	}

	@Override
	public TipoClasse getRowData(String rowKey) {
		try {
			if (rowKey == null || "null".equals(rowKey)) {
				return null;
			}
			return dao.pesquisarPor(ConversorGenerico.convert(rowKey, tipoId));
		} catch (NumberFormatException e) {
			return null;
		} catch (OSMException e) {
			return null;
		}
	}

	public List<TipoClasse> getEntidadesSelecionadas() {
		return entidadesSelecionadas;
	}

	public void setEntidadesSelecionadas(List<TipoClasse> entidadesSelecionadas) {
		this.entidadesSelecionadas = entidadesSelecionadas;
	}

	public void adicionarAosSelecionados(TipoClasse tipoClasse) {
		if (!getEntidadesSelecionadas().contains(tipoClasse)) {
			getEntidadesSelecionadas().add(tipoClasse);
		}
	}

	public void removerDosSelecionados(TipoClasse tipoClasse) {
		if (getEntidadesSelecionadas().contains(tipoClasse)) {
			getEntidadesSelecionadas().remove(tipoClasse);
		}
	}

	public Map<String, Object> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, Object> filters) {
		this.filters = filters;
	}
}
