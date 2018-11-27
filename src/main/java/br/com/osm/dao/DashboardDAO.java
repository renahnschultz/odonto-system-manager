package br.com.osm.dao;

import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.osm.entidades.Comentario;
import br.com.osm.entidades.Usuario;
import br.com.osm.enuns.SituacaoAgendamento;
import br.com.osm.exception.OSMException;

public class DashboardDAO extends GenericoDAO<Long, Comentario> {

	public DashboardDAO() {
		super(Comentario.class, null);
	}

	@Inject
	public DashboardDAO(EntityManager entityManager) {
		super(Comentario.class, entityManager);
	}

	public Object quantidadeAgendamentos(Usuario odontologo, Date inicio, Date fim) throws OSMException {
		try {
			StringBuilder sql = new StringBuilder("SELECT COUNT(agend.id) ")
					.append("FROM agendamento agend ")
					.append(" WHERE agend.data_hora BETWEEN ? AND ? ")
					.append(" AND agend.situacao != ? ")
					.append(" AND agend.situacao != ? ");
			if (odontologo != null) {
				sql.append(" AND agend.id_odontologo = ? ");
				sql.append(" GROUP BY agend.id_odontologo ");
			}
			Query query = entityManager.createNativeQuery(sql.toString());
			query.setParameter(1, inicio);
			query.setParameter(2, fim);
			query.setParameter(3, SituacaoAgendamento.REPROVADO);
			query.setParameter(4, SituacaoAgendamento.PENDENTE);
			if (odontologo != null) {
				query.setParameter(5, odontologo.getId());
			}
			return query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		} catch (Exception e) {
			throw new OSMException(e, "erro.dao.generico.listar", tipo.getSimpleName());
		}
	}

	public Object quantidadeAtendimentos(Usuario odontologo, Date inicio, Date fim) throws OSMException {
		try {
			StringBuilder sql = new StringBuilder("SELECT COUNT(agend.id) ")
					.append("FROM agendamento agend ")
					.append(" INNER JOIN atendimento atend ON agend.id = atend.id_agendamento ")
					.append(" WHERE agend.data_hora BETWEEN ? AND ? ");
			if (odontologo != null) {
				sql.append(" AND agend.id_odontologo = ? ");
				sql.append(" GROUP BY agend.id_odontologo ");
			}
			Query query = entityManager.createNativeQuery(sql.toString());
			query.setParameter(1, inicio);
			query.setParameter(2, fim);
			if (odontologo != null) {
				query.setParameter(3, odontologo.getId());
			}
			return query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		} catch (Exception e) {
			throw new OSMException(e, "erro.dao.generico.listar", tipo.getSimpleName());
		}
	}

	public Object debitoFaturado(Usuario odontologo, Date inicio, Date fim) throws OSMException {
		try {
			StringBuilder sql = new StringBuilder("SELECT SUM(pag.valor) ")
					.append("FROM debito deb ")
					.append(" INNER JOIN pagamento pag ON pag.id_debito = deb.id ")
					.append(" INNER JOIN agendamento agend ON agend.id = deb.id_agendamento ")
					.append(" WHERE agend.data_hora BETWEEN ? AND ?  ");
			if (odontologo != null) {
				sql.append(" AND agend.id_odontologo = ? ");
			}
			Query query = entityManager.createNativeQuery(sql.toString());
			query.setParameter(1, inicio);
			query.setParameter(2, fim);
			if (odontologo != null) {
				query.setParameter(3, odontologo.getId());
			}
			return query.getSingleResult();
		} catch (NoResultException e) {
			return 0.0;
		} catch (Exception e) {
			throw new OSMException(e, "erro.dao.generico.listar", tipo.getSimpleName());
		}
	}

	public Object debitoAberto(Usuario odontologo, Date inicio, Date fim) throws OSMException {
		try {
			StringBuilder sql = new StringBuilder("SELECT SUM(deb.valor) - pago.pago AS em_aberto ")
					.append("FROM debito deb ")
					.append(" INNER JOIN agendamento agend ON agend.id = deb.id_agendamento ")
					.append(" CROSS JOIN ( ")
					.append(" SELECT SUM(pag.valor) pago ")
					.append(" FROM debito deb ")
					.append(" INNER JOIN pagamento pag ON pag.id_debito = deb.id ")
					.append(" INNER JOIN agendamento agend ON agend.id = deb.id_agendamento ")
					.append(" WHERE agend.data_hora BETWEEN ?1 AND ?2  ");
			if (odontologo != null) {
				sql.append(" AND agend.id_odontologo = ?3 ");
			}
			sql.append(" ) as pago ")
					.append(" WHERE agend.data_hora BETWEEN ?4 AND ?5  ");
			if (odontologo != null) {
				sql.append(" AND agend.id_odontologo = ?6 ");
			}
			Query query = entityManager.createNativeQuery(sql.toString());
			query.setParameter(1, inicio);
			query.setParameter(2, fim);
			if (odontologo != null) {
				query.setParameter(3, odontologo.getId());
			}
			query.setParameter(4, inicio);
			query.setParameter(5, fim);
			if (odontologo != null) {
				query.setParameter(6, odontologo.getId());
			}
			return query.getSingleResult();
		} catch (NoResultException e) {
			return 0.0;
		} catch (Exception e) {
			throw new OSMException(e, "erro.dao.generico.listar", tipo.getSimpleName());
		}
	}

}
