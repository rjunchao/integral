<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<!-- 
  - Author(s): shitf
  - Date: 2013-03-03 20:01:00
  - Description:
-->
<head>
<%@include file="/coframe/tools/skins/common.jsp" %>
</head>
<body>
<body>
  <div id="form1" style="padding-top:5px;">
    <input class="nui-hidden" name="user.operatorId"/>
    <input class="nui-hidden" name="user.password"/>
    <table style="width:100%;height:100%;table-layout:fixed;" class="nui-form-table">
      <tr>
        <th class="nui-form-label"><label for="user.userId$text">登录用户名：</label></th>
        <td>
          <input id="user.userId" class="nui-textbox nui-form-input" name="user.userId" required="true" onvalidation="onCheck"/>
        </td>
        <th class="nui-form-label"><label for="user.userName$text">用户名称：</label></th>
        <td>
          <input id="user.userName" class="nui-textbox nui-form-input" name="user.userName"/>
        </td>
      </tr>
      <tr class="odd">
        <th class="nui-form-label"><label for="user.status$text">用户状态：</label></th>
        <td>
          <input id="user.status" class="nui-dictcombobox nui-form-input" name="user.status" value="1"
          valueField="dictID" textField="dictName" dictTypeId="COF_USERSTATUS"/>
        </td>
        <th class="nui-form-label"><label for="user.invaldate$text">密码失效日期：</label></th>
        <td>
          <input id="user.invaldate" class="nui-datepickerx nui-form-input" name="user.invaldate" allowInput="false"/>
        </td>
      </tr>
      <tr>
        <th class="nui-form-label"><label for="startdate$text">有效开始时间：</label></th>
        <td>
          <input id="startdate" class="nui-datepickerx nui-form-input" name="user.startdate" allowInput="false"/>
        </td>
        <th class="nui-form-label"><label for="user.enddate$text">有效截止时间：</label></th>
        <td>
          <input id="user.enddate" class="nui-datepickerx nui-form-input" name="user.enddate" onvalidation="onCompare" allowInput="false"/>
        </td>
      </tr>
      <tr class="odd">
        <th class="nui-form-label"><label for="user.authmode$text">认证模式：</label></th>
        <td>
          <input id="user.authmode" class="nui-dictcombobox nui-form-input" name="user.authmode" value="local"
          valueField="dictID" textField="dictName" dictTypeId="COF_AUTHMODE"/>
        </td>
        <th class="nui-form-label"><label for="user.menutype$text">菜单风格：</label></th>
        <td>
          <input id="user.menutype" class="nui-dictcombobox nui-form-input" name="user.menutype" value="0"
          valueField="dictID" textField="dictName" dictTypeId="COF_SKINLAYOUT"/>
        </td>
      </tr>
      <tr>
        <th class="nui-form-label"><label for="user.email$text">邮箱地址：</label></th>
        <td colspan="3">
          <input id="user.email" class="nui-textbox nui-form-input" name="user.email" vtype="email"/>
        </td>
      </tr>
      <tr class="odd">
        <th class="nui-form-label"><label for="user.ipaddress$text">IP地址：</label></th>
        <td colspan="3">
          <input id="user.ipaddress" class="nui-textarea nui-form-input" name="user.ipaddress"/>
        </td>
      </tr>
    </table>
    <div class="nui-toolbar" style="padding:0px;" borderStyle="border:0;">
	    <table width="100%">
	      <tr>
	        <td style="text-align:center;">
	          <a class="nui-button" iconCls="icon-save" onclick="onOk">保存</a>
	          <span style="display:inline-block;width:25px;"></span>
	          <a class="nui-button" iconCls="icon-cancel" onclick="onCancel">取消</a>
	        </td>
	      </tr>
	    </table>
	 </div>
  </div>
  
  <script type="text/javascript">
    nui.parse();
    var form = new nui.Form("#form1");
    var userId;
    var obj;
    
    function SetData(data){
      data = nui.clone(data);
      var json = nui.encode({user:data});
      $.ajax({
        url:"org.gocom.components.coframe.rights.UserManager.getUser.biz.ext",
        type:'POST',
        data:json,
        cache:false,
        contentType:'text/json',
        success:function(text){
          obj = nui.decode(text);
          form.setData(obj);
          userId = obj.user.userId;
          form.setChanged(false);
        }
      });
    }    
    
    function onOk(){
      saveData();
    }
    
    function saveData(){
       if(form.isChanged()){           
	      form.validate();
	      if(form.isValid()==false) return;
	      
	      var data = form.getData(false,true);
	      var json = nui.encode(data);
	      $.ajax({
	        url:"org.gocom.components.coframe.rights.UserManager.updateUser.biz.ext",
	        type:'POST',
	        data:json,
	        cache:false,
	        contentType:'text/json',
	        success:function(text){
	          var returnJson = nui.decode(text);
			  if(returnJson.exception == null){
				CloseWindow("saveSuccess");
			  }else{
				nui.alert("修改用户失败", "系统提示", function(action){
					if(action == "ok" || action == "close"){
						CloseWindow("saveFailed");
					}
				});
			  }
	        }
	      });
       }else{
          nui.alert("数据未改变");
       }
    }
    
    function onReset(){
      form.setData(obj);
      form.setChanged(false);
    }
    
    function onCancel(){
      CloseWindow("cancel");
    }
    
    function CloseWindow(action){
      if(action=="close" && form.isChanged()){
        if(confirm("数据已改变,是否先保存?")){
          return false;
        }
      }else if(window.CloseOwnerWindow) 
        return window.CloseOwnerWindow(action);
      else
        return window.close();
    }
    
    function onCheck(e){
      if (e.isValid) {
         if(checkPattern(e.value) == false){
            e.errorText = "只能包含字母、数字、下划线，且以字母开头";
            e.isValid = false;
         }else if (e.value != userId && isExist(e.value) == true) {
	        e.errorText = "用户名已存在";
	        e.isValid = false;
	     }
	  }
    }
    
    function checkPattern(v){
      var re = new RegExp("^[A-Za-z][A-Za-z0-9_]{0,63}$");
      if(re.test(v)) return true;
      return false;
    }
    
    function isExist(value){
      var bool;
      $.ajax({
        url:"org.gocom.components.coframe.rights.UserManager.checkUser.biz.ext",
        type:'POST',
        data:'userId='+value,
        cache:false,
        async:false,
        dataType:'json',
        success:function(text){
          bool=text.bool;
        }
      });
      return bool;
    }
    
    function onCompare(e){
      var startDate = nui.get("startdate").getFormValue();
      var endDate = e.value;
      if(startDate!="")
	    startDate=startDate.substring(0,4) + startDate.substring(5,7) + startDate.substring(8,10);
	  if(endDate!=""){
		endDate=endDate.substring(0,4) + endDate.substring(5,7) + endDate.substring(8,10);
        if(e.isValid){
          if(startDate>endDate){
            e.errorText="截止时间必须大于开始时间";
            e.isValid=false;
          }
        }
      }
    }
  </script>
</body>
</html>