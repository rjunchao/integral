package com.xbkj.common.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MapList<K, V>
  implements Serializable
{
  private static final long serialVersionUID = -8570285287235727009L;
  private Map<K, List<V>> map = new HashMap();

  public boolean containsKey(K key)
  {
    return this.map.containsKey(key);
  }

  public List<V> get(K key)
  {
    return (List)this.map.get(key);
  }

  public List<V> remove(K key)
  {
    return (List)this.map.remove(key);
  }

  public Set<K> keySet()
  {
    return this.map.keySet();
  }

  public Set<Map.Entry<K, List<V>>> entrySet()
  {
    return this.map.entrySet();
  }

  public void put(K key, V value)
  {
    List l = (List)this.map.get(key);
    if (l == null) {
      l = new ArrayList();
      this.map.put(key, l);
    }
    l.add(value);
  }

  public void putAll(K key, List<V> valueList)
  {
    List l = (List)this.map.get(key);
    if (l == null) {
      l = new ArrayList();
      this.map.put(key, l);
    }
    l.addAll(valueList);
  }

  public int size()
  {
    return this.map.size();
  }

  public Map<K, List<V>> toMap()
  {
    return this.map;
  }
}