/**
 *
 */
package br.com.osm.message;

import org.apache.deltaspike.core.api.message.MessageBundle;
import org.apache.deltaspike.core.api.message.MessageContextConfig;
import org.apache.deltaspike.core.api.message.MessageTemplate;

/**
 *
 * @author Renahn 06-02-2018
 *
 */
@MessageBundle
@MessageContextConfig(messageSource = "ValidationMessages", messageResolver = MessageResolver.class)
public interface Mensagens {

	@MessageTemplate("{acesso.negado}")
	String acessoNegado();

	@MessageTemplate("{erro.ao.realizar.login.usuario.senha.invalidos}")
	String erroAoRealizarLoginUsuarioSenhaInvalidos();

	@MessageTemplate("{usuario.nao.autenticado}")
	String usuarioNaoAutenticado();

}
