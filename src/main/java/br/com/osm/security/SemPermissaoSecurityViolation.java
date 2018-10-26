/**
 *
 */
package br.com.osm.security;

import org.apache.deltaspike.security.api.authorization.SecurityViolation;

/**
 *
 * @author Renahn 06-02-2018
 *
 */
public class SemPermissaoSecurityViolation implements SecurityViolation {

	private static final long serialVersionUID = -5017812464381395966L;

	private final String reason;

	public SemPermissaoSecurityViolation(String reason) {
		this.reason = reason;
	}

	@Override
	public String getReason() {
		return reason;
	}

	@Override
	public String toString() {
		return "SemPermissaoSecurityViolation [reason=" + reason + "]";
	}

}
