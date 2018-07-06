package br.com.osm.template;

import java.io.Serializable;

/**
 * Holds control sidebar initial configuration
 *
 * @author rmpestano
 */
public class ControlSidebarConfig implements Serializable {

	private Boolean showOnMobile;
	private Boolean fixedLayout;

	public ControlSidebarConfig(boolean showOnMobile, boolean fixedLayout) {
		this.showOnMobile = showOnMobile;
		this.fixedLayout = fixedLayout;
	}

	public Boolean getShowOnMobile() {
		return showOnMobile;
	}

	public Boolean getFixedLayout() {
		return fixedLayout;
	}

}