package com.xbkj.gd.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2018-9-10
 *@version 1.0.0
 *@desc
 */
public class FileImportUtils {

	/**
	 * 解析请求得到文件输入流
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static InputStream getExcelIs(HttpServletRequest request) throws Exception {
		//文件保存路径
		String sp = request.getSession().getServletContext().getRealPath("/fileUpload");
		File spf = new File(sp);
		if(!spf.exists()){spf.mkdir();}
		MultipartRequest mpr = new MultipartRequest(request, sp, 100 * 1024 * 1024, "UTF-8");
		Enumeration fileNames = mpr.getFileNames();
		while(fileNames.hasMoreElements()){
			String fileName = (String) fileNames.nextElement();
			File uploadFile = mpr.getFile(fileName);
			if(uploadFile != null){
				return new FileInputStream(uploadFile);
			}
		}
		return null;
	}
}
