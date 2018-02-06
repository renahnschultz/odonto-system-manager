/**
 *
 */
package br.com.osm.rest;

import java.io.Serializable;

import br.com.osm.entidades.Entidade;

/**
 *
 * @author Renahn 06-02-2018
 *
 * @param <PK>
 * @param <TipoClasse>
 */
public abstract class OSMServiceBase<PK extends Serializable, TipoClasse extends Entidade<?>> implements Serializable {

//	/**
//	 *
//	 */
//	private static final long serialVersionUID = 1L;
//	/**
//	 *
//	 */
//	protected GenericoDAO<PK, TipoClasse> dao;
//	@Inject
//	protected UriInfo uriInfo;
//	@Inject
//	protected TelefoneDAO telefoneDAO;
//	@Inject
//	protected EmailDAO emailDAO;
//	@Inject
//	protected ApplicationProducer applicationProducer;
//	@Inject
//	protected Mensagens mensagens;
//
//	@Inject
//	protected Locale locale;
//	protected ConstraintValidatorContext constraintValidatorContext;
//	private CriteriaBuilder builder;
//	private CriteriaQuery<TipoClasse> query;
//	private Root<TipoClasse> from;
//	protected MagicTradeLogger logger = MagicTradeLogger.getLogger(getClass().getName());
//	@Inject
//	protected Gson gson;
//
//	@Inject
//	@Auditoria(INSERIR)
//	private Event<Entidade<?>> entidadeCriada;
//
//	@Inject
//	@Auditoria(ATUALIZAR)
//	private Event<Entidade<?>> entidadeEditada;
//
//	@Inject
//	@Auditoria(EXCLUIR)
//	private Event<Entidade<?>> entidadeExcluida;
//
//	protected OSMServiceBase(GenericoDAO<PK, TipoClasse> dao) {
//		this.dao = dao;
//	}
//
//	/**
//	 * Método exclusivo para os casos onde existem validações específicas que não podem ser realizadas utilizando o Bean Validation.
//	 *
//	 * @param tipoClasse
//	 *            O objeto que será validado.
//	 *
//	 * @throws ConstraintViolationException
//	 *             Lança exceção quando algum erro de validação for encontrado.
//	 */
//	protected void validacaoSalvar(TipoClasse tipoClasse) throws ValidacaoException {
//
//	}
//
//	/**
//	 * Método exclusivo para os casos onde existem validações específicas que não podem ser realizadas utilizando o Bean Validation.
//	 *
//	 * @param tipoClasse
//	 *            O objeto que será validado
//	 *
//	 * @throws ConstraintViolationException
//	 *             Lança exceção quando algum erro de validação for encontrado.
//	 */
//	protected void validacaoExcluir(TipoClasse tipoClasse) throws ValidacaoException {
//
//	}
//
//	/**
//	 * Método exclusivo para os casos onde existem validações específicas que não podem ser realizadas utilizando o Bean Validation.
//	 *
//	 * @param tipoClasse
//	 *            O objeto que será validado
//	 *
//	 * @throws ConstraintViolationException
//	 *             Lança exceção quando algum erro de validação for encontrado.
//	 */
//	protected void validacaoReativar(TipoClasse tipoClasse) throws ValidacaoException {
//
//	}
//
//	/**
//	 * Salva um objeto.
//	 *
//	 * @param tipoClasse
//	 * @return
//	 *         <p>
//	 *         {@link Status#OK} com o Location indicando o caminho do objeto salvo.
//	 *         </p>
//	 *         <p>
//	 *         {@link Status#BAD_REQUEST} se ocorrer algum erro de validação ao salvar o objeto.
//	 *         </p>
//	 *         <p>
//	 *         {@link Status#INTERNAL_SERVER_ERROR} se algum erro inesperado ocorrer.
//	 *         </p>
//	 */
//	public Response salvar(TipoClasse tipoClasse) {
//		try {
//			boolean novo = tipoClasse.getId() == null;
//			validarCampoUnico(tipoClasse);
//			validacaoSalvar(tipoClasse);
//			dao.salvar(tipoClasse);
//			dao.flush();//para fazer as validações do JPA
//			JSONObject jsonObject = new JSONObject();
//			if (novo) {
//				entidadeCriada.fire(tipoClasse);
//				jsonObject.put(CriaJsonRetorno.MENSAGEM, mensagens.cadastradoComSucesso());
//			} else {
//				entidadeEditada.fire(tipoClasse);
//				jsonObject.put(CriaJsonRetorno.MENSAGEM, mensagens.editadoComSucesso());
//			}
//			UriBuilder builder = null;
//			if (uriInfo != null) {
//				try {
//					builder = uriInfo.getRequestUriBuilder()
//							.path(String.valueOf(tipoClasse.getId()));
//				} catch (IllegalProductException e) {
//					//ignorado, se der exception é pq não conseguiu in
//				}
//			}
//			if (logger.isLoggable(Level.FINE)) {
//				logger.fine("salvo com sucesso", gson.toJson(tipoClasse));
//			}
//			ResponseBuilder responseBuilder = Response.status(novo ? Response.Status.CREATED : Response.Status.OK)
//					.entity(jsonObject.toString());
//			if (builder != null) {
//				responseBuilder.contentLocation(builder.build());
//			}
//			return responseBuilder.build();
//		} catch (ValidacaoException e) {
//			dao.rollBackTransaction();
//			logger.severe("erro ao salvar", e, gson.toJson(tipoClasse));
//			return Response.status(BAD_REQUEST)
//					.type(MediaType.APPLICATION_JSON + ";charset=UTF-8")
//					.entity(new CriaJsonRetorno().criarRetornoValidacao(e).toString())
//					.build();
//		} catch (ConstraintViolationException e) {
//			dao.rollBackTransaction();
//			logger.severe("erro ao salvar", e, gson.toJson(tipoClasse));
//			return Response.status(BAD_REQUEST)
//					.type(MediaType.APPLICATION_JSON + ";charset=UTF-8")
//					.entity(new CriaJsonRetorno().criarRetornoValidacao(e).toString())
//					.build();
//		} catch (Exception e) {
//			dao.rollBackTransaction();
//			e = getCause(e);
//			String mensagem = new CriaJsonRetorno().criarRetornoIndefinido(e).toString();
//			if (e instanceof ConstraintViolationException) {
//				mensagem = new CriaJsonRetorno().criarRetornoValidacao((ConstraintViolationException) e).toString();
//			}
//			logger.severe("erro ao salvar", e, gson.toJson(tipoClasse));
//			return Response.status(INTERNAL_SERVER_ERROR)
//					.type(MediaType.APPLICATION_JSON + ";charset=UTF-8")
//					.entity(mensagem)
//					.build();
//		}
//	}
//
//	private Exception getCause(Exception exception) {
//		if (exception.getCause() == null) {
//			return exception;
//		}
//		for (int i = 0; i < 3; i++) {
//			if (exception.getCause() instanceof ConstraintViolationException) {
//				return (ConstraintViolationException) exception.getCause();
//			} else {
//				exception = (Exception) exception.getCause();
//			}
//		}
//		return exception;
//	}
//
//	public Response excluir(PK id) {
//		try {
//			TipoClasse tipoClasse = dao.pesquisarPor(id);
//			validacaoExcluir(tipoClasse);
//			if (tipoClasse instanceof AbstractAtivo) {
//				AbstractAtivo abstractAtivo = (AbstractAtivo) tipoClasse;
//				abstractAtivo.setAtivo(StatusAtivo.EXCLUIDO);
//				dao.salvar(tipoClasse);
//				dao.flush();
//				entidadeExcluida.fire(tipoClasse);
//			} else {
//				dao.excluir(tipoClasse);
//				dao.flush();
//				entidadeExcluida.fire(tipoClasse);
//			}
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put(CriaJsonRetorno.MENSAGEM, mensagens.excluidoComSucesso());
//			if (logger.isLoggable(Level.FINE)) {
//				logger.fine("excluido com sucesso", gson.toJson(tipoClasse));
//			}
//			return Response.ok()
//					.type(MediaType.APPLICATION_JSON + ";charset=UTF-8")
//					.entity(jsonObject.toString()).build();
//		} catch (Exception e) {
//			dao.rollBackTransaction();
//			logger.severe("erro ao excluir por id", e, String.valueOf(id));
//			return Response.status(INTERNAL_SERVER_ERROR)
//					.type(MediaType.APPLICATION_JSON + ";charset=UTF-8")
//					.entity(new CriaJsonRetorno().criarRetornoIndefinido(e).toString())
//					.build();
//		}
//	}
//
//	public Response reativar(PK id) {
//		try {
//			TipoClasse tipoClasse = dao.pesquisarPor(id);
//			validacaoReativar(tipoClasse);
//			AbstractAtivo abstractAtivo = (AbstractAtivo) tipoClasse;
//			abstractAtivo.setAtivo(StatusAtivo.ATIVO);
//			dao.salvar(tipoClasse);
//			dao.flush();
//			entidadeEditada.fire(tipoClasse);
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put(CriaJsonRetorno.MENSAGEM, mensagens.reativadoComSucesso());
//			if (logger.isLoggable(Level.FINE)) {
//				logger.fine("reativado com sucesso", gson.toJson(tipoClasse));
//			}
//			return Response.ok()
//					.type(MediaType.APPLICATION_JSON + ";charset=UTF-8")
//					.entity(jsonObject.toString()).build();
//		} catch (Exception e) {
//			dao.rollBackTransaction();
//			logger.severe("erro ao reativar por id", e, String.valueOf(id));
//			return Response.status(INTERNAL_SERVER_ERROR)
//					.type(MediaType.APPLICATION_JSON + ";charset=UTF-8")
//					.entity(new CriaJsonRetorno().criarRetornoIndefinido(e).toString())
//					.build();
//		}
//	}
//
//	//TODO não esta sendo feito o tratamento de erro na chamada do método
//	public Response pesquisarPorId(PK id) {
//		try {
//			TipoClasse retorno = dao.pesquisarPor(id);
//			return Response.ok(retorno).build();
//		} catch (MagicTradeException e) {
//			return Response.status(INTERNAL_SERVER_ERROR)
//					.type(MediaType.APPLICATION_JSON + ";charset=UTF-8")
//					.entity(new CriaJsonRetorno().criarRetornoIndefinido(e).toString())
//					.build();
//		}
//	}
//
//	public Response listarTudo() throws MagicTradeException {
//		List<TipoClasse> retorno = dao.listarTudo(null, false);
//		GenericEntity<List<TipoClasse>> lista = new GenericEntity<List<TipoClasse>>(retorno) {
//		};
//		return Response.ok(lista)
//				.build();
//	}
//
//	/**
//	 * Retorna os {@link AtributosUnicos} definidos na anotação {@link EntidadeUnica}
//	 *
//	 * @param tipoClasse
//	 *            A classe de onde os dados serão extraidos.
//	 * @return Os {@link AtributosUnicos} encontrados na classe.
//	 */
//	private List<AtributosUnicos> retornaAtributosUnicos(TipoClasse tipoClasse) {
//		List<AtributosUnicos> atributosUnicos = new ArrayList<AtributosUnicos>();
//		Class<?> classe = tipoClasse.getClass();
//		if (classe.isAnnotationPresent(EntidadeUnica.class)) {
//			AtributosUnicos[] atributos = classe.getDeclaredAnnotation(EntidadeUnica.class).atributos();
//			atributosUnicos.addAll(Arrays.asList(atributos));
//		}
//		return atributosUnicos;
//	}
//
//	private List<Field> retornaEntidadeCamposUnicos(TipoClasse tipoClasse, AtributosUnicos atributosUnicos) {
//		List<Field> fields = new ArrayList<Field>();
//		Class<?> classe = tipoClasse.getClass();
//		for (String atributo : atributosUnicos.atributos()) {
//			try {
//				Field field = classe.getDeclaredField(atributo);
//				fields.add(field);
//			} catch (NoSuchFieldException | SecurityException e) {
//				throw new RuntimeException(
//						String.format("A anotação @EntidadeUnica na classe %s não possui um atributo com o nome %s.",
//								tipoClasse.getClass().getSimpleName(),
//								atributo));
//			}
//		}
//		return fields;
//	}
//
//	@SuppressWarnings("unchecked")
//	private TipoClasse consultaEntidadeUnicaBanco(TipoClasse tipoClasse, AtributosUnicos atributosUnicos)
//			throws MagicTradeException, IllegalArgumentException, IllegalAccessException {
//		Class<TipoClasse> classeTipoClasse = (Class<TipoClasse>) tipoClasse.getClass();
//		this.builder = dao.getCriteriaBuilder();
//		this.query = builder.createQuery(classeTipoClasse);
//		this.from = query.from(classeTipoClasse);
//		Predicate predicate = null;
//		predicate = builder.and();
//		List<Field> fields = retornaEntidadeCamposUnicos(tipoClasse, atributosUnicos);
//		if (tipoClasse.getId() == null) {
//			for (Field field : fields) {
//				field.setAccessible(true);
//				Object valorField = field.get(tipoClasse);
//				if (valorField == null
//						&& (!field.isAnnotationPresent(NotBlank.class)
//								|| !field.isAnnotationPresent(NotEmpty.class)
//								|| !field.isAnnotationPresent(NotNull.class))) {
//					continue;
//				}
//				predicate = builder.and(predicate,
//						builder.equal(
//								from.get(field.getName()), valorField));
//			}
//		} else {
//			for (Field field : fields) {
//				field.setAccessible(true);
//				Object valorField = field.get(tipoClasse);
//				if (valorField == null
//						&& (!field.isAnnotationPresent(NotBlank.class)
//								|| !field.isAnnotationPresent(NotEmpty.class)
//								|| !field.isAnnotationPresent(NotNull.class))) {
//					continue;
//				}
//				predicate = builder.and(predicate,
//						builder.and(
//								builder.equal(
//										from.get(field.getName()), valorField),
//								builder.notEqual(
//										from.get("id"), tipoClasse.getId())));
//			}
//		}
//		return dao.pesquisar(query, from, predicate, null);
//	}
//
//	/**
//	 * Realiza as validações necessárias para que os campos unicos das entidade não se repitam em outras.
//	 * Realiza as validações necessárias para que as entidades não se repitam conforme regra de atributos atribuida na anotação
//	 * {@link EntidadeUnica}
//	 *
//	 * @param tipoClasse
//	 * @throws IllegalArgumentException
//	 * @throws IllegalAccessException
//	 * @throws MagicTradeException
//	 * @throws NoSuchFieldException
//	 * @throws SecurityException
//	 */
//	private void validarCampoUnico(TipoClasse tipoClasse)
//			throws IllegalArgumentException, IllegalAccessException, MagicTradeException, NoSuchFieldException, SecurityException {
//		List<Field> fields = retornaCamposUnicos(tipoClasse);
//		TipoClasse registroBanco = consultaCampoUnicoBanco(tipoClasse, fields);
//		validacaoCampoUnico(tipoClasse, fields, registroBanco);
//		List<AtributosUnicos> atributosUnicos = retornaAtributosUnicos(tipoClasse);
//		if (CollectionUtils.isEmpty(atributosUnicos)) {
//			return;
//		}
//		for (AtributosUnicos unicos : atributosUnicos) {
//			registroBanco = consultaEntidadeUnicaBanco(tipoClasse, unicos);
//			validacaoEntidadeUnica(tipoClasse, unicos, registroBanco);
//		}
//	}
//
//	/**
//	 * Realiza as validações necessárias para que as entidades não se repitam conforme regra de atributos atribuida na anotação
//	 *
//	 * @param tipoClasse
//	 * @param fields
//	 * @param registroBanco
//	 * @throws IllegalAccessException
//	 */
//	private void validacaoEntidadeUnica(TipoClasse tipoClasse, AtributosUnicos atributosUnicos, TipoClasse registroBanco)
//			throws IllegalAccessException {
//		if (registroBanco != null) {
//			List<Field> fields = retornaEntidadeCamposUnicos(tipoClasse, atributosUnicos);
//			for (Field field : fields) {
//				field.setAccessible(true);
//				boolean igual = field.get(tipoClasse).equals(field.get(registroBanco));
//				if (String.class.isAssignableFrom(field.getType())) {
//					//classe utilizada para fazer comparações ignorando acentuação e caixa alta e baixa de string
//					//retorna igual para comparações do tipo:
//					//joão == joao
//					//João == joão
//					//JOÃO == João
//					Collator colator = Collator.getInstance(locale);
//					colator.setStrength(Collator.PRIMARY);
//					igual = colator.compare(String.valueOf(field.get(tipoClasse)), String.valueOf(field.get(registroBanco))) == 0;
//				}
//				if (igual) {
//					String chave = tipoClasse.getClass().getDeclaredAnnotation(EntidadeUnica.class).chave();
//					String nomeDaEntidade = applicationProducer.getLabel(chave);
//					String mensagem = mensagens.entidadeJaExistente(nomeDaEntidade);
//					if (registroBanco instanceof AbstractAtivo) {
//						AbstractAtivo abstractAtivo = (AbstractAtivo) registroBanco;
//						if (abstractAtivo.getAtivo().equals(StatusAtivo.INATIVO)) {
//							mensagem = mensagens.entidadeJaExistenteInativa(nomeDaEntidade);
//						} else if (abstractAtivo.getAtivo().equals(StatusAtivo.EXCLUIDO)) {
//							mensagem = mensagens.entidadeJaExistenteExcluida(nomeDaEntidade);
//						}
//					}
//					ErroValidacao erroValidacao = new ErroValidacao(new CodigoErro().getCodigoErro("{ja.existe}"),
//							tipoClasse.getClass().getName(), mensagem);
//					throw new ValidacaoException(Arrays.asList(erroValidacao));
//				}
//			}
//
//		}
//	}
//
//	/**
//	 * Realiza as validações necessárias para que os campos unicos das entidade não se repitam em outras.
//	 *
//	 * @param tipoClasse
//	 *            A entidade que esta sendo cadastrada ou modificada.
//	 * @param fields
//	 *            Os atributos marcados com a anotação {@link CampoUnico} na entidade.
//	 * @param registroBanco
//	 *            O registro do banco de dados que será comparado, se null, indica que não existe um registro no banco de dados
//	 *            com os mesmos valores da entidade que está sendo cadastrada ou modificada.
//	 * @throws IllegalAccessException
//	 */
//	private void validacaoCampoUnico(TipoClasse tipoClasse, List<Field> fields, TipoClasse registroBanco) throws IllegalAccessException {
//		if (registroBanco != null) {
//			for (Field field : fields) {
//				field.setAccessible(true);
//				boolean igual = field.get(tipoClasse).equals(field.get(registroBanco));
//				if (String.class.isAssignableFrom(field.getType())) {
//					Collator colator = Collator.getInstance(locale);
//					colator.setStrength(Collator.PRIMARY);
//					if (StringUtils.isBlank(String.valueOf(field.get(tipoClasse)))) {
//						igual = false;
//					} else {
//						igual = colator.compare(String.valueOf(field.get(tipoClasse)), String.valueOf(field.get(registroBanco))) == 0;
//					}
//				}
//				if (igual) {
//					String mensagem = mensagens.mensagemComDoisParametro(
//							applicationProducer.getLabel(field.getName()), mensagens.jaExiste());
//					if (registroBanco instanceof AbstractAtivo) {
//						AbstractAtivo abstractAtivo = (AbstractAtivo) registroBanco;
//						if (abstractAtivo.getAtivo().equals(StatusAtivo.INATIVO)) {
//							mensagem = mensagens.mensagemComDoisParametro(
//									applicationProducer.getLabel(field.getName()), mensagens.jaExisteInativo());
//						} else if (abstractAtivo.getAtivo().equals(StatusAtivo.EXCLUIDO)) {
//							mensagem = mensagens.mensagemComDoisParametro(
//									applicationProducer.getLabel(field.getName()), mensagens.jaExisteExcluido());
//						}
//					}
//					ErroValidacao erroValidacao = new ErroValidacao(new CodigoErro().getCodigoErro("{ja.existe}"),
//							field.getName(), mensagem);
//					throw new ValidacaoException(Arrays.asList(erroValidacao));
//				}
//			}
//
//		}
//	}
//
//	/**
//	 * <p>
//	 * Pesquisa no banco de dados por uma determinada entidade usando como clausulas WHERE todos os atributos recebidos no parâmetro.
//	 * </p>
//	 * <p>
//	 * A consulta será feita usando o operando OR, onde irá buscar a entidade que contenha qualquer dos atributos já cadastrados no banco de
//	 * dados.
//	 * </p>
//	 *
//	 * @param tipoClasse
//	 *            A entidade que será consultada, contém os valores que serão extraídos para a busca.
//	 * @param fields
//	 *            Os atributos que serão utilizados para extrair os valores de busca da entidade.
//	 * @return A entidade encontrada ou null se nada for encontrado.
//	 * @throws MagicTradeException
//	 * @throws IllegalArgumentException
//	 * @throws IllegalAccessException
//	 */
//	@SuppressWarnings("unchecked")
//	private TipoClasse consultaCampoUnicoBanco(TipoClasse tipoClasse, List<Field> fields)
//			throws MagicTradeException, IllegalArgumentException, IllegalAccessException {
//		Class<TipoClasse> classeTipoClasse = (Class<TipoClasse>) tipoClasse.getClass();
//		this.builder = dao.getCriteriaBuilder();
//		this.query = builder.createQuery(classeTipoClasse);
//		this.from = query.from(classeTipoClasse);
//		Predicate predicate = null;
//		if (tipoClasse.getId() == null) {
//			predicate = builder.or();
//			for (Field field : fields) {
//				field.setAccessible(true);
//				Object valorField = field.get(tipoClasse);
//				if (valorField == null
//						&& (!field.isAnnotationPresent(NotBlank.class)
//								|| !field.isAnnotationPresent(NotEmpty.class)
//								|| !field.isAnnotationPresent(NotNull.class))) {
//					continue;
//				}
//				predicate = builder.or(predicate,
//						builder.equal(
//								from.get(field.getName()), valorField));
//			}
//		} else {
//			predicate = builder.or();
//			for (Field field : fields) {
//				field.setAccessible(true);
//				Object valorField = field.get(tipoClasse);
//				if (valorField == null
//						&& (!field.isAnnotationPresent(NotBlank.class)
//								|| !field.isAnnotationPresent(NotEmpty.class)
//								|| !field.isAnnotationPresent(NotNull.class))) {
//					continue;
//				}
//				predicate = builder.or(predicate,
//						builder.and(
//								builder.equal(
//										from.get(field.getName()), valorField),
//								builder.notEqual(
//										from.get("id"), tipoClasse.getId())));
//			}
//		}
//		return dao.pesquisar(query, from, predicate, null);
//	}
//
//	/**
//	 * Retorna os atributos que tenham a anotação {@link CampoUnico}
//	 *
//	 * @param tipoClasse
//	 *            A classe de onde serão extraidos os atributos.
//	 * @return Os atributos encontrados.
//	 */
//	private List<Field> retornaCamposUnicos(TipoClasse tipoClasse) {
//		List<Field> fields = new ArrayList<Field>();
//		Field[] declaredFields = tipoClasse.getClass().getDeclaredFields();
//		for (Field field : declaredFields) {
//			if (field.isAnnotationPresent(CampoUnico.class)) {
//				fields.add(field);
//			}
//		}
//		return fields;
//	}
//
//	/**
//	 * Método padrão para consultas de autocomplete no banco de dados, esse método deverá ser utilizado por todo componente de tela que
//	 * tenha tais característica de apresentar sugestão de resultados para o usuário.
//	 *
//	 * @param valorDaBusca
//	 *            O valor de busca que será utilizada para consultar o banco de dados.
//	 * @return Um {@link Response} com {@link Status#OK} que irá conter uma array de JSON dos objetos encontrados.
//	 * @throws MagicTradeException
//	 */
//	protected Response autoComplete(String valorDaBusca) throws MagicTradeException {
//		List<TipoClasse> retorno = dao.autoComplete(valorDaBusca);
//		GenericEntity<List<TipoClasse>> lista = new GenericEntity<List<TipoClasse>>(retorno) {
//		};
//		return Response.ok(lista).build();
//	}
//
//	/**
//	 * Monta retorno padrão para campo não encontrado. O {@link Response} de retorno irá conter o código {@link Status#BAD_REQUEST} e um
//	 * JSON contendo detalhes sobre o erro.
//	 *
//	 * @param campo
//	 *            O campo que foi pesquisado e não foi encontrado.
//	 * @return O {@link Response} de erro.
//	 */
//	protected Response criaRetornoNaoEncontrado(String campo) {
//		ValidacaoException validacaoException = new ValidacaoException(
//				Arrays.asList(new ErroValidacao("5", campo, mensagens.naoEncontrado())));
//		return Response.status(Status.BAD_REQUEST)
//				.type(MediaType.APPLICATION_JSON + ";charset=UTF-8")
//				.entity(new CriaJsonRetorno().criarRetornoValidacao(validacaoException).toString())
//				.build();
//	}
//
//	protected List<Email> recarregarEmailsJaCadastrados(List<Email> emails) {
//		try {
//			if (CollectionUtils.isNotEmpty(emails)) {
//				for (int x = 0; x < emails.size(); x++) {
//					if (emails.get(x).getId() != null) {
//						Email emailBanco = emailDAO.pesquisarPor(emails.get(x).getId());
//						if (!emailBanco.equals(emails.get(x))) {
//							emailBanco.setEmail(emails.get(x).getEmail());
//						}
//						emails.set(x, emailBanco);
//					}
//				}
//			}
//		} catch (Exception e) {
//			return null;
//		}
//		return emails;
//	}
//
//	protected List<Telefone> recarregarTelefonesJaCadastrados(List<Telefone> telefones) {
//		try {
//			if (CollectionUtils.isNotEmpty(telefones)) {
//				for (int x = 0; x < telefones.size(); x++) {
//					if (telefones.get(x).getId() != null) {
//						Telefone telefoneBanco = telefoneDAO.pesquisarPor(telefones.get(x).getId());
//						if (!telefoneBanco.equals(telefones.get(x))) {
//							telefoneBanco.setCodigoArea(telefones.get(x).getCodigoArea());
//							telefoneBanco.setNumero(telefones.get(x).getNumero());
//							telefoneBanco.setRamal(telefones.get(x).getRamal());
//							telefoneBanco.setTipoTelefone(telefones.get(x).getTipoTelefone());
//						}
//						telefones.set(x, telefoneBanco);
//					}
//				}
//			}
//		} catch (Exception e) {
//			return null;
//		}
//		return telefones;
//	}
//
//	/**
//	 * Monta retorno padrão para erros inesperados. O {@link Response} de retorno irá conter o código {@link Status#INTERNAL_SERVER_ERROR}
//	 * e um JSON contendo detalhes sobre o erro.
//	 *
//	 * @param idMensagem
//	 *            A chave da mensagem de erro contida no arquivo ValidationMessages.properties
//	 * @return O {@link Response} de erro.
//	 */
//	protected Response criaRetornoErro(String idMensagem) {
//		RuntimeException exception = new RuntimeException(applicationProducer.getLabel(idMensagem));
//		return Response.status(Status.INTERNAL_SERVER_ERROR)
//				.type(MediaType.APPLICATION_JSON + ";charset=UTF-8")
//				.entity(new CriaJsonRetorno().criarRetornoIndefinido(exception).toString())
//				.build();
//	}

}
