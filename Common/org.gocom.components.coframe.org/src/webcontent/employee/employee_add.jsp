<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<!-- 
  - Author(s): yangyong
  - Date: 2013-02-28 10:14:50
  - Description:
-->
<head>
<title>员工添加</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<%@include file="/coframe/tools/skins/common.jsp" %>
<style type="text/css">
    fieldset {
        border:solid 1px #aaa;
    }        
    .hideFieldset {
        border-left:0;
        border-right:0;
        border-bottom:0;
    }
    .hideFieldset .fieldset-body {
        display:none;
    }
    
</style>
</head>
<body>


<div class="nui-fit" style="padding-top:5px">
<form id="form">
<div id="tab1" class="nui-tabs" style="padding:0">
	<div title="基本信息"  showCloseButton="false" style="border-buttom:0px;">
		<div id="form1">
			<input class="nui-hidden" name="org.orgid" />
			<table style="width:100%;table-layout:fixed;" class="nui-form-table" >
				<tr>
					<td class="nui-form-label"><label for="empname$text">员工姓名：</label></td>
					<td><input id="empname" class="nui-textbox" name="employee.empname" required="true" vtype="maxLength:50"/></td>
					<td class="nui-form-label"><label for="empcode$text">员工代码：</label></td>
					<td><input id="empcode" class="nui-textbox" name="employee.empcode" required="true" vtype="maxLength:30"/></td>
				</tr>
				<tr class="odd">
					<td class="nui-form-label"><label for="gender$text">性别：</label></td>
					<td><input id="gender" name="employee.gender" data="Gender" emptyText="请选择"  class="nui-combobox"  /></td>
					<td class="nui-form-label"><label for="birthdate$text">出生日期：</label></td>
					<td><input id="birthdate" name="employee.birthdate" class="nui-datepicker" /></td>
				</tr>				
				<tr>
					<td class="nui-form-label"><label for="cardtype$text">证件类型：</label></td>
					<td><input id="cardtype" name="employee.cardtype" data="cardType" emptyText="请选择"  class="nui-combobox"  /></td>
					<td class="nui-form-label"><label for="cardno$text">证件号码：</label></td>
					<td><input id="cardno" name="employee.cardno" class="nui-textbox" vtype="maxLength:20" /></td>
				</tr>				
				<tr class="odd">
					<td class="nui-form-label"><label for="indate$text">入职日期：</label></td>
					<td><input id="indate" name="employee.indate" class="nui-datepicker" /></td>
					<td class="nui-form-label"><label for="outdate$text">离职日期：</label></td>
					<td><input id="outdate" name="employee.outdate" class="nui-datepicker" onvalidation="onOutdateValidation" />
					</td>
				</tr>				
				<tr>
					<td class="nui-form-label"><label for="empstatus$text">人员状态：</label></td>
					<td><input id="empstatus" name="employee.empstatus" data="empStatus" emptyText="请选择"  class="nui-combobox"  /></td>
					<td class="nui-form-label"><label for="mobileno$text">手机号码：</label></td>
					<td><input id="mobileno" name="employee.mobileno" class="nui-textbox" vtype="maxLength:14" /></td>
				</tr>
			</table>
		</div>
		<div id="form3" style="padding-top:5px;">		
			<fieldset border="0">
		        <legend><label><input type="checkbox" checked id="userRefCheckbox" onclick="toggleFieldSet(this, 'form3')" hideFocus/>&nbsp;用户信息</label></legend>
		        <div class="fieldset-body">
		            <table style="width:100%;table-layout:fixed;" class="nui-form-table" >
						<tr>
							<td class="nui-form-label"><label for="userId$text">用户登录名：</label></td>
							<td><input id="userId" class="nui-textbox" name="user.userId" required="true" vtype="maxLength:30"/></td>
							<td class="nui-form-label"><label for="password$text">用户密码：</label></td>
							<td><input id="password" class="nui-password" name="user.password" required="true" vtype="rangeLength:6,100"/></td>
						</tr>	
						<tr class="odd">
							<td class="nui-form-label"><label for="userstatus$text">用户状态：</label></td>
							<td><input id="userstatus" name="user.status" data="userStatus" emptyText="请选择"  class="nui-combobox"  value="1" /></td>
							<td class="nui-form-label"><label for="menutype$text">菜单风格：</label></td>
							<td><input id="menutype" name="user.menutype" data="menuType" emptyText="请选择"  class="nui-combobox"  value="0" /></td>
						</tr>	
						<tr>
						    <td class="nui-form-label"><label for="authmode$text">认证模式：</label></td>
							<td><input id="authmode" name="user.authmode" data="authMode" emptyText="请选择"  class="nui-combobox"  value="local" /></td>
							<td class="nui-form-label"><label for="invaldate$text">密码失效日期：</label></td>
							<td><input id="invaldate" class="nui-datepicker" name="user.invaldate" /></td>
						</tr>	
						<tr class="odd">
							<td class="nui-form-label"><label for="startdate$text">有效开始时间：</label></td>
							<td><input id="startdate" name="user.startdate" class="nui-datepicker" /></td>
							<td class="nui-form-label"><label for="enddate$text">有效截止时间：</label></td>
							<td><input id="enddate" name="user.enddate" class="nui-datepicker" onvalidation="onEnddateValidation" /></td>
						</tr>
					</table>
		        </div>
		    </fieldset>
	    </div>
	</div>
	<div title="详细信息" showCloseButton="false" style="width:100%;border-buttom:0px;">
		<div id="form2">
			<table style="width:100%;table-layout:fixed;" class="nui-form-table" >				
				<tr>
					<td class="nui-form-label"><label for="ozipcode$text">办公室邮编：</label></td>
					<td><input id="ozipcode" name="employee.ozipcode" class="nui-textbox"  vtype="int;rangeLength:0,6"/></td>
					<td class="nui-form-label"><label for="oemail$text">办公室邮件：</label></td>
					<td><input id="oemail" name="employee.oemail" class="nui-textbox" vtype="email;rangeLength:0,128" /></td>
				</tr>				
				<tr class="odd">
					<td class="nui-form-label"><label for="faxno$text">传真号码：</label></td>
					<td><input id="faxno" name="employee.faxno" class="nui-textbox"  vtype="maxLength:14" /></td>
					<td class="nui-form-label"><label for="otel$text">办公室电话：</label></td>
					<td><input id="otel" name="employee.otel" class="nui-textbox"  vtype="phone;rangeLength:0,20"/></td>
				</tr>
				<tr>
					<td class="nui-form-label"><label for="qq$text">QQ号码：</label></td>
					<td><input id="qq" name="employee.qq" class="nui-textbox"  vtype="maxLength:16"/></td>
					<td class="nui-form-label"><label for="htel$text">家庭电话：</label></td>
					<td><input id="htel" name="employee.htel" class="nui-textbox" vtype="phone;rangeLength:0,20"/></td>
				</tr>
				<tr class="odd">
					<td class="nui-form-label"><label for="haddress$text">家庭地址：</label></td>
					<td colspan="3"><input id="haddress" name="employee.haddress" class="nui-textbox" style="width:80%;"  vtype="maxLength:128"/></td>
				</tr>
				<tr>
					<td class="nui-form-label"><label for="oaddress$text">办公地址：</label></td>
					<td colspan="3"><input id="oaddress" name="employee.oaddress" class="nui-textbox" style="width:80%;" vtype="maxLength:255"/></td>
				</tr>
				<tr class="odd">
					<td class="nui-form-label"><label for="weibo$text">微博：</label></td>
					<td colspan="3"><input id="weibo" name="employee.weibo" class="nui-textbox" style="width:80%;" vtype="maxLength:255"/></td>
				</tr>	
				<tr>
					<td class="nui-form-label"><label for="hzipcode$text">家庭邮编：</label></td>
					<td><input id="hzipcode" name="employee.hzipcode" class="nui-textbox" vtype="int;rangeLength:0,10"/></td>
					<td class="nui-form-label"><label for="pemail$text">私人邮箱：</label></td>
					<td><input id="pemail" name="employee.pemail" class="nui-textbox" vtype="email;rangeLength:0,128"/></td>
				</tr>	
				<tr class="odd">
					<td class="nui-form-label"><label for="party$text">政治面貌：</label></td>
					<td><input id="party" name="employee.party" data="Party" emptyText="请选择"  class="nui-combobox"  /></td>
					<td class="nui-form-label"><label for="degree$text">人员类型：</label></td>
					<td><input id="degree" name="employee.degree" data="Degree" emptyText="请选择" allowInput="false" class="nui-buttonEdit"  
					   onbuttonclick="selectEmptype"/></td>
				</tr>			
				<tr>
					<td class="nui-form-label"><label for="major$text">直接主管：</label></td>
					<td colspan="3"><input id="major" name="employee.major" textName="employee.major" allowInput="false" class="nui-buttonEdit" onbuttonclick="selectMajor"/></td>
				</tr>			
				<tr class="odd">
					<td class="nui-form-label"><label for="orgidlist$text">可管理机构：</label></td> 
					<td colspan="3"><input id="orgidlist" name="employee.orgidlist" textName="employee.orgidlist" allowInput="false" class="nui-buttonEdit" onbuttonclick="selectOrg" /></td>
				</tr>
				<tr>
					<td class="nui-form-label"><label for="specialty$text">可管理角色：</label></td>
					<td colspan="3"><input id="specialty" name="employee.specialty" textName="employee.specialty" allowInput="false" class="nui-buttonEdit" onbuttonclick="selectRole"/></td>
				</tr>	
				<tr class="odd">
					<td class="nui-form-label"><label for="workexp$text">工作描述：</label></td>
					<td colspan="3"><input id="workexp" name="employee.workexp" class="nui-textarea" style="width:80%;" vtype="maxLength:512"/></td>
				</tr>			
				<tr>
					<td class="nui-form-label"><label for="remark$text">备注：</label></td>
					<td colspan="3"><input id="remark" name="employee.remark" class="nui-textarea" style="width:80%;" vtype="maxLength:512"/></td>
				</tr>
			</table>
		</div>
	</div>
</div>
</form>
</div>
<div class="nui-toolbar" style="text-align:center;padding-top:5px;padding-bottom:5px;" 
    borderStyle="border:0;">
    <a class="nui-button"  style="width:60px;" iconCls="icon-save" onclick="add">保存</a>
    <span style="display:inline-block;width:25px;"></span>
    <a class="nui-button" id="cancelBtn_01" iconCls="icon-cancel" style="width:60px;" onclick="cancel">取消</a>
</div>

<script type="text/javascript">
	var Gender = [{ id:'m', text: '男' }, { id:'f', text: '女'}, { id:'n', text: '未知'}];
	var cardType = [{ id:'id', text: '身份证' }, { id:'junguan', text: '军官证'}, { id:'passport', text: '护照'}, { id:'student', text: '学生证'}, { id:'zhanzhu', text: '暂住证'}];
	var empStatus = [{ id:'leave', text: '离职' }, { id:'off', text: '退休'}, { id:'on', text: '在岗'}, { id:'wait', text: '待岗'}];
	var userStatus= [{ id:'0', text: '挂起' }, { id:'1', text: '正常'}, { id:'2', text: '锁定'}, { id:'9', text: '注销'}];
	var menuType = [{ id:'default', text: '默认布局' }, { id:'outlook', text: 'Outlook布局'}, { id:'tabs', text: '多Tab布局'}];
	var authMode = [{ id:'ldap', text: 'LDAP认证' }, { id:'local', text: '本地密码认证'}, { id:'portal', text: 'Portal认证'}, { id:'remote', text: '远程认证'}];
	var Party = [{ id:'comsomol', text: '团员' }, { id:'crowd', text: '群众'}, { id:'partymember', text: '党员'}];
	var Degree = [{ id:'level1', text: '级别1' }, { id:'level2', text: '级别2'}, { id:'level3', text: '级别3'}];
	nui.parse();
	
	$(function(){
		$(".mini-textbox-input").first().focus();
	});
	
	var form = new nui.Form("#form");
	var form1 = new nui.Form("#form1");
	var form2 = new nui.Form("#form2");
	var form3 = new nui.Form("#form3");
	
	var tab = nui.get("tab1");
	
	function add(){
		var data = {};
       	//校验
		form1.validate();
        if (form1.isValid()==false){
        	tab.activeTab(tab.getTab(0));
        	return;
        }
		form2.validate();
        if (form2.isValid()==false) {
        	tab.activeTab(tab.getTab(1));
        	return;
        }
        if($("#userRefCheckbox")[0].checked){
        	form3.validate();
        	if (form3.isValid()==false) return;
        	//提交所有数据
        	data = form.getData(true,true);
        }else{
        	//只提交emp的数据
        	var form1Data = form1.getData(true,true);
        	var form2Data = form2.getData(true,true);
        	if(!form1Data || !form2Data) return;
        	if(form2Data.employee){
	        	for(var p in form2Data.employee){
	        		form1Data.employee[p] = form2Data.employee[p];
	        	}
        	}
        	data = form1Data;
        }
        
        var json = nui.encode(data);
        $.ajax({
            url: "org.gocom.components.coframe.org.employee.addEmployee.biz.ext",
            type: 'POST',
            data: json,
            cache: false,
            contentType:'text/json',
            success: function (text) {
            	var response = text.response || {};
            	if(response && response.flag){
            		CloseWindow("ok");
            	}else{
            		nui.alert(response.message);
            	}
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.responseText);
                CloseWindow();
            }
        });
        
	}
	
	function cancel(){
		CloseWindow("cancel");
	}
	
	function SetData(data){
		data = nui.clone(data);
		var org = data.parentNode || {};
		form1.setData({org:{orgid:org.orgid||""}});
	}
	
	//校验日期
	function onOutdateValidation(e){
       	var o = form.getData();
       	var org = o.employee || {};
		if(org.outdate && org.indate && org.outdate<=org.indate){
			e.errorText = "离职日期必须大于入职日期";
			e.isValid = false;
		}else{
			e.errorText = "";
		}
	}
	
	function onEnddateValidation(e){
       	var o = form.getData();
       	var org = o.user || {};
		if(org.enddate && org.startdate && org.enddate<=org.startdate){
			e.errorText = "失效日期必须大于生效日期";
			e.isValid = false;
		}else{
			e.errorText = "";
		}
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
	
	function resetForm(){
		form.reset();
	}
	
	var bootPath = "<%=request.getContextPath() %>";
	
	//选择机构
    function selectOrg(e) {
        var btnEdit = this;
        nui.open({
            url: bootPath + "/coframe/org/employee/select_manageorg_tree.jsp",
            showMaxButton: false,
            title: "选择机构",
            width: 500,
            height: 400,
            onload:function(){
                var iframe = this.getIFrameEl();
                var ids = btnEdit.getValue();
                var texts = btnEdit.getText();
                var data = {
                   ids:ids,
                   texts:texts
                };
                iframe.contentWindow.SetData(data);
            },
            ondestroy: function (action) {
                if (action == "ok") {
                    var iframe = this.getIFrameEl();
                    var data = iframe.contentWindow.GetData();
                    data = nui.clone(data);
                    if (data) {
                        btnEdit.setValue(data.id);
                        btnEdit.setText(data.text);
                    }
                }
            }
        });            
    }
    
    function selectRole(e) {
    	var btnEdit = this;
        nui.open({
            url: bootPath + "/coframe/org/employee/select_managed_role.jsp",
            showMaxButton: false,
            title: "选择可管理角色",
            width: 400,
            height: 450,
            onload:function(){
                var ids = btnEdit.getValue();
                var texts = btnEdit.getText();
                var data = {
                   parentNode: {nodeId:"", nodeName:"", nodeType:"OrgEmployee"},
                   ids:ids,
                   texts:texts
                };
                var iframe = this.getIFrameEl();
                iframe.contentWindow.SetData(data);
            },
            ondestroy: function (action) {
                if (action == "ok") {
                    var iframe = this.getIFrameEl();
                    var data = iframe.contentWindow.GetData();
                    data = nui.clone(data);
                    if (data) {
                        btnEdit.setValue(data.id);
                        btnEdit.setText(data.text);
                    }
                }
            }
        });            
    }
 
    //展开，折叠
    function toggleFieldSet(ck, id) {
        var dom = document.getElementById(id);
        if(ck.checked){
        	dom.className = "";//展开
        }else{
        	dom.className = "hideFieldset";
        }
    }
    
    function selectMajor(e){
    	var btnEdit = this;
        nui.open({
            url: bootPath + "/coframe/org/employee/select_major.jsp",
            showMaxButton: false,
            title: "选择直接主管",
            width: 800,
            height: 600,
            ondestroy: function (action) {
                if (action == "ok") {
                    var iframe = this.getIFrameEl();
                    var data = iframe.contentWindow.GetData();
                    data = nui.clone(data);
                    if (data) {
                        btnEdit.setValue(data.id);
                        btnEdit.setText(data.text);
                    }
                }
            }
        });
    }
    
    //人员类型
    function selectEmptype(e){
    	var btnEdit = this;
        nui.open({
            url: bootPath + "/coframe/org/employee/emptypeList.jsp",
            //showMaxButton: false,
            title: "选择人员类型",
            width: 350,
            height: 350,
            ondestroy: function (action) {
                if (action == "ok") {
                    var iframe = this.getIFrameEl();
                    var data = iframe.contentWindow.GetData();
                    data = nui.clone(data);
                    if (data) {
                        btnEdit.setValue(data.emptypeCode);
                        btnEdit.setText(data.emptypeName);
                    }
                }
            }
        });
    }
		                         
		                       
</script>

</body>
</html>