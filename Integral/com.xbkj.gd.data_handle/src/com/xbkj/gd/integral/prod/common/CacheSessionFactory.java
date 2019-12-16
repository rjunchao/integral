package com.xbkj.gd.integral.prod.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.gocom.components.coframe.tools.LoggerFactory;

import com.eos.system.logging.Logger;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-10-31
 *@version 1.0.0
 *@desc 系统缓存
 *	缓存签名数据
 */
public class CacheSessionFactory {

	private static final Logger log = LoggerFactory.getLogger(CacheSessionFactory.class);
	
	private static Map<String, String> cache = new ConcurrentHashMap<String, String>();
	
	private static long save_time = 10 * 60 * 1000;//十分钟
	
	private static CacheSessionFactory instance = null;
	
	private CacheSessionFactory(){
		new ClearCacheThread().start();
	}
	
	public static CacheSessionFactory getInstance(){
		synchronized (CacheSessionFactory.class) {
			if(instance == null){
				synchronized (CacheSessionFactory.class) {
					if(instance == null){
						instance = new CacheSessionFactory();
					}
				}
			}
		}
		return instance;
	}
	
	
	public String get(String key){
		return cache.get(key);
	}
	public void put(String key, String value){
		cache.put(key, value);
	}
	
	
	
	class ClearCacheThread extends Thread{
		@Override
		public void run() {
			while(true){
				try {
					
					Thread.sleep(save_time);
				} catch (InterruptedException e) {}
				//清理缓存
				if(!cache.isEmpty()){
					for(Map.Entry<String, String> map:cache.entrySet()){
						log.info("清理缓存签名数据" + map.getKey());
						cache.remove(map.getKey());
					}
				}
			}
		}
	}
}
