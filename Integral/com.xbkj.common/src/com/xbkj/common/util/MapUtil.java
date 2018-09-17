package com.xbkj.common.util;

import java.util.Map;

public class MapUtil
{
  public static boolean isEmpty(Map<?, ?> m)
  {
    return (m == null) || (m.size() == 0);
  }

  public static boolean isEmpty(MapList<?, ?> m)
  {
    return (m == null) || (m.size() == 0);
  }

  public static boolean isNotEmpty(Map<?, ?> m)
  {
    return (m != null) && (m.size() > 0);
  }

  public static boolean isNotEmpty(MapList<?, ?> m)
  {
    return (m != null) && (m.size() > 0);
  }

  public static <K, V> void addArrayToMap(Map<K, V> m, K[] keys, V[] values)
  {
    if ((m == null) || (ArrayUtil.isEmpty(keys)) || (ArrayUtil.isEmpty(values)) || 
      (!ArrayUtil.isLengthEqual(keys, values))) {
      return;
    }
    for (int i = 0; i < keys.length; i++)
      m.put(keys[i], values[i]);
  }
}