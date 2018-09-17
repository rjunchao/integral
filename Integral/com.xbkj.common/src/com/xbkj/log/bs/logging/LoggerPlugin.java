package com.xbkj.log.bs.logging;

import java.util.ResourceBundle;

import com.xbkj.log.vo.logging.ModuleLoggerConfiguration;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: ${DATE}$
 * Time: ${TIME}$
 * <p/>
 * ��־�������������ײ����־ϵͳ��һ��һ��ģ���Ӧһ��
 */
public interface LoggerPlugin {

	/**
	 * ���ָ���������־�Ƿ�����
	 *
	 * @param level
	 */
	public boolean isEnabled(Level level);

	/**
	 * ���ռ�����־��Ϣ
	 *
	 * @param level
	 * @param msg
	 * @param throwable
	 */
	public void log(Level level, Object msg, Throwable throwable);

	/**
	 * Ϊ�����Ч�ʶ����룬��ȷ�ļ�¼������־����ͷ���
	 *
	 * @param level
	 * @param msg
	 * @param clazz
	 * @param methodName
	 * @param throwable
	 */
	public void log(Level level, Object msg, Throwable throwable, Class clazz, String methodName);

	/**
	 * ���ռ�����־��Ϣ
	 *
	 * @param level
	 * @param msg
	 */
	public void log(Level level, Object msg);

	/**
	 * Ϊ�����Ч�ʶ����룬��ȷ�ļ�¼������־����ͷ���
	 *
	 * @param level
	 * @param msg
	 * @param clazz
	 * @param methodName
	 */
	public void log(Level level, Object msg, Class clazz, String methodName);

	/**
	 * ��ȡһ��MDC,  MDCΪ�̼߳���Ķ�̬��Ϣ����
	 *
	 * @return MDC
	 */
	public MDC getMDC();

	/**
	 * ��ȡһ��NDC,  NDCΪ�̼߳���Ŀ��ƣ�
	 *
	 * @return NDC
	 */
	public NDC getNDC();

	/**
	 * it is dynamic behavior,not persist, if you want to persist, use config
	 * 
	 * @param level
	 */
	public void setLevel(Level level);

	/**
	 * ����LoggerPluginΪ��̬���������Ľӿ�
	 * @param config
	 */
	public void config(ModuleLoggerConfiguration config, LoggerPlugin parent);

	public String getModuleName();

	public ResourceBundle getResourceBundle();
	
	public void setResourceBundle(ResourceBundle rb);

}
