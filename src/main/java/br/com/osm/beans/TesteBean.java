package br.com.osm.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.osm.dao.MedicoDAO;
import br.com.osm.entidades.Medico;

@Named
@ViewScoped
public class TesteBean implements Serializable {

	/**
	 * @author Renahn 24-01-2018
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	transient private MedicoDAO medicoDAO;
	private String teste;

	public TesteBean() {
		System.out.println("Criou merda");
	}

	public void listarMedicos() {
		List<Medico> listMedicos;
		try {
			System.out.println(teste);
			listMedicos = medicoDAO.listMedicos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getTeste() {
		return teste;
	}

	public void setTeste(String teste) {
		this.teste = teste;
	}

}
