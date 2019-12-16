package com.xbkj.gd.integral.prod.common;

import java.io.FileInputStream;
import java.io.IOException;

import org.gocom.components.coframe.tools.LoggerFactory;

import com.eos.system.logging.Logger;
import com.xbkj.common.util.StringUtil;
import com.xbkj.gd.integral.prod.dao.SignDao;
import com.xbkj.gd.integral.prod.vos.SignVO;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-10-31
 *@version 1.0.0
 *@desc
 */
public class SignUtils {
	
	private static final Logger log = LoggerFactory.getLogger(SignUtils.class);
	public static String getSign(String pk){
		
		String cacheSign = CacheSessionFactory.getInstance().get(pk);//直接获取
		if(StringUtil.isNotEmpty(cacheSign)){
			return cacheSign;
		}
		SignVO vo = SignDao.getByPkSub(pk);
		if(vo == null){
			return null;
		}
		String file = vo.getSign_file_path() + vo.getSign_file_name();
		FileInputStream is = null;
		try {
			is = new FileInputStream(file);
			byte[] buf = new byte[is.available()];
			is.read(buf);
			String sign = new String(buf);
//			String sign = FileUtils.readFileToString(new File(file));
			CacheSessionFactory.getInstance().put(pk, sign);//缓存数据
			return sign;
		} catch (IOException e) {
			log.error(e);
			return null;
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {}
			}
		}
	}
}
