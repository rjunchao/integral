package com.pub.xbkj.pubapp.converter;

import java.lang.reflect.Field;

public class EnumConverter
  implements Converter
{
  public Object convert(Object from, Class<?> toClass)
  {
    if (toClass.isInstance(from)) {
      return from;
    }

    String str = from.toString();

    if (toClass.isEnum()) {
      try {
        Field field = toClass.getField(str);
        if (field.isEnumConstant()) {
          field.setAccessible(true);
          return field.get(null);
        }

        throw new ConvertException(String.format("can't convert %s(%s) to %s", new Object[] { from, toClass.getName() }));
      }
      catch (Throwable e) {
        Field[] fds = toClass.getFields();
        for (Field fd : fds) {
          if ((fd.isEnumConstant()) && (fd.getName().equalsIgnoreCase(str))) {
            fd.setAccessible(true);
            try {
              return fd.get(null);
            } catch (Exception e1) {
              e = e1;
            }
          }
        }

        if ((e instanceof ConvertException)) {
          throw ((ConvertException)e);
        }
        throw new ConvertException(String.format("can't convert %s(%s) to %s", new Object[] { from, from.getClass(), toClass
          .getName() }), e);
      }
    }

    throw new ConvertException(toClass.getName() + " is not enum class");
  }
}