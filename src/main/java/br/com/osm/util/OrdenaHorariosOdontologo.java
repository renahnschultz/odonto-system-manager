package br.com.osm.util;

import java.util.Comparator;

import br.com.osm.entidades.HorarioOdontologo;

public class OrdenaHorariosOdontologo implements Comparator<HorarioOdontologo> {

	@Override
	public int compare(HorarioOdontologo o1, HorarioOdontologo o2) {
		if (o1.getDiaSemana().ordinal() == o2.getDiaSemana().ordinal()
				&& o1.getPeriodo() > o2.getPeriodo()) {
			return 1;
		} else if (o1.getDiaSemana().ordinal() == o2.getDiaSemana().ordinal()
				&& o1.getPeriodo() < o2.getPeriodo()) {
			return -1;
		} else if (o1.getDiaSemana().ordinal() > o2.getDiaSemana().ordinal()) {
			return 1;
		} else if (o1.getDiaSemana().ordinal() < o2.getDiaSemana().ordinal()) {
			return -1;
		}
		return 0;
	}

}
