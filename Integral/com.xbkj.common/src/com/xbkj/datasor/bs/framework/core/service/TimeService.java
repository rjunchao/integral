package com.xbkj.datasor.bs.framework.core.service;

import java.util.Timer;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.xbkj.datasor.bs.framework.common.NCLocator;
import com.xbkj.datasor.bs.framework.component.AbstractServiceComponent;

/**
 * 
 * @author hgy
 * 
 */
public class TimeService extends AbstractServiceComponent {

	private static TimeService ts;

	private final long DAY = 1000 * 60 * 60 * 24;

	private long base;

	private long masterBase;

	private ITimeSync timeSync;

	private Timer timer;

	private ReentrantReadWriteLock synLock = new ReentrantReadWriteLock();

	public TimeService(ITimeSync ts) {
		timeSync = ts;
	}

//	public long getTime() {
//		if (ServerConfiguration.getServerConfiguration().isMaster()
//				|| ServerConfiguration.getServerConfiguration().isSingle()) {
//			return System.currentTimeMillis();
//		} else {
//			if (isStarted()) {
//				synLock.readLock().lock();
//				try {
//					return (System.currentTimeMillis() - base) + masterBase;
//				} finally {
//					synLock.readLock().unlock();
//				}
//			} else {
//				getLog()
//						.warn("use the time which not synchronized with master");
//				return System.currentTimeMillis();
//			}
//		}
//
//	}

//	public void start() throws Exception {
//		if (ServerConfiguration.getServerConfiguration().isMaster()
//				|| ServerConfiguration.getServerConfiguration().isSingle()) {
//			setStarted(true);
//		} else {
//			sync();
//			timer = new Timer();
//			timer.schedule(new TimerTask() {
//				@Override
//				public void run() {
//					sync();
//				}
//
//			}, DAY, DAY);
//			setStarted(true);
//		}
//	}

	private void sync() {
		synLock.writeLock().lock();
		try {
			base = System.currentTimeMillis();
			masterBase = timeSync.sync() + (System.currentTimeMillis() - base) / 2;
		} finally {
			synLock.writeLock().unlock();
		}
	}

//	public void stop() throws Exception {
//		if (ServerConfiguration.getServerConfiguration().isMaster()
//				|| ServerConfiguration.getServerConfiguration().isSingle()) {
//			setStarted(false);
//		} else {
//			timer.cancel();
//			timer = null;
//			setStarted(false);
//		}
//	}

	public static TimeService getInstance() {
		if (ts == null)
			ts = (TimeService) NCLocator.getInstance().lookup(
					TimeService.class.getName());
		return ts;
	}

}
