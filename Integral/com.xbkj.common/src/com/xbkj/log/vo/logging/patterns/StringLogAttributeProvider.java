/*
 * Created on 2005-2-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.xbkj.log.vo.logging.patterns;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * @author cc
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class StringLogAttributeProvider implements LogAttributeProvider {
    protected HashSet attrs = new HashSet();

    protected StringLogAttributeProvider() {
    }

    public StringLogAttributeProvider(String t) {
        HashSet hs = new HashSet();
        StringTokenizer stoken = new StringTokenizer(t, "%");

        for (; stoken.hasMoreTokens();)
            hs.add(stoken.nextToken().trim());

        for (Iterator itr = hs.iterator(); itr.hasNext();)
            addAttribute((String) itr.next());
    }

    private void addAttribute(String t) {
        // First char only
        switch (t.charAt(0)) {
        case 'd':
            attrs.add(new LogAttribute(LogAttribute.LOG_ATTR_TIMESTAMP, LogAttribute.LOG_ATTR_TIMESTAMP_VAL));
            break;
        case 't':
            attrs.add(new LogAttribute(LogAttribute.LOG_ATTR_THREAD, LogAttribute.LOG_ATTR_THREAD_VAL));
            break;
        case 'p':
            attrs.add(new LogAttribute(LogAttribute.LOG_ATTR_LEVEL, LogAttribute.LOG_ATTR_LEVEL_VAL));
            break;
        case 'c':
            attrs.add(new LogAttribute(LogAttribute.LOG_ATTR_MODULE, LogAttribute.LOG_ATTR_MODULE_VAL));
            break;
        case 'C':
            attrs.add(new LogAttribute(LogAttribute.LOG_ATTR_CLASS, LogAttribute.LOG_ATTR_CLASS_VAL));
            break;
        case 'M':
            attrs.add(new LogAttribute(LogAttribute.LOG_ATTR_METHOD, LogAttribute.LOG_ATTR_METHOD_VAL));
            break;
        case 'L':
            attrs.add(new LogAttribute(LogAttribute.LOG_ATTR_LINE, LogAttribute.LOG_ATTR_LINE_VAL));
            break;
        case 'F':
            attrs.add(new LogAttribute(LogAttribute.LOG_ATTR_FILE, LogAttribute.LOG_ATTR_FILE_VAL));
            break;
        case 'l':
            attrs.add(new LogAttribute(LogAttribute.LOG_ATTR_FLINE, LogAttribute.LOG_ATTR_FLINE_VAL));
            break;
        case 'r': //relative time
            attrs.add(new LogAttribute(LogAttribute.LOG_ATTR_ELAPSED, LogAttribute.LOG_ATTR_ELAPSED_VAL));
            break;
        case 'A':
            attrs.add(new LogAttribute(LogAttribute.LOG_ATTR_AMODULE, LogAttribute.LOG_AMODULE_VAL));
            break;
        case 'x':
            attrs.add(new LogAttribute(LogAttribute.LOG_ATTR_NDC, LogAttribute.LOG_NDC_VAL));
            break;
        case 'X':
            int beginIndex = t.indexOf('{');
            int endIndex = t.indexOf('}');
            if (beginIndex > 0 && endIndex > 0 && endIndex > (beginIndex + 1)) {
                String attrName = t.substring(beginIndex + 1, endIndex);
                attrs.add(new LogAttribute(attrName, "%X{" + attrName + "}"));
                beginIndex = -1;
                endIndex = -1;
            }
            break;

        default:
            break;
        }
    }

    public Set getAttributes() {
        return attrs;
    }
}