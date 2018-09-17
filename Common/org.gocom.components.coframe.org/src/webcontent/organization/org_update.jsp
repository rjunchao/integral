<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): yangyong
  - Date: 2013-02-28 10:14:50
  - Description:
-->
<head>
<title>机构添加</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%request.setCharacterEncoding("UTF-8");%>
<script type="text/javascript" src="<%=contextPath%>/coframe/org/js/org_common.js"></script>
</head>
<body>
<div class="nui-fit" style="padding-top:5px;min-width:450px;min-height:300px;">
	<div id="form1" style="width:100%;height:99%;overflow:hidden;">
		<input class="nui-hidden" name="orgOrganization.orgid" />
		<input class="nui-hidden" name="orgOrganization.orgOrganization.orgid" />
		<input class="nui-hidden" name="orgOrganization.orgOrganization.orglevel" />
		<input class="nui-hidden" name="orgOrganization.orgOrganization.orgseq" />
		<table style="width:100%;height:100%;table-layout:fixed;" class="nui-form-table">
			<tr>
				<th class="nui-form-label"><label for="orgname$text">机构名称：</label></th>
				<td><input id="orgname" class="nui-textbox nui-form-input" name="orgOrganization.orgname" required="true" vtype="maxLength:64"/></td>
				<th class="nui-form-label"><label for="orgcode$text">机构代码：</label></th>
				<td><input id="orgcode" class="nui-textbox nui-form-input" name="orgOrganization.orgcode" onblur="" required="true" vtype="maxLength:32"/></td>
			</tr>					
			<tr class="odd">
				<th class="nui-form-label"><label for="orgParentname$text">上级机构：</label></th>
				<td><input id="orgParentname" class="nui-textbox nui-form-input" name="orgOrganization.orgOrganization.orgname" allowInput="false" /></td>
				<th class="nui-form-label"><label for="orgtype$text">机构类型：</label></th>
				<td><input id="orgtype" name="orgOrganization.orgtype" data="orgType"   class="nui-combobox" emptyText="请选择"  /></td>
			</tr>					
			<tr>
				<th class="nui-form-label"><label for="orgdegree$text">机构等级：</label></th>
				<td><input id="orgdegree" name="orgOrganization.orgdegree" data="orgDegree"  class="nui-combobox" emptyText="请选择"  />
				</td>
				<th class="nui-form-label"><label for="status$text">机构状态：</label></th>
				<td><input id="status" name="orgOrganization.status" data="orgStatus"  class="nui-combobox" emptyText="请选择"/>
			</tr>	
			<tr class="odd">
				<th class="nui-form-label"><label for="sortno$text">排列顺序：</label></th>
				<td><input id="sortno" class="nui-textbox nui-form-input" name="orgOrganization.sortno" vtype="int"/></td>
				<th class="nui-form-label"><label for="area$text">所属地域：</label></th>
				<td><input id="area" class="nui-textbox nui-form-input" name="orgOrganization.area"  vtype="maxLength:30"/></td>
			</tr>				
			<tr>
				<th class="nui-form-label"><label for="orgaddr$text">机构地址：</label></th>
				<td><input id="orgaddr" class="nui-textbox nui-form-input" name="orgOrganization.orgaddr"   vtype="maxLength:256"/></td>
				<th class="nui-form-label"><label for="zipcode$text">邮编：</label></th>
				<td><input id="zipcode" class="nui-textbox nui-form-input" name="orgOrganization.zipcode"  vtype="int;rangeLength:0,10"/></td>
			</tr>	
			<!-- 	
			<tr>
				<th class="nui-form-label"><label for="empname$text">机构主管岗位：</label></th>
				<td><input class="nui-textbox nui-form-input" name="orgOrganization.manaposition" /></td>
				<th class="nui-form-label"><label for="empname$text">机构主管人员：</label></th>
				<td><input class="nui-textbox nui-form-input" name="orgOrganization.orgmanager" /></td>
			</tr>	
			 -->			
			<tr class="odd">
				<th class="nui-form-label"><label for="linkman$text">联系人：</label></th>
				<td><input id="linkman" class="nui-textbox nui-form-input" name="orgOrganization.linkman"  vtype="maxLength:30"/></td>
				<th class="nui-form-label"><label for="linktel$text">联系电话：</label></th>
				<td><input id="linktel" class="nui-textbox nui-form-input" name="orgOrganization.linktel"  vtype="phone;rangeLength:0,20"/></td>
			</tr>				
			<tr>
				<th class="nui-form-label"><label for="email$text">电子邮件：</label></th>
				<td><input id="email" class="nui-textbox nui-form-input" name="orgOrganization.email" vtype="email;rangeLength:0,128" /></td>
				<th class="nui-form-label"><label for="weburl$text">网站地址：</label></th>
				<td><input id="weburl" class="nui-textbox nui-form-input" name="orgOrganization.weburl" vtype="url;rangeLength:0,512" /></td>
			</tr>				
			<tr class="odd">
				<th class="nui-form-label"><label for="startdate$text">生效日期：</label></th>
				<td><input id="startdate" name="orgOrganization.startdate" class="nui-datepicker nui-form-input" /></td>
				<th class="nui-form-label"><label for="enddate$text">失效日期：</label></td>
				<td><input id="enddate" name="orgOrganization.enddate" class="nui-datepicker nui-form-input" onvalidation="onEnddateValidation" />
				</td>
			</tr>				
			<tr>
				<th class="nui-form-label"><label for="remark$text">备注：</label></th>
				<td colspan="3"><input id="remark" name="orgOrganization.remark" class="nui-textarea nui-form-input" style="width:95%;"  vtype="maxLength:512"/></td>
			</tr>	
		</table>
	</div>
  </div>
  <div class="mini-toolbar" borderstyle="border:0;" style="text-align: center; padding-top: 5px; padding-bottom: 5px; border: 0px none;">
    <a class="nui-button"  style="width:60px;" iconCls="icon-save" onclick="update">保存</a>
    <span style="display:inline-block;width:25px;"></span>
    <a class="nui-button" id="resetBtn_01" style="width:60px;" iconCls="icon-reset" onclick="resetForm">重置</a>
    <span style="display:inline-block;width:25px;"></span>
    <a class="nui-button" id="cancelBtn_01" iconCls="icon-cancel" style="width:60px;display:none;" onclick="cancel">取消</a>
  </div>

<script type="text/javascript">
	var orgStatus= [{ id:'running', text: '正常' }, { id:'cancel', text: '注销'}];
	var orgDegree= [{ id:'hq', text: '总行' }, { id:'branch', text: '分行'}, { id:'oversea', text: '海外分行'}];
	var orgType= [{ id:'h', text: '总公司' }, { id:'hd', text: '总公司部门'}, { id:'b', text: '分公司'}, { id:'bd', text: '分公司部门'}];
	nui.parse();
    var form = new nui.Form("form1");
	
	(function(){
		if(window.parent.getCurrentNode){
			var node = window.parent.getCurrentNode();
			window['parentNode'] = node;
		}
	})();
	
    function update() {
        var o = form.getData(true,true);            
        form.validate();
        if (form.isValid() == false) return;
        var json = nui.encode(o);
        $.ajax({
            url: "org.gocom.components.coframe.org.organization.updateOrg.biz.ext",
            type: 'POST',
            data: json,
            cache: false,
            contentType:'text/json',
            success: function (text) {
            	var response = text.response;
            	if(response && response.flag && window.isCloseWindow){
            		CloseWindow("ok");
            	}else{
            		nui.alert(response.message);
            		window['formData'] = o;
            		if(response.flag && window.parent){
            			window.parent.refreshParentNode();
            		}
            	}
            },
            error: function (jqXHR, textStatus, errorThrown) {
                nui.alert(jqXHR.responseText);
                CloseWindow();
            }
        });
    }

    ////////////////////
    //标准方法接口定义
    function SetData(data) {
    	if(data.action=="update"){
    		window.isCloseWindow = true;
    		showCancelBtn();
    		$("#form1").css("height","100%");
    	}
        //跨页面传递的数据对象，克隆后才可以安全使用
        data = nui.clone(data);
		var json = nui.encode({template:data});
        $.ajax({
            url: "org.gocom.components.coframe.org.organization.getOrgWithParent.biz.ext",
            type: 'POST',
            data: json,
            cache: false,
            contentType:'text/json',
            cache: false,
            success: function (mydata) {
                var o = nui.decode(mydata);
                form.setData(o);
                window['formData'] = o;
            }
        });
    }

    function CloseWindow(action) {
        if (action == "close" && form.isChanged()) {
            if (confirm("数据被修改了，是否先保存？")) {
                return false;
            }
        }
        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
        else window.close();
    }
    function cancel(e) {
    	if(window.isCloseWindow){
	        CloseWindow("cancel");
    	}
    }
	
	//校验日期
	//失效日期必须大于生效日期
	function onEnddateValidation(e){
       	var o = form.getData();
       	var org = o.orgOrganization || {};
		if(org.enddate && org.startdate && org.enddate<=org.startdate){
			e.errorText = "失效日期必须大于生效日期";
			e.isValid = false;
		}
	}
	
     function showCancelBtn(){
    	$("#cancelBtn_01").show();
    	$("#resetBtn_01").hide();
    }
    
    function resetForm(){
		var data = window['formData'];
		if(data){
			form.setData(data);
		}else{
			form.reset();
		}
	}
    
</script>

</body>
</html>