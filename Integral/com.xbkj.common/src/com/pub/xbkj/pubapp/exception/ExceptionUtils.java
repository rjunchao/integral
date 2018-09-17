package com.pub.xbkj.pubapp.exception;


import nc.vo.pub.BusinessRuntimeException;

import com.eos.runtime.core.TraceLoggerFactory;
import com.eos.system.logging.Logger;
import com.xbkj.basic.vo.jcom.lang.StringUtil;
import com.xbkj.basic.vo.pub.BusinessException;

/**
 * 异常工具类
 * @author lijbe
 *
 */
public class ExceptionUtils {
	
	private static Logger log = TraceLoggerFactory
			.getLogger(ExceptionUtils.class);
  
 private ExceptionUtils() {
    // 缺省构造方法
  }



  /**
   * 在EJB边界对所有的异常进行BusinessException包装
   * 
   * @param ex 要处理的异常
   * @throws BusinessException 要抛出EJB的异常
   */
  public static void marsh(Exception ex) throws BusinessException {
    Throwable cause = ExceptionUtils.unmarsh(ex);
    log.error(cause);

    if (cause instanceof TransferException) {
      throw (TransferException) cause;
    }
    else if (cause instanceof BusinessException) {
      throw (BusinessException) cause;
    }
    else if (cause instanceof BusinessRuntimeException) {
      throw new BusinessException(cause);
    }
    else {
      TransferException tex = new TransferException(cause);
      throw tex;
    }
  }

  /**
   * 抛出未实现异常
   */
  public static void notImplement() {
    String message = "还没有实现此功能"; /*-=notranslate=-*/
    PubAppBusinessException ex = new PubAppBusinessException(message);
    log.error(ex);

    throw new CarrierRuntimeException(ex);
  }

  /**
   * 将最底层的异常解析出来
   * 
   * @param ex 要处理的异养
   * @return 最底层的异常
   */
  public static Throwable unmarsh(Throwable ex) {
    Throwable cause = ex.getCause();
    if (cause != null) {
      cause = ExceptionUtils.unmarsh(cause);
    }
    else {
      cause = ex;
    }
    return cause;
  }
   
    /**
     * 抛出不支持异常
     */
    public static void unSupported() {
      String message = "不支持此种业务，请检查"; /*-=notranslate=-*/
      PubAppBusinessException ex = new PubAppBusinessException(message);
      log.error(ex);

      throw new CarrierRuntimeException(ex);
    }
    
    /**
     * 抛出不支持异常
     */
    public static void unSupported(String message) {
     
    	if(StringUtil.isEmpty(message)){
    		 message = "不支持此种业务，请检查"; 
    	}
      PubAppBusinessException ex = new PubAppBusinessException(message);
      log.error(ex);

      throw new CarrierRuntimeException(ex);
    }

    /**
     * 抛出业务异常
     * 
     * @param message 异常信息
     */
    public static void wrappBusinessException(String message) {
      PubAppBusinessException ex = new PubAppBusinessException(message);
      log.error(ex);

      throw new CarrierRuntimeException(ex);
    }
  /**
   * 抛出业务异常
   * 
   * @param message 异常信息
   * @param location 出现异常的位置
   */
  public static void wrappBusinessException(String message, String location) {
    PubAppBusinessException ex = new PubAppBusinessException(message, location);
    log.error(ex);

    throw new CarrierRuntimeException(ex);
  }

  /**
   * 抛出业务异常。将非BusinessException的异常转义为BusinessException<br>
   * 一定要慎重使用
   * 
   * @param message 异常信息
   * @param location 出现异常的位置
   * @param ex 原始异常，它不能是BusinessException，因为牵涉到异常的转义
   * @deprecated 此方法有可能导致异常转义，尽量不要使用
   */
  @Deprecated
  public static void wrappBusinessException(String message, String location,
      Throwable ex) {
    Throwable cause = ExceptionUtils.unmarsh(ex);

    // 如果是BusinessException，根本就不用调用这个方法，有对异常转义的嫌疑。但是。。。广大的蚂蚁同志啊。
    if (cause instanceof BusinessException) {
      log.error(message + location);
      throw new CarrierRuntimeException((BusinessException) cause);
    }
    PubAppBusinessException exception =
        new PubAppBusinessException(message, location, cause);
    log.error(exception);

    throw new CarrierRuntimeException(exception);
  }

//  /**
//   * 抛出业务异常。将非BusinessException的异常转义为BusinessException<br>
//   * 一定要慎重使用
//   * 
//   * @param message 异常信息
//   * @param ex 原始异常，它不能是BusinessException，因为牵涉到异常的转义
//   * @deprecated 此方法有可能导致异常转义，尽量不要使用
//   */
//  @Deprecated
//  public static void wrappBusinessException(String message, Throwable ex) {
//    Throwable cause = ExceptionUtils.unmarsh(ex);
//
//    // 如果是BusinessException，根本就不用调用这个方法，有对异常转义的嫌疑。但是。。。广大的蚂蚁同志啊。
//    if (cause instanceof BusinessException) {
//      log.error(message);
//      throw new CarrierRuntimeException((BusinessException) cause);
//    }
//    PubAppBusinessException exception =
//        new PubAppBusinessException(message, cause);
//    log.error(exception);
//
//    throw new CarrierRuntimeException(exception);
//  }

  /**
   * 将异常装载到快速异常通道中向上传递
   * 
   * @param ex 要装载的异常
   */
  public static void wrappException(Exception ex) {
    log.error(ex);

    throw new CarrierRuntimeException(ex);
  }

}
