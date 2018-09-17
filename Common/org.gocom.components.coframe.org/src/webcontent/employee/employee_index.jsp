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
<style>
#table1 .tit{
	height: 10px;
    line-height: 10px;
}
#table1 td{
	height: 10px;
    line-height: 10px;
}
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/coframe/tools/skins/common.jsp" %>
<link id="css_icon" rel="stylesheet" type="text/css" href="<%=contextPath%>/coframe/org/css/org.css"/>
<title>机构列表</title>
<body>
<div class="search-condition">
  <div class="list">
    <div id="form1" style="padding:5px;">
	  <table style="width:100%;table-layout:fixed;" id="table1" class="table" >
		<tr>
			<td class="tit">人员姓名：</td>
			<td>
				<input class="nui-textbox" name="criteria._expr[1].empname" style="width:80%" vtype="maxLength:50"/>
				<input class="nui-hidden" name="criteria._expr[1]._op" value="like"/>
				<input class="nui-hidden" name="criteria._expr[1]._likeRule" value="all"/>
			</td>
			<td class="tit">人员代码：</td>
			<td>
				<input class="nui-textbox" name="criteria._expr[2].empcode" style="width:80%" vtype="maxLength:30"/>
				<input class="nui-hidden" name="criteria._expr[2]._op" value="like"/>
				<input class="nui-hidden" name="criteria._expr[2]._likeRule" value="all"/>
			</td>
			<td rowspan="2" class="btn-wrap">
				<input class="nui-button" text="查询" iconCls="icon-search" onclick="search"/>
			</td>
		</tr>					
		<tr>
			<td class="tit">用户登录名：</td>
			<td>
				<input class="nui-textbox" name="criteria._expr[3].userid" style="width:80%" vtype="maxLength:30"/>
				<input class="nui-hidden" name="criteria._expr[3]._op" value="="/>
			</td>
			<td class="tit">所属机构：</td>
			<td>
				<input name="criteria._expr[4].orgid" textName="orgname" class="nui-buttonedit searchbox" onbuttonclick="selectOrg" style="width:80%" allowinput="false"/>
				<input class="nui-hidden" name="criteria._expr[4]._op" value="="/>
			</td>
		</tr>	
	  </table>
    </div>
  </div>
</div>
<div class="nui-fit" style="padding:5px;">
	<div id="datagrid1" class="nui-datagrid" style="width:100%;height:100%;" 
	    url="org.gocom.components.coframe.org.employee.queryEmployee.biz.ext" dataField="treeNodes" sizeList="[10,20,50,100]">
	    <div property="columns">
	        <div type="indexcolumn"></div>                
	        <div field="empname" width="120" headerAlign="center" >人员姓名</div>    
	        <div field="empcode" width="120" headerAlign="center" >人员代码</div>    
	        <div field="gender" width="120" headerAlign="center" renderer="renderGender" >性别</div>    
	        <div field="empstatus" width="120" headerAlign="center" renderer="renderEmpStatus">员工状态</div>    
	        <div field="userid" width="120" headerAlign="center" >用户登录名</div>    
	     </div>
	</div>
</div>

<script type="text/javascript">
	nui.parse();
	
	var form = new nui.Form("#form1");
	var grid = nui.get("datagrid1");
    
    grid.load();
	
	function search(){
		//校验
		form.validate();
        if (form.isValid()==false) return;
        
		var formData = form.getData(false, true);
		
		//var json = nui.encode(formData);
        grid.load(formData);
	}
	
	function resetForm(){
		form.reset();
	}
	var bootPath = "<%=request.getContextPath() %>";
	
	 //选择机构
    function selectOrg(e) {
        var btnEdit = this;
        nui.open({
            url: bootPath + "/coframe/org/employee/select_org_tree.jsp",
            showMaxButton: false,
            title: "选择机构",
            width: 350,
            height: 350,
            ondestroy: function(action){
                if (action == "ok") {
                    var iframe = this.getIFrameEl();
                    var data = iframe.contentWindow.GetData();
                    data = nui.clone(data);
                    if (data) {
                        btnEdit.setValue(data.orgid);
                        btnEdit.setText(data.orgname);
                    }
                }
            }
        });            
    }
	
	function renderGender(e){
		return nui.getDictText("COF_GENDER",e.row.gender);
	}
	
	function renderEmpStatus(e){
		return nui.getDictText("COF_EMPSTATUS",e.row.empstatus);
	}
	
	function reset() {
		form.reset();
	}
</script>

</body>
</html>