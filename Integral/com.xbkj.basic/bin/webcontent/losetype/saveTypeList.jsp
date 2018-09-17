<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): lqy
  - Date: 2016-08-02 10:09:15
  - Description:新增修改损失形态类型
-->
<head>
<title>新增修改损失形态类型</title>
</head>
<body>
	<div id="form1" method="post" class="nui-fit" style="height:60%;">
	     <!-- 用于判断新增还是编辑 -->
		<input id="pk_loss_type" name="pk_loss_type" class="nui-hidden" />
		<table style="width:100%;height:100%;table-layout:fixed;" class="nui-form-table_new">
			<tr>
				<td class="nui-form-label">损失形态编码：</td>
				<td>
					<input id="losstype_code"  name="losstype_code" class="nui-textbox nui-form-input"
						required="true"  onvalidation="validateCode"/>
				</td>
				<td class="nui-form-label">损失形态名称：</td>
				<td>
					<input id="lossname_name"  name="lossname_name" class="nui-textbox nui-form-input"
						required="true"  onvalidation="validateName" vtype="rangeLength:1,200"/>
				</td>
			</tr>
			<tr>
				<td class="nui-form-label">损失形态说明：</td>
				<td colspan="3">
					<input id="describe"  name="describe" class="nui-textarea nui-form-input" required="true"
						style="width:300px;height:60px;"/>
				</td>
			</tr>
		</table>
	</div>
	<div class="nui-toolbar" style="text-align:center;border:0;height:15%;padding:5px;"borderStyle="border:0;">
	  	<a id="savedata" class="nui-button" style="width:60px;" iconCls="icon-save" onclick="SavaData()">保存</a>
    	<span style="display:inline-block;width:25px;"></span>
    	<a class="nui-button" style="width:60px;" iconCls="icon-cancel" onclick="onCancel()">取消</a>
	</div>
   <script type="text/javascript">
     nui.parse();
     var form = new nui.Form("#form1");//获取对象
     var addUrl="com.vbm.grc.basic.losetype.losetype.addLoseType.biz.ext";
     var updateUrl="com.vbm.grc.basic.losetype.losetype.updateLoseType.biz.ext";
     var saveUrl="";
     //编码的校验 
      function validateCode(e){
       	//得到输入的编码
       	var code = e.value;
       	//对空和null的校验
       	if(code == "" || code == null){
       	  e.isValid = false;
       	  return;
       	}
		var reg = new RegExp("^[0-9a-zA-Z]*$");
		if(!reg.test(code)){
			e.isValid = false;//输入的特殊字符
			nui.alert("编码由数字字母组成，请重新填写");
			return;
		}
      
      }
      //名称的校验
      function validateName(e){
         var value = e.value;
        //正则表达式
        var reg = new RegExp("^[\u2E80-\u9FFF]*$");
        if(!reg.test(value)){
        	//输入特殊字符，验证不通过
        	e.isValid = false;
        	nui.alert("名称只能是中文！");
        	return;
        }
      }
      //取消按钮的操作
      function onCancel(action){
          CloseWindow(action);
      }
      //关闭弹窗的操作
      function CloseWindow(action){
            //点击弹出框的x触发
      		/* if(action == "close" && form.isChanged()){
      		  if(confirm("数据被修改了，是否先保存？")){
      		  		return false;
      		  }
      		} */
      		//点击取消按钮触发
      		if(window.CloseOwnerWindow){
      			return window.CloseOwnerWindow(action);
      		}else{
      		  window.close();//关闭窗口
      		}
      }
      //保存数据的操作
      function SavaData(){
      		form.validate();
      		//验证没有通过
      		if(form.isValid == false){
      			return;
      		}
      		var pk=nui.get("pk_loss_type").getValue();
      		if(pk){
      		saveUrl = updateUrl;
      		}else{
      		saveUrl = addUrl;
      		}
      		var o = form.getData(true, true);//获取数据
      		var json = nui.encode({vo:o});
      		nui.ajax({
      		   url: saveUrl,
      		   cache: false,
      		   data: json,
      		   type: "POST",
      		   contentType: "text/json",
      		   success: function(msg){
      		       	nui.alert(msg.msg.message);
					if(msg.msg.flag){
					CloseWindow("ok");
				}   
      		   },
      		   error: function(jqXHR, textStatus, errorThows){
      		    	nui.alert(jqXHR.responseText);
      		   }
      		});
      }
      //设置数据
      function setData(data){
      		data = nui.decode(data);
      		form.setData(data);
      }
   </script>
</body>
</html>