package com.pub.xbkj.pubapp.converter;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class Strings
{
  public static final Charset UTF8_CHARSET = Charset.forName("utf-8");

  public static List<String> toList(String inputstring, String splitstr) {
    StringTokenizer st = new StringTokenizer(inputstring, splitstr, false);
    List reslist = new ArrayList(st.countTokens());
    while (st.hasMoreTokens()) {
      reslist.add(st.nextToken().trim());
    }
    return reslist;
  }

  public static String toStringFromUTF8(byte[] bytes) {
    try {
      return new String(bytes, UTF8_CHARSET.name()); } catch (UnsupportedEncodingException e) {
    }
    throw new RuntimeException("not support: utf-8");
  }

  public static String toStringFromUTF8(byte[] bytes, int start, int length)
  {
    try
    {
      return new String(bytes, start, length, UTF8_CHARSET.name()); } catch (UnsupportedEncodingException e) {
    }
    throw new RuntimeException("not support: utf-8");
  }

  public static String[] toArray(String str, String delim)
  {
    return toArray(str, delim, false, false);
  }

  public static String[] toArray(String s)
  {
    return toArray(s, ",", false, false);
  }

  public static String[] split(String str, String token) {
    return toArray(str, token);
  }

  public static String[] toArray(String s, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens)
  {
    if (s == null) {
      return new String[0];
    }
    StringTokenizer st = new StringTokenizer(s, delimiters);
    List tokens = new ArrayList();
    while (st.hasMoreTokens()) {
      String token = st.nextToken();
      if (trimTokens) {
        token = token.trim();
      }
      if ((!ignoreEmptyTokens) || (token.length() != 0)) {
        tokens.add(token);
      }
    }
    return (String[])tokens.toArray(new String[tokens.size()]);
  }

  public static boolean notEmpty(String str)
  {
    int strLen;
    if ((str == null) || ((strLen = str.length()) == 0))
      return false;
    for (int i = 0; i < strLen; i++) {
      if (!Character.isWhitespace(str.charAt(i))) {
        return true;
      }
    }
    return false;
  }

  public static String toString(Object[] arr) {
    return toString(arr, ",");
  }

  public static String toString(char[] value, String delim) {
    int iMax = value.length - 1;
    if (iMax == -1) {
      return "";
    }
    StringBuilder b = new StringBuilder();
    for (int i = 0; ; i++) {
      b.append(value[i]);
      if (i == iMax)
        return b.toString();
      b.append(delim);
    }
  }

  public static String toString(float[] value, String delim) {
    int iMax = value.length - 1;
    if (iMax == -1) {
      return "";
    }
    StringBuilder b = new StringBuilder();
    for (int i = 0; ; i++) {
      b.append(value[i]);
      if (i == iMax)
        return b.toString();
      b.append(delim);
    }
  }

  public static String toString(boolean[] value, String delim) {
    int iMax = value.length - 1;
    if (iMax == -1) {
      return "";
    }
    StringBuilder b = new StringBuilder();
    for (int i = 0; ; i++) {
      b.append(value[i]);
      if (i == iMax)
        return b.toString();
      b.append(delim);
    }
  }

  public static String toString(double[] value, String delim) {
    int iMax = value.length - 1;
    if (iMax == -1) {
      return "";
    }
    StringBuilder b = new StringBuilder();
    for (int i = 0; ; i++) {
      b.append(value[i]);
      if (i == iMax)
        return b.toString();
      b.append(delim);
    }
  }

  public static String toString(short[] value, String delim) {
    int iMax = value.length - 1;
    if (iMax == -1) {
      return "";
    }
    StringBuilder b = new StringBuilder();
    for (int i = 0; ; i++) {
      b.append(value[i]);
      if (i == iMax)
        return b.toString();
      b.append(delim);
    }
  }

  public static String toString(byte[] value, String delim) {
    int iMax = value.length - 1;
    if (iMax == -1) {
      return "";
    }
    StringBuilder b = new StringBuilder();
    for (int i = 0; ; i++) {
      b.append(value[i]);
      if (i == iMax)
        return b.toString();
      b.append(delim);
    }
  }

  public static String toString(long[] value, String delim) {
    int iMax = value.length - 1;
    if (iMax == -1) {
      return "";
    }
    StringBuilder b = new StringBuilder();
    for (int i = 0; ; i++) {
      b.append(value[i]);
      if (i == iMax)
        return b.toString();
      b.append(delim);
    }
  }

  public static String toString(int[] value, String delim) {
    int iMax = value.length - 1;
    if (iMax == -1) {
      return "";
    }
    StringBuilder b = new StringBuilder();
    for (int i = 0; ; i++) {
      b.append(value[i]);
      if (i == iMax)
        return b.toString();
      b.append(delim);
    }
  }

  public static String toString(Object value, String delim) {
    if ((value instanceof String)) {
      return (String)value;
    }
    if ((value instanceof Iterable))
      return toString((Iterable)value, delim);
    if (value.getClass().isArray()) {
      Class et = value.getClass().getComponentType();
      if (et.isPrimitive()) {
        if (et == Byte.TYPE)
          return toString((byte[])value, delim);
        if (et == Integer.TYPE)
          return toString((int[])value, delim);
        if (et == Long.TYPE)
          return toString((long[])value, delim);
        if (et == Short.TYPE)
          return toString((short[])value, delim);
        if (et == Float.TYPE)
          return toString((float[])value, delim);
        if (et == Character.TYPE)
          return toString((char[])value, delim);
        if (et == Double.TYPE)
          return toString((double[])value, delim);
        if (et == Boolean.TYPE) {
          return toString((boolean[])value, delim);
        }
        return null;
      }
      return toString((Object[])value, delim);
    }

    return value.toString();
  }

  public static String toString(Iterable<?> c, String delim, String prefix, String suffix)
  {
    if (c == null) {
      return "null";
    }
    StringBuffer sb = new StringBuffer();
    Iterator it = c.iterator();
    int i = 0;
    while (it.hasNext()) {
      if (i++ > 0) {
        sb.append(delim);
      }
      sb.append(prefix + it.next() + suffix);
    }
    return sb.toString();
  }

  public static String toString(Iterable<?> c, String delim) {
    return toString(c, delim, "", "");
  }

  public static String toString(Object value) {
    return toString(value, ",");
  }

  public static String toString(Object[] arr, String delim) {
    if (arr == null) {
      return "null";
    }
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < arr.length; i++) {
      if (i > 0)
        sb.append(delim);
      sb.append(arr[i]);
    }
    return sb.toString();
  }

  public static String unqualify(String qualifiedName, char separator)
  {
    return qualifiedName
      .substring(qualifiedName.lastIndexOf(separator) + 1);
  }

  public static String capitalize(String str) {
    return changeFirstCharacterCase(true, str);
  }

  public static String uncapitalize(String str) {
    return changeFirstCharacterCase(false, str);
  }

  private static String changeFirstCharacterCase(boolean capitalize, String str)
  {
    int strLen;
    if ((str == null) || ((strLen = str.length()) == 0))
      return str;
    StringBuffer buf = new StringBuffer(strLen);
    if (capitalize)
      buf.append(Character.toUpperCase(str.charAt(0)));
    else {
      buf.append(Character.toLowerCase(str.charAt(0)));
    }
    buf.append(str.substring(1));
    return buf.toString();
  }

  public static String unqualify(String qualifiedName) {
    return unqualify(qualifiedName, '.');
  }

  public static StackTraceElement toStackTraceElement(String str) {
    if (str == null) {
      return null;
    }

    if (str.startsWith("at ")) {
      str = str.substring(3);
    }

    int index = str.indexOf("(");
    int lindex = str.indexOf(")");
    if ((index == -1) || (lindex < index)) {
      return null;
    }

    String cam = str.substring(0, index);

    String fal = str.substring(index + 1, lindex);

    int ics = cam.lastIndexOf(".");

    String c = null;
    String m = null;
    String f = null;
    int line = -1;
    if (ics == -1) {
      return null;
    }
    c = cam.substring(0, ics);
    m = cam.substring(ics + 1);
    String l = null;
    int ils = fal.indexOf(":");
    if (ils > 0) {
      f = fal.substring(0, ils);
      l = fal.substring(ils + 1);
      try
      {
        line = Integer.valueOf(l).intValue();
      } catch (Throwable t) {
        line = -1;
      }
    }
    return new StackTraceElement(c, m, f, line);
  }

  public static boolean isEmpty(String str)
  {
    return (str == null) || (str.trim().length() <= 0);
  }

  public static boolean isEmpty(List<String> list)
  {
    if ((list == null) || (list.size() == 0)) {
      return true;
    }

    return (list.size() == 1) && (isEmpty((String)list.get(0)));
  }

  public static String trim(String target, String token)
  {
    int tokenLength = token.length();
    int targetLength = target.length();

    if (target.startsWith(token)) {
      return trim(target.substring(tokenLength), token);
    }
    if (target.endsWith(token)) {
      return trim(target.substring(0, targetLength - tokenLength), token);
    }
    return target;
  }

  public static boolean isEqualUri(String uri1, String uri2) {
    if ((uri1.substring(uri1.length() - 1).equals("/")) && 
      (!uri2.substring(uri2.length() - 1).equals("/")))
      return uri1.substring(0, uri1.length() - 1).equals(uri2);
    if ((uri2.substring(uri2.length() - 1).equals("/")) && 
      (!uri1.substring(uri1.length() - 1).equals("/"))) {
      return uri2.substring(0, uri2.length() - 1).equals(uri1);
    }
    return uri1.equals(uri2);
  }
}