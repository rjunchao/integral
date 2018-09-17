package com.xbkj.datasor.bs.framework.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Random;

import com.grc.log.bs.logging.Logger;
 
/**
 * Ϊ����Զ�̵�����Ҫ���ݵ�������Ϣ
 */
public class InvocationInfo implements Externalizable {

    private static final long serialVersionUID = -2872276266073304000L;

    public static final String DEFAULT_DATASOURCE_NAME = "default";

    public static final String DEFAULT_LANG_CODE_VALUE = "simpchn";

    public static final String DEFAULT_USERID_VALUE = "#UAP#";

    public static final String DEFAULT_PKCORP_VALUE = "0001";

    public static final String LOGIN_LANG_CODE_PROP = "Login_Lang_Code";

    public static final String USERID_PROP = "USERID";

    public static final String USER_PKCORP_PROP = "UserPKCorp";

    public static final String USER_DATA_SOURCE_PROP = "UserDataSource";

    public static final String LOGIN_USER_TYPE_PROP = "loginUserType";

    private String servicename = null;

    private String methodname = null;

    private Object[] parameters = null;

    private Class[] parametertypes = null;

    private String module;

    private byte sysid;

    /**���Դ��� */
    private String userDataSource = DEFAULT_DATASOURCE_NAME;

    /**�ͻ��˵ĵ�ַ */
    private String clientHost;

    /**����������� */
    private String langCode;

    /** ��ǰ��¼�Ĺ�˾���� */
    private String corpCode = null;

    /** ��ǰ��¼���û����� */
    private String userCode = null;

    /** ��ǰ��¼���� */
    private String date;

    private int moduleLang = -1;

    private transient String remoteHost;

    private transient int remotePort;

    private transient String serverHost;

    private transient int serverPort;

    private transient String serverName;
  	private String callId;
  	private String userLevel;
    public InvocationInfo() {
        fillClientInfoClient();
    }

    public InvocationInfo(String module, String serviceName, String methodName, Class[] parameterTypes,
            Object[] parameters) {
        this(serviceName, methodName, parameterTypes, parameters);
        this.module = module;
    }

    /**
     * construct an instance, other information are from System or Thread(server side)
     * 
     * @param servicename
     * @param methodname
     * @param parametertypes
     * @param parameters
     */
    public InvocationInfo(String servicename, String methodname, Class[] parametertypes, Object[] parameters) {
        this.servicename = servicename;
        this.methodname = methodname;
        this.parameters = parameters;
        this.parametertypes = parametertypes;
        fillClientInfo();

    }

    public String getServiceName() {
        return servicename;
    }

    public String getMethodName() {
        return methodname;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public Class[] getParametertypes() {
        return parametertypes;
    }

    @SuppressWarnings("deprecation")
    private void fillClientInfo() {
        if (RuntimeEnv.getInstance().isThreadRunningInServer()) {
            InvocationInfoProxy infoProxy = InvocationInfoProxy.getInstance();
            userDataSource = infoProxy.getUserDataSource();
            if (userDataSource == null) {
                userDataSource = System.getProperty(USER_DATA_SOURCE_PROP, DEFAULT_DATASOURCE_NAME);
            }

            corpCode = infoProxy.getCorpCode();

            if (isEmpty(corpCode)) {
                corpCode = getSysProperty(USER_PKCORP_PROP, DEFAULT_PKCORP_VALUE);
            }

            userCode = infoProxy.getUserCode();
            if (isEmpty(userCode)) {
                userCode = getSysProperty(USERID_PROP, DEFAULT_USERID_VALUE);
            }

            langCode = infoProxy.getLangCode();

            if (isEmpty(langCode)) {
                langCode = getSysProperty(LOGIN_LANG_CODE_PROP, DEFAULT_LANG_CODE_VALUE);
            }

            moduleLang = infoProxy.getLoginUserType();

            // it is a hack
            clientHost = infoProxy.getClientHost();

            if (isEmpty(clientHost)) {
                try {
                    clientHost = ClientInvocationInfo.clientHost;
                } catch (Exception e) {

                }
            }

            date = infoProxy.getDate();

            sysid = infoProxy.getSysid();
      			//TODO:NEED HGY TO AUDIT.
      			callId = infoProxy.getCallId();
      			userLevel = infoProxy.getUserLevel();

        } else {
            ClientInvocationInfo cinfo = ClientInvocationInfo.getClientInvocationInfo();
            userDataSource = cinfo.getUserDataSource();

            corpCode = cinfo.getCorpCode();
            userCode = cinfo.getUserCode();
            clientHost = cinfo.getClientHost();
            langCode = cinfo.getLangCode();
            date = cinfo.getDate();
            moduleLang = cinfo.getLoginUserType();
      			sysid = cinfo.getSysid();
      			//TODO:NEED HGY TO AUDIT.
      			callId = cinfo.getCallId();
      			userLevel = cinfo.getUserLevel();

        }
  			//TODO:NEED HGY TO AUDIT.
      	if (callId == null) {
    			callId = System.currentTimeMillis() + "-" + new Random().nextInt(10000);
    			Logger.debug("callid="+callId);
    		}

    }

    public String getClientHost() {
        return clientHost;
    }

    public String getDate() {
        return "" + date;
    }

    /**
     * @deprecated
     * 
     * @return
     */
    public String getDefaultDataSource() {
        return this.getUserDataSource();
    }

    /**
     * @deprecated
     * 
     * @param ds
     */
    public void setDefaultDataSource(String ds) {
        this.setUserDataSource(ds);
    }

    public String getUserDataSource() {
        if (userDataSource == null) {
            return DEFAULT_DATASOURCE_NAME;
        }
        return userDataSource;
    }

    public void setUserDataSource(String ds) {
        this.userDataSource = ds;
    }

    public String getCorpCode() {
        return corpCode;
    }

    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public void setServiceName(String serviceName) {
        this.servicename = serviceName;
    }

    public void setMetodName(String methodName) {
        this.methodname = methodName;
    }

    @Deprecated
    public int getModuleLang() {
        return this.moduleLang;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    @Deprecated
    public void setModuleLang(int moduleLang) {
        this.moduleLang = moduleLang;
    }

    public int getLoginUserType() {
        return this.moduleLang;
    }

    public void setLoginUserType(int moduleLang) {
        this.moduleLang = moduleLang;
    }

    private boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public int getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(int remotePort) {
        this.remotePort = remotePort;
    }

    private String getSysProperty(String prop, String defVal) {
        String v = System.getProperty(prop);
        if (isEmpty(v)) {
            v = defVal;
        }
        return v;
    }

    @SuppressWarnings("deprecation")
    private void fillClientInfoClient() {
        if (RuntimeEnv.getInstance().isThreadRunningInServer()) {

            userDataSource = System.getProperty("UserDataSource", "design");

            corpCode = getSysProperty("UserPKCorp", "0001");

            userCode = getSysProperty("UserCode", "#UAP#");

            date = System.getProperty("userlogintime");
            try {
                sysid = Byte.valueOf(getSysProperty("SystemId", "0"));
                clientHost = ClientInvocationInfo.clientHost;
            } catch (Exception e) {
            }
            langCode = getSysProperty("Login_Lang_Code", "simpchn");
            String strUt = System.getProperty("loginUserType");
            try {
                if (strUt != null && strUt.length() > 0)
                    moduleLang = Integer.parseInt(strUt);
            } catch (Throwable t) {
                moduleLang = -1;
            }
            //TODO:NEED HGY TO AUDIT.
      			callId = System.currentTimeMillis() + "-" + new Random().nextInt(10000);
      			userLevel = System.getProperty("nc.log.userLevel");


        } else {
            ClientInvocationInfo cinfo = ClientInvocationInfo.getClientInvocationInfo();
            userDataSource = cinfo.getUserDataSource();

            corpCode = cinfo.getCorpCode();
            userCode = cinfo.getUserCode();
            clientHost = cinfo.getClientHost();
            langCode = cinfo.getLangCode();
            date = cinfo.getDate();
            moduleLang = cinfo.getLoginUserType();
            sysid = cinfo.getSysid();
            //TODO:NEED HGY TO AUDIT.
        		callId = cinfo.getCallId();
      			userLevel = cinfo.getUserLevel();
      		}

      		if (callId == null)
      			callId = System.currentTimeMillis() + "-" + new Random().nextInt(10000);

    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        module = readString(in);
        servicename = readString(in);
        methodname = readString(in);
        userDataSource = readString(in);
        langCode = readString(in);
        userCode = readString(in);
        corpCode = readString(in);
        clientHost = readString(in);
        moduleLang = in.readInt();
        sysid = in.readByte();
        //TODO:NEED HGY TO AUDIT.
      	userLevel = readString(in);
    		callId = readString(in);
        long ldate = in.readLong();
        if (ldate != -1) {
            date = "" + ldate;
        }
        Object obj = in.readObject();
        if (obj != null) {
            parametertypes = (Class[]) obj;
            parameters = (Object[]) in.readObject();
        }

    }

    public void writeExternal(ObjectOutput out) throws IOException {
        writeString(out, module);
        writeString(out, servicename);
        writeString(out, methodname);
        writeString(out, userDataSource);
        writeString(out, langCode);
        writeString(out, userCode);
        writeString(out, corpCode);
        writeString(out, clientHost);
        out.writeInt(moduleLang);
        out.write(sysid);
        //TODO:NEED HGY TO AUDIT.
        writeString(out, userLevel);
    		writeString(out, callId);
        long i = -1;
        if (date != null) {
            try {
                i = Long.parseLong(date);
            } catch (Throwable thr) {
                i = -1;
            }
        }
        out.writeLong(i);

        if (parametertypes != null && parametertypes.length != 0) {
            out.writeObject(parametertypes);
            out.writeObject(parameters);
        } else {
            out.writeObject(null);
        }

    }

    private String readString(ObjectInput in) throws IOException {
        byte len = in.readByte();
        if (len == -1) {
            return null;
        } else {
            byte[] bytes = new byte[len];
            in.read(bytes);
            return new String(bytes, "UTF-8");
        }
    }

    private void writeString(ObjectOutput out, String str) throws IOException {
        if (str != null) {
            byte[] bytes = str.getBytes("UTF-8");
            out.writeByte(bytes.length);
            out.write(bytes);

        } else {
            out.writeByte(-1);
        }

    }

    public void setDate(String loginDate) {
        this.date = loginDate;
    }

    public byte getSysid() {
        return sysid;
    }

    public void setSysid(byte sysid) {
        this.sysid = sysid;
    }
    //TODO:NEED HGY TO AUDIT.
    public String getCallId() {
  		return this.callId;
  	}
    //TODO:NEED HGY TO AUDIT.
  	public String getUserLevel() {
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