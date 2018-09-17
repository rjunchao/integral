package com.xbkj.log.bs.logging;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author �ι���
 *
 * Date: 2006-8-30
 * Time: ����06:42:33
 */
public class LogMessageBuffer implements Serializable {

    private static final long serialVersionUID = -7004711312385241265L;

    private int bufferSize;

    private transient boolean enabled;
    
    private transient boolean enableDetailTrace = false;;

    private LinkedList<LogMessage> bufferList;

    private static HashMap<Object, LogMessageBuffer> bufferMap = new HashMap<Object, LogMessageBuffer>();

    public LogMessageBuffer() {
        this(100);
    }

    public LogMessageBuffer(int bufferSize) {
        this.bufferSize = bufferSize;
        bufferList = new LinkedList<LogMessage>();
    }

    public synchronized void addMessage(LogMessage message) {
        if (bufferSize <= 0)
            return;

        // remove the oldest element if the buffer is to capacity.  
        if (bufferList.size() == bufferSize)
            bufferList.removeFirst();

        bufferList.addLast(message);
    }

    public synchronized LogMessage[] getMessages() {
        return bufferList.toArray(new LogMessage[bufferList.size()]);
    }

    public synchronized void reset() {
        bufferList = new LinkedList<LogMessage>();
    }

    public synchronized int getBufferSize() {
        return bufferSize;
    }

    public synchronized void setBufferSize(int newBufferSize) {

        if (bufferSize > newBufferSize)
            bufferList = reduceBuffer(newBufferSize);

        bufferSize = newBufferSize;

    }

    private LinkedList<LogMessage> reduceBuffer(int newSize) {
        LinkedList<LogMessage> newBuffer = new LinkedList<LogMessage>();

        Iterator<LogMessage> iter = bufferList.iterator();
        int i = 0;

        int startPos = bufferList.size() - newSize;

        while (iter.hasNext()) {
            LogMessage message = iter.next();
            if (i < startPos) {
                i++;

                continue;
            }
            newBuffer.add(message);
        }

        return newBuffer;
    }

    public synchronized int getMessageCount() {
        return bufferList.size();
    }

    public synchronized void enable() {
        this.enabled = true;
    }

    public synchronized void disable() {
        this.enabled = false;

    }

    public synchronized boolean isEnabled() {
        return enabled;
    }

    /**
     * name is a module, if the module's log is not existed, null return
     * @param name
     * @return
     */
    public static synchronized LogMessageBuffer getLogMessageBuffer(String name) {
        String module = LoggerPluginProvider.getInstance().getLoggerPlugin(name).getModuleName();
        if(module == null)
            module = LoggerGeneralConfig.ANONY_MODULE;
        return bufferMap.get(module);
    }
    
    public static synchronized LogMessageBuffer getInternalLogMessageBuffer(String name) {
        return bufferMap.get(name);
    }

    /**
     * don't call it
     * 
     * @param obj
     * @param buffer
     */
    public static void addLogMessageBuffer(Object obj, LogMessageBuffer buffer) {

        bufferMap.put(obj, buffer);
    }
    
    
    public synchronized boolean isEnableDetailTrace() {
        return this.enableDetailTrace;
    }
    
    public synchronized void setEnableDetailTrace(boolean value) {
        this.enableDetailTrace = value;
    }

}
