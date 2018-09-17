package com.xbkj.datasor.bs.framework.server;

import com.xbkj.datasor.bs.framework.common.ComponentMetaVO;
import com.xbkj.datasor.bs.framework.core.TxAttribute;
//import com.grc.datasor.bs.framework.core.AbstractMeta;
//import com.grc.datasor.bs.framework.instantiator.ConstantInstantiator;
//import com.grc.datasor.bs.framework.instantiator.CtorInstantiator;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: 2005-1-17
 * Time: 10:58:48
 */
public class ComponentMetaImpl //extends AbstractMeta implements ComponentMeta 
{

	private boolean remote = false;

	private String cluster;

	private boolean pub = false;

	private TxAttribute txAttribute = TxAttribute.NONE;

	private String ejbName;

	private transient ComponentMetaVO metaVO;

//	public ComponentMetaImpl(Container container) {
//		super(container);
//	}
//
//	public String getCluster() {
//		return cluster;
//	}
//
//	public void setCluster(String cluster) {
//		this.cluster = cluster;
//	}
//
//	public Container getContainer() {
//		return (Container) super.getContainer();
//	}
//
//	public boolean isRemote() {
//		return remote;
//	}
//
//	public void setRemote(boolean remote) {
//		this.remote = remote;
//	}
//
//	public TxAttribute getTxAttribute() {
//		return txAttribute;
//	}
//
//	public void setTxAttribute(TxAttribute txAttribute) {
//		this.txAttribute = txAttribute;
//	}
//
//	public boolean isPublic() {
//		return pub;
//	}
//
//	public void setPublic(boolean flag) {
//		this.pub = flag;
//	}
//
//	private boolean isExposeInterface() {
//		return getInterfaces() != null && getInterfaces().length > 0;
//	}
//
//	public void validate() {
//		Class<?> implClass = null;
//		Class<?>[] itfs = getInterfaces();
//		if (getRawInstantiator() instanceof CtorInstantiator) {
//			implClass = ((CtorInstantiator) getRawInstantiator())
//					.getImplementationClass();
//
//		} else if (getRawInstantiator() instanceof ConstantInstantiator) {
//			implClass = ((ConstantInstantiator) getRawInstantiator())
//					.getConstant().getClass();
//		}
//		if (getName() == null) {
//			if (isSupportAlias()) {
//				for (Class c : itfs) {
//					addAlia(c.getName());
//				}
//			}
//
//			if (getName() == null) {
//				if (itfs.length > 0) {
//					setName(itfs[0].getName());
//				} else if (implClass != null) {
//					setName(implClass.getName());
//				}
//			}
//		}
//
//		if (!StringUtil.hasText(getName())) {
//			throw new InvalidException("no name");
//		}
//
//		if (itfs.length == 0
//				&& (isRemote() || StringUtil.hasText(getCluster()))) {
//			if (implClass != null) {
//				Class[] ifArray = ClassUtil.getInterfaces(implClass,
//						new String[] { Serializable.class.getName(),
//								Externalizable.class.getName() });
//				for (int i = 0; i < ifArray.length; i++) {
//					addInterface(ifArray[i]);
//				}
//			}
//		}
//
//		if ((isRemote() || StringUtil.hasText(getCluster()))
//				&& !isExposeInterface()) {
//			throw new InvalidException(getName(),
//					"remote or cluster component must be designated at least one interface");
//		}
//
//		itfs = getInterfaces();
//		if (isExposeInterface()) {
//			for (int i = 0; i < itfs.length; i++) {
//				if (!itfs[i].isInterface())
//					throw new InvalidException(getName(), String.format(
//							notInterface, itfs[i]));
//				if (implClass != null && !itfs[i].isAssignableFrom(implClass)) {
//					throw new InvalidException(getName(), String.format(
//							invalidHierarchy, implClass.getName(), itfs[i]
//									.getName()));
//				}
//			}
//		}
//
//		try {
//			getInstantiator();
//		} catch (ComponentException e) {
//			throw new InvalidException(getName(),
//					"build instantiator from meta error", e);
//		}
//
//		if (!isActive()) {
//			if (implClass != null) {
//				if (ActiveComponent.class.isAssignableFrom(implClass)) {
//					setActive(true);
//					setSingleton(true);
//				}
//			}
//		} else {
//			setSingleton(true);
//		}
//
//		if (getTxAttribute() == TxAttribute.CMT
//				|| getTxAttribute() == TxAttribute.BMT) {
//			if (ejbName == null) {
//				ejbName = getName();
//			} else {
//				resolveEjbName();
//			}
//		}
//	}
//
//	public Instantiator getInstantiator() throws ComponentException {
//		if (getState() == ComponentState.ERROR && isSingleton()) {
//			throw new InstException(getName(), getName(), getStateString());
//		}
//		if (instantiator == null) {
//
//			Configuration cfg = getContainer().getServer().getConfiguration();
//			if (cfg.isServiceHost(getName(), cluster)) {
//				instantiator = super.getInstantiator();
//			} else {
//				String[] targetServers = cfg.getServiceAt(getName(), cluster);
//				int rdidx = new Random().nextInt();
//				if (rdidx < 0) {
//					rdidx = -rdidx;
//				}
//				String ts = targetServers[rdidx % targetServers.length];
//
//				instantiator = new RemoteInstantiator(ts, this);
//			}
//		}
//		return instantiator;
//	}
//
//	public String toString() {
//		return getName();
//	}
//
//	public String getEjbName() {
//		if (ejbName == null) {
//			return getName();
//		} else {
//
//			return ejbName;
//		}
//	}
//
//	public void setEjbName(String str) {
//		this.ejbName = str;
//		resolveEjbName();
//	}
//
//	public ComponentMetaVO getComponentMetaVO() {
//		if (metaVO == null) {
//			Class[] itfs = getInterfaces();
//			String[] itfNames = new String[itfs.length];
//			for (int i = 0; i < itfs.length; i++) {
//				itfNames[i] = itfs[i].getName();
//				metaVO = new ComponentMetaVO(getContainer() == null ? null
//						: getContainer().getName(), getName(), getAlias(),
//						itfNames);
//			}
//		}
//
//		return metaVO;
//
//	}

//	private void resolveEjbName() {
//		if (getContainer() != null && ejbName != null) {
//			if (ejbName.indexOf(".") < 0) {
//				String tx = "";
//				if (getTxAttribute() == TxAttribute.CMT)
//					tx = "cmt";
//				else
//					tx = "bmt";
//				ejbName = ejbName.substring(0, 1).toUpperCase()
//						+ ejbName.substring(1);
//				ejbName = "nc.gejb." + getContainer().getName() + "." + tx
//						+ "." + ejbName;
//			}
//		}
//	}

}
