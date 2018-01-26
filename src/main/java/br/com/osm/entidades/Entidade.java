package br.com.osm.entidades;

import java.io.Serializable;

public interface Entidade<TipoId extends Serializable> extends Serializable {

	TipoId getId();
}
