<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): heHei
  - Date: 2016-07-05 09:39:28
  - Description:
-->
<head>
<title>产品目录信息</title>
</head>
<body>
	<div id="form1" method="post">
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
					<input id="isleaf" class="nui-combobox nui-form-input" name="isleaf" value="0"
                   		 valueField="id" textField="text" data="leafData"/> 
				</td>
			</tr>
		</table>
		 <div class="nui-toolbar" style="text-align:center;padding-top:5px;padding-bottom:5px;" borderStyle="border:0;">
	        <a class="nui-button" style="width:60px;" iconCls="icon-save" onclick="SavaData()">保存</a>
	    </div>    
	</div>
</body>
   <script type="text/javascript">
  		var leafData = [{id:0,text:'否'},{id:1,text:'是'}];
  		nui.parse();
  		var form = new nui.Form("#form1");
  		//获取主键
  		var pk = "<%=request.getParameter("pk_product") %>";
  		var json = nui.encode({pk:pk});
  		//nui.alert(json);
  		//定义临时的编码
  		var tempCode = "";
  		//加载数据根据主键来加载数据
  		$.ajax({
  			url: "com.vbm.grc.basic.product.productcontrol.productcatalogue.findprocataloguebyid.biz.ext",
  			cache: false,
  			data: json,
  			type: "POST",
  			contentType: "text/json",
  			success:function(vo){
  				var o = nui.decode(vo.vo);//反序列化
  				form.setData(o);
  				//层级不可用
  				nui.get("product_level").enabled = false;
  				tempCode = o.prod_code;//获取编码
  				//nui.alert(tempCode);
  				//如果是叶子节点才可以修改isleaf的属性
  				var isleaf = o.isleaf;
  				if(isleaf == 1){
  					nui.get("isleaf").enable = true;
  				}else {
  					nui.get("isleaf").enable = false;
  				}
  			}
  		});
  		//产品目录的校验
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
 			var pk_product = nui.get("pk_product").getValue();
 			var json = nui.encode({vo:{pk_product:pk_product,prod_code:val}});
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
 					if(action.count){
 						e.isValid = false;
 					}
 					
 				}
 			});
  		}
  		//产品编码的校验
  		function validateName(e){
  			//得到输入的产品目录的名称
 			var val = e.value;
 			//nui.alert(val);
 			var reg = new RegExp("^[\u4E00-\u9FA5A-Za-z0-9_]*$");//验证产品目录的正则表达式
 			if(!reg.test(val)){
 				//输入的是特殊的字符串
 				e.isVslid = false;
 				nui.alert("产品目录名称只能是中文英文数字！");
 				return;
 			}
  		}
  		//保存的操作:就是对节点的更新事务操作
  		function SavaData(){
  		 	//数据的校验
  		 	form.validate();
  		 	if(form.isValid == false){
  		 		return;
  		 	}
  		 	//得到操作的数据
  		 	var o = form.getData(true, true);
  		 	var json = nui.encode({vo:o});//序列化成json
  		 	nui.ajax({
  		 		url: "com.vbm.grc.basic.product.productcontrol.productcatalogue.updateProductCatalogue.biz.ext",
  		 		cache: false,
  		 		data: json,
  		 		type: "POST",
  		 		contentType: "text/json",
  		 		success:function(msg){
  		 			if(msg.msg.flag){
  		 			 //刷新数据
  		 			 parent.refreshNode();
  		 			}
  		 		},
  		 		error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                    CloseWindow();
                }
  		 	});
  		}
   </script>
</html>