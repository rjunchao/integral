package com.pub.xbkj.pubapp.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import com.xbkj.basic.vo.pub.BusinessException;

/**
 * 传递性异常．将后台的异常堆栈传递到前台，而不至于造成后台的异常直接传递到前台后，由于前台
 * 没有相关的异常类，而导致反序列化失败
 * 
 */
public class TransferException extends BusinessException {
  private static final long serialVersionUID = -1175141188691689620L;

  /**
   * 在异常处理过程中需要额外增加的异常信息
   */
  private final List<String> messageList = new ArrayList<String>();

  /**
   * 底层异常的堆栈字符串
   */
  private final String stackTrace;

  /**
   * 传递性异常构造函数
   * 
   * @param exception 底层异常
   */
  public TransferException(Throwable exception) {
    super(exception.getMessage());
    this.stackTrace = this.getStackTrace(exception);
  }

  /**
   * 需要增加的额外的异常信息
   * 
   * @param message 额外的异常信息
   */
  public void addMessage(String message) {
    this.messageList.add(message);
  }

  /**
   * 获取底层异常的堆栈字符串
   * 
   * @return 底层异常的堆栈字符串
   */
  public String getInnerStackTrace() {
    return this.stackTrace;
  }

  /**
   * 获取额外的异常信息
   * 
   * @return 额外的异常信息列表
   */
  public List<String> getMessageList() {
    return this.messageList;
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
