package com.xbkj.datasor.bs.framework.common;

import java.net.InetAddress;

/**
 * @author �ι���
 *
 * It is designed for the backend server run as the nc client
 * Date: 2006-5-9
 * Time: 14:39:11
 */
class ClientInvocationInfo {

    private String defaultDataSource = null;

    private String userDataSource = null;

    private String langCode;

    private String corpCode = null;

    private String userCode = null;

    private String date = null;

    static String clientHost;

    private byte sysid;
    //TODO:NEED HGY TO AUDIT.
  	private String callId;

  	private String userLevel;
    static {
        try {
            clientHost = InetAddress.getLocalHost().getHostAddress();
            int idx = -1;
            if ( (idx = clientHost.indexOf('/')) > -1) {
            	clientHost = clientHost.substring(idx + 1);
            }
        } catch (Throwable e) {
        }
    }

    private Integer moduleLang;

    private static ThreadLocal<ClientInvocationInfo> clientInvInfo = new ThreadLocal<ClientInvocationInfo>() {
		protected ClientInvocationInfo initialValue() {
			return new ClientInvocationInfo();
		}
	};

    private ClientInvocationInfo() {

    }

    static ClientInvocationInfo getClientInvocationInfo() {
        return (ClientInvocationInfo) clientInvInfo.get();
    }

    public String getCorpCode() {
        if (corpCode == null || "".equals(corpCode.trim()))
            return getSysProperty(InvocationInfo.USER_PKCORP_PROP, InvocationInfo.DEFAULT_PKCORP_VALUE);
        return corpCode;
    }

    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }

    public String getDate() {
        if (date == null || "".equals(date.trim())) {
            return System.getProperty("userlogintime");
        }
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @deprecated
     * 
     * @return
     */
    public String getDefaultDataSource() {
        return defaultDataSource;
    }

    /**
     * @deprecated
     * 
     * @param defaultDataSource
     */
    public void setDefaultDataSource(String defaultDataSource) {
        if (defaultDataSource == null) {
            getSysProperty(InvocationInfo.USER_DATA_SOURCE_PROP, InvocationInfo.DEFAULT_DATASOURCE_NAME);
        }
        this.defaultDataSource = defaultDataSource;
    }

    public String getLangCode() {
        if (langCode == null)
            return getSysProperty(InvocationInfo.LOGIN_LANG_CODE_PROP, InvocationInfo.DEFAULT_LANG_CODE_VALUE);
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    @Deprecated
    public int getModuleLang() {
        return moduleLang;
    }

    @Deprecated
    public void setModuleLang(int moduleLang) {
        this.moduleLang = moduleLang;
    }

    public int getLoginUserType() {
        if (moduleLang == null) {
            String strUt = System.getProperty(InvocationInfo.LOGIN_USER_TYPE_PROP);
            if (strUt != null && strUt.length() > 0) {
                try {
                    return Integer.parseInt(strUt);
                } catch (Throwable thr) {
                    return -1;
                }
            }
            return -1;
        }
        return moduleLang;
    }

    public void setLoginUserType(int ut) {
        this.moduleLang = ut;
    }

    public String getUserCode() {
        if (userCode == null)
            return getSysProperty(InvocationInfo.USERID_PROP, InvocationInfo.DEFAULT_USERID_VALUE);
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserDataSource() {
        if (userDataSource == null) {
            return System.getProperty(InvocationInfo.USER_DATA_SOURCE_PROP, InvocationInfo.DEFAULT_DATASOURCE_NAME);
        }
        return userDataSource;
    }

    public void setUserDataSource(String userDataSource) {
        this.userDataSource = userDataSource;
    }

    public void reset() {
        userDataSource = null;
        defaultDataSource = null;
        corpCode = null;
        userCode = null;
        langCode = null;
    }

    public String getClientHost() {
        return clientHost;
    }

    private String getSysProperty(String prop, String defVal) {
        String v = System.getProperty(prop);
        if (isEmpty(v)) {
            v = defVal;
        }
        return v;
    }

    private boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    public byte getSysid() {
        return sysid;
    }

    public void setSysid(byte sysid) {
        this.sysid = sysid;
    }
    //TODO:NEED HGY TO AUDIT.
    public String getCallId() {
  		return callId;
  	}
    //TODO:NEED HGY TO AUDIT.
  	public String getUserLevel() {
  		if (userLevel == null) {
  			return System.getProperty("nc.log.userLevel");
  		}
  		return userLevel;
  	}
    //TODO:NEED HGY TO AUDIT.
  	public void setUserLevel(String userLevel) {
  		this.userLevel = userLevel;
  	}
    //TODO:NEED HGY TO AUDIT.
  	public void setCallId(String callId2) {
  		this.callId = callId2;
  	}
  
}
