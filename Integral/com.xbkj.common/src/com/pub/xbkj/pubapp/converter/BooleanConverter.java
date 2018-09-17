package com.pub.xbkj.pubapp.converter;

public class BooleanConverter
  implements Converter
{
  private Boolean nullas;

  public BooleanConverter()
  {
    this(null);
  }

  public BooleanConverter(Boolean nullas) {
    this.nullas = nullas;
  }

  public Object convert(Object value, Class<?> toClass) {
    if (value == null) {
      if (this.nullas != null) {
        return this.nullas;
      }
      throw new ConvertException("Null semantic not supported for convert null value");
    }

    if ((value instanceof Boolean)) {
      return value;
    }

    String stringValue = value.toString();

    if ((stringValue.equalsIgnoreCase("yes")) || (stringValue.equalsIgnoreCase("y")) || 
      (stringValue.equalsIgnoreCase("true")) || (stringValue.equalsIgnoreCase("on")) || 
      (stringValue.equalsIgnoreCase("1")))
      return Boolean.TRUE;
    if ((stringValue.equalsIgnoreCase("no")) || (stringValue.equalsIgnoreCase("n")) || 
      (stringValue.equalsIgnoreCase("false")) || (stringValue.equalsIgnoreCase("off")) || 
      (stringValue.equalsIgnoreCase("0"))) {
      return Boolean.FALSE;
    }
    if (this.nullas != null) {
      return Boolean.valueOf(!this.nullas.booleanValue());
    }
    throw new ConvertException("Null semantic not supported for convert non-null value");
  }
}