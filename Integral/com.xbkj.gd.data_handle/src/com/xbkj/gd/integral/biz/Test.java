/**
 * 
 */
package com.xbkj.gd.integral.biz;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.codehaus.jettison.json.JSONObject;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-3-5
 *@version 1.0.0
 *@desc
 */
public class Test {

	public static void main(String[] args) {
		//发送http请求到
		HttpURLConnection conn = null;
		try {
			URL url = new URL("http://127.0.0.1:8013/integral/single/token");
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("POST");
			conn.setDoInput(true);//可以允许输入
			conn.setDoOutput(true);//可以允许输出
			conn.setRequestProperty("Content-Type", "application/json");//
		    conn.setRequestProperty("Charset", "UTF-8");
			conn.connect();
			OutputStream os = conn.getOutputStream();
			JSONObject json = new JSONObject();
			json.put("login_user", "integral");
			json.put("sys_src", "old_sys");
			os.write(json.toString().getBytes());
			os.flush();
			os.close();
			int code = conn.getResponseCode();
			if(200 == code){
				InputStream is = conn.getInputStream();
				byte[] buf = new byte[1024];
				StringBuilder sb = new StringBuilder();
				while(is.read(buf) > 0){
					String str = new String(buf, 0, buf.length);
					sb.append(str);
				}
				System.out.println(sb.toString());
				System.out.println(code);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(conn != null){
				conn.disconnect();
			}
		}
				
	}
}
