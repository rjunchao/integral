package com.pub.xbkj.pubapp.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.eos.runtime.core.TraceLoggerFactory;
import com.eos.system.logging.Logger;

public class EsaConverterManager extends ConverterManager
{
 private static Logger logger = TraceLoggerFactory.getLogger(EsaConverterManager.class);
	  	
  public EsaConverterManager()
  {
    this(Lazy.lazy.def);
  }

  public EsaConverterManager(IConverterManager parent) {
    super(parent);
  }

  private static class Lazy
  {
    static Lazy lazy = new Lazy();
    private final ConverterManager def;

    Lazy()
    {
      this.def = new ConverterManager();
     
      this.def.registerConverter(Character.class, new Converter() {
        public Object convert(Object from, Class<?> toClass) {
          if ((from == null) || (from.toString() == null) || (from.toString().length() < 1))
          {
            return null;
          }
          return Character.valueOf(from.toString().charAt(0));
        }
      });
      this.def.registerConverter(Long.class, new Converter() {
        public Object convert(Object from, Class<?> toClass) {
          if ((from == null) || (from.toString() == null) || (from.toString().length() < 1))
          {
            return null;
          }if ((from instanceof Long))
            return from;
          if ((from instanceof Number)) {
            return Long.valueOf(((Number)from).longValue());
          }
          String value = from.toString();
          if (value.indexOf(".") > -1)
            value = value.substring(0, value.indexOf("."));
          try
          {
            return new Long(value);
          } catch (Exception e) {
        	  logger.error(e);
          }
          return null;
        }
      });
      this.def.registerConverter(Integer.class, new Converter() {
        public Object convert(Object from, Class<?> toClass) {
          if ((from == null) || (from.toString() == null) || (from.toString().length() < 1))
          {
            return null;
          }if ((from instanceof Integer))
            return from;
          if ((from instanceof Number)) {
            return Integer.valueOf(((Number)from).intValue());
          }
          String value = from.toString();
          if (value.indexOf(".") > -1)
            value = value.substring(0, value.indexOf("."));
          try
          {
            return new Integer(value);
          } catch (Exception e) {
        	  logger.error(e);
          }
          return null;
        }
      });
      this.def.registerConverter(Date.class, new Converter()
      {
        public Object convert(Object from, Class<?> toClass) {
          if ((from == null) || ((from instanceof Date)))
            return from;
          if ((from instanceof Number))
            return new Date(((Number)from).longValue());
          if ((from instanceof Calendar))
            return ((Calendar)from).getTime();
          String str = from.toString().trim();
          if ("".equals(str))
            return null;
          try
          {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            return dateFormat.parse(str);
          } catch (Exception e) {
        	  logger.error(e);
          }
          return null;
        }
      });
    }
  }
}