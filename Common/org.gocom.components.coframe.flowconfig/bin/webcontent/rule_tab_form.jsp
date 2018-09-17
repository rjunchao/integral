<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<%@include file="/coframe/tools/skins/common.jsp" %>
<!-- 
  - Author(s): wanghl
  - Date: 2013-03-04 17:09:58
  - Description:
-->
<head>
<title>Title</title>
</head>
<body>
<%
String procId = request.getParameter("procId");
String actId = request.getParameter("actId");
String param = "?proc/procId=" + procId;
	param+="&proc/actId=" + actId;
String url = "org.gocom.components.coframe.flowconfig.processMgr.getCapRules.biz.ext";
%>

	<div class="nui-toolbar" style="border-bottom:0;padding:2px;">
        <table style="width:100%;">
            <tr>
                <td style="width:100%;">
                    <a class="nui-button" iconCls="icon-add" onclick="add()">增加</a>
                    <a class="nui-button" iconCls="icon-edit" onclick="edit()">编辑</a>
                    <a class="nui-button" iconCls="icon-remove" onclick="remove()">删除</a>       
                </td>
                <td style="white-space:nowrap;">
                <input id="key" class="nui-textbox" emptyText="请输入规则名称" onenter="onKeyEnter"/>   
                <a class="nui-button" iconCls="icon-search" onclick="search()">查询</a>
            </td>
            </tr>
        </table>           
    </div>
    <div class="nui-fit">
		<div id="datagrid1" class="nui-datagrid" url="<%=url %>"  idField="ruleId" allowResize="false"
			    sizeList="[5,10,20,30,50]" pageSize="5" style="height:100%;width:100%;" ajaxData="setGridData"
			>
		    <div property="columns">
		        <div type="checkcolumn" width="10"></div>
		        <div field="ruleId" width="30" >规则ID</div>
		        <div field="ruleName" width="20" headerAlign="center" allowSort="true">规则名称</div>
		        <div field="ruleType" width="20" headerAlign="center" allowSort="true">规则类型</div> 
		        <div field="namespace" width="80" headerAlign="center" allowSort="true">命名空间</div> 
		        <div field="createuser" width="20" headerAlign="center" allowSort="true">创建者</div> 
		        <div field="createtime" width="20" headerAlign="center" allowSort="true">创建时间</div>     
		    </div>
		</div>
	</div>
</body>
<script type="text/javascript">
	nui.parse();
	
	var grid = nui.get("datagrid1");
    grid.load();
    grid.hideColumn(1);
    
    function setGridData(){
    	return {proc:{"procId": "<%=procId %>", "actId": "<%=actId %>"}};
    }

	var ruleName = "";
	function add(){
		mini.prompt("请输入规则名：", "新建规则",
            function (action, value) {
                if (action == "ok") {
                    ruleName = value;
                    var checkResult = checkRuleName(ruleName);
                    if(checkResult == 0){
                    	openAddCondition();
                    }else{
                    	add();
                    }
                } else {
                    return;
                }
            }
        );
	}
	
	//验证规则名称
	//返回0表示验证成功，可以用该名称；
	//返回1表示验证失败，不可以用该名称；
	function checkRuleName(ruleName){
		if(ruleName == ""){
		    alert("名称不能为空！"); 
		    return(1);
	  	}else if(ruleName.length >64){
	  		alert("名称长度不能超过64！"); 
	 	}else{
	 		var gridData = grid.getData();
	 		for(var i = 0; i < gridData.length; i++){
	 			if(gridData[i].ruleName == ruleName){
	 				alert("该名称已存在！");
	 				return(1);
	 			}
	 		}
	 		return(0);
	    }
	}
	
	//打开添加规则页面
	function openAddCondition(){
		var iruleName = ruleName;
		nui.open({
			url: "<%=contextPath%>/coframe/flowconfig/process_form_auth.jsp",
			title: "添加规则", width: "695px", height: "500px",
			onload: function () {
                var iframe = this.getIFrameEl();
                var data = {ruleName: iruleName, createFlag: "1", proc: {"procId": "<%=procId %>", "actId": "<%=actId %>"}};
                iframe.contentWindow.loadData(data);
            },
            ondestroy: function (action) {
                grid.reload();
            }
		});
	}
	
	//编辑规则
	function edit(){
		var row = grid.getSelected();
    	if (row) {
    		var ruleId = row.ruleId;
    		var ruleName = row.ruleName;
    		var ruleType = row.ruleType;
    		var namespace = row.namespace;
    		
    		nui.open({
				url: "<%=contextPath%>/coframe/flowconfig/process_form_auth.jsp",
				title: "更新规则", width: "695px", height: "500px",
				onload: function () {
	                var iframe = this.getIFrameEl();
	                var data = {ruleId: ruleId, ruleName: ruleName, ruleType: ruleType, namespace: namespace, proc: {"procId": "<%=procId %>", "actId": "<%=actId %>"}};
	                iframe.contentWindow.loadData(data);
	            },
	            ondestroy: function (action) {
	                grid.reload();
	            }
			});
    	}else{
    		alert("请选择一条规则");
    	}
		
	}
	
	//删除规则
	function remove(){
		var row = grid.getSelected();
    	if (row) {
    		var data = {"ruleId": row.ruleId};
    		mini.confirm("确定删除记录？", "确定？",
	            function (action) {            
	                if (action == "ok") {
	                    $.ajax({
			    			url: "org.gocom.components.coframe.flowconfig.processMgr.deleteRule.biz.ext",
			    			type: "POST",
			    			data: nui.encode(data),
			    			contentType:'text/json',
			    			success: function(text){
			    				var json = nui.decode(text);
			    				if(json.ret == 0){
			    					alert("删除成功！");
			    					grid.reload();
			    				}
			    			}
			    		});
	                } else {
	                    return;
	                }
	            }
	        );
    	}else{
    		alert("请选择一条规则");
    	}
	}
	
	
	function search(){
		var key = nui.get("key").getValue();
        grid.load({ "criteria/_expr[1]/ruleName": key ,"criteria/_expr[1]/_op":"like", "criteria/_expr[1]/_likeRule":"all"});
	}
</script>
</html>