package br.com.osm.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.osm.entidades.Atendimento;
import br.com.osm.entidades.Debito;
import br.com.osm.entidades.Usuario;
import br.com.osm.enuns.SimNao;
import br.com.osm.enuns.SituacaoAgendamento;
import br.com.osm.exception.OSMException;

public class AtendimentoDAO extends GenericoDAO<Long, Atendimento> {

	public AtendimentoDAO() {
		super(Atendimento.class, null);
	}

	@Inject
	public AtendimentoDAO(EntityManager entityManager) {
		super(Atendimento.class, entityManager);
	}

	public Atendimento atendimentoEmExecucao(Usuario odontologo) throws OSMException {
		try {
			StringBuilder sql = new StringBuilder("SELECT p FROM ")
					.append(tipo.getSimpleName())
					.append(" AS p ")
					.append(" WHERE p.agendamento.odontologo = :odontologo")
					.append(" AND p.agendamento.situacao = :situacao")
					.append(" ORDER BY p.dataInicio DESC");
			TypedQuery<Atendimento> query = entityManager.createQuery(sql.toString(), Atendimento.class);
			query.setParameter("odontologo", odontologo);
			query.setParameter("situacao", SituacaoAgendamento.EXECUCAO);
			query.setMaxResults(1);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new OSMException(e, "erro.dao.generico.listar", tipo.getSimpleName());
		}
	}

}
