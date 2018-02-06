/**
 *
 */
package br.com.osm.security;

import java.util.Set;

import org.apache.deltaspike.security.api.authorization.AccessDecisionVoter;
import org.apache.deltaspike.security.api.authorization.SecurityViolation;

/**
 *
 * @author geraldo 23 de jun de 2017
 *
 */
public class NaoAutenticadoException extends SecurityException {
	private static final long serialVersionUID = -4066763895951237969L;

	private Set<SecurityViolation> violations;

	/**
	 * Constructor for creating the exception for the given violations and error-view
	 * 
	 * @param violations
	 *            current violations
	 */
	public NaoAutenticadoException(Set<SecurityViolation> violations) {
		this.violations = violations;
	}

	/**
	 * All {@link SecurityViolation} which were found by a {@link AccessDecisionVoter}
	 *
	 * @return all security-violations
	 */
	public Set<SecurityViolation> getViolations() {
		return violations;
	}
}
