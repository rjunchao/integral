package com.pub.xbkj.pubapp.converter;

public class ClassConverter
  implements Converter
{
  private ClassLoader loader;

  public ClassConverter()
  {
    this(null);
  }

  public ClassConverter(ClassLoader loader) {
    this.loader = loader;
  }

  public Object convert(Object from, Class<?> toClass)
  {
    if ((from instanceof Class)) {
      return from;
    }
    try
    {
      String className = from.toString();
      ClassLoader l = this.loader;
      if (l == null) {
        l = Thread.currentThread().getContextClassLoader();
        if (l == null) {
          l = ClassConverter.class.getClassLoader();
        }
      }
      return l.loadClass(className);
    } catch (Exception e) {
    	 throw new ConvertException(e.getMessage(), e);
    }
   
  }
}