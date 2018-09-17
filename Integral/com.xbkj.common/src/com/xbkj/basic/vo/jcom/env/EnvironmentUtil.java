package com.xbkj.basic.vo.jcom.env;

import java.io.File;
import java.util.StringTokenizer;

import com.xbkj.basic.vo.jcom.util.ClassUtil;

/**
 * 
 * �ṩһ�鷽������ϵͳ�����ļ��
 * <DL>
 * <DT><B>Provider:</B></DT>
 * <DD>NC-UAP</DD>
 * </DL>
 * 
 * @author chenxy,hgy
 * @since 2.0
 */
public abstract class EnvironmentUtil {

    private static String[] m_strOsKey = { "windows", "solaris", "aix", "linux" };

    /** ����ϵͳ���� */
    private static int m_iOsType = -1;

    public static final int WINDOWS = 0;

    public static final int SOLARIS = 1;

    public static final int AIX = 2;

    public static final int LINUX = 3;

    public static final int UNKNOWUNIX = 100;

    /** Java version 1.0 token */
    public static final int VERSION_1_0 = 0x01;

    /** Java version 1.1 token */
    public static final int VERSION_1_1 = 0x02;

    /** Java version 1.2 token */
    public static final int VERSION_1_2 = 0x03;

    /** Java version 1.3 token */
    public static final int VERSION_1_3 = 0x04;

    /** Java version 1.4 token */
    public static final int VERSION_1_4 = 0x05;

    /** Java version 1.5 token */
    public static final int VERSION_1_5 = 0x06;

    /** 
     * Private to avoid over optimization by the compiler.
     *
     */
    private static final int VERSION;

    /**
     * the wls InitialContextFactory implementation class name
     */
    private static final String WLS_JNDI_CONTEXT = "weblogic.jndi.WLInitialContextFactory";

    /**
     * the was InitialContextFactory implementaion class name
     */
    private static final String WAS_JNDI_CONTEXT = "com.ibm.websphere.naming.WsnInitialContextFactory";

    private static Boolean isRunningInWebSphere;

    private static Boolean isRunningInWeblogic;

    static {
        // default to 1.0
        int version = VERSION_1_0;

        try {
            // check for 1.1
            Class.forName("java.lang.Void");
            version = VERSION_1_1;

            // check for 1.2
            Class.forName("java.lang.ThreadLocal");
            version = VERSION_1_2;

            // check for 1.3
            Class.forName("java.lang.StrictMath");
            version = VERSION_1_3;

            // check for 1.4
            Class.forName("java.lang.StackTraceElement");
            version = VERSION_1_4;

            // check for 1.5
            Class.forName("java.lang.Enum");
            version = VERSION_1_5;
        } catch (ClassNotFoundException e) {

        }
        VERSION = version;
    }

    /**
     * ���ز���ϵͳ��ơ�
     * <p>
     * ��������:(2001-8-1 21:11:16)
     * 
     * @return ����ϵͳ���
     */
    public static String getOsName() {
        return System.getProperty("os.name");
    }

    /**
     * ���ز���ϵͳ���͡�
     * <p>
     * ��������:(2001-8-1 21:15:18)
     * 
     * @return int
     */
    public static int getOsType() {
        if (m_iOsType == -1) {
            String strOsName = getOsName();
            for (int i = 0; i < m_strOsKey.length; i++) {
                if (strOsName.toLowerCase().indexOf(m_strOsKey[i]) != -1) {
                    m_iOsType = i;
                    break;
                }
            }
            if (m_iOsType == -1)
                m_iOsType = UNKNOWUNIX;
        }
        return m_iOsType;
    }

    /**
     * ���ظ�����л�����صĻ���·����
     * <p>
     * ��������:(2001-10-2 10:02:23)
     * 
     * @param oldFilePath
     *            �ļ�·����
     * @return ת������뵱ǰ����ϵͳ��ص��ļ�·����
     * @deprecated since 5.0 JDK���Դ��?ͬ���ļ��ָ����ˡ�
     */
    public static String convert2LocalPath(String oldFilePath) {
        if (null == oldFilePath)
            return null;
        String filePath = oldFilePath.replace('\\', '/');
        StringTokenizer st = new StringTokenizer(filePath, "/");

        StringBuffer newPath = new StringBuffer();
        String strFileSep = getPathPad();
        while (st.hasMoreElements()) {
            String str = st.nextElement().toString();
            newPath.append(str);
            if (!st.hasMoreElements())
                break;
            newPath.append(strFileSep);
        }
        return newPath.toString();
    }

    /**
     * Ŀ¼�ָ���
     * <p>
     * ��������:(2001-8-11 11:32:25)
     * 
     * @return Ŀ¼�ָ���
     */
    public static String getPathPad() {
        return File.separator;
        // return System.getProperty("file.separator");
    }

    /**
     * �����û�Ĭ�ϵĹ���Ŀ¼��
     * <p>
     * ��������:(2001-8-1 21:23:26)
     * 
     * @return �û�����Ŀ¼
     */
    public static String getUserDir() {
        return System.getProperty("user.dir");
    }

    /**
     * Return the version of <em>Java</em> supported by the VM.
     *
     * @return  The version of <em>Java</em> supported by the VM.
     */
    public static int getVersion() {
        return VERSION;
    }

    /**
     * Returns true if the given version identifer is equal to the
     * version identifier of the current virtuial machine.
     *
     * @param version    The version identifier to check for.
     * @return           True if the current virtual machine is the same version.
     */
    public static boolean isVersion(final int version) {
        return VERSION == version;
    }

    /**
     * Returns true if the current virtual machine is compatible with
     * the given version identifer.
     *
     * @param version    The version identifier to check compatibility of.
     * @return           True if the current virtual machine is compatible.
     */
    public static boolean isCompatible(final int version) {
        return VERSION >= version;
    }

    public static boolean isRunningInWebSphere() {
        if (isRunningInWebSphere == null) {
            String s = System.getProperty("nc.target.platform");
            if (s != null) {
                if ("was".equals(s)) {
                    isRunningInWebSphere = true;
                } else {
                    isRunningInWebSphere = false;
                }
            } else {
                try {
                	ClassUtil.loadClass(WAS_JNDI_CONTEXT);
                    isRunningInWebSphere = true;

                } catch (Throwable throwable) {
                    isRunningInWebSphere = false;
                }
            }
        }
        return isRunningInWebSphere;
    }

    /**
     * check wether the enviroment is weblogic
     *
     * @return
     */
    public static boolean isRunningInWeblogic() {
        if (isRunningInWeblogic == null) {
            String s = System.getProperty("nc.target.platform");
            if (s != null) {
                if ("wls".equals(s)) {
                    isRunningInWeblogic = true;
                } else {
                    isRunningInWeblogic = false;
                }
            } else {
                try {
                	ClassUtil.loadClass(WLS_JNDI_CONTEXT);
                    isRunningInWeblogic = true;

                } catch (Throwable throwable) {
                    isRunningInWeblogic = false;
                }
            }
        }
        return isRunningInWeblogic;

    }

}
