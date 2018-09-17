package com.xbkj.common.util;

import java.math.BigDecimal;

public class ArithUtil
{
  private static final int DEF_DIV_SCALE = 10;
  private static final int DEF_SCALE = 6;
  private static final int _SCALE = 6;
  static final BigDecimal one = new BigDecimal(1);
  static final Double zero = new Double(0.0D);
  static final BigDecimal big_zero = new BigDecimal(0.0D);

  public static double add(double v1, double v2)
  {
    BigDecimal b1 = new BigDecimal(Double.toString(v1));
    BigDecimal b2 = new BigDecimal(Double.toString(v2));
    return round(b1.add(b2).doubleValue(), 6);
  }

  public static BigDecimal add(String v1, String v2) {
    BigDecimal b1 = new BigDecimal(v1);
    BigDecimal b2 = new BigDecimal(v2);
    return round(b1.add(b2), 6);
  }

  public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
    return round(v1.add(v2), 6);
  }

  public static double sub(double v1, double v2)
  {
    BigDecimal b1 = new BigDecimal(Double.toString(v1));
    BigDecimal b2 = new BigDecimal(Double.toString(v2));
    return round(b1.subtract(b2).doubleValue(), 6);
  }

  public static BigDecimal sub(BigDecimal b1, BigDecimal b2)
  {
    return round(b1.subtract(b2), 6);
  }

  public static double mul(double v1, double v2)
  {
    BigDecimal b1 = new BigDecimal(Double.toString(v1));
    BigDecimal b2 = new BigDecimal(Double.toString(v2));
    return round(b1.multiply(b2).doubleValue(), 6);
  }

  public static double mul(String v1, double v2) {
    if ((v1 == null) || ("".equals(v1)) || (Double.isNaN(v2)) || (v2 == 0.0D)) {
      return 0.0D;
    }
    BigDecimal b1 = new BigDecimal(Double.valueOf(v1).doubleValue());
    BigDecimal b2 = new BigDecimal(Double.valueOf(v2).doubleValue());
    return round(b1.multiply(b2).doubleValue(), 6);
  }

  public static BigDecimal mul(BigDecimal v1, BigDecimal v2)
  {
    return round(v1.multiply(v2), 6);
  }

  public static BigDecimal mulOne(BigDecimal v1)
  {
    return round(v1.multiply(one), 6);
  }

  public static BigDecimal mulOne(Double v1) {
    return round(new BigDecimal(v1.doubleValue()).multiply(one), 6);
  }
  public static BigDecimal mulOne_null(Double v1) {
    if (v1 == null) {
      return null;
    }
    return round(new BigDecimal(v1.doubleValue()).multiply(one), 6);
  }

  public static double div(double v1, double v2)
  {
    if (v2 == 0.0D) {
      return v1;
    }
    return div(v1, v2, 10);
  }

  public static BigDecimal div(BigDecimal v1, BigDecimal v2)
  {
    if (v2.doubleValue() == zero.doubleValue()) {
      return null;
    }
    return div(v1, v2, 6);
  }

  public static double div(double v1, double v2, int scale)
  {
    if (scale < 0) {
      throw new IllegalArgumentException(
        "The scale must be a positive integer or zero");
    }
    BigDecimal b1 = new BigDecimal(Double.toString(v1));
    BigDecimal b2 = new BigDecimal(Double.toString(v2));
    return b1.divide(b2, scale, 4).doubleValue();
  }

  public static BigDecimal div(BigDecimal v1, BigDecimal v2, int scale) {
    if (scale < 0) {
      throw new IllegalArgumentException(
        "The scale must be a positive integer or zero");
    }

    return v1.divide(v2, scale, scale);
  }

  public static double round(double v, int scale)
  {
    if (scale < 0) {
      throw new IllegalArgumentException(
        "The scale must be a positive integer or zero");
    }
    String vv = Double.toString(v);
    BigDecimal b = null;
    if (vv.contains("E")) {
      vv = vv.substring(0, vv.indexOf("E"));
      b = new BigDecimal(vv);
    } else {
      b = new BigDecimal(Double.toString(v));
    }

    BigDecimal one = new BigDecimal("1");
    return b.divide(one, scale, 4).doubleValue();
  }

  public static BigDecimal round(BigDecimal v, int scale) {
    if (scale < 0) {
      throw new IllegalArgumentException(
        "The scale must be a positive integer or zero");
    }

    BigDecimal one = new BigDecimal("1");
    return v.divide(one, scale, 4);
  }
}