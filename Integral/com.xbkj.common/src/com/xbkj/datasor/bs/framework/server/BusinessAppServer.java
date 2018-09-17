package com.xbkj.datasor.bs.framework.server;

//import com.grc.datasor.bs.framework.cluster.MemberChangeAcceptor;
//import com.grc.datasor.bs.framework.cluster.MemberChangeListener;
//import com.grc.datasor.bs.framework.common.RegistService;
//import com.grc.datasor.bs.framework.core.ModuleDeployerAccess;
//import com.grc.datasor.bs.framework.core.common.IDataSourceQuery;
//import com.grc.datasor.bs.framework.core.service.ClusterEchoImpl;
//import com.grc.datasor.bs.framework.core.service.ClusterTestAction;
//import com.grc.datasor.bs.framework.core.service.IClusterEcho;
//import com.grc.datasor.bs.framework.core.service.RequestAttributeManager;
//import com.grc.datasor.bs.framework.core.service.ThreadLocalAttributeManager;
//import com.grc.datasor.bs.framework.jmx.JmxBus;
//import com.grc.datasor.bs.framework.loading.BasicClassLoaderRepository;
//import com.grc.datasor.bs.framework.loading.BusinessServerClassLoader;
//import com.grc.datasor.bs.framework.loading.ClassLoaderRepository;
//import com.grc.datasor.bs.framework.naming.ListableContext;
//import com.grc.datasor.bs.framework.server.deploy.ModulesMonitor;
//import com.grc.datasor.bs.framework.server.deploy.NewModuleDeployer;
//import com.grc.datasor.bs.framework.server.util.ConfigurationUtil;
//import com.grc.datasor.bs.framework.server.util.MiscUtil;
//import com.grc.basic.vo.jcom.io.IOUtil;

//import org.granite.convert.ConverterManager;
//import org.granite.lang.util.Clazzs;
//import org.granite.lang.util.Objects;

/**
 * 
 * Created by UFSoft. User: �ι���
 * 
 * Date: 2007-11-20 Time: ����06:37:42
 * 
 */
public class BusinessAppServer //extends AbstractContainer implements Server 
{

//	public static final String SERVER_MODE = "nc.server.mode";
//
//	private static final String SERVER_SDEPLOY_XML = "/nc/bs/framework/server/server.xml";
//
//	private static final String EXTERNAL_LOCATION_PROPERTY = "nc.external.location";
//
//	public static final String EXCLUDED_MODULES_PROPERTY = "nc.exclude.modules";
//
//	private static final String DEF_SERVER_LOCATION = ".";
//
//	private static final String DEF_MODULES_LOCATION = "modules";
//
//	private static final String DEF_EXTERNAL_LOCATION = "external";
//
//	private Properties environment;
//
//	private Map<URL, Container> modulesMap;
//
//	private Map<String, Container> namedModulesMap;
//
//	private ClassLoader serverClassLoader;
//
//	private ClassLoaderRepository repository;
//
//	private ModulesMonitor monitor;
//
//	private BusinessAppServerRemoteContext remoteCtx;
//
//	private BusinessAppServerForEJBContext forEJBCtx;
//
//	private URL serverBaseUrl;
//
//	private static BusinessAppServer server;
//
//	private Set<String> excludedModules;
//
//	private JndiContextComponent jndiContext;
//
//	private boolean normalMode;
//
//	private JmxBus jmxService;
//
//	private Deployer deployer;
//
//	private ServiceCache serviceCache;
//
//	private Mode mode;
//
//	private Configuration configuration;
//
//	private File serverBase;
//
//	private File moduleBase;
//
//	private AbstractContext serverCtx;
//
//	private ReentrantReadWriteLock mcLock;
//
//	public static BusinessAppServer getInstance() {
//		if (server == null) {
//			synchronized (BusinessAppServer.class) {
//				if (server == null) {
//					BusinessAppServer server1 = new BusinessAppServer();
//					server = server1;
//				}
//			}
//		}
//		return server;
//	}
//
//	private BusinessAppServer() {
//		super("BizAppServer", new PublicMetaRepo());
//		state = State.NOT_INIT;
//		environment = RuntimeEnv.getInstance().getArbitraryProperties();
//		modulesMap = new ConcurrentHashMap<URL, Container>();
//		normalMode = "normal".equals(System.getProperty("classloader.mode",
//				"normal"));
//		serviceCache = new ServiceCacheImpl();
//		mode = Mode.DEVELOPMENT;
//		mcLock = new ReentrantReadWriteLock();
//		namedModulesMap = new ConcurrentHashMap<String, Container>();
//	}
//
//	/**
//	 * init the Business server
//	 * 
//	 * @param initInfo
//	 */
//	public synchronized void init(Properties initInfo) {
//		if (state != State.NOT_INIT && state != State.STOPPED) {
//			throw new InitException(String.format(initErrMsg, Thread
//					.currentThread().getName()));
//		}
//
//		initEnv(initInfo);
//
//		if (state == State.NOT_INIT) {
//			state = State.INITING;
//
//			jndiContext = new JndiContextComponent();
//
//			try {
//				jndiContext.start();
//			} catch (Exception e) {
//				getLogger().error("init jndi error", e);
//			}
//
//			jmxService = new JmxBus();
//			try {
//				jmxService.start();
//			} catch (Exception e) {
//				getLogger().error("jmx service start error", e);
//			}
//
//			getConfiguration();
//
//			state = State.INITED;
//			Runtime.getRuntime().addShutdownHook(new ShutdownThread(this));
//		}
//	}
//
//	protected void beforeInternalStart() {
//		prepareStart();
//		super.beforeInternalStart();
//		startClassLoader();
//
//		newBuilder().name(Context.REMOTE_META_SERVICE).to(
//				new BusinessServerRemoteMetaContext()).asPublic(true).asRemote(
//				true).bindin();
//
//		newBuilder().name("ThreadLocalAttributeManager").to(
//				new ThreadLocalAttributeManager()).asPublic(true).bindin();
//
//		newBuilder().name(RequestAttributeManager.class.getName()).to(
//				new RequestAttributeManager()).asPublic(true).bindin();
//
//		newBuilder().name("RemoteProcessComponetFactory").export(
//				"nc.bs.framework.component.RemoteProcessComponetFactory")
//				.toClass("nc.bs.framework.server.RemoteCallPostProcessFactory")
//				.asPublic(true).bindin();
//
//		final Object obj = environment.get("ServletContext");
//
//		if (obj != null) {
//			try {
//				newBuilder().name("ServletContext").to(obj).asPublic(true)
//						.bindin();
//			} catch (ComponentException e) {
//				getLogger().error("error when regist ServletConetxt", e);
//			}
//		}
//		try {
//			newBuilder().name("IMetaVersionService").export(
//					nc.bs.framework.core.common.IMetaVersionService.class).to(
//					nc.bs.framework.server.MetaVersionServiceImpl.class)
//					.asPublic(true).asRemote(true).at(ComponentMeta.CLUSTER_SP)
//					.pri(0).bindin();
//		} catch (ComponentException e) {
//			getLogger().error("error when regist IMetaVersionService", e);
//		}
//
//		try {
//			newBuilder().name("ClusterEcho").export(IClusterEcho.class).alias(
//					IClusterEcho.class.getName()).to(ClusterEchoImpl.class)
//					.asPublic(true).asRemote(true).at(ComponentMeta.CLUSTER_SP)
//					.pri(0).bindin();
//			newBuilder().name("ClusterTest").pri(0).to(new ClusterTestAction())
//					.asPublic(true).bindin();
//		} catch (ComponentException e) {
//			getLogger().error("error when regist ClusterEcho", e);
//		}
//
//		try {
//			newBuilder().name(Context.REGIST_SERVICE).export(
//					RegistService.class).to(new RegistServiceImpl()).asPublic(
//					true).asRemote(true).scope(Scope.SINGLETON).bindin();
//
//		} catch (ComponentException e) {
//			getLogger().error("error when regist RegistService", e);
//		}
//
//		try {
//			newBuilder().name(IDataSourceQuery.class.getName()).export(
//					IDataSourceQuery.class).to(new DataSourceQuery()).asPublic(
//					true).asRemote(true).scope(Scope.SINGLETON).bindin();
//
//		} catch (ComponentException e) {
//			getLogger().error("error when regist IDataSourceQuery", e);
//		}
//
//		// deployer = new ModuleDeployer(this);
//		deployer = new NewModuleDeployer(this);
//
//		newBuilder().name(ModuleDeployerAccess.class.getName()).export(
//				ModuleDeployerAccess.class).to(
//				new ModuleDeployerAccessService()).asPublic(true)
//				.asRemote(true).bindin();
//
//		try {
//			URL svrDeployUrl = getClass().getResource(SERVER_SDEPLOY_XML);
//
//			if (svrDeployUrl != null) {
//				InputStream input = null;
//				try {
//					input = svrDeployUrl.openStream();
//					deployer.deploy(SERVER_SDEPLOY_XML, this, input);
//				} finally {
//					IOUtil.close(input);
//				}
//			} else {
//				getLogger().warn("Server internal deploy file is not found");
//			}
//
//		} catch (Exception e) {
//			getLogger().warn("Server internal deploy file config error", e);
//		}
//
//	}
//
//	protected void prepareStart() {
//
//	}
//
//	/**
//	 * start the server
//	 */
//	protected void afterInternalStart() {
//		getLogger().debug("deploy and start modules");
//		File modulesDir = new File(getModulesBase());
//		monitor = new ModulesMonitor(this, deployer, modulesDir,
//				getExcludedModules());
//		monitor.start();
//
//		try {
//			Configuration cfg = getConfiguration();
//			if (cfg.getClusterConf() == null) {
//				getLogger()
//						.debug("single server not supported dynamic cluster");
//			} else {
//				MemberChangeAcceptor mca = (MemberChangeAcceptor) getContext()
//						.lookup(MemberChangeAcceptor.class.getName());
//				mca.addMemberChangeListener(new MemberChangeListener() {
//
//					public void memberAdded(ServerConf member) {
//						Configuration cfg = getConfiguration();
//						cfg.getClusterConf().addPeer(member);
//					}
//
//					public void memberRemoved(String server) {
//						Configuration cfg = getConfiguration();
//						cfg.getClusterConf().removePeer(server);
//					}
//
//				});
//			}
//		} catch (Throwable thr) {
//			getLogger().error("dymamic cluster may not supported", thr);
//		}
//
//		try {
//			LiveServerAgent lsa = (LiveServerAgent) getContext().lookup(
//					LiveServerAgent.class.getName());
//			lsa.notifyServerStart();
//		} catch (Exception exp) {
//			getLogger().error("Not support LiveServer notify", exp);
//		}
//
//		jmxService.exportServerToJmx(this);
//	}
//
//	protected void beforeInternalStop() {
//		try {
//			LiveServerAgent lsa = (LiveServerAgent) getContext().lookup(
//					LiveServerAgent.class.getName());
//			lsa.notifyServerStop();
//		} catch (Exception exp) {
//			getLogger().error("Not support LiveServer notify", exp);
//		}
//		if (monitor != null) {
//			monitor.stop();
//		}
//		Container[] cs = getContainers();
//		Arrays.sort(cs, new ContainerComparator(false));
//		for (int i = 0; i < cs.length; i++) {
//			try {
//				getLogger().debug(
//						String.format(Messages.bfOpModule, "stop", cs[i]
//								.getName()));
//				cs[i].stop();
//				getLogger().debug(
//						String.format(Messages.afOpModule, "stop", cs[i]
//								.getName()));
//
//			} catch (Exception e) {
//				getLogger().error(
//						String.format(Messages.stopErr, cs[i].getName()), e);
//			}
//		}
//
//	}
//
//	protected void afterInternalStop() {
//		super.afterInternalStop();
//		modulesMap.clear();
//		namedModulesMap.clear();
//		serviceCache.clear();
//		try {
//			jmxService.stop();
//		} catch (Exception e) {
//			getLogger().error("jmx service stop error", e);
//		}
//		stopClassLoader();
//		readyStop();
//
//	}
//
//	protected void readyStop() {
//		ServerConfiguration.clear();
//		server = null;
//	}
//
//	public Container[] getContainers() {
//		mcLock.readLock().lock();
//		try {
//			Container[] cs = new Container[modulesMap.size()];
//			modulesMap.values().toArray(cs);
//			return cs;
//		} finally {
//			mcLock.readLock().unlock();
//		}
//	}
//
//	/**
//	 * undeploySingle module and removes it
//	 * 
//	 * @param url
//	 */
//	public Container removeContainer(URL url) {
//		mcLock.writeLock().lock();
//		try {
//			Container c = modulesMap.remove(url);
//			if (c != null)
//				namedModulesMap.remove(c.getName());
//			return c;
//		} finally {
//			mcLock.writeLock().unlock();
//		}
//	}
//
//	public void addContainer(Container container) {
//		mcLock.writeLock().lock();
//		try {
//			modulesMap.put(container.getUrl(), container);
//			namedModulesMap.put(container.getName(), container);
//		} finally {
//			mcLock.writeLock().unlock();
//		}
//	}
//
//	public Container getContainer(URL url) {
//		mcLock.readLock().lock();
//		try {
//			return modulesMap.get(url);
//		} finally {
//			mcLock.readLock().unlock();
//		}
//	}
//
//	public Container getContainer(String name) {
//		mcLock.readLock().lock();
//		try {
//			return namedModulesMap.get(name);
//		} finally {
//			mcLock.readLock().unlock();
//		}
//	}
//
//	public Properties getEnvironment() {
//		if (state == State.NOT_INIT || state == State.INITING)
//			throw new FrameworkRuntimeException("Business server is not inited");
//		return environment;
//	}
//
//	public String getServerBase() {
//		return environment.getProperty(RuntimeEnv.SERVER_LOCATION_PROPERTY);
//	}
//
//	public String getModulesBase() {
//		return environment.getProperty(RuntimeEnv.MODULES_LOCATION_PROPERTY);
//	}
//
//	public String getExternalLocation() {
//		return environment.getProperty(EXTERNAL_LOCATION_PROPERTY);
//	}
//
//	public Set getExcludedModules() {
//		return excludedModules;
//	}
//
//	private void initEnv(Properties initInfo) {
//		environment.clear();
//		if (initInfo != null) {
//			environment.putAll(initInfo);
//		}
//		initProperty(RuntimeEnv.SERVER_LOCATION_PROPERTY, DEF_SERVER_LOCATION);
//		initProperty(RuntimeEnv.MODULES_LOCATION_PROPERTY,
//				getProperty(RuntimeEnv.SERVER_LOCATION_PROPERTY) + "/"
//						+ DEF_MODULES_LOCATION);
//
//		initProperty(EXTERNAL_LOCATION_PROPERTY,
//				getProperty(RuntimeEnv.SERVER_LOCATION_PROPERTY) + "/"
//						+ DEF_EXTERNAL_LOCATION);
//
//		initProperty(EXCLUDED_MODULES_PROPERTY,
//				getProperty(EXCLUDED_MODULES_PROPERTY));
//
//		String strExcluded = getProperty(EXCLUDED_MODULES_PROPERTY);
//
//		if (strExcluded != null) {
//			excludedModules = new HashSet<String>();
//			StringTokenizer tokenizer = new StringTokenizer(strExcluded, ";,:");
//			while (tokenizer.hasMoreTokens()) {
//				excludedModules.add(tokenizer.nextToken());
//			}
//
//		}
//
//		serverBase = new File(getServerBase());
//		verifyDirectory(serverBase, "NC_HOME");
//		moduleBase = new File(getModulesBase());
//		verifyDirectory(moduleBase, "MODULE_BASE");
//		try {
//			serverBaseUrl = serverBase.toURL();
//		} catch (MalformedURLException e) {
//			throw new InitException(environment, "Init business server error",
//					e);
//		}
//
//		String defMode = "development";
//		if (RuntimeEnv.isRunningInWebSphere()
//				|| RuntimeEnv.isRunningInWeblogic()) {
//			defMode = "product";
//		}
//		initProperty(SERVER_MODE, defMode);
//		String m = getProperty(SERVER_MODE);
//		mode = Mode.toMode(m);
//
//	}
//
//	private void verifyDirectory(File fdir, String msg) {
//		if (!fdir.exists()) {
//			throw new InitException(String.format(notExistMsg, msg, fdir));
//		}
//		if (!fdir.isDirectory()) {
//			throw new InitException(String.format(notDirMsg, msg, fdir));
//		}
//	}
//
//	private void initProperty(String name, String defaultValue) {
//		if (environment.getProperty(name) == null) {
//			String value = System.getProperty(name);
//
//			if (value == null) {
//				value = defaultValue;
//			}
//
//			if (value != null) {
//				environment.put(name, value);
//			}
//		}
//	}
//
//	public ClassLoaderRepository getClassLoaderReposiotry() {
//		if (state == State.NOT_INIT || state == State.INITING)
//			throw new FrameworkRuntimeException("Business server is not inited");
//		return repository;
//	}
//
//	public String getProperty(String name) {
//		return environment.getProperty(name);
//	}
//
//	public String getName() {
//		return "BizAppServer";
//	}
//
//	public ClassLoader getClassLoader() {
//		return serverClassLoader;
//	}
//
//	public ListableContext getRemoteContext() {
//		if (remoteCtx == null) {
//			remoteCtx = new BusinessAppServerRemoteContext();
//		}
//		{
//			return remoteCtx;
//		}
//
//	}
//
//	public Context getContextForEJB() {
//		if (forEJBCtx == null)
//			forEJBCtx = new BusinessAppServerForEJBContext();
//		return forEJBCtx;
//	}
//
//	public URL getUrl() {
//		return serverBaseUrl;
//	}
//
//	/**
//	 * ��ָ��ģ����ض����ഴ��������ȱʡ�Ĺ��캯��
//	 * 
//	 * @param moduleName
//	 * @param className
//	 * @return
//	 */
//	public Object newBizObject(String moduleName, String className) {
//		return newBizObject(moduleName, className, null);
//	}
//
//	/**
//	 * ��ָ��ģ����ض����ഴ������������Ϊnull����ȱʡ�Ĺ��캯��
//	 * 
//	 * @param moduleName
//	 * @param className
//	 * @param args
//	 * @return
//	 */
//	public Object newBizObject(String moduleName, String className,
//			Object[] args) {
//		Object ret = null;
//		if (StringUtil.hasText(moduleName)) {
//			Container c = getContainer(moduleName);
//			if (c == null) {
//				throw new InstException(String.format(newObjErr, className,
//						moduleName + " is not found"));
//			} else {
//				ret = newBizObject(c, className, args);
//			}
//		} else {
//			ret = newBizObject(className, args);
//		}
//
//		return ret;
//
//	}
//
//	public Object newBizObject(String className, Object[] args) {
//		Container[] cs = getContainers();
//		Object ret = null;
//		for (int i = 0; i < cs.length && ret == null; i++) {
//			try {
//				ret = newBizObject(cs[i], className, args);
//			} catch (Throwable thr) {
//				ret = null;
//			}
//
//		}
//		if (ret == null) {
//			throw new InstException(String.format(newObjErr, className,
//					"All modules: " + StringUtil.toString(cs)));
//		}
//		return ret;
//	}
//
//	private Object newBizObject(Container module, String className,
//			Object args[]) {
//		Throwable cause = null;
//		if (module != null) {
//			if (module instanceof Module) {
//				Module deployed = (Module) module;
//				Class clazz;
//				try {
//					clazz = deployed.getClassLoader().loadClass(className);
//				} catch (ClassNotFoundException e) {
//					throw new InstException(String.format(newObjErr, className,
//							deployed.getName()), e);
//				}
//
//				Class[] types = Clazzs.getClassArray(args);
//				Constructor[] ctors = Clazzs.findMostMatchCtors(clazz, types,
//						true);
//				try {
//					ConverterManager cm = getExtension(ConverterManager.class);
//					return new Objects(cm).newObject(ctors, args).result;
//				} catch (Exception e) {
//					if (e instanceof InvocationTargetException) {
//						throw new InstException(String.format(
//								Messages.instErrWithCtor, clazz.getName(),
//								StringUtil.toString(types)), e.getCause());
//					} else {
//						throw new InstException(String.format(
//								Messages.instErrWithCtor, clazz.getName(),
//								StringUtil.toString(types)), e);
//					}
//				}
//
//			}
//		}
//
//		throw new InstException(String.format(newObjErr, className,
//				module == null ? "<null>" : module.getName()), cause);
//
//	}
//
//	private void startClassLoader() {
//		ClassLoader parentClassLoader = selectParentClassLoader();
//
//		repository = new BasicClassLoaderRepository(normalMode);
//
//		serverClassLoader = new BusinessServerClassLoader(new URL[] {},
//				parentClassLoader, repository);
//		if (!normalMode) {
//
//			URL[] externalURLs = null;
//			URL[] serverURLs = null;
//
//			externalURLs = MiscUtil.computeStdClasspath(new File(
//					getExternalLocation()));
//			serverURLs = MiscUtil.computeStdClasspath(new File(serverBaseUrl
//					.getFile()));
//			((BusinessServerClassLoader) serverClassLoader).addURLs(serverURLs);
//
//			((BusinessServerClassLoader) serverClassLoader)
//					.addURLs(externalURLs);
//		}
//
//		RuntimeEnv.getInstance().setBizClassLoader(serverClassLoader);
//	}
//
//	private ClassLoader selectParentClassLoader() {
//		ClassLoader ctxClassLoader = Thread.currentThread()
//				.getContextClassLoader();
//		ClassLoader thisClassLoader = getClass().getClassLoader();
//		ClassLoader ctxParent = ctxClassLoader;
//		ClassLoader parentClassLoader = null;
//		boolean bUseCtxClassLoader = false;
//		do {
//			if (ctxParent == thisClassLoader) {
//				bUseCtxClassLoader = true;
//				break;
//			}
//			ctxParent = ctxParent.getParent();
//
//		} while (ctxParent != null && ctxParent.getParent() != null);
//
//		if (bUseCtxClassLoader) {
//			getLogger()
//					.debug(
//							"Use current thread context classloader as the BusinessAppServer's parent classloader");
//			parentClassLoader = ctxClassLoader;
//		} else {
//			getLogger()
//					.warn(
//							"Use the framework's bootstrap classloader as the BusinessAppServer's parent classloader");
//			parentClassLoader = thisClassLoader;
//		}
//
//		parentClassLoader = bUseCtxClassLoader ? ctxClassLoader
//				: thisClassLoader;
//		return parentClassLoader;
//
//	}
//
//	private void stopClassLoader() {
//		serverClassLoader = null;
//		repository = null;
//		RuntimeEnv.getInstance().setBizClassLoader(
//				RuntimeEnv.class.getClassLoader());
//	}
//
//	private class BusinessAppServerRemoteContext extends AbstractContext {
//		protected ComponentMeta findMeta(String name) throws ComponentException {
//			ComponentMeta meta = publicRepo.getComponentMeta(name);
//			if (meta == null) {
//				throw new ComponentNotFoundException(getName(), name,
//						" can't find remote component meta");
//			}
//
//			if (meta.isRemote()) {
//				return meta;
//			} else {
//				throw new ComponentException(meta.getContainer().getName(),
//						name, " not remote component");
//			}
//		}
//
//		protected ComponentMeta[] listComponentMetas(Class clazz) {
//			ComponentMeta[] metas = publicRepo.getComponentMetas();
//			List<ComponentMeta> list = new ArrayList<ComponentMeta>();
//			for (ComponentMeta meta : metas) {
//				if (meta.isRemote()) {
//					list.add(meta);
//				}
//			}
//
//			for (int i = 0; i < metas.length; i++) {
//				if (clazz == null
//						|| ClassUtil.isAssignableFrom(clazz, metas[i]
//								.getInterfaces()))
//					list.add(metas[i]);
//			}
//
//			ComponentMeta[] retMetas = new ComponentMeta[list.size()];
//			list.toArray(retMetas);
//			return retMetas;
//		}
//
//		protected JndiContext getJNDIContext() {
//			return jndiContext;
//		}
//
//		protected void checkComponent(String name, Object obj)
//				throws ComponentException {
//			if (obj instanceof Resolvable)
//				throw new InstException(name,
//						"the object now is in constructing process:"
//								+ getName());
//			if (obj == null)
//				throw new ComponentNotFoundException(getName(), name);
//		}
//
//		@Override
//		protected Container getContainer() {
//			return BusinessAppServer.this;
//		}
//	}
//
//	private class BusinessServerRemoteMetaContext implements Context {
//		public Object lookup(String name) throws ComponentException {
//			ComponentMetaVO vo = getRemoteContext().getComponentMetaVO(name);
//			ArrayList<ComponentMetaVO> list = new ArrayList<ComponentMetaVO>();
//			list.add(vo);
//			if (StringUtil.hasText(vo.getModule())) {
//				ComponentMeta[] metas = publicRepo.getComponentMetas(vo
//						.getModule());
//				for (int i = 0; i < metas.length; i++) {
//					if (metas[i].isRemote()
//							&& !metas[i].getName().equals(vo.getName())) {
//						list.add(metas[i].getComponentMetaVO());
//					}
//				}
//			}
//			return list.toArray(new ComponentMetaVO[list.size()]);
//		}
//
//	}
//
//	public JndiContext getJndiContext() {
//		return jndiContext;
//	}
//
//	private class BusinessAppServerForEJBContext implements Context {
//		public Object lookup(String name) throws ComponentException {
//			ComponentMeta meta = publicRepo.getComponentMeta(name);
//			if (meta == null) {
//				throw new ComponentNotFoundException(String.format(
//						Messages.nfdTxCmnt, name, " all module",
//						", please deploy it"));
//			}
//			if (meta.getTxAttribute() == TxAttribute.BMT
//					|| meta.getTxAttribute() == TxAttribute.CMT) {
//				Object object = null;
//				for (int i = 0; i < 3; i++) {
//					try {
//						object = meta.getInstantiator().instantiate(
//								meta.getContainer().getContext(),
//								meta.getName(), null);
//						if (object instanceof Resolvable && (i != 2)) {
//							Logger.warn("wait componet construct finished:"
//									+ name);
//							Thread.sleep(10);
//							continue;
//						} else {
//							break;
//						}
//					} catch (InterruptedException e) {
//					}
//				}
//
//				if (object instanceof Resolvable) {
//					throw new ComponentException(name,
//							" component now is constructing...");
//				}
//				return object;
//			} else {
//				throw new ComponentException(name, "not tx component");
//			}
//		}
//	}
//
//	public AbstractContext getServerContext() {
//		if (serverCtx == null) {
//			serverCtx = new AbstractContext() {
//				@Override
//				protected Container getContainer() {
//					return BusinessAppServer.this;
//				}
//
//				public Object lookup(String name) {
//					if (Server.class.getName().equals(name)) {
//						return getServer();
//					} else {
//						return super.lookup(name);
//					}
//
//				}
//
//				protected ComponentMeta[] listComponentMetas(Class clazz) {
//					ArrayList<ComponentMeta> list = new ArrayList<ComponentMeta>(
//							200);
//					ComponentMeta[] metas1 = publicRepo.getComponentMetas();
//					ComponentMeta[] metas2 = metas
//							.toArray(new ComponentMeta[metas.size()]);
//					for (int i = 0; i < metas1.length; i++) {
//						if (clazz == null
//								|| ClassUtil.isAssignableFrom(clazz, metas1[i]
//										.getInterfaces()))
//							list.add(metas1[i]);
//
//					}
//					for (int i = 0; i < metas2.length; i++) {
//						if (clazz == null
//								|| ClassUtil.isAssignableFrom(clazz, metas2[i]
//										.getInterfaces()))
//							list.add(metas2[i]);
//
//					}
//					ComponentMeta[] retMetas = new ComponentMeta[list.size()];
//					list.toArray(retMetas);
//					return retMetas;
//				}
//			};
//		}
//		return serverCtx;
//	}
//
//	public Context getContext(String moduleName) {
//		if (moduleName == null || getName().equals(moduleName)
//				|| "".equals(moduleName.trim())) {
//			return getContext();
//		} else {
//			Container module = getContainer(moduleName);
//			if (module == null) {
//				throw new FrameworkRuntimeException(String.format(
//						Messages.nfdModule, moduleName));
//			}
//			return module.getContext();
//		}
//	}
//
//	public void destroy() {
//		if (state != State.NOT_INIT) {
//			try {
//				jmxService.stop();
//			} catch (Exception e) {
//
//			}
//			try {
//				if (jndiContext != null)
//					jndiContext.stop();
//			} catch (Exception e) {
//				getLogger().error("JNDIComponent stop error");
//			}
//
//			jmxService = null;
//			jndiContext = null;
//			environment.clear();
//			state = State.NOT_INIT;
//			serverBase = null;
//			moduleBase = null;
//			repository = null;
//			server = null;
//			// configuration = null;
//
//		}
//
//	}
//
//	public static void main(String args[]) throws Exception {
//		BusinessAppServer.getInstance().init(null);
//		BusinessAppServer.getInstance().start();
//	}
//
//	public PublicMetaRepo getPublicComponentMetaRepo() {
//		return this.publicRepo;
//	}
//
//	public Server getServer() {
//		return this;
//	}
//
//	public ServiceCache getServiceCache() {
//		return serviceCache;
//	}
//
//	public Deployer getDeployer() {
//		return deployer;
//	}
//
//	public Mode getMode() {
//		return mode;
//	}
//
//	/**
//	 * @deprecated only for be compatible with previous version
//	 */
//	@SuppressWarnings("deprecation")
//	public void addServerListener(
//			final nc.bs.framework.core.ServerListener listener) {
//		addLifecycleListener(new LifycycleListener() {
//			public void afterStart(LifeCycle container) {
//				listener.afterStarted();
//			}
//
//			public void beforeStop(LifeCycle container) {
//			}
//		});
//	}
//
//	public Monitor getMonitor() {
//		return this.monitor;
//	}
//
//	public JmxService getJmxService() {
//		return this.jmxService;
//	}
//
//	public Configuration getConfiguration() {
//		if (configuration == null) {
//			configuration = ConfigurationUtil.getConfiguration(RuntimeEnv
//					.getInstance().getNCHome());
//		}
//		return configuration;
//	}
//
//	public String getServerName() {
//		return configuration.getServerName();
//	}
//
//	public int getPriority() {
//		return -1;
//	}
//
//	public Container removeContainer(String name) {
//		Container c = namedModulesMap.remove(name);
//		if (c != null)
//			modulesMap.remove(c.getUrl());
//		return c;
//	}
//
//	@Deprecated
//	public Module getModule(String name) {
//		Container c = getContainer(name);
//		if (c instanceof Module) {
//			return new DeployedModule((Module) c);
//		} else {
//			return null;
//		}
//	}
//
//	@Deprecated
//	public Module getModule(URL url) {
//		Container c = getContainer(url);
//		if (c instanceof Module) {
//			return new DeployedModule((Module) c);
//		} else {
//			return null;
//		}
//	}

}