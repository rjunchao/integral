package com.xbkj.log.bs.logging;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.xbkj.log.vo.logging.Debug;
import com.xbkj.log.vo.logging.LoggerStrategy;
import com.xbkj.log.vo.logging.ModuleLoggerConfiguration;
import com.xbkj.log.vo.logging.Util;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: ${DATE}$
 * Time: ${TIME}$
 * <p/>
 * <p/>
 * ���ļ��м���������Ϣ���ṩ����־������ʹ��
 */
public class FileLoggerConfigManager extends DefaultLoggerConfigManager {
    //"E:/module-config.properties"; // cc + for test
    
	/**
     *���ļ���Ҫֱ�ӷ��ʣ���get��������Ϊ�ļ���ֲ�����Ĭ������
     */
    private String fileName;
    
    private String absoluteFileName = null;

    /**
     * ������������Properties
     */
    private Properties prop = new GB2312Properties();

    /** 
     * ��ȡģ�������ļ����ļ���
     *
     * @return
     */
    public String getFileName() {
        //�ӻ��������л�ȡ
        if (fileName == null) {
            //first from system property
            fileName = Util.getSystemProperty(LoggerGeneralConfig.MODULE_CONFIG_PROPERTY, null);
            if (fileName == null) {
                //from enviroment set by Logger System
                fileName = (String) getAttribute(LoggerGeneralConfig.MODULE_CONFIG_PROPERTY);
                if (fileName == null) {
                    fileName = LoggerGeneralConfig.MODULE_CONFIG_DEFAULT;
                    setAttribute(LoggerGeneralConfig.MODULE_CONFIG_PROPERTY, fileName);
                }
            } else {

                setAttribute(LoggerGeneralConfig.MODULE_CONFIG_PROPERTY, fileName);
            }
        }
        return fileName;
    }
    public String getAbsoluteFileName(){
    	if(absoluteFileName==null){
    		absoluteFileName = getFileName();    		
            if (System.getProperty("nc.server.location") != null) {
                if (absoluteFileName.startsWith("./")) {
                	absoluteFileName = System.getProperty("nc.server.location") + "/" + absoluteFileName.substring(2);
                } else if (absoluteFileName.charAt(0) != '/') {
                	absoluteFileName = System.getProperty("nc.server.location") + "/" + absoluteFileName;
                }
            }
    	}    	 
        return absoluteFileName;
    }
    /**
     * ����ģ�����õ��ļ�
     *
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
        //�ı价������
        setAttribute(LoggerGeneralConfig.MODULE_CONFIG_PROPERTY, fileName);
    }

    /**
     * ���캯����ϵͳ���Ի���
     */
    public FileLoggerConfigManager() {
    }

    /**
     * ���캯����ģ�������ļ�λ��
     *
     * @param fileName
     */
    public FileLoggerConfigManager(String fileName) {
        this.fileName = fileName;
    }

    /**
     * ��ģ�������ļ��м���������Ϣ,�̰߳�ȫ
     */
    public synchronized void loadConfig() {
        if (LoggerGeneralConfig.isRunningInServer()) {
            try{
                //���¼���ǰ������һ����ղ���
                configurationMap.clear();
                modulePackageMap.clear();
                List<ModuleLoggerConfiguration> configs = loadModuleLoggerConfigurations();
				for(ModuleLoggerConfiguration config :configs){
					 addConfig(config);
				}
            } catch (Exception e) {
                //���ü���ʧ�ܲ���Ӱ��ϵͳʹ��
                if(System.getProperty("nc.print.logConfig") != null)
                    Debug.debug("��־���ü���ʧ��,������־�����Ƿ���ڻ�����־������ȷ:" + fileName);
            }             
        }

    }
    public List<ModuleLoggerConfiguration> loadModuleLoggerConfigurations(){
    	if (LoggerGeneralConfig.isRunningInServer()) {
    		List<ModuleLoggerConfiguration> result = new ArrayList<ModuleLoggerConfiguration>();
            String loggerFileName = getAbsoluteFileName();
            if (loggerFileName == null)
                return null;
            File file = new File(loggerFileName);

            InputStream input = null;
            if (file.exists()) {
                try {
                    input = new FileInputStream(file);
                } catch (FileNotFoundException e) {

                }
            } else
            	input = getClass().getResourceAsStream("/nc/bs/logging/logger-config.properties");
            
            try {
                List modules = new ArrayList();

                prop.load(input);
                Iterator keys = prop.keySet().iterator();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    int index = key.lastIndexOf(".size");
                    if (index == -1)
                        index = key.lastIndexOf(".file");
                    if (index == -1)
                        index = key.lastIndexOf(".prefix");
                    if (index == -1)
                        index = key.lastIndexOf(".level");
                    if (index == -1)
                        index = key.lastIndexOf(".outModule");

                    if (index == -1)
                        index = key.lastIndexOf(".time");
                    if (index == -1)
                        index = key.lastIndexOf(".pattern");

                    if (index == -1)
                        index = key.lastIndexOf(".inherited");

                    if (index == -1)
                        index = key.lastIndexOf(".socket");

                    if (index != -1) {
                        String module = key.substring(0, index);
                        if (!modules.contains(module))
                            modules.add(module);
                    }
                }

                for (int i = 0; i < modules.size(); i++) {
                    String module = (String) modules.get(i);
                    ModuleLoggerConfiguration config = new ModuleLoggerConfiguration(module);
                    config.setModuleName(module);

                    String prefix = prop.getProperty(module + ".prefix");
                    config.setPackagePrefix(prefix);

                    config.setLogLevel(prop.getProperty(module + ".level"));
                    config.setLogPattern(prop.getProperty(module + ".pattern"));
                    config.setLogType(prop.getProperty(module + ".type"));
                    String datePattern = prop.getProperty(module + ".time");
                    String fname = prop.getProperty(module + ".file");

                    config.setSocketConfig(prop.getProperty(module + ".socket"));

                    if (file != null) {
                        config.setLogFile(fname);

                        //�������ڹ���
                        if (datePattern != null) {
                            LoggerStrategy strategy = new LoggerStrategy(LoggerStrategy.TIME_TYPE);
                            strategy.setAttribute("datePattern", datePattern);
                            config.setStrategy(strategy);
                        } else {

                            String maxIndex = prop.getProperty(module + ".size.maxIndex");
                            String maxSize = prop.getProperty(module + ".size.maxSize");
                            if (maxIndex != null && maxSize != null) {
                                LoggerStrategy strategy = new LoggerStrategy(LoggerStrategy.SIZE_TYPE);
                                strategy.setAttribute("maxSize", maxSize);
                                strategy.setAttribute("maxIndex", maxIndex);
                                config.setStrategy(strategy);
                            } 
                        }
                        
                    }
                    config.setOutModule(prop.getProperty(module + ".outModule"));
                    config.setInherited(prop.getProperty(module + ".inherited", "true"));
                    //LoggerPluginProvider.getInstance().config(config);
                    result.add(config);

                }
            } catch (Exception e) {
                //���ü���ʧ�ܲ���Ӱ��ϵͳʹ��
                if(System.getProperty("nc.print.logConfig") != null)
                    Debug.debug("��־���ü���ʧ��,������־�����Ƿ���ڻ�����־������ȷ:" + fileName);
            } finally {
                try {
                    if (input != null)
                        input.close();
                } catch (IOException e) {
                }
            }
            return result;
            
        }
    	return null;
    }
    /**
     * �������е�������Ϣ���̰߳�ȫ
     * @return
     */
    public Iterator iterate() {
        return configurationMap.values().iterator();
    }

    /**
     * ���������ļ����̰߳�ȫ
     */
    public synchronized void saveConfig() {
        if (getFileName() == null)
            return;
        PrintStream output = null;
        String loggerFileName = getAbsoluteFileName();
        File file = new File(loggerFileName);

        try {
            Util.makePath((String) getAttribute(LoggerGeneralConfig.MODULE_CONFIG_PROPERTY));
            output = new PrintStream(new FileOutputStream(file));
            Iterator itr = configurationMap.values().iterator();
            while (itr.hasNext()) {
                ModuleLoggerConfiguration config = (ModuleLoggerConfiguration) itr.next();
                //if(config.isNeedSave())
                    output.println(config.toString());
            }
            
            output.flush();

        } catch (Exception e) {
            Logger.error("save error", e);

        } finally {
            if (output != null)
                output.close();
        }
    }

    /**
     * ��ȡ���ԣ��̰߳�ȫ
     * @param name
     * @return
     */
    public Object getAttribute(String name) {
        //attributesΪHashtable���̰߳�ȫ
        return (attributes.get(name));
    }

    /**
     * �������ԣ��̰߳�ȫ
     * @param name
     * @param value
     */
    public void setAttribute(String name, Object value) {
        //ttributesΪHashtable �̰߳�ȫ
        attributes.put(name, value);
    }

    /**
     * �����ϵͳ���ص���������
     */
    private static class GB2312Properties extends Properties {

        private static final long serialVersionUID = -355542869692062123L;

        public GB2312Properties() {
            super(null);
        }

        public GB2312Properties(Properties defaults) {
            this.defaults = defaults;

        }

        private static final String keyValueSeparators = "=: \t\r\n\f";

        private static final String strictKeyValueSeparators = "=:";

        private static final String specialSaveChars = "=: \t\r\n\f#!";

        private static final String whiteSpaceChars = " \t\r\n\f";

        public void load(InputStream inStream) throws IOException {
            BufferedReader in = new BufferedReader(new InputStreamReader(inStream, "GB2312"));
            while (true) {
                // Get next line
                String line = in.readLine();
                if (line == null)
                    return;

                if (line.length() > 0) {

                    // Find start of key
                    int len = line.length();
                    int keyStart;
                    for (keyStart = 0; keyStart < len; keyStart++)
                        if (whiteSpaceChars.indexOf(line.charAt(keyStart)) == -1)
                            break;

                    // Blank lines are ignored
                    if (keyStart == len)
                        continue;

                    // Continue lines that end in slashes if they are not comments
                    char firstChar = line.charAt(keyStart);
                    if ((firstChar != '#') && (firstChar != '!')) {
                        while (continueLine(line)) {
                            String nextLine = in.readLine();
                            if (nextLine == null)
                                nextLine = "";
                            String loppedLine = line.substring(0, len - 1);
                            // Advance beyond whitespace on new line
                            int startIndex;
                            for (startIndex = 0; startIndex < nextLine.length(); startIndex++)
                                if (whiteSpaceChars.indexOf(nextLine.charAt(startIndex)) == -1)
                                    break;
                            nextLine = nextLine.substring(startIndex, nextLine.length());
                            line = new String(loppedLine + nextLine);
                            len = line.length();
                        }

                        // Find separation between key and value
                        int separatorIndex;
                        for (separatorIndex = keyStart; separatorIndex < len; separatorIndex++) {
                            char currentChar = line.charAt(separatorIndex);
                            if (currentChar == '\\')
                                separatorIndex++;
                            else if (keyValueSeparators.indexOf(currentChar) != -1)
                                break;
                        }

                        // Skip over whitespace after key if any
                        int valueIndex;
                        for (valueIndex = separatorIndex; valueIndex < len; valueIndex++)
                            if (whiteSpaceChars.indexOf(line.charAt(valueIndex)) == -1)
                                break;

                        // Skip over one non whitespace key value separators if any
                        if (valueIndex < len)
                            if (strictKeyValueSeparators.indexOf(line.charAt(valueIndex)) != -1)
                                valueIndex++;

                        while (valueIndex < len) {
                            if (whiteSpaceChars.indexOf(line.charAt(valueIndex)) == -1)
                                break;
                            valueIndex++;
                        }
                        String key = line.substring(keyStart, separatorIndex);
                        String value = (separatorIndex < len) ? line.substring(valueIndex, len) : "";

                        key = loadConvert(key);
                        value = loadConvert(value);
                        put(key, value);
                    }
                }
            }
        }

        private boolean continueLine(String line) {
            int slashCount = 0;
            int index = line.length() - 1;
            while ((index >= 0) && (line.charAt(index--) == '\\'))
                slashCount++;
            return (slashCount % 2 == 1);
        }

        private String loadConvert(String theString) {
            char aChar;
            int len = theString.length();
            StringBuffer outBuffer = new StringBuffer(len);

            for (int x = 0; x < len;) {
                aChar = theString.charAt(x++);
                if (aChar == '\\') {
                    aChar = theString.charAt(x++);
                    if (aChar == 'u') {
                        int value = 0;
                        for (int i = 0; i < 4; i++) {
                            aChar = theString.charAt(x++);
                            switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
                            }
                        }
                        outBuffer.append((char) value);
                    } else {
                        if (aChar == 't')
                            aChar = '\t';
                        else if (aChar == 'r')
                            aChar = '\r';
                        else if (aChar == 'n')
                            aChar = '\n';
                        else if (aChar == 'f')
                            aChar = '\f';
                        outBuffer.append(aChar);
                    }
                } else
                    outBuffer.append(aChar);
            }
            return outBuffer.toString();
        }

        private String saveConvert(String theString, boolean escapeSpace) {
            int len = theString.length();
            StringBuffer outBuffer = new StringBuffer(len * 2);

            for (int x = 0; x < len; x++) {
                char aChar = theString.charAt(x);
                switch (aChar) {
                case ' ':
                    if (x == 0 || escapeSpace)
                        outBuffer.append('\\');

                    outBuffer.append(' ');
                    break;
                case '\\':
                    outBuffer.append('\\');
                    outBuffer.append('\\');
                    break;
                case '\t':
                    outBuffer.append('\\');
                    outBuffer.append('t');
                    break;
                case '\n':
                    outBuffer.append('\\');
                    outBuffer.append('n');
                    break;
                case '\r':
                    outBuffer.append('\\');
                    outBuffer.append('r');
                    break;
                case '\f':
                    outBuffer.append('\\');
                    outBuffer.append('f');
                    break;
                default:
                    if ((aChar < 0x0020) || (aChar > 0x007e)) {
                        outBuffer.append('\\');
                        outBuffer.append('u');
                        outBuffer.append(toHex((aChar >> 12) & 0xF));
                        outBuffer.append(toHex((aChar >> 8) & 0xF));
                        outBuffer.append(toHex((aChar >> 4) & 0xF));
                        outBuffer.append(toHex(aChar & 0xF));
                    } else {
                        if (specialSaveChars.indexOf(aChar) != -1)
                            outBuffer.append('\\');
                        outBuffer.append(aChar);
                    }
                }
            }
            return outBuffer.toString();
        }

        public synchronized void store(OutputStream out, String header) throws IOException {
            BufferedWriter awriter;
            awriter = new BufferedWriter(new OutputStreamWriter(out, "GB2312"));
            if (header != null)
                writeln(awriter, "#" + header);
            writeln(awriter, "#" + new Date().toString());
            for (Enumeration e = keys(); e.hasMoreElements();) {
                String key = (String) e.nextElement();
                String val = (String) get(key);
                key = saveConvert(key, true);

                val = saveConvert(val, false);
                writeln(awriter, key + "=" + val);
            }
            awriter.flush();
        }

        private static void writeln(BufferedWriter bw, String s) throws IOException {
            bw.write(s);
            bw.newLine();
        }

        private static char toHex(int nibble) {
            return hexDigit[(nibble & 0xF)];
        }

        private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
                'D', 'E', 'F' };

    }
}