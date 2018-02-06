/**
 *
 */
package br.com.osm.viewconfig;

import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.jsf.api.config.view.View;
import org.apache.deltaspike.jsf.api.config.view.View.NavigationMode;

/**
 *
 * @author Renahn 06-02-2018
 *
 */
public interface Paginas extends ViewConfig {

	//Use interface para pastas e class para paginas.
	@View(navigation = NavigationMode.REDIRECT)
	class aaaa implements ViewConfig {
	}
}
