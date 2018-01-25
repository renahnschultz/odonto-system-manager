package br.com.osm.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.osm.entidades.Autenticacao;
import br.com.osm.exception.OSMException;

public class AutenticacaoDAO extends GenericoDAO<Long, Autenticacao> {

	public AutenticacaoDAO() {
		super(Autenticacao.class, null);
	}

	@Inject
	public AutenticacaoDAO(EntityManager entityManager) {
		super(Autenticacao.class, entityManager);
	}

	public boolean possuiPermissao(String login, String senha, List<String> permissoes) throws OSMException {
		try {
			StringBuilder jpql = new StringBuilder("SELECT a.id FROM Autenticacao a ")
					.append(" INNER JOIN a.permissoes p ")
					.append(" WHERE p.permissao IN :permissoes ")
					.append(" AND c.login = :login ")
					.append(" AND c.senha = :senha ");
			entityManager.createQuery(jpql.toString(), Long.class)
					.setParameter("permissoes", permissoes)
					.setParameter("login", login)
					.setParameter("senha", senha)
					.setMaxResults(1)
					.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		} catch (Exception e) {
			throw new OSMException(e, "erro.dao.colaborador.possui.permissao");
		}
	}

	public Autenticacao pesquisarPorLogin(String login) throws OSMException {
		try {
			StringBuilder jpql = new StringBuilder("SELECT a FROM Autenticacao a ")
					.append(" WHERE a.login = :login ");
			return entityManager.createQuery(jpql.toString(), Autenticacao.class)
					.setParameter("login", login)
					.setMaxResults(1)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new OSMException(e, "erro.dao.autenticacao.nao.encontrado.com.login");
		}
	}

}
