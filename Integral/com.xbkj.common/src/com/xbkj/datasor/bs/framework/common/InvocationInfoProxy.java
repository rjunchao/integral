/*
 * Created on 2005-7-8
 */
package com.xbkj.datasor.bs.framework.common;

import java.util.Properties;

import com.grc.log.bs.logging.Logger;

/**
 * User: �ι���
 * Date: 2005-8-17
 * Time: 14:49:47
 * 
 * �������˱���Զ�̵��õ���Ϣ,�õ��ý���ʱ,����.
 * ��ȡ����3.x�е�RegistCenter/ServletCallDescriptor��ʹ��
 */
public class InvocationInfoProxy {

    private static InvocationInfoProxy cen = new InvocationInfoProxy();

    private ThreadLocal<InvocationInfo> infoLocal = new ThreadLocal<InvocationInfo>();

    private ThreadLocal<Properties> invProperties = new ThreadLocal<Properties>() {
        protected Properties initialValue() {
            return new Properties();
        }
    };

    /**
     * ��ȡһ��ʵ��
     */
    public static InvocationInfoProxy getInstance() {
        return cen;
    }

    private InvocationInfoProxy() {
        super();
    }

    protected Object initialValue() {
        return new InvocationInfo();
    }

    protected InvocationInfo getInvocationInfo() {
        InvocationInfo info = (InvocationInfo) infoLocal.get();
        if (info == null) {
            info = new InvocationInfo();
            infoLocal.set(info);
        }
        return info;

    }

    public InvocationInfo get() {
        return getInvocationInfo();
    }

    public void set(InvocationInfo info) {
        infoLocal.set(info);
    }

    /**
     * �ͻ��˵��������
     * 
     * @return
     */
    public String getClientHost() {
        if (RuntimeEnv.getInstance().isThreadRunningInServer()) {
            return getInvocationInfo().getClientHost();
        } else {
            return ClientInvocationInfo.getClientInvocationInfo().getClientHost();
        }
    }

    /**
     * ���ڵ��õ�ʱ��
     * 
     * @return
     */
    public String getDate() {
        if (RuntimeEnv.getInstance().isThreadRunningInServer()) {
            return getInvocationInfo().getDate();
        } else {
            return ClientInvocationInfo.getClientInvocationInfo().getDate();
        }
    }

    /**
     * ȱʡ�����Դ����
     * 
     * @deprecated
     * @return
     */
    public String getDefaultDataSource() {
        if (RuntimeEnv.getInstance().isThreadRunningInServer()) {
            return getInvocationInfo().getUserDataSource();
        } else {
            return ClientInvocationInfo.getClientInvocationInfo().getUserDataSource();
        }
    }

    /**
     * ����ȱʡ�����Դ����
     * @deprecated
     * @param ds
     */
    public void setDefaultDataSource(String ds) {
        if (RuntimeEnv.getInstance().isThreadRunningInServer()) {
            Logger.putMDC("datasource", ds);
            getInvocationInfo().setUserDataSource(ds);
        } else {
            ClientInvocationInfo.getClientInvocationInfo().setUserDataSource(ds);
        }
    }

    /**
     * �û�ָ�������Դ
     * 
     * @return
     */
    public String getUserDataSource() {
        if (RuntimeEnv.getInstance().isThreadRunningInServer()) {
            return getInvocationInfo().getUserDataSource();
        } else {
            return ClientInvocationInfo.getClientInvocationInfo().getUserDataSource();
        }
    }

    /**
     * �����û�ָ�������Դ
     * @param ds
     */
    public void setUserDataSource(String ds) {
        if (RuntimeEnv.getInstance().isThreadRunningInServer()) {
            Logger.putMDC("datasource", ds);
            getInvocationInfo().setUserDataSource(ds);

        } else {
            ClientInvocationInfo.getClientInvocationInfo().setUserDataSource(ds);
        }
    }

    /**
     * ��ȡ��˾����
     * @return
     */
    public String getCorpCode() {
        if (RuntimeEnv.getInstance().isThreadRunningInServer()) {
            return getInvocationInfo().getCorpCode();
        } else {
            return ClientInvocationInfo.getClientInvocationInfo().getCorpCode();
        }
    }

    /**
     * ���ù�˾����
     * @param corpCode
     */
    public void setCorpCode(String corpCode) {
        if (RuntimeEnv.getInstance().isThreadRunningInServer()) {
            getInvocationInfo().setCorpCode(corpCode);
        } else {
            ClientInvocationInfo.getClientInvocationInfo().setCorpCode(corpCode);
        }
    }

    /**
     * ��ȡ���Ա���
     * 
     * @return
     */
    public String getLangCode() {
        if (RuntimeEnv.getInstance().isThreadRunningInServer()) {
            return getInvocationInfo().getLangCode();
        } else {
            return ClientInvocationInfo.getClientInvocationInfo().getLangCode();
        }
    }

    /**
     * �������Ա���
     * 
     * @param langCode
     */
    public void setLangCode(String langCode) {
        if (RuntimeEnv.getInstance().isThreadRunningInServer()) {
            getInvocationInfo().setLangCode(langCode);
        } else {
            ClientInvocationInfo.getClientInvocationInfo().setLangCode(langCode);
        }
    }

    /**
     * ��ȡ�û�����
     * @return
     */
    public String getUserCode() {
        if (RuntimeEnv.getInstance().isThreadRunningInServer()) {
            return getInvocationInfo().getUserCode();
        } else {
            return ClientInvocationInfo.getClientInvocationInfo().getUserCode();
        }
    }

    /**
     * �����û�����
     * @param userCode
     */
    public void setUserCode(String userCode) {
        if (RuntimeEnv.getInstance().isThreadRunningInServer()) {
            getInvocationInfo().setUserCode(userCode);
        } else {
            ClientInvocationInfo.getClientInvocationInfo().setUserCode(userCode);
        }

    }

    /**
     * ��������ݿ��йصĴ���ת��������������ݿ����
     * @param moduleLang
     * @deprecated
     */
    public void setModuleLang(int moduleLang) {
        if (RuntimeEnv.getInstance().isThreadRunningInServer()) {
            getInvocationInfo().setModuleLang(moduleLang);
        } else {
            ClientInvocationInfo.getClientInvocationInfo().setModuleLang(moduleLang);
        }
    }

    public void setLoginUserType(int moduleLang) {
        if (RuntimeEnv.getInstance().isThreadRunningInServer()) {
            getInvocationInfo().setLoginUserType(moduleLang);
        } else {
            ClientInvocationInfo.getClientInvocationInfo().setLoginUserType(moduleLang);
        }
    }

    public int getLoginUserType() {
        if (RuntimeEnv.getInstance().isThreadRunningInServer()) {
            return getInvocationInfo().getLoginUserType();
        } else {
            return ClientInvocationInfo.getClientInvocationInfo().getLoginUserType();
        }
    }

    /**
     * ��ȡ����ݿ��йصĴ���ת������
     * @return
     * @deprecated
     */
    public int getModuleLang() {
        if (RuntimeEnv.getInstance().isThreadRunningInServer()) {
            return getInvocationInfo().getModuleLang();
        } else {
            return ClientInvocationInfo.getClientInvocationInfo().getModuleLang();
        }
    }

    public String getRemoteHost() {
        return getInvocationInfo().getRemoteHost();
    }

    public int getRemotePort() {
        return getInvocationInfo().getRemotePort();
    }

    public void setRemotePort(int port) {
        getInvocationInfo().setRemotePort(port);
    }

    public void setRemoteHost(String remoteHost) {
        getInvocationInfo().setRemoteHost(remoteHost);
    }

    public String getServerHost() {
        return getInvocationInfo().getServerHost();
    }

    public void setServerHost(String serverHost) {
        getInvocationInfo().setServerHost(serverHost);
    }

    public String getServerName() {
        return getInvocationInfo().getServerName();
    }

    public void setServerName(String serverName) {
        getInvocationInfo().setServerName(serverName);
    }

    public int getServerPort() {
        return getInvocationInfo().getServerPort();
    }

    public void setServerPort(int serverPort) {
        getInvocationInfo().setServerPort(serverPort);
    }

    public void reset() {
        if (RuntimeEnv.getInstance().isThreadRunningInServer()) {
            return;
        } else {
            ClientInvocationInfo.getClientInvocationInfo().reset();
        }
    }

    public void setProperty(String key, String value) {
        if (value == null)
            this.invProperties.get().remove(key);
        else
            this.invProperties.get().setProperty(key, value);
    }

    public String getProperty(String key) {
        return invProperties.get().getProperty(key);
    }

    public void setDate(String date) {
        getInvocationInfo().setDate(date);
    }

    public byte getSysid() {
        if (RuntimeEnv.getInstance().isThreadRunningInServer()) {
            return getInvocationInfo().getSysid();
        } else {
            return ClientInvocationInfo.getClientInvocationInfo().getSysid();
        }
    }

    public void setSysid(byte sysid) {
        if (RuntimeEnv.getInstance().isThreadRunningInServer()) {
            getInvocationInfo().setSysid(sysid);
        } else {
            ClientInvocationInfo.getClientInvocationInfo().setSysid(sysid);
        }
    }
    
    //TODO:NEED HGY TO AUDIT.
    public String getCallId() {
      if (RuntimeEnv.getInstance().isThreadRunningInServer()) {
          return getInvocationInfo().getCallId();
      } else {
          return ClientInvocationInfo.getClientInvocationInfo().getCallId();
      }
  }
  //TODO:NEED HGY TO AUDIT.
  public void setCallId(String callId) {
  	if (RuntimeEnv.getInstance().isThreadRunningInServer()) {
           getInvocationInfo().setCallId(callId);
      } else {
           ClientInvocationInfo.getClientInvocationInfo().setCallId(callId);
      }
  	
  }
  //TODO:NEED HGY TO AUDIT.
  public void setUserLevel(String level) {
      if (RuntimeEnv.getInstance().isThreadRunningInServer()) {     
          getInvocationInfo().setUserLevel(level);

      } else {
          ClientInvocationInfo.getClientInvocationInfo().setUserLevel(level);
      }
  }
  //TODO:NEED HGY TO AUDIT.
  public String getUserLevel() {
      if (RuntimeEnv.getInstance().isThreadRunningInServer()) {
          return getInvocationInfo().getUserLevel();
      } else {
          return ClientInvocationInfo.getClientInvocationInfo().getUserLevel();
      }
  }
  
}
