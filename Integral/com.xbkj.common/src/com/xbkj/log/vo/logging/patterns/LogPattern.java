/*
 * Created on 2005-1-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.xbkj.log.vo.logging.patterns;

import java.util.Iterator;

/**
 * @author cc
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LogPattern {
    protected String logTag;

    protected LogAttributeProvider lap;

    public static final String LOG_MSG = "%m";

    public static final String LOG_ENDL = "%n";

    public static final String XML_TAG_MSG = "Msg";

    public static final String XML_TAG_THROWABLE = "Throws";

    public static final String XML_TAB = "    ";

    public static final String XML_TAG_BEGIN = "<";

    public static final String XML_TAG_END = ">";

    public static final String XML_SPACE = " ";

    public static final String XML_EQUAL = "=";

    public static final String XML_SLASH = "/";

    public static final String XML_QUOT = "\"";

    public static final String XML_ENDL = System.getProperty("line.separator");;

    protected StringBuffer buf = new StringBuffer();

    private boolean formatted = false;

    private LogPattern() {
    }

   

    public static String createLogPattern() {
        return createLogPattern(null);
    }

    public static String createLogPattern(String t) {
        if (t == null || "".equals(t.trim()))
            return (new LogPattern("%T", new DefaultLogAttributeProvider())).getLogPattern();

        return (new LogPattern("%T", new StringLogAttributeProvider(t))).getLogPattern();
    }


    protected LogPattern(String tag, LogAttributeProvider lap) {
        this.logTag = tag;
        this.lap = lap;
    }

    protected String getLogPattern() {
        if (!formatted)
            format();
        return buf.toString();
    }

    private synchronized final void format() {
        writeAttributes();
        writeMessage();
        writeEndTag();
        formatted = true;
    }

    private final void writeAttributes() {
        buf.append(XML_TAG_BEGIN);
        buf.append(logTag);
        for (Iterator itr = lap.getAttributes().iterator(); itr.hasNext();) {
            LogAttribute attr = (LogAttribute) itr.next();
            // write ---- attrname="attrval"  ---- to buf
            buf.append(XML_SPACE);
            buf.append(attr.getName());
            buf.append(XML_EQUAL);
            buf.append(XML_QUOT);
            buf.append(attr.getValue());
            buf.append(XML_QUOT);
        }
        buf.append(XML_TAG_END);
        buf.append(XML_ENDL);
    }

    private final void writeMessage() {
        buf.append(LOG_MSG);
    }

    private final void writeEndTag() {
        buf.append(XML_ENDL);
        buf.append(XML_TAG_BEGIN);
        buf.append(XML_SLASH);
        buf.append(logTag);
        buf.append(XML_TAG_END);
        buf.append(LOG_ENDL);
    }
}