package com.pub.xbkj.pubapp.converter;

import java.beans.PropertyEditorManager;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class ConverterManager
  implements IConverterManager
{
  private Map<Class<?>, Converter> converterMap;
  protected IConverterManager parent;

  public ConverterManager(IConverterManager parent)
  {
    if (parent == null)
      this.parent = Lazy.lazy.def;
    else {
      this.parent = parent;
    }
    this.converterMap = new HashMap();
  }

  public ConverterManager() {
    this(null);
  }

  public void removeConverter(Class<?> toClass) {
    this.converterMap.remove(toClass);
  }

  public void registerConverter(Class<?> t, Converter c) {
    this.converterMap.put(t, c);
  }

  public void registerConverter(Class<?> toClass, Class<? extends Converter> convertClass)
  {
	 Constructor<? extends Converter> constructor;
    try
    {
      constructor = convertClass.getConstructor(new Class[0]);
    }
    catch (SecurityException e)
    {
     
      throw new RuntimeException("regist converter error for " + convertClass.getName(), e);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException("regist converter error because no appropriate constructor for " + 
        convertClass.getName(), e);
    }
  
    Converter c = null;
    try {
      c = (Converter)constructor.newInstance(new Object[0]);
      this.converterMap.put(toClass, c);
    } catch (InstantiationException e) {
      throw new RuntimeException("instantiate conver error for " + convertClass.getName(), e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException("instantiate conver error for " + convertClass.getName(), e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException("instantiate conver error for " + convertClass.getName(), e.getCause());
    }
  }

  public Converter findConverter(Class<?> clazz)
  {
    Converter c = findConverter(clazz, this.converterMap);

    if ((c == null) && (this.parent != null)) {
      c = this.parent.findConverter(clazz);
    }

    return c;
  }

  static Converter findConverter(Class<?> clazz, Map<Class<?>, Converter> map) {
    Converter c = null;
    c = (Converter)map.get(clazz);
    if (c == null) {
      if (clazz.isArray())
        c = (Converter)map.get(new Object[0].getClass());
      else if (Collection.class.isAssignableFrom(clazz))
        c = (Converter)map.get(Collection.class);
      else if (Map.class.isAssignableFrom(clazz))
        c = (Converter)map.get(Map.class);
      else if (clazz.isEnum()) {
        c = Lazy.lazy.enumConverter;
      }
    }
    return c;
  }

  private static class Lazy
  {
    static Lazy lazy = new Lazy();
    private final PropertyEditorConverter pec;
    private final EnumConverter enumConverter;
    private final IConverterManager def;

	Lazy()
    {
      final Map<Class<?>,Converter> converterMap = new HashMap<Class<?>,Converter>();
      converterMap.put(Class.class, new ClassConverter());
      converterMap.put(File.class, new FileConverter());
      converterMap.put(Locale.class, new LocaleConverter());
      converterMap.put(Properties.class, new PropertiesConverter());
      converterMap.put(URL.class, new URLConverter());
      converterMap.put(String.class, new StringConverter());
      converterMap.put(Boolean.class, new BooleanConverter());
      converterMap.put(Boolean.TYPE, new BooleanConverter());
      converterMap.put(Byte.class, new NumberConverter());
      converterMap.put(Byte.TYPE, new NumberConverter());
      converterMap.put(Short.class, new NumberConverter());
      converterMap.put(Short.TYPE, new NumberConverter());
      converterMap.put(Integer.class, new NumberConverter());
      converterMap.put(Integer.TYPE, new NumberConverter());
      converterMap.put(Long.class, new NumberConverter());
      converterMap.put(Long.TYPE, new NumberConverter());
      converterMap.put(BigInteger.class, new NumberConverter());
      converterMap.put(Float.class, new NumberConverter());
      converterMap.put(Float.TYPE, new NumberConverter());
      converterMap.put(Double.class, new NumberConverter());
      converterMap.put(Double.TYPE, new NumberConverter());
      converterMap.put(BigDecimal.class, new NumberConverter());
      converterMap.put(Class.class, new ClassConverter());

      converterMap.put(java.util.Date.class, new DateConverter());
      converterMap.put(java.sql.Date.class, new DateConverter());

      this.pec = new PropertyEditorConverter();
      this.enumConverter = new EnumConverter();
      this.def = new ConverterManager((IConverterManager) converterMap) {
        public Converter findConverter(Class<?> clazz) {
          Converter c = ConverterManager.findConverter(clazz, converterMap);
          if ((c == null) && (PropertyEditorManager.findEditor(clazz) != null)) {
            c = ConverterManager.Lazy.lazy.pec;
          }
          return c;
        }
      };
    }
  }
}