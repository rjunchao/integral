package com.xbkj.common.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Vector;

public class StringUtil
{
  public static final boolean equals(String s1, String s2)
  {
    if ((s1 == null) && (s2 == null)) {
      return true;
    }
    if ((s1 == null) || (s2 == null)) {
      return false;
    }

    return s1.trim().equals(s2.trim());
  }

  public static final boolean isEmpty(String sParam)
  {
    return (sParam == null) || ("".equals(sParam.trim()));
  }
  
  public static final boolean isNotEmpty(String sParam)
  {
    return (sParam != null) && (!"".equals(trimAll(sParam)));
  }

  /**
   * 去除所有空格
   * @param str
   * @return
   */
  public static  String trimAll(String str){
	  String rtnStr = str.replaceAll(" ", "");
	  return rtnStr;
  }
  public static String toYorN(String sSrc)
  {
    if (equals(sSrc, "是"))
      return "Y";
    if (equals(sSrc, "否")) {
      return "N";
    }
    return sSrc;
  }

  public static String trim(String str){
	  return str.trim();
  }
  public static int findIndex(String[] arrStr, String sKey)
  {
    int iIndex = -1;

    if (arrStr == null) {
      return iIndex;
    }

    int iLen = arrStr.length;

    for (int i = 0; i < iLen; i++) {
      if (equals(arrStr[i], sKey)) {
        iIndex = i;
        break;
      }
    }
    return iIndex;
  }

  public static String[] getParams(String str)
  {
    return split(str, ",");
  }

  public static String[] split(String str, String sp)
  {
    Vector v = new Vector();
    String[] strs = null;
    if (str == null) return null;
    if (sp == null) return new String[] { str };
    StringBuffer sb = new StringBuffer();
    String chr = null;
    for (int i = 0; i < str.length(); i++) {
      chr = str.substring(i, i + 1);
      if (chr.equals(sp)) {
        v.add(sb.toString());
        sb = new StringBuffer();
      } else {
        sb.append(chr);
        if (i == str.length() - 1) {
          v.add(sb.toString());
        }
      }
    }
    if (v.size() > 0) {
      strs = new String[v.size()];
      v.copyInto(strs);
    }
    return strs;
  }

  public static String getRealString(String str) {
    if (str.startsWith("\"")) {
      return str.substring(1, str.length() - 1);
    }
    return str;
  }

  public static boolean hasText(String str)
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

  public static boolean startsWith(String src, String tar, boolean isTrim) {
    if ((!hasText(src)) || (!hasText(tar))) {
      return false;
    }

    if (isTrim) {
      src = src.trim();
    }
    src = src.toLowerCase();
    tar = tar.toLowerCase();

    return src.startsWith(tar);
  }

  public static String getStringByStrArry(String[] strArry, String tar)
  {
    String retStr = "";
    if ((strArry == null) || (strArry.length == 0)) {
      return retStr;
    }

    if (tar == null) {
      tar = "";
    }

    for (int i = 0; i < strArry.length; i++) {
      if (strArry[i] != null) {
        retStr = retStr + tar + strArry[i].trim();
      }
    }

    if (!"".equals(retStr.trim())) {
      retStr = retStr.substring(tar.length(), retStr.length());
    }
    return retStr;
  }

  public static String[] deleteRepeatData(String[] datas)
  {
    HashSet codeSet = new HashSet(Arrays.asList(datas));
    return (String[])codeSet.toArray(new String[codeSet.size()]);
  }

  public static String getZeroLenString(Object value, Boolean isRetNull)
  {
    String retStr = null;
    if ((value == null) || (value.toString().trim().length() == 0))
      retStr = null;
    else {
      retStr = value.toString();
    }

    if ((retStr == null) && (!isRetNull.booleanValue())) {
      retStr = "";
    }

    return retStr;
  }

  public static String getWherePartByKeys(String fld, String[] arsKey, boolean bNullAll)
  {
    int MAX = 200;
    if ((arsKey == null) || (arsKey.length == 0)) {
      return bNullAll ? " 1 = 1 " : "1>2";
    }
    if (arsKey.length == 1)
      return fld + "='" + arsKey[0] + "'";
    if (arsKey.length <= 200) {
      String sTmp = fld + " in (";
      for (int i = 0; i < arsKey.length; i++) {
        if (i == arsKey.length - 1) {
          sTmp = sTmp + "'" + arsKey[i] + "')";
          break;
        }
        sTmp = sTmp + "'" + arsKey[i] + "',";
      }
      return sTmp;
    }
    int ipos = 0;
    int itimes = arsKey.length / 200;
    int mode = arsKey.length % 200;
    String where = null;
    for (int i = 0; i < itimes; i++) {
      if (where == null)
        where = " ( " + fld + " in ( ";
      else
        where = where + " or " + fld + " in (";
      for (int j = 0; j < 200; j++) {
        if (j == 199) {
          where = where + "'" + arsKey[(ipos + j)] + "')";
          break;
        }
        where = where + "'" + arsKey[(ipos + j)] + "',";
      }
      ipos += 200;
    }
    if (mode == 0) {
      where = where + " )";
    } else {
      where = where + " or " + fld + " in (";
      for (int k = 0; k < mode; k++) {
        if (k == mode - 1) {
          where = where + "'" + arsKey[(ipos + k)] + "'))";
          break;
        }
        where = where + "'" + arsKey[(ipos + k)] + "',";
      }
    }
    return where;
  }

  public static boolean isKeyType(String strKey)
  {
    if ((strKey == null) || ("".equals(strKey.trim()))) {
      return false;
    }

    return strKey.trim().length() == 20;
  }

  public static String getInsqlFroArray(String[] arr)
  {
    String ret = "";
    if ((arr != null) && (arr.length > 0)) {
      for (int i = 0; i < arr.length; i++) {
        ret = ret + ",'" + arr[i] + "'";
      }
      ret = ret.substring(1);
    }
    return ret;
  }

  public static String[] unionArrays(String[] obj1, String[] obj2)
  {
    int len = -1;
    len = obj1.length + obj2.length;
    String[] ret = new String[len];
    for (int i = 0; i < obj1.length; i++) {
      ret[i] = obj1[i];
    }
    int j = len - obj2.length;
    for (int i = 0; i < obj2.length; i++) {
      ret[j] = obj2[i];
      j++;
    }

    return ret;
  }
}