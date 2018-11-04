package br.com.osm.beans;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.osm.annotations.LazyModel;
import br.com.osm.dao.MaterialDAO;
import br.com.osm.entidades.Auditoria;
import br.com.osm.entidades.Material;
import br.com.osm.model.AbstractLazyModel;
import br.com.osm.rest.MaterialWebService;

/**
 * Classe responsável pelo controle da tela de cadastro de Paciente.
 *
 * @author Lucas 28-07-2018
 *
 */
@Named
@ViewScoped
public class LogUsuarioBean implements Serializable {

	@Inject
	@LazyModel
	private AbstractLazyModel<Long, Auditoria> auditoriaLazy;

	public LogUsuarioBean() {
	}

	public AbstractLazyModel<Long, Auditoria> getAuditoriaLazy() {
		return auditoriaLazy;
	}

	public void setAuditoriaLazy(AbstractLazyModel<Long, Auditoria> auditoriaLazy) {
		this.auditoriaLazy = auditoriaLazy;
	}

}
