package com.pub.xbkj.pubapp.converter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class PropertiesConverter
  implements Converter
{
  private static final String COMMENT_MARKERS = "#!";

  public Object convert(Object from, Class<?> toClass)
  {
    if ((from == null) || ((from instanceof Properties)))
      return from;
    if ((from instanceof Map)) {
      Properties props = new Properties();
      props.putAll((Map)from);
      return props;
    }
    Properties props = new Properties();
    try {
      props.load(new ByteArrayInputStream(Strings.toString(from, "\r\n").getBytes()));
      dropComments(props);
      return props; } catch (IOException ex) {
    }
    throw new ConvertException("Failed to parse [" + from + "] into Properties");
  }

  private void dropComments(Properties props)
  {
    Iterator<?> keys = props.keySet().iterator();
    List<String> commentKeys = new ArrayList<String>();
    while (keys.hasNext()) {
      String key = (String)keys.next();
      if ((key.length() > 0) && ("#!".indexOf(key.charAt(0)) != -1)) {
        commentKeys.add(key);
      }
    }
    for (Iterator<String> it = commentKeys.iterator(); it.hasNext(); ) {
      String key = (String)it.next();
      props.remove(key);
    }
  }
}