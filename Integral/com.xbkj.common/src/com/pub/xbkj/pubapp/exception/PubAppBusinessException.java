package com.pub.xbkj.pubapp.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.xbkj.basic.vo.pub.BusinessException;

/**
 * 通用框架业务异常。 之所以增加stackTrace变量，就是防止在异常传播到前台的时候，由于缺少相应的异常堆栈 中的
 * class，导致class not found的错误。而我们系统能够在前台打印相关的异常堆栈， 对于系统的可维护性有很大
 * 的提高。因为某些项目出错的时候，是根本不会给你上传环境的。在集群的情况下，后台日志众多，要前方实施找到准确的
 * 日志文件传回来，就目前看来，这 也是对他们的一个严厉的挑战。
 * 
 */
public class PubAppBusinessException extends BusinessException {
  private static final long serialVersionUID = -5506475841265255052L;

  /**
   * 出现异常的额外业务定位信息（例如当前单据的单据号、物料编码、名称等等）
   */
  private final String location;

  /**
   * 底层异常堆栈字符串
   */
  private final String stackTrace;

  /**
   * 通用业务异常的构造函数
   * 
   * @param message 业务异常信息
   */
  public PubAppBusinessException(String message) {
    super(message);
    this.location = null;
    this.stackTrace = null;
  }

  /**
   * 通用业务异常的构造函数
   * 
   * @param message 业务异常信息
   * @param location 出现异常的额外业务定位信息
   */
  public PubAppBusinessException(String message, String location) {
    super(message);
    this.location = location;
    this.stackTrace = null;
  }

  /**
   * 通用业务异常的构造函数
   * 
   * @param message 业务异常信息
   * @param location 出现异常的额外业务定位信息
   * @param ex 底层业务异常
   */
  public PubAppBusinessException(String message, String location, Throwable ex) {
    super(message);
    this.location = location;
    this.stackTrace = this.getStackTrace(ex);
  }

  /**
   * 通用业务异常的构造函数
   * 
   * @param message 业务异常信息
   * @param ex 底层业务异常
   */
  public PubAppBusinessException(String message, Throwable ex) {
    super(message);
    this.stackTrace = this.getStackTrace(ex);
    this.location = null;
  }

  /**
   * 获取额外业务定位信息
   * 
   * @return 额外业务定位信息
   */
  public String getLocation() {
    return this.location;
  }

  private String getStackTrace(Throwable ex) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw, true);
    ex.printStackTrace(pw);
    return sw.getBuffer().toString();
  }

  @Override
  public void printStackTrace() {
    if (this.stackTrace != null) {
      this.printStackTrace(System.err);
    }
    else {
      super.printStackTrace();
    }
  }

  @Override
  public void printStackTrace(PrintStream s) {
    if (this.stackTrace != null) {
      synchronized (s) {
        s.print(this.stackTrace);
      }
    }
    else {
      super.printStackTrace(s);
    }
  }

  @Override
  public void printStackTrace(PrintWriter s) {
    if (this.stackTrace != null) {
      synchronized (s) {
        s.print(this.stackTrace);
      }
    }
    else {
      super.printStackTrace(s);
    }
  }

}
