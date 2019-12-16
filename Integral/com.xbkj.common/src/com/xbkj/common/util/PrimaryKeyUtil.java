package com.xbkj.common.util;



public class PrimaryKeyUtil
{
  private static long workerIdBits = 10L;
  private static long maxWorkerId = 0xFFFFFFFF ^ -1L << (int)workerIdBits;
  private static IdWorker w = new IdWorker(maxWorkerId);

  
  public static String getPrimaryKey() { Long l = Long.valueOf(w.nextId());
    return "GD" + l.toString();
  }
}