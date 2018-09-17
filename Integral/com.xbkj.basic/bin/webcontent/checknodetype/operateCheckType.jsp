<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): xjm
  - Date: 2016-09-26 16:43:43
  - Description: 添加检查点类型
-->
<head>
	<title>添加检查点类型</title>
</head>
<body>
	<div id="form1" method="post" class="nui-fit" style="height:85%;padding-top:10px">
		<input id="pk_check_type" name="pk_check_type" class="nui-hidden" />
		<table style="width:96%;height:100%;table-layout: fixed;">
			<tr>
				<td style="width:20%" class="nui-form-label">类型编码：</td>
				<td style="width:30%">
					 <input id="check_type_code" name="check_type_code" enabled="false" class="nui-textbox nui-form-input" style="width:40%" onvalidation="validateCode" required="true" vtype="maxLength:20"/>
				</td>
				<td style="width:20%" class="nui-form-label">类型名称：</td>
				<td style="width:30%">
					 <input id="check_type_name"  name="check_type_name" class="nui-textbox nui-form-input" style="width:40%" onvalidation="validateName" required="true" vtype="maxLength:100"/>
				</td>
			</tr>
			<tr>
				<td class="nui-form-label">类型说明：</td>
				<td  colspan="3">
					<input id="describe" class="nui-textarea nui-form-input" name="describe" style="width:300px;height:60px;" vtype="maxLength:200"/> 
				</td>
			</tr>
		</table>
	</div>
	<div class="nui-toolbar" style="text-align:center;border:0;height:15%;">
	  	<a id="savedata" class="nui-button" style="width:60px;"iconCls="icon-save" onclick="onOk()">保存</a>
    	<span style="display:inline-block;width:25px;"></span>
    	<a class="nui-button" style="width:60px;" iconCls="icon-cancel" onclick="onCancel()">取消</a>
	</div> 
</body>
<script type="text/javascript">
	nui.parse();
	var form = new nui.Form("#form1");
	
	//生成唯一检查点类型编码
    window.onload=function(){
       var date=new Date();
       var code="GZ"+Date.UTC(date.getFullYear(),date.getMonth(),date.getDate(),date.getHours(),date.getMinutes(),date.getSeconds(),date.getMilliseconds());
       nui.get("check_type_code").setValue(code);
    }
	
	//设置数据
	function setData(data){
		data = nui.decode(data);
		form.setData(data);
	}
	
	//保存数据
	function save(){
		//校验
		form.validate();
		if(form.isValid() == false){
			return;
		}
		//提交数据，判断是新增还是修改
		var o = form.getData(true,true);     
		var url = "";
		var update_url = "com.vbm.grc.basic.checktype.checktype.updateCheckType.biz.ext";
		var add_url = "com.vbm.grc.basic.checktype.checktype.addCheckType.biz.ext";
		//可以根据pk_check_type来判断是新增还是修改
		var pk = o.pk_check_type;
		if(pk == "" || pk == null){
			url=add_url;
		}else{
			url=update_url;
		}
		var json = nui.encode({vo:o});
		nui.ajax({
			url:url,
			cache:false,
			data:json,
			type:"POST",
			contentType:"text/json",
			success: function(msg){
				nui.alert(msg.msg.message);
				if(msg.msg.flag){
					CloseWindow("ok");
				}
			},
			error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.responseText);
            }
		});
	}
	
	//校验名称
	function validateName(e){
		var val = e.value;
		var reg = new RegExp("^[\u2E80-\u9FFF]*$");
		if(!reg.test(val)){
			e.isValid = false;//输入的特殊字符
			nui.alert("名称只能输入中文！");
			return;
		}
	}
	
	//校验编码
	function validateCode(e){
		//得到输入的编码
		var val = e.value;
		if(val == "" || val == null){//空校验
			e.isValid = false;
			return;
		}
		var regu = "^[0-9a-zA-Z]*$"
		var re = new RegExp(regu);
		if(!re.test(val)){
			e.isValid = false;//输入的特殊字符
			nui.alert("编码由数字字母组成，请重新填写");
			return;
		}
		var check_type_code = nui.get("check_type_code").getValue();//检查类型编码
		var json = nui.encode({vo:{check_type_code:check_type_code}});//校验数据
		//唯一性校验
		nui.ajax({
			url: "com.vbm.grc.basic.checktype.checktype.validateCode.biz.ext",
			data: json,
			cache:false,
			type:"POST",
			contentType:"text/json",
			success:function(count){
				if(count.count > 0){
					nui.alert("编码重复!");
				}
			},
			error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.responseText);
            }
		});
		
	}

	function onCancel(e){
		CloseWindow("cancel");
	}
	function onOk(e){
		save();
	}
	//关闭窗口
	function CloseWindow(action) {            
        if (action == "close" && form.isChanged()) {
            if (confirm("数据被修改了，是否先保存？")) {
                return false;
            }
        }
        if(window.CloseOwnerWindow){
			return window.CloseOwnerWindow(action);
		}else{
			window.close();
		}   
               
    }
</script>
</html>