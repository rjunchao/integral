package com.xbkj.common.util;

public class IdWorker
{
  private final long workerId;
//  private final long twepoch = 1303895660503L;
  private long sequence = 0L;
//  private final long workerIdBits = 10L;
  private final long maxWorkerId = 0xFFFFFFFF ^ -1L << 10;
//  private final long sequenceBits = 12L;

  private final long workerIdShift = 12L;
  private final long timestampLeftShift = 12L + 10L;
  private final long sequenceMask = 0xFFFFFFFF ^ -1L << 12;

  private long lastTimestamp = -1L;

  public IdWorker(long workerId)
  {
    if ((workerId > this.maxWorkerId) || (workerId < 0L)) {
      throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", new Object[] { 
        Long.valueOf(this.maxWorkerId) }));
    }
    this.workerId = workerId;
  }

  public synchronized long nextId() {
    long timestamp = timeGen();
    if (this.lastTimestamp == timestamp) {
      this.sequence = (this.sequence + 1L & this.sequenceMask);
      if (this.sequence == 0L)
        timestamp = tilNextMillis(this.lastTimestamp);
    }
    else {
      this.sequence = 0L;
    }
    if (timestamp < this.lastTimestamp) {
      new Exception(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", new Object[] { 
        Long.valueOf(this.lastTimestamp - timestamp) }));
    }

    this.lastTimestamp = timestamp;
    return timestamp - 1303895660503L << (int)this.timestampLeftShift | this.workerId << (int)this.workerIdShift | 
      this.sequence;
  }

  private long tilNextMillis(long lastTimestamp) {
    long timestamp = timeGen();
    while (timestamp <= lastTimestamp) {
      timestamp = timeGen();
    }
    return timestamp;
  }

  private long timeGen() {
    return System.currentTimeMillis();
  }
}