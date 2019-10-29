/**
 * 
 */
package com.xbkj.gd.integral.biz;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.eos.system.annotation.Bizlet;
import com.pub.xbkj.common.MsgResponse;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-2-27
 *@version 1.0.0
 *@desc
 */


public class TestLoginSalary {

	@Bizlet
	public MsgResponse loginSalary(){
		//发送http请求到
		HttpURLConnection conn = null;
		try {
			URL url = new URL("http://localhost:8013/integral/single/token");
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestProperty("login_user", "admin");
			conn.setRequestMethod("POST");
			conn.connect();
			int code = conn.getResponseCode();
			if(200 == code){
				InputStream is = conn.getInputStream();
				byte[] buf = new byte[1024];
				int len = 0;
				StringBuilder sb = new StringBuilder();
				while((len = is.read()) != -1){
					sb.append(new String(buf, 0, len));
				}
				System.out.println(sb.toString());
				System.out.println(code);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
			return new MsgResponse("登录成功", true);
		}finally{
			if(conn != null){
				conn.disconnect();
			}
		}
		return new MsgResponse("登录成功", true);
		
	}
}
