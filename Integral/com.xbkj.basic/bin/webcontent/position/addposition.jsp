<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2016-07-06 09:06:55
  - Description: 添加岗位
-->
<head>
<title>添加岗位</title>
</head>
<body>
<div id="from1" method="post" class="nui-fit" style="height:85%;">
	<input id="pk_position" name="pk_position" class="nui-hidden" />
	<table style="width:100%;align:center;" class="nui-form-table_new">
		<tr>
			<td class="nui-form-label">岗位编码：</td>
			<td>
				<input id="post_code"  name="post_code" class="nui-textbox nui-form-input"
					required="true"  onvalidation="validateCode"/>
			</td>
			<td class="nui-form-label">岗位名称：</td>
			<td>
				<input id="post_name"  name="post_name" class="nui-textbox nui-form-input"
					required="true"  onvalidation="validateName"/>
			</td>
		</tr>
		<tr>
			<td class="nui-form-label">岗位级别：</td>
			<td>
				<input id="post_grade"  name="post_grade" class="nui-textbox nui-form-input"
					required="true" maxLength="2"/>
			</td>
			<td class="nui-form-label">岗位类型：</td>
			<td>
				<input id="post_type"  maxLength="4" name="post_type" class="nui-textbox nui-form-input" required="true"/>
			</td>
			
		</tr>
		<tr>
			<td class="nui-form-label">岗位职责：</td>
			<td colspan="3">
				<input id="post_duty"  name="post_duty" class="nui-textbox nui-form-input"/>
			</td>
		</tr>
		<tr>
			<td class="nui-form-label">岗位说明：</td>
			<td colspan="3">
				<input id="describe"  name="describe" class="nui-textarea nui-form-input" required="true"
					style="width:300px;height:60px;"/>
			</td>
		</tr>
	</table>
</div>
	<div class="nui-toolbar" style="text-align:center;border:0;height:15%;">
	  	<a id="savedata" class="nui-button" style="width:60px;"iconCls="icon-save" onclick="onOk()">保存</a>
    	<span style="display:inline-block;width:25px;"></span>
    	<a class="nui-button" style="width:60px;" iconCls="icon-cancel" onclick="onCancel()">取消</a>
	</div>

<script type="text/javascript">
	nui.parse();
	
	var form = new nui.Form("#from1");
	
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
		var update_url = "com.vbm.grc.basic.position.position.updateposition.biz.ext";
		var add_url = "com.vbm.grc.basic.position.position.addposition.biz.ext";
		//可以根据pk_position来判断是新增还是修改
//		var pk = nui.get("pk_position").getValue();
		var pk = o.pk_position;
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
		var pk = nui.get("pk_position").getValue();
		var json = nui.encode({vo:{post_code:val,pk_position:pk}});//校验数据
		//唯一性校验
		nui.ajax({
			url: "com.vbm.grc.basic.position.position.validateCode.biz.ext",
			data: json,
			cache:false,
			type:"POST",
			contentType:"text/json",
			success:function(count){
				if(count.count > 0){
					nui.alert("编码重复!");
				}
			},error: function (jqXHR, textStatus, errorThrown) {
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
	
	//设置数据
	function setData(data){
		data = nui.decode(data);
		form.setData(data);
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

</body>
</html>