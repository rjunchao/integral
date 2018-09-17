<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): heFei
  - Date: 2016-07-07 11:03:29
  - Description:
-->
<head>
<title>修改产品目录</title>
</head>
<body>
	<div id="form1" method="post" class="nui-fit" style="height:80%;">
		<input id="parent_code" name="parent_code" class="nui-hidden" />
		<input id="pk_product" name="pk_product" class="nui-hidden" />
		           
		<table style="width:100%;height:100%;table-layout:fixed;" class="nui-form-table_new" cellpadding="5px">
			<tr>
				<td class="nui-form-label">产品目录编码：</td>
				<td>
					 <input id="prod_code" name="prod_code"  class="nui-textbox nui-form-input"
					 	required="true"  onvalidation="onCodeValidation" />
				</td>
				<td class="nui-form-label">产品目录名称：</td>
				<td>
					 <input id="prod_name"  name="prod_name" class="nui-textbox nui-form-input"
					 	onvalidation="validateName" required="true" />
				</td>
			</tr>
			<tr>
				<td class="nui-form-label">产品目录层级：</td>
				<td>
					<input id="product_level" name="product_level" class="nui-textbox nui-form-input" />
				</td>
				<td class="nui-form-label">是否为叶子节点：</td>
				<td>
					<input id="isLeaf" class="nui-combobox nui-form-input" name="isleaf" value="0"
                   		 valueField="id" textField="text" data="leafData"/> 
				</td>
			</tr>
		</table>
	</div>
	 <div class="nui-toolbar" style="text-align:center;border:0;height:20%;padding:7px;">
        <a id="savedata" class="nui-button" style="width:60px;" iconCls="icon-save" onclick="onOk()">保存</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="nui-button" style="width:60px;" iconCls="icon-cancel" onclick="onCancel()">取消</a>
    </div>    
</body>
  <script type="text/javascript">
      var leafData = [{id:0,text:"否"},{id:1,text:"是"}];
      nui.parse();
      var form = new nui.Form("#form1");
      //临时的编码
      var tempCode = "";
      var pid = "";
      //产品编码的验证：1.校验编码的合法2.在校验编码是不是重复
      function onCodeValidation(e){
      		//得到编码的数据
       		var val = e.value;
       		//如果没有编码就不做校验
       		if(val == tempCode){
       			return;
       		}
       		if(val == "" || val == null){
       			e.isValid = false;
       			return;
       		}
      		var reg = new RegExp("^[0-9a-zA-Z]*$");
      		if(!reg.test(val)){
      			//输入的是特殊的字符
      			e.isValid = false;
      			nui.alert("编码只能是字母数字，请重新填写！");
      			nui.get("savedata").enabled = false;
      			return;
      		}
      		//以下是对编码的重复的校验
      		var pk_product = nui.get("pk_product").getValue();//获取主键
      		//nui.alert(pk_product);
      		var json = nui.encode({vo:{pk_product:pk_product,prod_code:val}});
      		nui.ajax({
      			url: "com.vbm.grc.basic.product.productcontrol.productcatalogue.validatecode.biz.ext",
      			cache: false,
      			data: json,
      			type: "POST",
      			contentType: "text/json",
      			success: function(action){
      				if(action.count > 0){
      				    //编码重复验证不通过
      					e.isValid = false;
      					nui.alert("编码" + val + " 重复，请重新输入！")
      				}
      				 if(action.count < 0){
      						//不符合的编码校验
      						e.isValid = false;
      						nui.alert("编码只能由数字和字母组成，请重新输入！")	
      				}
      			}
      		});
      }
      //产品目录的名称的验证
      function validateName(e){
      	var val = e.value;
      	var reg = new RegExp("^[\u4E00-\u9FA5A-Za-z0-9_]*$");
      	if(!reg.test(val)){
      		//输入的特殊的字符
      		e.isValid = false;
      		nui.alert("产品目录名称只能是中文英文数字！");
      		//nui.get("savedata").enabled = false;
      		return;
      	}
      }
      //初始化数据，得到parentCode
		function SetData(data){
			//跨页面的数据，必须克隆才可以安全使用
			data = nui.clone(data);
			form.setData(data);
			//产品目录的层级不可用
			nui.get("product_level").enabled = false;
			tempCode = data.prod_code;
			//叶子节点
			var leaf = data.isleaf;
			if(leaf == 1){
				nui.get("isLeaf").enable = true;
			}else{
				nui.get("isLeaf").enable = false;
			}
		}
		//取消按钮的操作：取消的是时候关闭窗口
		function onCancel(e){
				CloseWindow("cancel");
		}
		//关闭窗口的操作:如果数据被修改了，先提示用户保存数据的操作，
		function CloseWindow(action){
			if(action == "close" && form.isChanged()){
				if(confirm("数据被修改了，是否先保存数据？")){
					return false;
				}
			}
			if(window.CloseOwnerWindow){
				//这是对nui.open的操作确认是关闭还是取消的操作
				return window.CloseOwnerWindow(action);
			}else{
				window.close();
			}
		}
		//修改后数据的保存的操作
		function onOk(e){
			SavaData();
		}
		//保存的操作
		function SavaData(){
			//阻止反复的提交数据
			nui.get("savedata").enabled = false;
			//校验
			form.validate();
			if(form.isValid == false){
				return;
			}
			//nui.alert("修改数据");
			//得到数据
			var o = form.getData(true, true);
			var json = nui.encode({vo:o});
			nui.ajax({
				url: "com.vbm.grc.basic.product.productcontrol.productcatalogue.updateProductCatalogue.biz.ext",
				cache: false,
				data: json,
				type: "POST",
				contentType: "text/json",
				success: function(msg){
						if(msg.msg.flag){
							CloseWindow("ok");
						}else{
						   //保存按钮有效
							nui.get("savedata").enabled = true;
						}
				},
				error: function(jqXHR, textStatus, errorThrown){
					 alert(jqXHR.responseText);
                     CloseWindow();
				}
			});
		}
  </script>
  
</html>