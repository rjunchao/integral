/*
 * Created on 2005-1-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.xbkj.log.vo.logging.patterns;

/**
 * @author cc
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LogAttribute {
    private final String name;

    private final String value;

    public static final String LOG_ATTR_THREAD = "Thd";

    public static final String LOG_ATTR_THREAD_VAL = "%t";

    public static final String LOG_ATTR_TIMESTAMP = "Ts";

    public static final String LOG_ATTR_TIMESTAMP_VAL = "%d{yyyy-MM-dd HH:mm:ss}";

    public static final String LOG_ATTR_LEVEL = "Lvl";

    public static final String LOG_ATTR_LEVEL_VAL = "%p";

    public static final String LOG_ATTR_MODULE = "Mod";

    public static final String LOG_ATTR_MODULE_VAL = "%c";

    public static final String LOG_ATTR_CLASS = "Clz";

    public static final String LOG_ATTR_CLASS_VAL = "%C";

    public static final String LOG_ATTR_METHOD = "Mthd";

    public static final String LOG_ATTR_METHOD_VAL = "%M";

    public static final String LOG_ATTR_LINE = "Mthd";

    public static final String LOG_ATTR_LINE_VAL = "%L";

    public static final String LOG_ATTR_FILE = "File";

    public static final String LOG_ATTR_FILE_VAL = "%F";

    public static final String LOG_ATTR_FLINE = "FLINE";

    public static final String LOG_ATTR_FLINE_VAL = "%l";

    public static final String LOG_ATTR_ELAPSED = "Elapsed";

    public static final String LOG_ATTR_ELAPSED_VAL = "%r";

    public static final String LOG_ATTR_NDC = "NDC";

    public static final String LOG_NDC_VAL = "%x";
    
    public static final String LOG_ATTR_AMODULE = "AModule";
    public static final String LOG_AMODULE_VAL = "%A";

    private LogAttribute() {
        name = "";
        value = "";
    }

    public LogAttribute(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof LogAttribute) {
            if (!(((LogAttribute) anObject).getName().equals(this.name)))
                return false;
            if (!(((LogAttribute) anObject).getValue().equals(this.value)))
                return false;
            return true;
        }

        return super.equals(anObject);
    }
}