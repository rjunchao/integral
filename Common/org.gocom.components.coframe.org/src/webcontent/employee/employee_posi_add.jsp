<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<!-- 
  - Author(s): YANGZHOU
  - Date: 2013-03-01 17:43:27
  - Description:
-->
<%@page import="com.eos.foundation.eoscommon.ResourcesMessageUtil"%>
<%
	// 机构条件查询
	String orgConditionQuery = ResourcesMessageUtil.getI18nResourceMessage("orgConditionQuery"); 
	// 机构代码
	String orgCode = ResourcesMessageUtil.getI18nResourceMessage("orgCode"); 
	// 机构名称
	String orgName = ResourcesMessageUtil.getI18nResourceMessage("orgName"); 
	// 机构类型
	String orgType = ResourcesMessageUtil.getI18nResourceMessage("orgType"); 
 	// 机构等级
	String orgLevel = ResourcesMessageUtil.getI18nResourceMessage("orgLevel"); 
 	// 机构层级
	String orgDegree = ResourcesMessageUtil.getI18nResourceMessage("orgDegree"); 
 	// 机构状态
	String orgStatus = ResourcesMessageUtil.getI18nResourceMessage("orgStatus"); 
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/coframe/tools/skins/common.jsp" %>
<link id="css_icon" rel="stylesheet" type="text/css" href="<%=contextPath%>/coframe/org/css/org.css"/>
<title>岗位人员</title>
<body>
<div class="search-condition">
 <div class="list">
   <div id="form1">
	<input class="nui-hidden" name="positionid" />
	<table style="width:100%;table-layout:fixed;" class="table" >
		<tr>
			<td class="tit" style="width:15%;">登录名：</td>
			<td style="width:25%;">
				<input class="nui-textbox" name="userid" style="width:95%" vtype="maxLength:30"/>
			</td>
			<td class="tit" style="width:15%;">人员姓名：</td>
			<td style="width:25%;">
				<input class="nui-textbox" name="empname" style="width:95%" vtype="maxLength:50"/>
			</td>
			<td class="btn-wrap">
				<input class="nui-button" text="查询" onclick="search" iconCls="icon-search"/>
			</td>
		</tr>					
	</table>
   </div>
 </div>
</div>

<div class="nui-fit" style="padding-top:5px;">
	<div id="datagrid1" class="nui-datagrid" style="width:100%;height:100%;" borderStyle="border-left:0;border-right:0;" 
	    url="org.gocom.components.coframe.org.employee.queryEmpAllowAddInPosition.biz.ext" ajaxData="getAjaxData" sizeList="[2,10,20,50,100]" multiSelect="true">
	    <div property="columns">
	        <div type="checkcolumn"></div>
	       <div field="empname" width="120" headerAlign="center" allowSort="true">人员姓名</div>
	        <div field="empcode" width="120" headerAlign="center" allowSort="true">人员代码</div>
	        <div field="userid" width="120" headerAlign="center" allowSort="true">登录名</div>
	     </div>
	</div>
</div>

 <div class="nui-toolbar" style="border:0;">
    <table style="width:100%;">
        <tr>
            <td style="text-align:center;width:100%;">
                <a class="nui-button" iconCls="icon-ok" onclick="add">确定</a>
            	<a class="nui-button" iconCls="icon-cancel" onclick="cancel">取消</a>
            </td>
        </tr>
    </table>
</div>

<script type="text/javascript">
	nui.parse();
	
	var form = new nui.Form("#form1");
	var grid = nui.get("datagrid1");
    
	function search(){
		//校验
		form.validate();
        if (form.isValid()==false) return;
        
		var formData = form.getData(false, true);
		
		//var json = nui.encode(formData);
        grid.load(formData);
	}
	
	function add(){
		var rows = grid.getSelecteds();
		var node = window.parentNode||{};
		if(rows && rows.length>0){
			var empPositions = [];
			for(var i=0,len=rows.length;i<len;i++){
				var empPosition = {orgEmployee:{empid:rows[i].empid},orgPosition:{positionid:node.positionid}};
				empPositions.push(empPosition);
			}
			var json = nui.encode({empPositions:empPositions});
			grid.loading("操作中，请稍后......");
            $.ajax({
                url: "org.gocom.components.coframe.org.position.addEmpposition.biz.ext",
                type: 'POST',
                data: json,
                cache: false,
                contentType:'text/json',
                success: function (text) {
                   //grid.reload();
                    CloseWindow("ok");
                },
                error: function () {
                }
            });
		}
	}
	
	function cancel() {
		CloseWindow("cancel");
	}
	
	function SetData(data){
		var data = nui.clone(data);
		form.setData({positionid:data.parentNode.positionid});
		window['parentNode'] = data.parentNode;
		grid.load(form.getData());
	}
	
	function resetForm(){
		form.reset();
	}
	
    function getAjaxData(){
    	return form.getData();
    }   
    
    function CloseWindow(action) {            
        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
        else window.close();            
    }   
	
</script>

</body>
</html>