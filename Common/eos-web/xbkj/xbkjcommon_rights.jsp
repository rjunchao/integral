<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/common/common.jsp"%>
<%@include file="/coframe/tools/skins/common.jsp" %>
<%@include file="/common/skins/skin0/component.jsp" %>

<%@include file="/common/skins/skin0/component.jsp" %>
<%@page import ="com.eos.data.datacontext.UserObject" %>
<%@page import ="java.util.Date" %>
<%@page import ="java.text.DateFormat" %>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.eos.foundation.common.utils.DateUtil"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.eos.common.connection.ConnectionHelper"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.sql.ResultSet"%>
<script type="text/javascript" src="<%=contextPath%>/common/nui/locale/zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/nui/nui.js"  pageEncoding="UTF-8"  charset="UTF-8"></script>
    <link href="../demo.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
    </style> 

<html>
<!-- 
  - Author(s): wangnt
  - Date: 2015-02-02 13:00:28
  - Description:
-->
<head>
<title>Title</title>
</head>
<!-- 当前登陆相关信息 -->
<%
    	UserObject currUser =(UserObject)session.getAttribute("userObject");
    	String isinput = null;
    	String isManager = null;
    	//当前用户所在机构
    	String currOrgid = currUser.getUserOrgId();
    	//out.println("==1===="+currOrgid);
    	if( "".equals(currOrgid) || "null".equals(currOrgid)){
    		currOrgid ="0";
    	}
    	//out.println("==2===="+currOrgid);
    	//当前用户ID
    	String currUserid = currUser.getUserId();
    	//查询出当前用户的查询权限
    	/* if(null == isinput){
    		Connection conn = ConnectionHelper
				.getCurrentContributionConnection("default");
			Statement stmt = null;
			Map<String,String> map = new HashMap<String,String>();
			String sql = "select auth from org_employee where empid = "+currUserid+" ";
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					map = new HashMap<String,String>();
					map.put("auth", rs.getString("auth"));			
				}				
			}catch(Exception e){
				e.printStackTrace();
			}finally {
				try {
					stmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			isinput = map.get("auth");
    	} */
    	//查询审批主管
    	/* if(null == isManager){
    		Connection conn = ConnectionHelper
				.getCurrentContributionConnection("default");
			Statement stmt = null;
			Map<String,String> mapb = new HashMap<String,String>();
			String sql = "select manager_leader from mas_team_b where empid = "+currUserid+" ";
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					mapb = new HashMap<String,String>();
					mapb.put("auth", rs.getString("manager_leader"));			
				}				
			}catch(Exception e){
				e.printStackTrace();
			}finally {
				try {
					stmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			isManager = mapb.get("auth");
    	}    	     */	 
    	Date d = new Date();
    	/* Date ts=DateUtil.getJVMDate();
    	String createTime=DateUtil.format(ts, "yyyy-MM-dd HH:mm:ss");
    	System.out.print(createTime); */
    	//当前时间 -'yyyy-MM-dd'
    	//String currDate =DateFormat.getDateInstance(DateFormat.DEFAULT).format(d);
    	//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    	SimpleDateFormat sdfData = new SimpleDateFormat("yyyy-MM-dd");
    	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM"); 
        String currDateTime = sdf.format(d);
        String currDate = sdfData.format(d);
        String dateMon = sdfDate.format(d);
        //System.out.print(currDate);
    	SimpleDateFormat month = new SimpleDateFormat("MM");   
        String monthDate = month.format(d);
        int treewidth=350;
        int onecolomnwidth=400;
        int twocolomnwidth=450;
        int threecolomnwidth=500;
        String ReportIP="192.168.120.91:9300";
 %>


<body>
</body>
<script>

</script>
</html>