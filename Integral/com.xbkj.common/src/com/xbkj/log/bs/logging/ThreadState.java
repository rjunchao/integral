package com.xbkj.log.bs.logging;

public class ThreadState {
	private String state;

	private long lastSetTime;
	
	private String threadName;
	
	private boolean isAlive;

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public ThreadState(String s, long l) {
		this.state = s;
		this.lastSetTime = l;
	}

	public long getLastSetTime() {
		return lastSetTime;
	}

	public void setLastSetTime(long lastSetTime) {
		this.lastSetTime = lastSetTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}
	

	
	
	
}
