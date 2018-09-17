package com.xbkj.log.bs.logging;

import java.io.PrintStream;

import com.xbkj.log.vo.logging.ModuleLoggerConfiguration;
/**
 * System.out,System.err�İ�װ�ࡣ���ڽ������е�System.out,System.err�����NC Log������
 * @author zhaopq
 * Date:2008-8-19
 */
public class NCSysOutWrapper extends PrintStream{
	
	/**JVMĬ�ϵĿ���̨���PrintSteam*/
	private PrintStream sysStream;
	
	/**true���debug  false���error*/
	private boolean level;
	
	public static Boolean useNCLog;
    
    public static final String MODULE_NAME = "sysout";
	
    /**
     * @param sysStream
     * @param level      true ���debug  false ���error
     */
	public NCSysOutWrapper(PrintStream sysStream,boolean level) {
		super(sysStream);
		this.sysStream = sysStream;
		this.level = level;
	} 
	
	private void print0(Object o){
		sysStream.print(o);		
		
		if(shouldUseNCLog())
			writeByNCLog(String.valueOf(o));
	}
	
	private void println0(Object o){
		if(shouldUseNCLog())
			writeByNCLog(String.valueOf(o));
		
		sysStream.println(o);
	}
	
	private void writeByNCLog(String msg){
		if(level)
			Log.getInstance(MODULE_NAME).debug(msg);
		else
			Log.getInstance(MODULE_NAME).error(msg); 
	}
	
	private boolean shouldUseNCLog(){
		if(useNCLog==null){
			synchronized (NCSysOutWrapper.class) {
				if(useNCLog!=null){
					return useNCLog;
				}
				ModuleLoggerConfiguration config = LoggerPluginProvider.getLoggerConfigManager()
								.getModuleLoggerConfiguration(MODULE_NAME);
				if(config==null||config.getLogFile()==null){
					useNCLog = false;
				}else{
					useNCLog = true;
				}
			}
		}
		return useNCLog;
	}
	
	public boolean getLevel() {
		return level;
	}
	
	@Override
	public void print(boolean b) {
		print0(b);		
	}

	@Override
	public void print(char c) {
		print0(c);
	}

	@Override
	public void print(char[] s) {
		print0(s);
	}

	@Override
	public void print(double d) {
		print0(d);
	}

	@Override
	public void print(float f) {
		print0(f);
	}

	@Override
	public void print(int i) {
		print0(i);
	}

	@Override
	public void print(long l) {
		print0(l);
	}

	@Override
	public void print(String s) {
		print0((Object)s);
	}
	
	@Override
	public void print(Object s) {
		print0(s);
	}

	@Override
	public void println() {
		print0("\r\n");
	}

	@Override
	public void println(boolean x) {
		println0(x);
	}

	@Override
	public void println(char x) {
		println0(x);
	}

	@Override
	public void println(char[] x) {
		println0(x);
	}

	@Override
	public void println(double x) {
		println0(x);
	}

	@Override
	public void println(float x) {
		println0(x);
	}

	@Override
	public void println(int x) {
		println0(x);
	}

	@Override
	public void println(long x) {
		println0(x);
	}

	@Override
	public void println(String x) {
		println0((Object)x);
	}
	
	@Override
	public void println(Object x) {
		println0(x);
	}

	/**����JVMĬ�ϵĿ���̨���PrintSteam*/
	public PrintStream getSysStream() {
		return sysStream;
	}

	public void setSysStream(PrintStream sysStream) {
		this.sysStream = sysStream;
	}
		

}
