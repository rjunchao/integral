<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<%@include file="/coframe/tools/skins/common.jsp" %>
<!-- 
  - Author(s): wanghl
  - Date: 2013-03-03 12:02:07
  - Description:
-->
<head>
<title>Title</title>
</head>
<body>
<%
String procId = request.getParameter("procId");
String procName = request.getParameter("procName");
String pchName = request.getParameter("pchName");
String version = request.getParameter("version");
String actId = request.getParameter("actId");
%>
<div class="nui-fit" style="padding:5px;">
	<div id="tabs1" class="nui-tabs" activeIndex="0" style="width:100%;height:100%;"
	     titleField="name"
	>        
	</div>
</div>

</body>
<script type="text/javascript">
	nui.parse();
	var tabs = nui.get("tabs1");
	
	function setUrlParam(url,params){
		if(!url){
			return url;
		}
		var paramsStr = [];
		for(var prop in params){
			paramsStr.push(prop + "=" + params[prop]);
		}
		if(url.indexOf("?")>=0){
			return url + "&" + paramsStr.join("&");
		}else{
			return url + "?" + paramsStr.join("&");
		}
	}

	function loadTabs(){
		var param="?procId="+ "<%=procId %>";
		param += "&procName="+ "<%=procName %>";
		param+="&pchName="+ "<%=pchName %>";
		param+="&version="+ "<%=version %>";
		param+="&actId=" + "<%=actId %>";
		
		var tabsData = [{"id":"1", "name":"权限", "url":"<%=contextPath%>/coframe/flowconfig/rule_tab_form.jsp", "order":"1"}];
		
		var data = {"procId": "<%=procId %>", "actId": "<%=actId %>"};
		var json = nui.encode({proc:data});
		
		$.ajax({
			url:"org.gocom.components.coframe.flowconfig.processMgr.checkHasForm.biz.ext",
			type: 'POST',
			cache: false,
			contentType: 'text/json',
			data: json,
			success: function(text){
				var retData = nui.decode(text);
				if((!retData.retProc.formId) || retData.retProc.formId == "null"){
					tabsData[0].url = "<%=contextPath%>/coframe/flowconfig/act_no_form.jsp";
				}
				else{
					tabsData[0].url = tabsData[0].url + param;
				}
				tabs.setTabs(tabsData);
			}
		});
		
		/*$.ajax({
			url:"org.gocom.components.coframe.flowconfig.processMgr.getAuthTabPages.biz.ext",
			type:'POST',
			cache: false,
            contentType: 'text/json',
			success: function (text) {
				var json = nui.decode(text);
				var arrays = json.tabPages;
				//for(var i = 0; i < arrays.length; i++){
					//var obj = arrays[i];
					//obj.url = setUrlParam(obj.path,node);
				//}
				arrays[0].url = arrays[0].url +　param;
				var data = [{"id":"1", "title":"权限", "url":"ruleTabForm.jsp", "order":"1"}];
				data[0].url = data[0].url + param;
				tabs.setTabs(data);
			}
		});*/
	
	}

	

</script>
</html>