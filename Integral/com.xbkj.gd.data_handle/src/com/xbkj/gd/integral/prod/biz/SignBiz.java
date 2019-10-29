/**
 * 
 */
package com.xbkj.gd.integral.prod.biz;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import sun.misc.BASE64Encoder;

import com.eos.system.annotation.Bizlet;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-10-27
 *@version 1.0.0
 *@desc
 */

@Bizlet("")
public class SignBiz {

	@Bizlet("")
	public String signQueryTest(){
		// 1
		BASE64Encoder be = new BASE64Encoder();
		ByteOutputStream bos = new ByteOutputStream();
		FileInputStream is = null;
		try {
//			File f = new File("f:\\Decode_Image_29280.txt");
			is = new FileInputStream("f:\\Decode_Image_29280.PNG");
			byte[] buf = new byte[1024];
			while(is.read(buf) != -1){
				bos.write(buf);
			}
			String encode = be.encode(bos.getBytes());
			bos.close();
			return "data:image/png;base64," + encode;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {}
			}
		}
		return null;
		
		// 2
		/*FileInputStream is = null;
		try {
			File f = new File("f:\\Decode_Image_29280.txt");
			is = new FileInputStream(f);
			int len = is.available();
			byte[] buf = new byte[len];
			is.read(buf);
			String str = new String(buf);
			str = str.replaceAll("[\\s*\t\n\r]", "");
			return "data:image/png;base64," + str;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {}
			}
		}
		return null;*/
	}
}
