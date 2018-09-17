package com.pub.xbkj.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import com.eos.common.transaction.ITransactionManager;
import com.eos.common.transaction.TransactionManagerFactory;
import com.eos.runtime.core.ApplicationContext;
import com.eos.runtime.core.TraceLoggerFactory;
import com.eos.system.logging.Logger;
import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.Part;
import com.pub.xbkj.pubapp.query.DBTool;
import com.xbkj.common.util.DateUtil;
import com.xbkj.common.util.PrimaryKeyUtil;
import com.xbkj.common.util.StringUtil;

/**
 *@author rjc
 *@date 2016-7-11
 *@version 1.0.0
 *@desc: 主要功能：
 *	文件上传
 *		上传到临时表
 *	
 *	
 */
public class FileManager {
	private static Logger logger = TraceLoggerFactory.getLogger(FileManager.class);
	
	/**
	 * 文件上传和下载
	 * 文件上传到一张临时表（pk, 临时文件名，文件类型，文件内容）中
	 * 	解析请求得到文件流
	 * 	创建一个临时文件，把上传文件写入临时文件
	 * 	调用保存文件的方法（file,fileName,fileType）
	 * 		创建临时表
	 * 		插入数据
	 * 		设置参数
	 * 	保存成功返回主键和标志
	 * @param request
	 * @param response
	 * @return 
	 */
	public Map<String, Object> upload(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object>  map = new HashMap<String, Object>();
		MsgResponse msg = null;
		int maxSize = 100 * 1024 * 1024;
		if (request == null){
			map.put("msg", "请求为空");
			map.put("pk", null);
			return map;
		}
		//解析得到文件信息
		try {
			
			MultipartParser parser = new MultipartParser(request, maxSize, true, true, "UTF-8");
			//遍历解析的信息
			Part part = null;
			//得到应用路径
			String temppath = ApplicationContext.getInstance().getWarRealPath()+"/tempfiles";
			File dirFile = new File(temppath);
			if(!dirFile.exists()){
				new File(temppath);
			}
			while((part = parser.readNextPart())!=null){
				if(part.isFile()){//是否是文件
					FilePart filePart = (FilePart) part;
					//得到文件名
					String fileName = filePart.getFileName();
					//
					if(StringUtil.isNotEmpty(fileName)){
						/*
						 * 得到文件输入流 实际得到的是PartInputStream 不能转换为 FileInputStream，也不能得到流的大小available
						 */
						InputStream is = filePart.getInputStream();
						//String temppath = request.getSession().getServletContext().getRealPath("/") + "/tempfiles/";
						//创建临时文件
						
						File file = new File(temppath + new Date().getTime() + fileName);
						FileOutputStream fos = new FileOutputStream(file);
						int len = 0;
						byte[] buf = new byte[1024];
						while((len = is.read(buf)) != -1){
							fos.write(buf, 0, len);
						}
						//关闭流
						fos.close();
						is.close();
						// 得到文件后缀
						fileName = fileName.replaceAll("&", "_");//去除上传文件中的&符号
						String fileType = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());//得到文件后缀
						String pk = writeToDB(file, fileName, fileType);//将文件写入数据库
						if(StringUtil.isNotEmpty(pk)){
							msg = new MsgResponse("文件上传成功", true);
							map.put("msg", msg);
							map.put("pk", pk);
						}else{
							msg = new MsgResponse("文件上传失败", false);
							map.put("msg", msg);
							map.put("pk", null);
						}
					}else{//文件名为空
						msg = new MsgResponse("文件上传失败，请选择文件", false);
						map.put("msg", msg);
						map.put("pk", null);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			msg = new MsgResponse("文件上传失败" + e.getMessage(), false);
			map.put("msg", msg);
			map.put("pk", null);
		}
		//保存数据
		return map;
	}
	
	/**
	 * 文件下载
	 * @param sql 查询sql 只查询文件字段 :select 文件字段 from 表名 where 主键='pk'
	 * @param filename: 名称+后缀
	 * @param response
	 * @throws IOException 
	 */
	public void download(String sql, String fileName, HttpServletResponse response,JspWriter out){
		response.reset();//清空页面，设置页面不缓存
		
		//1、查询出文件信息
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		//
		InputStream is = null;
		ServletOutputStream os = null;
		try {
			os = response.getOutputStream();
			//指定文件的类型和参数
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-disposition", "attachment;filename=\""+ URLEncoder.encode(fileName,"UTF-8") + "\"");
			
			//查询文件信息,java.sql.Blob oracle.sql.BLOB
			DBTool tool = new DBTool();
			conn = tool.getConnection();//得到连接
			ps = conn.prepareStatement(sql);
			//查询记录
			rs = ps.executeQuery();
			while(rs.next()){
				is = rs.getBinaryStream(1);
			}
			if(is != null){
				//把文件写到客户端
				int len = 0;
				byte[] buf = new byte[1024];
				while((len = is.read(buf))!=-1){
					os.write(buf, 0, len);
				}
				response.flushBuffer();
			}else{
				out.write("<script language='javascript'>nui.alert('没有相关文件!')</script>");
			}
			/*out.clear();
			out = pageContext.pushBody();*/
		} catch (Exception e) {
			/*e.printStackTrace();
			logger.error(e.getMessage(), e);*/
		} finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(os != null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			DBTool.closeDB(conn, ps, rs);
		}
	}
	
	/**
	 * 把文件写入数据库, 返回主键
	 * @param is 文件输入流
	 * @param file 文件
	 * @param fileType 文件类型（后缀）
	 * @param path 项目的绝对路径
	 * @return
	 */
	private String writeToDB(File file, String fileName, String fileType){
		String pk = null;
		Connection conn = null;
		PreparedStatement ps = null;
		FileInputStream fis = null;
		ITransactionManager manager = TransactionManagerFactory.getTransactionManager();
		try {
			manager.begin();
			//获得数据库连接
			DBTool dbTool = new DBTool();
			conn = dbTool.getConnection();
			//插入sql
			String sql = "insert into temp_file(pk_temp_file,file_name,file_type,file_content,file_size) values (?,?,?,?,?)";

			pk = PrimaryKeyUtil.getPrimaryKey();
			//设置插入数据的sql
			ps = conn.prepareStatement(sql);
			ps.setString(1, pk);//主键
			ps.setString(2, fileName);//文件 名
			ps.setString(3, fileType);//文件类型
			fis = new FileInputStream(file);
			//文件大小
			int len = fis.available();
			/*
			 * 这里必须使用 prepareStatement.setBinaryStream(index,inputstream,length)这个方法
			 * 如果使用prepareStatement.setBlob(index,inputstream,length)会报异常
			 * length 如果直接使用 is.available()不能获取，只会返回0，在request中获取到的inputstream也获取不到大小。
			 * 只能使用fileinputstream的available方法才能获取到，所以这里采用临时文件的形式，写入数据库后在删除临时文件
			 * 
			 */
			ps.setBinaryStream(4, fis, len);
			ps.setLong(5, len);
			//ps.setBlob(4, fis, len);
			int  count = ps.executeUpdate();
			//插入成功，返回主键
			if(count > 0){
				manager.commit();
				return pk;
			}else{
				return null;
			}
		} catch (Exception e) {
			manager.rollback();
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return null;
		}finally{
			//关闭文件流
			if(fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//删除临时文件
			if(file.exists() && file.isFile()){
				file.delete();//删除临时文件
			}
			//关闭数据库连接
			DBTool.closeDB(conn, ps);      
		}
	}


	/**
	 * 将本地文件批量写入数据库（外规文件的导入）
	 * @param vos 外规信息
	 * @param filepath 本地路径
	 * @param filename 文件名
	 * @return 返回导入的结果信息
	 */
	public String fileToOracle(ExtRegulationVO[] vos, String[] filepath){
		StringBuffer sb = new StringBuffer();
		//判断三个数组是否相等
		int vlen = vos.length;
		int plen = filepath.length;
		//判断内容长度
		if(vlen != plen){
			return "要导入的文件内容不对应，请检查文件路径、文件名的对应关系";
		}
		Connection conn = null;//数据库连接对象
		PreparedStatement ps = null;//执行对象
		DBTool tool = new DBTool();
		conn = tool.getConnection();
		try {
			//构造插入sql
			int len = vos.length;
			for(int i = 0; i < len; i++){
				//得到文件信息
				ExtRegulationVO vo = vos[i];
				if(vo == null){continue;}
				String path = filepath[i];
				if(StringUtil.isEmpty(path)){continue;}
				String name = vo.getRegulation_name();
				if(StringUtil.isNotEmpty(path) && StringUtil.isNotEmpty(name)){
					if(!path.endsWith("\\")){
						path = path + "\\";//文件路径不是以\结尾，在加上\
					}
					if(name.lastIndexOf(".") == -1){
						sb.append( name + " 文件名不合法");
						continue;
					}
					File file = new File(path + name);
					//拼接sql,根据vo的内容进行拼接
					executeSql(vo, file, conn, ps, sb);
				}
				
			}
		}catch (Exception e) {
			e.printStackTrace();
			sb.append(e.getMessage());
		}finally{
			DBTool.closeDB(conn, ps);
		}
		String result = sb.toString();
		if(StringUtil.isEmpty(result)){
			return "success";
		}else{
			return result;
		}
		
	}
	/*
	 * 
	 * @param vo 要插入的实体类
	 * @param file 导入的文件
	 * @param conn 数据库连接对象
	 * @param ps 执行对象
	 * @param sb 保存错误信息
	 * @return
	 */
	private void executeSql(ExtRegulationVO vo, File file, Connection conn, PreparedStatement ps, StringBuffer sb){
		String sql = "insert into grc_ext_regulation(pk_ext_regulation,ref_number,pk_ext_regulation_dept," +
				"pk_regulation_level_effect,pk_grc_regulation_timeliness,issue_date,impl_date,regu_content," +
				"regu_explain,creater,createtime,regulation_name,regulation_dept_name,level_effect_name," +
				"timeliness_name,ts)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		FileInputStream is = null;
		
		try {
			//得到prepareStatement对象
			ps = conn.prepareStatement(sql);
			//拼接sql
			if(vo != null){
				String pk = vo.getPk_ext_regulation();
				if(StringUtil.isNotEmpty(pk)){//主键
					ps.setString(1, pk);
				}else{
					ps.setString(1, PrimaryKeyUtil.getPrimaryKey());
				}
				String refNumber = vo.getRef_number();//发文文号
				if(StringUtil.isNotEmpty(refNumber)){
					ps.setString(2, refNumber);
				}else{
					ps.setString(2, "");
				}
				String pk_ext_regulation_dept = vo.getPk_ext_regulation_dept();
				if(StringUtil.isNotEmpty(pk_ext_regulation_dept)){//发文部门编码
					ps.setString(3, pk_ext_regulation_dept);
				}else{
					ps.setString(3, "");
				}
				String pk_regulation_level_effect = vo.getPk_regulation_level_effect();//发文效力级别主键
				if(StringUtil.isNotEmpty(pk_regulation_level_effect)){
					ps.setString(4, pk_regulation_level_effect);
				}else{
					ps.setString(4, "");
				}
				String pk_grc_regulation_timeliness = vo.getPk_grc_regulation_timeliness();
				if(StringUtil.isNotEmpty(pk_grc_regulation_timeliness)){//发文时效性主键
					ps.setString(5, pk_grc_regulation_timeliness);
				}else{
					ps.setString(5, "");
				}
				String issue_date = vo.getIssue_date();//发文日期
				if(StringUtil.isNotEmpty(issue_date)){
					ps.setString(6, issue_date);
				}else{
					ps.setString(6, "");
				}
				String impl_date = vo.getIssue_date();
				if(StringUtil.isNotEmpty(impl_date)){//实施日期
					ps.setString(7, impl_date);
				}else{
					ps.setString(7, "");
				}
				//文档内容
				is = new FileInputStream(file);
				if(is != null){
					ps.setBinaryStream(8, is, is.available());//设置为二进制
				}
				
				String regu_explain = vo.getRegu_explain();
				if(StringUtil.isNotEmpty(regu_explain)){//文档说明
					ps.setString(9, vo.getPk_ext_regulation());
				}else{
					ps.setString(9, "");
				}
				//创建人，创建时间，时间戳
				ps.setString(10, XbkjCommonUtil.getSysUserId());
				ps.setString(11, DateUtil.getCurrDateTime());
				ps.setString(16, DateUtil.getCurrDateTime());
				//文件名称
				
				String filename = file.getName();
				if(StringUtil.isNotEmpty(filename)){//文件名称
					ps.setString(12, filename);
				}else{
					ps.setString(12, "");
				}
				String regulation_dept_name = vo.getRegulation_dept_name();
				if(StringUtil.isNotEmpty(regulation_dept_name)){//发文部门名称
					ps.setString(13, regulation_dept_name);
				}else{
					ps.setString(13, "");
				}
				String level_effect_name = vo.getLevel_effect_name();//效力级别名称
				if(StringUtil.isNotEmpty(level_effect_name)){
					ps.setString(14, level_effect_name);
				}else{
					ps.setString(14, "");
				}
				String timeliness_name = vo.getTimeliness_name();
				if(StringUtil.isNotEmpty(timeliness_name)){//时效性名称
					ps.setString(15, timeliness_name);
				}else{
					ps.setString(15, "");
				}
			}
			
			if(ps != null){
				ps.execute();//执行
			}else{
				sb.append(" 文件 " + file.getName() + " 导入失败  ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			sb.append(" " + e.getMessage());
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
					sb.append(e.getMessage());
				}
			}
		}
	}
	
	
}
