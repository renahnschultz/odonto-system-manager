/**
 *
 */
package br.com.osm.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.ManyToMany;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.validation.ConstraintViolationException;

import br.com.generico.AbstractAtivo;
import br.com.generico.Filtro;
import br.com.osm.entidades.Entidade;
import br.com.osm.enuns.StatusAtivo;
import br.com.osm.exception.OSMException;

public class GenericoDAO<PK extends Serializable, TipoClasse extends Entidade<?>> {

	protected EntityManager entityManager;

	protected Class<TipoClasse> tipo;

	protected final String pacoteEntidade;

	public GenericoDAO(Class<TipoClasse> tipoClasse, EntityManager entityManager) {
		this.tipo = tipoClasse;
		this.entityManager = entityManager;
		this.pacoteEntidade = this.tipo.getPackage().getName();
	}

	public CriteriaBuilder getCriteriaBuilder() {
		return entityManager.getCriteriaBuilder();
	}

	public TipoClasse pesquisarPor(PK id) throws OSMException {
		try {
			return entityManager.find(tipo, id);
		} catch (Exception e) {
			throw new OSMException(e, "erro.dao.generico.pesquisa.por.id", tipo.getSimpleName());
		}
	}

	/**
	 * Pesquisa um registro de acordo com os {@link Filtro} passados.
	 *
	 * @param filtros
	 *            Os {@link Filtro} que serão utilizados na busca.
	 * @param consultarCache
	 *            <ul>
	 *            <li><b>true </b> Irá utilizar o cache e o banco de dados na consulta.</li>
	 *            <li><b>false </b>NÃO vai utilizar o cache, a consulta será feita diretamento no banco de dados</li>
	 *            </ul>
	 * @param filtros
	 * @param consultarCache
	 * @param orderBy
	 * @return
	 * @throws OSMException
	 * @author Renahn - 2018-02-07
	 */
	public TipoClasse pesquisar(List<Filtro> filtros, boolean consultarCache, String... orderBy) throws OSMException {
		if (filtros == null || filtros.isEmpty()) {
			throw new IllegalArgumentException("É necessário informar pelo menos um filtro para a pesquisa.");
		}
		List<TipoClasse> retorno = listar(0, 1, filtros, consultarCache, orderBy);
		if (!retorno.isEmpty()) {
			if (retorno.size() > 1) {
				throw new NonUniqueResultException("A pesquisa retornou mais de um registro.");
			}
			return retorno.get(0);
		}
		return null;
	}

	public TipoClasse pesquisar(CriteriaQuery<TipoClasse> query, Root<TipoClasse> from, Predicate predicate, Order order)
			throws OSMException {
		List<TipoClasse> retorno = listar(0, 1, query, from, predicate, order);
		if (!retorno.isEmpty()) {
			if (retorno.size() > 1) {
				throw new NonUniqueResultException("A pesquisa retornou mais de um registro.");
			}
			return retorno.get(0);
		}
		return null;
	}

	public TipoClasse recuperar(TipoClasse tipoClasse) throws OSMException {
		try {
			tipoClasse = entityManager.merge(tipoClasse);
			entityManager.refresh(tipoClasse);
			return tipoClasse;
		} catch (Exception e) {
			throw new OSMException(e, "erro.dao.generico.recuperar", tipo.getSimpleName());
		}
	}

	public void salvar(TipoClasse tipoClasse) throws OSMException {
		try {
			if (tipoClasse.getId() == null) {
				entityManager.persist(tipoClasse);
			} else {
				entityManager.merge(tipoClasse);
			}
		} catch (ConstraintViolationException e) {
			throw e;
		} catch (Exception e) {
			throw new OSMException(e, "erro.dao.generico.salvar", tipo.getSimpleName());
		}
	}

	public void excluir(TipoClasse tipoClasse) throws OSMException {
		try {
			tipoClasse = entityManager.merge(tipoClasse);
			entityManager.remove(tipoClasse);
		} catch (Exception e) {
			throw new OSMException(e, "erro.dao.generico.apagar", tipo.getSimpleName());
		}
	}

	/**
	 * Pesquisa todos os registros de acordo com os {@link Filtro} passados e ordenação.
	 *
	 * @param primeiro
	 *            O primeiro registro a partir do qual serão retornados, -1 para retornar todos.
	 * @param maximoResultados
	 *            O total de registros que serão retornados na consulta, -1 para retornar todos.
	 * @param filtros
	 *            Os {@link Filtro} que serão utilizados na busca, <code>null</code> para desprezar os filtros.
	 * @param consultarCache
	 *            <ul>
	 *            <li><b>true </b> Irá utilizar o cache e o banco de dados na consulta.</li>
	 *            <li><b>false </b>NÃO vai utilizar o cache, a consulta será feita diretamento no banco de dados</li>
	 *            </ul>
	 *            -
	 * @param primeiro
	 * @param maximoResultados
	 * @param filtros
	 * @param consultarCache
	 * @param orderBy
	 * @return
	 * @throws OSMException
	 * @author Renahn - 2018-02-07
	 */
	public List<TipoClasse> listar(int primeiro, int maximoResultados, List<Filtro> filtros, boolean consultarCache, String... orderBy)
			throws OSMException {
		try {
			StringBuilder hql = new StringBuilder("SELECT o FROM ");
			hql.append(tipo.getSimpleName());
			hql.append(" AS o ");
			adicionarClausulas(hql, filtros);
			if (orderBy != null && orderBy.length > 0) {
				hql.append(" ORDER BY ");
				for (String order : orderBy) {
					hql.append(" o." + order + ",");
				}
				hql.delete(hql.length() - 1, hql.length());
			}
			TypedQuery<TipoClasse> query = entityManager.createQuery(hql.toString(), tipo);
			adicionarValoresClausulas(query, filtros);
			if (primeiro != -1 && maximoResultados != -1) {
				query.setFirstResult(primeiro)
						.setMaxResults(maximoResultados);
			}
			if (!consultarCache) {
				query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH)
						.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
			}
			return query.getResultList();
		} catch (Exception e) {
			throw new OSMException(e, "erro.dao.generico.listar", tipo.getSimpleName());
		}
	}

	public List<TipoClasse> listar(int primeiro, int maximoResultados, Predicate predicate, Order order) throws OSMException {
		try {
			CriteriaBuilder builder = getCriteriaBuilder();
			CriteriaQuery<TipoClasse> query = builder.createQuery(tipo);
			Root<TipoClasse> from = query.from(tipo);

			query = query.select(from)
					.where(predicate);
			if (order != null) {
				query.orderBy(order);
			}
			TypedQuery<TipoClasse> tq = entityManager.createQuery(query);
			if (primeiro != -1 && maximoResultados != -1) {
				tq.setFirstResult(primeiro)
						.setMaxResults(maximoResultados);
			}
			return tq.getResultList();
		} catch (Exception e) {
			throw new OSMException(e, "erro.dao.generico.listar", tipo.getSimpleName());
		}
	}

	public List<TipoClasse> listar(int primeiro, int maximoResultados, CriteriaQuery<TipoClasse> query, Selection<TipoClasse> from,
			Predicate predicate, Order order) throws OSMException {
		try {
			query = query.select(from)
					.where(predicate);
			if (order != null) {
				query.orderBy(order);
			}
			TypedQuery<TipoClasse> tq = entityManager.createQuery(query);
			if (primeiro != -1 && maximoResultados != -1) {
				tq.setFirstResult(primeiro)
						.setMaxResults(maximoResultados);
			}
			return tq.getResultList();
		} catch (Exception e) {
			throw new OSMException(e, "erro.dao.generico.listar", tipo.getSimpleName());
		}
	}

	public List<TipoClasse> listarTudo() throws OSMException {
		try {
			StringBuilder sql = new StringBuilder("SELECT p FROM ")
					.append(tipo.getSimpleName())
					.append(" AS p ");
			if (AbstractAtivo.class.isAssignableFrom(tipo)) {
				sql.append(" WHERE p.ativo = :ativo ");
			}
			TypedQuery<TipoClasse> query = entityManager.createQuery(sql.toString(), tipo);
			if (AbstractAtivo.class.isAssignableFrom(tipo)) {
				query.setParameter("ativo", StatusAtivo.ATIVO);
			}
			return query.getResultList();
		} catch (Exception e) {
			throw new OSMException(e, "erro.dao.generico.listar", tipo.getSimpleName());
		}
	}

	/**
	 * Conta o total de registros de acordo com os {@link Filtro} informados.
	 *
	 * @param filtros
	 *            Os {@link Filtro} que serão utilizados na contagem, <code>null</code> para desprezar os filtros.
	 * @return O total de registros.
	 * @throws MagicTradeException
	 */
	public int getTotalRegistros(List<Filtro> filtros) throws OSMException {
		try {
			StringBuilder jpql = new StringBuilder("select count(o) from ")
					.append(tipo.getSimpleName()).append(" as o ");
			adicionarClausulas(jpql, filtros);
			Query query = entityManager.createQuery(jpql.toString());
			adicionarValoresClausulas(query, filtros);
			return ((Long) query.getSingleResult()).intValue();
		} catch (Exception e) {
			throw new OSMException(e, "erro.dao.generico.total.registro", tipo.getSimpleName());
		}
	}

	public int getTotalRegistros(Predicate predicate) throws OSMException {
		try {
			CriteriaBuilder builder = getCriteriaBuilder();
			CriteriaQuery<Long> query = builder.createQuery(Long.class);
			Root<TipoClasse> from = query.from(tipo);
			TypedQuery<Long> tq = entityManager.createQuery(
					query.select(builder.count(from))
							.where(predicate));
			return tq.getSingleResult().intValue();
		} catch (Exception e) {
			throw new OSMException(e, "erro.dao.generico.total.registro", tipo.getSimpleName());
		}
	}

	public Long getTotalRegistros(Root<TipoClasse> from, Predicate predicate) throws OSMException {
		try {
			CriteriaBuilder builder = getCriteriaBuilder();
			CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
			TypedQuery<Long> tq = entityManager.createQuery(
					countCriteria.select(builder.count(from))
							.where(predicate));
			return tq.getSingleResult();
		} catch (Exception e) {
			throw new OSMException(e, "erro.dao.generico.total.registro", tipo.getSimpleName());
		}
	}

	private void adicionarClausulas(StringBuilder jpql, List<Filtro> filtros) {
		// adiciona as clausulas que serão utilizadas na consults
		if (filtros != null && !filtros.isEmpty()) {
			jpql.append(" WHERE ");
			int contador = 1;
			for (Filtro filtro : filtros) {
				if (Filtro.ABRE_PARENTESE.equals(filtro.getNome()) || Filtro.FECHA_PARENTESE.equals(filtro.getNome())) {
					jpql.append(filtro.getValor());
					continue;
				}
				if (filtro.getTipoClausula() != null) {
					jpql.append(filtro.getTipoClausula().getDescricao());
					continue;
				}
				if (filtro.getOperandoClausula().getDescricao().toLowerCase().contains("like")) {
					jpql.append("CAST (o.").append(filtro.getNome()).append(" char)")
							.append(filtro.getOperandoClausula().getDescricao()).append(" ?").append(contador++);
				} else {
					boolean adicionarNome = filtro.getNome() != null && !filtro.getNome().trim().isEmpty();
					if (adicionarNome) {
						try {
							Field atributo = tipo.getDeclaredField(filtro.getNome());
							if (Collection.class.isAssignableFrom(atributo.getType()) && atributo.isAnnotationPresent(ManyToMany.class)) {
								if (contador > 1) {
									throw new RuntimeException("Atributo Collection deve ser o primeiro filtro adicionado.");
								} else {
									jpql.replace(jpql.indexOf("WHERE"), jpql.length(), "");
									jpql.append(" INNER JOIN o.").append(filtro.getNome()).append(" o1 ")
											.append(" WHERE o1 ").append(filtro.getOperandoClausula().getDescricao()).append(" ?")
											.append(contador++);
								}
							} else {
								jpql.append(" o.").append(filtro.getNome()).append(filtro.getOperandoClausula().getDescricao())
										.append(" ?")
										.append(contador++);
							}
						} catch (NoSuchFieldException | SecurityException e) {
							jpql.append(" o.").append(filtro.getNome()).append(filtro.getOperandoClausula().getDescricao())
									.append(" ?")
									.append(contador++);
						}
					} else {
						jpql.append(" o ").append(filtro.getOperandoClausula().getDescricao()).append(" ?").append(contador++);
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void adicionarValoresClausulas(Query query, List<Filtro> filtros) {
		if (filtros != null && !filtros.isEmpty()) {
			int contador = 1;
			for (Filtro filtro : filtros) {
				//se for parentese ou alguma clausula não faz nada
				if (Filtro.ABRE_PARENTESE.equals(filtro.getNome()) || Filtro.FECHA_PARENTESE.equals(filtro.getNome())
						|| filtro.getTipoClausula() != null) {
					continue;
				}
				switch (filtro.getOperandoClausula()) {
				case LIKE_INICIO:
					query.setParameter(contador++, filtro.getValor() + "%");
					continue;
				case LIKE_FINAL:
					query.setParameter(contador++, "%" + filtro.getValor());
					continue;
				case LIKE_TODOS:
					query.setParameter(contador++, "%" + filtro.getValor() + "%");
					continue;
				default:
					if (isListaDeEntidade(filtro.getValor())) {
						query.setParameter(contador++, getIds((Collection<Entidade<Serializable>>) filtro.getValor()));
					} else {
						query.setParameter(contador++, filtro.getValor());
					}
					continue;
				}
			}
		}
	}

	public void detach(TipoClasse type) {
		entityManager.detach(type);
	}

	public void flush() {
		entityManager.flush();
	}

	/**
	 * Verifica se um determinado {@link Object} é uma lista de {@link Entidade}.
	 *
	 * @param object
	 *            O {@link Object} que será verificado.
	 * @return
	 *         <li>true - É uma lista de {@link Entidade}.
	 *         <li>false - NÃO é uma lista de {@link Entidade}.
	 */
	@SuppressWarnings("rawtypes")
	private Boolean isListaDeEntidade(Object object) {
		if (!(object instanceof Collection)) {
			return false;
		}
		Collection list = (Collection) object;
		if (list.isEmpty()) {
			return false;
		}
		if (list.iterator().next() instanceof Entidade) {
			return true;
		}
		return false;
	}

	/**
	 * Obtem a lista da Ids das {@link Entidade}.
	 *
	 * @param entidades
	 *            As {@link Entidade} de onde serão obtidos os ids.
	 * @return Os ids obtidos das {@link Entidade}.
	 */
	private List<Serializable> getIds(Collection<Entidade<Serializable>> entidades) {
		List<Serializable> ids = new ArrayList<Serializable>();
		for (Entidade<Serializable> entidade : entidades) {
			ids.add(entidade.getId());
		}
		return ids;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	public void limparCache() {
		entityManager.clear();
	}

	public void rollBackTransaction() {
		EntityTransaction transaction = entityManager.getTransaction();
		if (transaction.isActive()) {
			transaction.rollback();
		}
	}
	
	/**
	 * Altera o horário de uma data para a primeira hora minuto segundo do dia.
	 *
	 * @param date
	 *            A data que será convertida.
	 * @return A data midificada.
	 */
	public static Date dataPrimeiraHora(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	/**
	 * Altera o horário de uma data para a primeira hora minuto segundo do dia.
	 *
	 * @param date
	 *            A data que será convertida.
	 * @return A data midificada.
	 */
	public static Date dataUltimaHora(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

}
