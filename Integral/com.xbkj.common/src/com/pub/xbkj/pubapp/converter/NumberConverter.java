package com.pub.xbkj.pubapp.converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.ParseException;

public class NumberConverter
  implements Converter
{
  private Number nullas;
  private final NumberFormat numberFormat;

  private NumberConverter(Number nullas, NumberFormat nf)
  {
    this.nullas = nullas;
    this.numberFormat = nf;
  }

  public NumberConverter() {
    this(Integer.valueOf(0), null);
  }

  public Object convert(Object from, Class<?> toClass) {
   
	 Number number;
	  if (from == null) {
      return this.nullas;
    }

    if (toClass.isInstance(from)) {
      return from;
    }

    String text = from.toString();
    if (this.numberFormat != null)
    {
      try {
        number = this.numberFormat.parse(text);
      }
      catch (ParseException e)
      {
       
        throw new ConvertException("Cannot convert [" + text + "] into value of class [" + 
          toClass.getName() + "] " + e.getMessage());
      }
   
      if (toClass.isInstance(number)) {
        return number;
      }
      if ((toClass.equals(Integer.class)) || (toClass.equals(Integer.TYPE)))
        return new Integer(number.intValue());
      if ((toClass.equals(Long.class)) || (toClass.equals(Long.TYPE)))
        return new Long(number.longValue());
      if ((toClass.equals(Float.class)) || (toClass.equals(Float.TYPE)))
        return new Float(number.floatValue());
      if ((toClass.equals(Double.class)) || (toClass.equals(Double.TYPE)))
        return new Double(number.doubleValue());
      if (toClass.equals(BigDecimal.class))
        return new BigDecimal(Double.toString(number.doubleValue()));
      if (toClass.equals(BigInteger.class))
        return BigInteger.valueOf(number.longValue());
      if ((toClass.equals(Short.class)) || (toClass.equals(Short.TYPE)))
        return new Short(number.shortValue());
      if ((toClass.equals(Byte.class)) || (toClass.equals(Byte.TYPE))) {
        return new Byte(number.byteValue());
      }
      throw new ConvertException("Cannot convert [" + text + "] into value of class [" + 
        toClass.getName() + "]");
    }

    if ((toClass.equals(Integer.class)) || (toClass.equals(Integer.TYPE)))
      return Integer.valueOf(text);
    if ((toClass.equals(Long.class)) || (toClass.equals(Long.TYPE)))
      return Long.valueOf(text);
    if ((toClass.equals(Float.class)) || (toClass.equals(Float.TYPE)))
      return Float.valueOf(text);
    if ((toClass.equals(Double.class)) || (toClass.equals(Double.TYPE)))
      return Double.valueOf(text);
    if (toClass.equals(BigDecimal.class))
      return new BigDecimal(text);
    if (toClass.equals(BigInteger.class))
      return new BigInteger(text);
    if ((toClass.equals(Short.class)) || (toClass.equals(Short.TYPE)))
      return Short.valueOf(text);
    if ((toClass.equals(Byte.class)) || (toClass.equals(Byte.TYPE))) {
      return Byte.valueOf(text);
    }
    throw new ConvertException("Cannot convert [" + text + "] to [" + toClass.getName() + "]");
  }
}