<%@page import="com.primeton.bfs.engine.json.JSONObject"%>
<%@page import="com.vbm.common.util.MapUtil"%>

<%@page import="com.vbm.grc.common.MsgResponse"%>
<%@page import="com.vbm.grc.common.FileManager"%>
<%@page import="java.util.Map"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>

	

<% 	
	FileManager upload = new FileManager();
	Map<String, Object> map = upload.upload(request, response);
	//设置返回的编码
	response.setCharacterEncoding("utf-8");
	//设置返回类型为JSON对象
	response.setContentType("application/json;charset=utf-8");
	//获取返回值
	if(MapUtil.isNotEmpty(map)){
		MsgResponse msg = (MsgResponse)map.get("msg");
		String pk = (String)map.get("pk");
		//返回json对象
		//使用JSONObject 进行封装json数据
		JSONObject json = new JSONObject();
		json.append("pk", pk);
		json.append("flag", msg.isFlag());
		json.append("msg", msg.getMessage());
		String jsonStr = json.toString();
		//输出到客户端
		response.getWriter().append(jsonStr);
	}
	
	
%>
