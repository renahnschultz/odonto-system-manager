package br.com.osm.beans;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.osm.annotations.LazyModel;
import br.com.osm.dao.MaterialDAO;
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
public class MaterialBean implements Serializable {

	@Inject
	transient private MaterialDAO materialDAO;
	private Material material = new Material();
	@Inject
	@LazyModel
	private AbstractLazyModel<Long, Material> materialLazy;

	public MaterialBean() {
	}

	public void salvar() {
		new MaterialWebService(materialDAO).salvar(material);
		cancelar();
	}

	public void cancelar() {
		material = new Material();
	}

	public void excluir() {
		new MaterialWebService(materialDAO).excluir(material.getId());
		material = new Material();
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public AbstractLazyModel<Long, Material> getMaterialLazy() {
		return materialLazy;
	}

	public void setMaterialLazy(AbstractLazyModel<Long, Material> materialLazy) {
		this.materialLazy = materialLazy;
	}

}
