package com.pub.xbkj.pubapp.converter;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;

public class PropertyEditorConverter
  implements Converter
{
  public Object convert(Object from, Class<?> toClass)
  {
    PropertyEditor pe = PropertyEditorManager.findEditor(toClass);
    if (pe != null) {
      if ((from instanceof String))
        pe.setAsText((String)from);
      else {
        pe.setValue(from);
      }
      return pe.getValue();
    }
    return null;
  }
}