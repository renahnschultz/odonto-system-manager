package br.com.osm.dao;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.osm.entidades.Agendamento;
import br.com.osm.entidades.Usuario;
import br.com.osm.enuns.SituacaoAgendamento;
import br.com.osm.exception.OSMException;

public class AgendamentoDAO extends GenericoDAO<Long, Agendamento> {

	public AgendamentoDAO() {
		super(Agendamento.class, null);
	}

	@Inject
	public AgendamentoDAO(EntityManager entityManager) {
		super(Agendamento.class, entityManager);
	}
	
	public List<Agendamento> buscarAgendamentosPendentes() throws OSMException {
		try {
			StringBuilder sql = new StringBuilder("SELECT p FROM ")
					.append(tipo.getSimpleName())
					.append(" AS p ")
					.append(" WHERE p.situacao = :situacao");
			TypedQuery<Agendamento> query = entityManager.createQuery(sql.toString(), Agendamento.class);
			query.setParameter("situacao", SituacaoAgendamento.PENDENTE);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new OSMException(e, "erro.dao.generico.listar", tipo.getSimpleName());
		}
	}

	public List<Agendamento> buscarAgendamentosAprovados(Usuario odontologo, Date diaInicio, Date diaFim) throws OSMException {
		try {
			StringBuilder sql = new StringBuilder("SELECT p FROM ")
					.append(tipo.getSimpleName())
					.append(" AS p ")
					.append(" WHERE p.situacao != :situacao ")
					.append(" AND p.situacao != :situacao2 ")
					.append(" AND p.odontologo = :odontologo ")
					.append(" AND p.dataHora BETWEEN :inicioDia AND :fimDia ");
			TypedQuery<Agendamento> query = entityManager.createQuery(sql.toString(), Agendamento.class);
			query.setParameter("situacao", SituacaoAgendamento.PENDENTE);
			query.setParameter("situacao2", SituacaoAgendamento.REPROVADO);
			query.setParameter("odontologo", odontologo);
			query.setParameter("inicioDia", dataPrimeiraHora(diaInicio));
			query.setParameter("fimDia", dataUltimaHora(diaFim));
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new OSMException(e, "erro.dao.generico.listar", tipo.getSimpleName());
		}
	}
	
	public List<Agendamento> buscarAgendamentosAprovadosEPendentes(Usuario odontologo, Date dia) throws OSMException {
		try {
			StringBuilder sql = new StringBuilder("SELECT p FROM ")
					.append(tipo.getSimpleName())
					.append(" AS p ")
					.append(" WHERE p.situacao != :situacao ")
					.append(" AND p.odontologo = :odontologo ")
					.append(" AND p.dataHora BETWEEN :inicioDia AND :fimDia ");
			TypedQuery<Agendamento> query = entityManager.createQuery(sql.toString(), Agendamento.class);
			query.setParameter("situacao", SituacaoAgendamento.REPROVADO);
			query.setParameter("odontologo", odontologo);
			query.setParameter("inicioDia", dataPrimeiraHora(dia));
			query.setParameter("fimDia", dataUltimaHora(dia));
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new OSMException(e, "erro.dao.generico.listar", tipo.getSimpleName());
		}
	}
	
}
