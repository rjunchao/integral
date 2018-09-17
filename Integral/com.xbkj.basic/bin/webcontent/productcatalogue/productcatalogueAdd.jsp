<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): heFei
  - Date: 2016-07-05 17:04:18
  - Description:
-->
<head>
<title>Title</title>
</head>
<body>
	<div id="form1" method="post" class="nui-fit" style="height:80%;">
		<input id="parent_code" name="parent_code" class="nui-hidden" />
		<table style="width:100%;height:100%;table-layout:fixed;" class="nui-form-table_new" cellpadding="5px">
			<tr>
				<td class="nui-form-label">产品目录编码：</td>
				<td style="padding-left:0px;margin-left:0px;">
					 <input id="prod_code" name="prod_code"  class="nui-textbox nui-form-input"
					 	required="true"  onvalidation="onCodeValidation" />
				</td>
				<td class="nui-form-label">产品目录名称：</td>
				<td style="padding-left:0px;margin-left:0px;">
					 <input id="prod_name"  name="prod_name" class="nui-textbox nui-form-input"
					 	onvalidation="validateName" required="true" />
				</td>
			</tr>
			<tr>
				<td class="nui-form-label">产品目录层级：</td>
				<td style="padding-left:0px;margin-left:0px;">
					<input id="product_level" name="product_level" class="nui-textbox nui-form-input" />
				</td>
				<td class="nui-form-label">是否为叶子节点：</td>
				<td style="padding-left:0px;margin-left:0px;">
					<input id="isleaf" class="nui-combobox nui-form-input" name="isleaf" value="0"
                   		 valueField="id" textField="text" data="leafData"/> 
				</td>
			</tr>
		</table>
	</div>
	<div class="nui-toolbar" style="text-align:center;border:0;height:20%;padding:7px;">
        <a id="savedata" class="nui-button" style="width:60px;" iconCls="icon-save" onclick="SaveData()">保存</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="nui-button" style="width:60px;" iconCls="icon-cancel" onclick="onCancel()">取消</a>
    </div>  
</body>
<script type="text/javascript">
  		//是否为叶子节点的默认数据
      	var leafData = [{id:0,text:'否'},{id:1,text:'是'}];
  		nui.parse();
      	var form = new nui.Form("form1");
      	//定义全局的变量
      	var pid = "";
 		//产品目录编码的验证
      	function onCodeValidation(e){
 			//得到输入的编码
 			var val = e.value;
 			//nui.alert(val);
 			if(val == "" || val == null){
 				//验证不通过
 				e.isValid = false;
 				return;
 			}
 			//产品目录编码的正则表达式
 			var reg = new RegExp("^[0-9a-zA-Z]*$");
 			if(!reg.test(val)){
 				e.isValid = false;
 				nui.alert("编码由数字字母组成，请重新填写");
 				return;
 			}
 			//校验的编码
 			var json = nui.encode({vo:{prod_code:val}});
 			nui.ajax({
 				url: "com.vbm.grc.basic.product.productcontrol.productcatalogue.validatecode.biz.ext",
 				cache: false,
 				data: json,
 				type: "POST",
 				contentType: "text/json",
 				success:function(action){
 					if(action.count >　0){
 					     e.isValid = false;//验证不通过
 					     nui.alert("编码" + val + " 重复，请重新输入！");
 					}
 					if(action.count == -1){
 						e.isValid = false;
 					}
 					
 				}
 			});
    	 }
 		//产品目录名称的验证
 		function validateName(e){
 			//得到输入的产品目录的名称
 			var val = e.value;
 			//nui.alert(val);
 			var reg = new RegExp("^[\u4E00-\u9FA5A-Za-z0-9_]*$");//验证产品目录的正则表达式
 			if(!reg.test(val)){
 				//输入的是特殊的字符串
 				e.isValid = false;
 				nui.alert("产品目录名称只能是中文英文数字！");
 				return;
 			}
 		}
 	
 		//取消按钮的操作
 		function onCancel(e){
 			CloseWindow("cancel");
 		}
 		//关闭窗口
 		function CloseWindow(action){
 			//nui.alert("关闭窗口");
 			//1.如果数据被修改了，先保存数据的操作
 			//2.如果不是，直接关闭，弹出的窗口
 			if(action == "close" && from.isChanged){
 				if(confirm("数据被修改，是否先保存？")){
 				 		return false;
 				}
 			}
 			if(window.CloseOwnerWindow){
 				return window.CloseOwnerWindow(action);
 			}else{
 				//关闭弹出的窗口
 			  	window.close();
 			}
 		}
 		//保存按钮的操作：数据的保存
 		function SaveData(){
 		 	//nui.alert("保存数据");
 		 	//防止数据重复的提交
 		 	nui.get("savedata").enable = false;
 		 	//数据的校验
 		 	form.validate();
 		 	//nui.alert("红色框必须输入！");
 		 	if(form.isValid == false){
 		 		//nui.alert("红色框必须输入！");
 		 		return;
 		 	}
 		 	//得到数据
 		 	var o = form.getData(true, true);
 		 	if(pid == ""){
 		 		//设置pid
 		 		o.parent_code = "root";
 		 	}else{
 		 		o.parent_code = pid;
 		 	}
 		 	var json = nui.encode({vo:o});
 		 	//nui.alert(json);
 		 	nui.ajax({
 		 		url: "com.vbm.grc.basic.product.productcontrol.productcatalogue.addproductcatalogue.biz.ext",
 		 		cache: false,
 		 		data: json,
 		 		type: "POST",
 		 		contentType: "text/json",
 		 		success: function(msg){
 		 				//添加成功关闭窗口，添加不成功不关闭窗口
 		 				if(msg.msg.flag){
 		 					CloseWindow("ok");
 		 				}else{
 		 				//阻止反复的提交数据,保存按钮不可用
 		 				nui.get("savedata").enable = false;
 		 				}
 		 		},
 		 		error: function(jqXHR, textStatus, errorThrown){
 		 			alert(jqXHR.responseText);
                    CloseWindow();
 		 		}
 		 	
 		 	});
 		}
 		//初始化数据，得到parentcode
 		function SetData(data){
 			//nui.alert("初始化数据")
 			//跨页面传递的数据对象，克隆后才可以安全使用
 			data = nui.clone(data);
 			//设置产品目录层级不可编辑
 			nui.get("product_level").enabled = false;
 			//获取pid
 			pid = data.parentCode;
 			var level = data.level;
 			level = Number(level) + 1;

 			//设置层级的值
 			nui.get("product_level").setValue(level);
 			nui.get("parent_code").setValue(pid);
 			
 		}
</script>
</html>