package br.com.osm.util;

import java.util.Comparator;

import br.com.osm.entidades.DenteOdontograma;

public class OrdenaDentesOdontograma implements Comparator<DenteOdontograma> {

	@Override
	public int compare(DenteOdontograma o1, DenteOdontograma o2) {
		if(o1.getDente().getOrdem() > o2.getDente().getOrdem()) {
			return 1;
		} else if(o1.getDente().getOrdem() < o2.getDente().getOrdem()) {
			return -1;
		}
		return 0;
	}

}
