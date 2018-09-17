<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): lwl
  - Date: 2016-07-05 14:45:17
  - Description:
-->
<head>
<title>风险成因分类信息</title>
</head>
<body>
	<div id="form1" method="post">
		<input id="parent_code" name="parent_code" class="nui-hidden" />
		<input id="pk_risk_classify" name="pk_risk_classify" class="nui-hidden" />
		<input id="classify_level" name="classify_level" class="nui-hidden" />
		<table style="width:100%;height:100%;table-layout:fixed;" class="nui-form-table_new" cellpadding="5px">
			<tr>
				<td class="nui-form-label">分类编码：</td>
				<td>
					 <input id="classify_code" name="classify_code"  class="nui-textbox nui-form-input" required="true"  onvalidation="onCodeValidation"/>
				</td>
				<td class="nui-form-label">分类名称：</td>
				<td>
					 <input id="classify_name"  name="classify_name" class="nui-textbox nui-form-input" onvalidation="validateName" required="true" />
				</td>
			</tr>
			<tr>
				<td class="nui-form-label">是否为叶子节点：</td>
				<td>
					<input id="isLeaf" class="nui-combobox nui-form-input" name="isLeaf" valueField="id" textField="text" data="isLeaf"/> 
				</td>
			</tr>
			<tr>
				<td class="nui-form-label">举例说明：</td>
				<td  colspan="3">
					<input id="explain" class="nui-textarea nui-form-input" name="explain" style="width:300px;height:60px;"/> 
				</td>
			</tr>
		</table>
		<div style="text-align:center;padding-top:5px;padding-bottom:5px;" borderStyle="border:0;">
	        <a class="nui-button" style="width:60px;" iconCls="icon-save" onclick="onOk()">保存</a>
	    </div>  
	</div>
</body>
<script type="text/javascript">
	 	var isLeaf = [{id:0,text:'否'},{id:1,text:'是'}];
		nui.parse();
		var form = new nui.Form("#form1");
		//获取pk
		var pid = "<%=request.getParameter("pk_risk_classify") %>";
		var json = nui.encode({pk:pid});
		var tempCode = "";
		//显示数据
		$.ajax({
			url:"com.vbm.grc.basic.risk.classify.riskclassify.findRiskClassifyById.biz.ext",
			cache:false,
			data:json,
			type:"POST",
			contentType:"text/json",
			success:function(vo){
				var o = nui.decode(vo.vo);
				//设置数据
				form.setData(o);
				//得到叶子节点的值
				var leaf = vo.vo.isLeaf;
				if(leaf != 1){
					//非叶子节点，不能被修改
					var isleaf = nui.get("isLeaf");
					isleaf.enabled = false;
				}
				tempCode = vo.vo.busi_code;
				form.setChanged(false);
			},
			error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.responseText);
            }
		});
		
		function saveData(){
			var o = form.getData(true,true);            
            form.validate();
            if (form.isValid() == false) return;
            var json = nui.encode({vo:o});
            nui.ajax({
				url:"com.vbm.grc.basic.risk.classify.riskclassify.updateRiskClassify.biz.ext",
				cache:false,
				data:json,
				type:"POST",
				contentType:"text/json",
				success: function(msg){
					nui.alert(msg.msg.message);
					//刷新树节点
					parent.refreshNode();
				},
				error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                }
			});
		}
		//名称校验
		function validateName(e){
			var val = e.value;
			var reg = new RegExp("^[\u2E80-\u9FFF]*$");
			if(!reg.test(val)){
				//输入的特殊字符
				e.isValid = false;
				nui.alert("分类名称只能输入中文！");
				return;
			}
		}
		function onCodeValidation(e){
			//得到输入的编码
			var val = e.value;
			//编码相同，就不用校验了
			if(val == tempCode)return;
			var regu = "^[0-9a-zA-Z]*$"
			var re = new RegExp(regu);
			if(!re.test(val)){
				//输入的特殊字符
				e.isValid = false;
				nui.alert("编码由数字字母组成，请重新填写");
				return;
			}
			
			//得到主键
			var pk = nui.get("pk_risk_classify").getValue();
			//封装参数
			var json = nui.encode({vo:{classify_code:val,pk_risk_classify:pk}});
			$.ajax({
				url:"com.vbm.grc.basic.risk.classify.riskclassify.validateRiskClassifyCode.biz.ext",
				cache:false,
				data:json,
				type:"POST",
				contentType:"text/json",
				success: function(action){
					if(action.result > 0){
						e.isValid = false;
						nui.alert("编码 "+val+" 重复");
						return;
					}
					if(action.result == -1){
						e.isValid = false;
						nui.alert("编码为空");
						return;
					}
				}
			});	
		}	
		function onOk(){
			saveData();
		}
	 </script>
</html>