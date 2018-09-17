<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2016-06-24 17:50:26
  - Description:
-->
<head>
<title>Title</title>
</head>
<body>
	<div id="form1" method="post" class="nui-fit" style="height:90%;">
		<input id="parent_code" name="parent_code" class="nui-hidden" />
		<input id="pk_inter_cntr" name="pk_inter_cntr" class="nui-hidden" />
		<table style="width:100%;height:100%;table-layout:fixed;" class="nui-form-table_new">
			<tr>
				<td class="nui-form-label">内控要素编码：</td>
				<td>
					 <input id="interCntrCode" name="inter_cntr_code"  class="nui-textbox"
					 	required="true"  onvalidation="onCodeValidation" />
				</td>
				<td class="nui-form-label">内控要素名称：</td>
				<td>
					 <input id="interCntrName"  name="inter_cntr_name" class="nui-textbox"
					 	onvalidation="validateName" required="true" />
				</td>
			</tr>
			<tr>
				<td class="nui-form-label">显示顺序：</td>
				<td>
					<input id="displayorder" name="displayorder" class="nui-textbox" vtype="range:0,100"/>
				</td>
				<td class="nui-form-label">是否为叶子节点：</td>
				<td>
					<input id="isLeaf" class="nui-combobox" name="is_leaf" value="0"
                   		 valueField="id" textField="text" data="leafData"/> 
				</td>
			</tr>
			<tr>
				<td class="nui-form-label">内控要素说明：</td>
				<td  colspan="3">
					<input id="interCntrExplain" class="nui-textarea" name="inter_cntr_explain" 
						style="width:430px;height:60px;"/> 
				</td>
			</tr>
		</table>
	</div>
	 <div class="nui-toolbar" style="text-align:center;padding:0px;height:15%;"borderStyle="border:0;">
        <a id="savedata" class="nui-button" style="width:60px;" iconCls="icon-save" onclick="onOk()">保存</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="nui-button" style="width:60px;" iconCls="icon-cancel" onclick="onCancel()">取消</a>
    </div>    
</body>
	 <script type="text/javascript">
	 	var leafData = [{id:0,text:'否'},{id:1,text:'是'}];
		nui.parse();
		var form = new nui.Form("#form1");
		
		//设置数据
		function SetData(data){
			//设置数据
			data = nui.clone(data);
			form.setData(data);//设置表单数据
			var leaf = data.is_leaf;
			if(leaf != 1){
				//非叶子节点，不能被修改
				var isleaf = nui.get("isLeaf");
				isleaf.enabled = false;//
			}
		}
		
		//保存数据
		function SaveData(){
			nui.get("savedata").enabled = false;//防止反复提交
			var o = form.getData(true,true);            
            form.validate();
            if (form.isValid() == false) return;
            var json = nui.encode({vo:o});
            nui.ajax({
				url:"com.vbm.grc.basic.inner.element.innercontrol.innerconele.updateInnElem.biz.ext",
				cache:false,
				data:json,
				type:"POST",
				contentType:"text/json",
				success: function(msg){
					if(msg.msg.flag){
						CloseWindow("ok");
						return;
					}else{
						nui.get("savedata").enabled = true;//防止反复提交
					}
				},
				error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                    CloseWindow();
                }
			});
		}
		
		//校验数字
		function validateLevel(e){
			var val = e.value;
			var reg = new RegExp("^[1-9]*$");
			if(!reg.test(val)){
				e.isValid = false;//只能输入数字
				return;
			}
		}
		
		//名字校验
		function validateName(e){
			var val = e.value;
			var reg = new RegExp("^[\u2E80-\u9FFF]*$");
			if(!reg.test(val)){
				e.isValid = false;//输入的特殊字符
				nui.alert("级别名称只能输入中文！");
				return;
			}
		}
		
		function onCodeValidation(e){
			//得到输入的编码
			var val = e.value;
			/* if(val == "" || val == null){
				e.isValid = false;
			}
			 */
			var regu = "^[0-9a-zA-Z]*$"
			var re = new RegExp(regu);
			if(!re.test(val)){
				e.isValid = false;//输入的特殊字符
				nui.alert("编码由数字字母组成，请重新填写");
				return;
			}
			
			//得到主键
			var pk = nui.get("pk_inter_cntr").getValue();
			//封装参数
			var json = nui.encode({vo:{inter_cntr_code:val,pk_inter_cntr:pk}});
			//nui.alert(json);
			$.ajax({
				url:"com.vbm.grc.basic.inner.element.innercontrol.innerconele.validateInnElemCode.biz.ext",
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
		
		//关闭窗口
		function CloseWindow(action) {            
            if (action == "close" && form.isChanged()) {
                if (confirm("数据被修改了，是否先保存？")) {
                    return false;
                }
            }
            if (window.CloseOwnerWindow) {
            	return window.CloseOwnerWindow(action);
            }
            else window.close();            
        }
        function onOk(e) {
           SaveData();
        }
        function onCancel(e) {
            CloseWindow("cancel");
        }
		
	 </script>
</html>