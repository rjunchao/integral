<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2016-07-01 10:22:34
  - Description:
-->
<head>
<title>Title</title>
</head>
<body>
	<div id="form1" method="post">
		<input id="parent_code" name="parent_code" class="nui-hidden" />
		<input id="pk_lose_eventtype" name="pk_lose_eventtype" class="nui-hidden" />
		<table style="width:100%;height:100%;table-layout:fixed;" class="nui-form-table_new" cellpadding="5px">
			<tr>
				<td class="nui-form-label">损失事件类型编码：</td>
				<td>
					 <input id="eventtype_code" name="eventtype_code"  class="nui-textbox nui-form-input"
					 	required="true"  onvalidation="onCodeValidation" />
				</td>
				<td class="nui-form-label">损失事件类型名称：</td>
				<td>
					 <input id="eventtype_name"  name="eventtype_name" class="nui-textbox nui-form-input"
					 	onvalidation="validateName" required="true" />
				</td>
			</tr>
			<tr>
				<td class="nui-form-label">层级：</td>
				<td>
					<input id="eventtype_level" name="eventtype_level" class="nui-textbox nui-form-input" />
				</td>
				<td class="nui-form-label">是否为叶子节点：</td>
				<td>
					<input id="isLeaf" class="nui-combobox nui-form-input" name="isleaf" value="0"
                   		 valueField="id" textField="text" data="leafData"/> 
				</td>
			</tr>
			
			<tr>
				<td class="nui-form-label">损失事件类型说明：</td>
				<td  colspan="3">
					<input id="eventtype_explain" class="nui-textarea nui-form-input" name="eventtype_explain" 
						style="width:300px;height:60px;"/> 
				</td>
			</tr>
		</table>
		 <div class="nui-toolbar" style="text-align:center;padding-top:5px;padding-bottom:5px;" borderStyle="border:0;">
	        <a class="nui-button" style="width:60px;" iconCls="icon-save" onclick="onOk()">保存</a>
	    </div>    
	</div>
	 <script type="text/javascript">
	 	var leafData = [{id:0,text:'否'},{id:1,text:'是'}];
		nui.parse();
		var form = new nui.Form("#form1");
		var pk = "<%=request.getParameter("pk_lose_eventtype") %>";
		var json= nui.encode({pk:pk});
		var tempCode = "";
		//加载数据,根据id进行查询
		$.ajax({
			url:"com.vbm.grc.basic.loseevent.loseeventtype.findlostenentbyid.biz.ext",
			cache:false,
			data:json,
			type:"POST",
			contentType:"text/json",
			success: function(vo){
				var o = nui.decode(vo.vo);
				form.setData(o);
				nui.get("eventtype_level").enabled= false;//层级不可编辑
				tempCode = o.eventtype_code;
				//如果是叶子节点才可以修改isleaf属性
				var leaf = o.isleaf;
				if(leaf == 1){
					nui.get("isLeaf").enabled = true;
				}else{
					nui.get("isLeaf").enabled = false;
				}
			}
		});
		
		//保存数据
		function SaveData(){
			//校验
			form.validate();
			if(form.isValid() == false){
				return;
			}
			//得到数据
			var o = form.getData(true, true);
			var json = nui.encode({vo:o});
			nui.ajax({
				url:"com.vbm.grc.basic.loseevent.loseeventtype.updateloseevent.biz.ext",
				cache:false,
				data:json,
				type:"POST",
				contentType:"text/json",
				success: function(msg){
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
		
		//名字校验
		function validateName(e){
			var val = e.value;
			var reg = new RegExp("^[\u2E80-\u9FFF]*$");
			/* if(!reg.test(val)){
				e.isValid = false;//输入的特殊字符
				nui.alert("级别名称只能输入中文！");
				return;
			} */
		}
		
		function onCodeValidation(e){
			//得到输入的编码
			var val = e.value;
			//如果编码没有修改就不用校验了
			if(val == tempCode){
				return;
			}
			if(val == "" || val == null){
				e.isValid = false;
				retutn;
			}
			
			/* var regu = "^[0-9a-zA-Z]*$"
			var re = new RegExp(regu);
			if(!re.test(val)){
				e.isValid = false;//输入的特殊字符
				nui.alert("编码由数字字母组成，请重新填写");
				return;
			} */
			var pk_lose_eventtype = nui.get("pk_lose_eventtype").getValue();
			var json = nui.encode({vo:{pk_lose_eventtype:pk_lose_eventtype,eventtype_code:val}});
			nui.ajax({
				url:"com.vbm.grc.basic.loseevent.loseeventtype.validatecode.biz.ext",
				cache:false,
				data:json,
				type:"POST",
				contentType:"text/json",
				success: function(action){
					if(action.count > 0){
						e.isValid = false;
						nui.alert("编码 "+val+" 重复，请重新输入！");
					}
					if(action.count < 0){
						e.isValid = false;
						nui.alert("编码由数字和字母组成，请重新输入！")
					}
				}
			});
			
		}
		
        function onOk(e) {
            SaveData();
        }
	</script>
</body>
</html>