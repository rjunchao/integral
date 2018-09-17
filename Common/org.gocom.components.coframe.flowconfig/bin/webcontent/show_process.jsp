<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://eos.primeton.com/tags/workflow" prefix="wf"%>
<html>
<%@include file="/coframe/tools/skins/common.jsp" %>
<!-- 
  - Author(s): wanghl
  - Date: 2013-02-28 15:02:33
  - Description:
-->
<head>
<title>showProcess</title>
<script src="<%=request.getContextPath()%>/workflow/wfcomponent/web/js/Graphic.js"></script>
</head>
<body>
<%
String procId = request.getParameter("procId");
String procName = request.getParameter("procName");
String pchName = request.getParameter("pchName");
String version = request.getParameter("version");
%>

<table style="width: 100%;height: 100%;border: 1px">
	 <tr style="height: 10%">
		 <td style="width: 100%;text-align: right;padding-right: 30px;font-size:14px;">
		 	当前流程:<%=procName %>(<%=version %>)
		 </td>
	 </tr>
	 <tr style="height: 90%;vertical-align: middle;">
		 <td style="width: 100%;overflow: scroll;">
			<wf:processGraph onmouseover="this.style.cursor='pointer'" onclick="clickActivity(this)" processName='<%=procName %>' />
		 </td>
 	 </tr>
 </table>
 	
</body>

</body>
<script type="text/javascript">
	nui.parse();

	var actId = "";
	
	function clickActivity(obj){
		actId=obj.getAttribute("activitydefid");
		var data = {"procId": "<%=procId %>", "actId": actId};
		var json = nui.encode({proc:data});
		$.ajax({
			url: "org.gocom.components.coframe.flowconfig.processMgr.checkIsSubForm.biz.ext",
			type: "POST",
			cache: false,
			data: json,
			contentType: 'text/json',
			success: function (text) {
				var data = text.retProc.ret;
				if(data == 1){ //是子流程
					showSubProcess(text.retProc);
				}else{
					openProcessAuth();
				}
			}
		});
	}
	
	function showSubProcess(proc){
		var procId = proc.procId;
		var procName = proc.procName;
		var pchName = proc.pchName;
		var version = proc.version;
		var param="?procId="+procId;
		param += "&procName="+procName;
		param+="&pchName="+pchName;
		param+="&version='"+version + "'";
		
		window.location.href= "<%=contextPath%>/coframe/flowconfig/show_process.jsp" + param;
	}
	
	
	function openProcessAuth(){
		var param="?procId="+ "<%=procId %>";
		param+="&procName="+ "<%=procName %>";
		param+="&pchName="+ "<%=pchName %>";
		param+="&version="+ "<%=version %>";
		param+="&actId=" + actId;
		
		nui.open({
            url: "<%=contextPath%>/coframe/flowconfig/process_auth.jsp" + param,
            title: "规则配置", width: 650, height: 500,
            onload: function(){
            	var iframe = this.getIFrameEl();
                iframe.contentWindow.loadTabs();
            }
            
        });
	}
</script>
</html>


