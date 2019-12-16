package com.xbkj.gd.integral.prod.sign.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-10-27
 *@version 1.0.0
 *@desc
 */
public class SignTest {

	public static void main(String[] args) {
		Base64ToImage(imgToBase64());
	}
	
	
	public static String imgToBase64(){
		BASE64Encoder be = new BASE64Encoder();
		ByteOutputStream bos = new ByteOutputStream();
		OutputStream os  = null;
		FileInputStream is = null;
		try {
			File f = new File("f:\\Decode_Image_29280.txt");
			is = new FileInputStream("f:\\Decode_Image_29280.PNG");
//			os = new FileOutputStream(f);
			byte[] buf = new byte[1024];
			while(is.read(buf) != -1){
				bos.write(buf);
			}
//			os.write(bos.getBytes());
//			be.encode(is, os);
			String encode = be.encode(bos.getBytes());
			System.out.println(encode);
			return encode;
			/*
			is = new FileInputStream(f);
			int len = is.available();
			byte[] buf = new byte[len];
			is.read(buf);
			String str = new String(buf);
			str = str.replaceAll("[\\s*\t\n\r]", "");
			str = str.replaceAll(" ", "");
			
			System.out.println("data:image/png;base64," + str);*/
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(os != null){
				try {
					os.close();
				} catch (IOException e) {}
			}
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {}
			}
		}
		return null;
	}
	
	 public static boolean Base64ToImage(String imgStr){
	        //对字节数组字符串进行Base64解码并生成图片
	        if (imgStr == null) //图像数据为空
	            return false;
	        BASE64Decoder decoder = new BASE64Decoder();
	        try{
	            //Base64解码
	        	byte[] b = decoder.decodeBuffer(imgStr);
	            for(int i=0;i<b.length;++i){
	                if(b[i]<0){//调整异常数据
	                    b[i]+=256;
	                }
	            }
	            //生成png图片
	            String imgFilePath = "f:\\new_timg.png";//新生成的图片
	            OutputStream out = new FileOutputStream(imgFilePath);
	            out.write(b);
	            out.flush();
	            out.close();
	            return true;
	        }catch (Exception e){
	            return false;
	        }
	    }
}
