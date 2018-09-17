package com.xbkj.log.bs.logging.impl.log4j;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.helpers.FormattingInfo;
import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.helpers.PatternParser;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;

import com.xbkj.log.bs.logging.LoggerGeneralConfig;
import com.xbkj.log.bs.logging.LoggerMDC;
import com.xbkj.log.vo.logging.message.ILogMessage;
import com.xbkj.log.vo.logging.message.XmlCharConverter;
import com.xbkj.log.vo.logging.patterns.LogPattern;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: 2004-11-18
 * Time: 16:51:02
 * <p/>
 * <p/>
 * ��־��ʽ��,��Ҫ���Log4J�еĵ����Ƶ��㷨�����еĸ�ʽ�ַ������µĹ������
 * <p/>
 * ��ʽ�ַ���%��Ϊת���
 * c	���ģ�����
 * d	������ڣ���SimpleDateFormatter��ͬʹ����ͬ�����ڸ�ʽ
 * m	�����־��Ϣ
 * n	�������
 * p	������Լ���
 * x	���NDC
 * X	���MDC������%X{mdcname}
 * C	������־�������
 * %	���%
 * t	������־���߳�
 * M	�����־����
 * L	��־���ڵ��� (��Ҫ�����-g ѡ��)
 */
public class Log4jPatternFormatter extends org.apache.log4j.PatternLayout {

    /**
     The XMLLayout prints and does not ignore exceptions. Hence the
     return value <code>false</code>.
     */
    public boolean ignoresThrowable() {
        return false;

    }

    /**
     * ���캯���ʽΪpattern����ʽ�ַ���%��Ϊת���
     * c	���ģ�����
     * d	������ڣ���SimpleDateFormatter��ͬʹ����ͬ�����ڸ�ʽ
     * m	�����־��Ϣ
     * n	�������
     * p	������Լ���
     * x	���NDC
     * X	���MDC������%X{mdcname}
     * C	������־�������
     * %	���%
     * t	������־���߳�
     * M	�����־����
     * L	��־���ڵ��� (��Ҫ�����-g ѡ��)
     *
     * @param pattern
     */
    public Log4jPatternFormatter(String pattern) {
        super(pattern);
    }

    protected PatternParser createPatternParser(String pattern) {
        return new PatternParserEx(pattern);
    }

    public class PatternParserEx extends PatternParser {
        public PatternParserEx(String pattern) {
            super(pattern);
        }

        String[] cml = null;

        protected void finalizeConverter(char c) {
            PatternConverter pc = null;
            switch (c) {
            case 'm':
                if (LoggerGeneralConfig.isFormatAsXml()) {
                    pc = new XMLMessageConverter(formattingInfo);
                    currentLiteral.setLength(0);
                } else {
                    pc = new MessageConverter(formattingInfo);
                    currentLiteral.setLength(0);
                }
                break;
            case 'x':
                pc = new NDCConverter(formattingInfo);
                currentLiteral.setLength(0);
                break;
            case 'X':
                String key = extractOption();
                pc = new MDCConverter(formattingInfo, key);
                currentLiteral.setLength(0);
                break;
            case 'C':
                pc = new InferConvert(0);
                currentLiteral.setLength(0);
                break;
            case 'M':
                pc = new InferConvert(1);
                currentLiteral.setLength(0);
                break;
            case 'L':
                pc = new InferConvert(2);
                currentLiteral.setLength(0);
                break;
            case 'T':
                pc = new LogTypeConvert();
                currentLiteral.setLength(0);
                break;
            case 'A':
                pc = new ModuleAncestorConvert();
                currentLiteral.setLength(0);
                break;
            default:
                super.finalizeConverter(c);
                return;
            }
            addConverter(pc);
        }
    }

    public class ModuleAncestorConvert extends PatternConverter {
        protected String convert(LoggingEvent event) {
            Log4jLoggingEvent logEvent = (Log4jLoggingEvent) event;
            String moduleName = logEvent.getModuleName();

            if (moduleName == null || "anonymous".equals(moduleName))
                moduleName = event.getLoggerName();
            return moduleName;

        }
    }

    class LogTypeConvert extends PatternConverter {
        protected String convert(LoggingEvent event) {
            Log4jLoggingEvent logEvent = (Log4jLoggingEvent) event;
            String logType = logEvent.getLogType();
            if (logType == null)
                logType = LoggerGeneralConfig.LOG_TYPE_PUB;
            return logType;

        }
    }

    class MessageConverter extends PatternConverter {
        public MessageConverter(FormattingInfo formattingInfo) {
            super(formattingInfo);
        }

        protected String convert(LoggingEvent event) {

            Object realMsg = event.getMessage();

            if (realMsg instanceof ILogMessage) {
                return getRenderedMsg(LoggerGeneralConfig.LINE_SEP + realMsg, event);
            } else {
                return getRenderedMsg("" + realMsg, event);
            }

        }
    }

    /**
     * MDCת��
     */
    class MDCConverter extends PatternConverter {
        private String key;

        public MDCConverter(FormattingInfo formattingInfo, String key) {
            super(formattingInfo);
            this.key = key;
        }

        protected String convert(LoggingEvent loggingEvent) {
            Object val = LoggerMDC.mdc.get(key);
            String strVal = null;
            if (val != null)
                strVal = val.toString();
            return strVal;
        }

    }

    /**
     * NDC��ʽת��
     */
    class NDCConverter extends PatternConverter {
        public NDCConverter(FormattingInfo formattingInfo) {
            super(formattingInfo);
        }

        protected String convert(LoggingEvent loggingEvent) {
            Object val = Log4jNDC.ndc.get();
            String strVal = null;
            if (val != null)
                strVal = val.toString();
            return strVal;
        }

    }

    public class XMLMessageConverter extends PatternConverter {
        public XMLMessageConverter(FormattingInfo formattingInfo) {
            super(formattingInfo);
        }

        protected String convert(LoggingEvent event) {
            Object message = event.getMessage();
            Object realMessage = message;

            StringBuffer sb = new StringBuffer();
            if (realMessage instanceof ILogMessage) {
                sb.append(((ILogMessage) realMessage).toXMLString());
            } else {
                sb.append(event.getRenderedMessage());
            }
            String[] s = event.getThrowableStrRep();

            if (s != null) {
                // <Throws>
                sb.append(LogPattern.XML_ENDL);
                sb.append(LogPattern.XML_TAB);
                sb.append(LogPattern.XML_TAG_BEGIN);
                sb.append(LogPattern.XML_TAG_THROWABLE);
                sb.append(LogPattern.XML_TAG_END);
                sb.append(LogPattern.XML_ENDL);
                sb.append(XmlCharConverter.getXMLString(s));
                // </Throws>
                sb.append(LogPattern.XML_TAB);
                sb.append(LogPattern.XML_TAG_BEGIN);
                sb.append(LogPattern.XML_SLASH);
                sb.append(LogPattern.XML_TAG_THROWABLE);
                sb.append(LogPattern.XML_TAG_END);
            }
            return sb.toString();

        }
    }

    /**
     * �Ƶ��ĸ�ʽת��
     */
    private class InferConvert extends PatternConverter {
        private int type;

        public InferConvert(int type) {
            this.type = type;
        }

        protected String convert(LoggingEvent event) {
            //�������ܶ��

            String retValue = null;
            Log4jLoggingEvent logEvent = (Log4jLoggingEvent) event;

            if (type == 0) {
                retValue = logEvent.getClassName();
            } else if (type == 1) {
                retValue = logEvent.getMethodName();
            }

            if (retValue == null && logEvent.getClassName() == null && logEvent.getMethodName() == null) {
                LocationInfo locationInfo = event.getLocationInformation();
                switch (type) {
                case 0:
                    retValue = locationInfo.getClassName();
                    break;
                case 1:
                    retValue = locationInfo.getMethodName();
                    break;
                case 2:
                    retValue = locationInfo.getLineNumber();
                    break;
                default:
                    retValue = "?";

                }
            }

            if (retValue == null)
                retValue = "?";
            return retValue;

        }
    }

    private String getRenderedMsg(String msg, LoggingEvent event) {
        StringBuffer sb = new StringBuffer(msg == null ? "" : msg);

        if (event.getThrowableInformation() != null) {
            Throwable thr = event.getThrowableInformation().getThrowable();
            if (thr != null) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                pw.println();
                StackTraceUtil.printStackTrace(pw, thr);
                sb.append(sw);
            }
        }

        return sb.toString();
    }

}