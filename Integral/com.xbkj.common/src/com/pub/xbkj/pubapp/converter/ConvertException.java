package com.pub.xbkj.pubapp.converter;

public class ConvertException extends RuntimeException
{
  private static final long serialVersionUID = 2353394826950404244L;

  public ConvertException()
  {
  }

  public ConvertException(String message, Throwable cause)
  {
    super(message, cause);
  }

  public ConvertException(String message)
  {
    super(message);
  }

  public ConvertException(Throwable cause)
  {
    super(cause);
  }
}