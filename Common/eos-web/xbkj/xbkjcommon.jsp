<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/common/common.jsp"%>
<%@include file="/coframe/tools/skins/common.jsp" %>
<%@include file="/common/skins/skin0/component.jsp" %>

<%@include file="/common/skins/skin0/component.jsp" %>
<%@page import ="com.eos.data.datacontext.UserObject" %>
<%@page import ="com.vbm.common.util.DateUtil" %>

<script type="text/javascript" src="<%=request.getContextPath()%>/common/nui/locale/zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/nui/nui.js"  pageEncoding="UTF-8"  charset="UTF-8"></script>
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
		String pkCardDefine = "ff80808151c21e880151c74bb4500022";   //零售方案下的统计模型卡片主键，用于在调整、审核页面过滤该卡片对应客户（不支持调整、审核）
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
    	if(null == isManager){/* 
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
    	 */}    	    	 
    	/* Date ts=DateUtil.getJVMDate();
    	String createTime=DateUtil.format(ts, "yyyy-MM-dd HH:mm:ss");
    	System.out.print(createTime); */
    	//当前时间 -'yyyy-MM-dd'
    	//String currDate =DateFormat.getDateInstance(DateFormat.DEFAULT).format(d);
    	//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currDateTime = DateUtil.getCurrDateTime();
        String currDate = DateUtil.getCurrDate();
        String dateMon = String.valueOf(DateUtil.getMonthLastDay(currDate));
        //System.out.print(currDate);
        String monthDate = DateUtil.getCurrMonth();
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