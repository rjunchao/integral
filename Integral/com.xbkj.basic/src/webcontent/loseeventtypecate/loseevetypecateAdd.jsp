<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2016-07-01 10:23:07
  - Description: 添加损失事件类型
-->
<head>
<title>Title</title>
<body>
	<div id="form1" method="post" class="nui-fit" style="height:85%;">
		<input id="parent_code" name="parent_code" class="nui-hidden" />
		<table style="width:100%;height:100%;table-layout:fixed;" class="nui-form-table_new">
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
	</div>
	 <div class="nui-toolbar" style="text-align:center;padding:0px;height:15%;" borderStyle="border:0;">
        <a id="savedata" class="nui-button" style="width:60px;" iconCls="icon-save" onclick="onOk()">保存</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="nui-button" style="width:60px;" iconCls="icon-cancel" onclick="onCancel()">取消</a>
    </div>    
</body>
	 <script type="text/javascript">
	 	var leafData = [{id:0,text:'否'},{id:1,text:'是'}];
		nui.parse();
		var form = new nui.Form("#form1");
		var pid = "";
		
		//保存损失事件类型记录
		function SaveData(){
			nui.get("savedata").enabled = false;//防止反复提交
			//校验
			form.validate();
			if(form.isValid() == false){
				return;
			}
			//得到数据
			var o = form.getData(true, true);
			if(pid == ""){
				o.parent_code = "root";//设置pid	
			}else{
				o.parent_code = pid;//设置pid
			}
			var json = nui.encode({vo:o});
			nui.ajax({
				url:"com.vbm.grc.basic.loseevent.loseeventtype.addloseevent.biz.ext",
				cache:false,
				data:json,
				type:"POST",
				contentType:"text/json",
				success: function(msg){
					if(msg.msg.flag){//添加成功关闭，添加不成功，不关闭窗口
						CloseWindow("ok");
					}else{
						nui.get("savedata").enabled = false;//防止反复提交
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
		
		/*
			校验编码
			空校验
			格式校验（数字和英文）
			唯一性校验
		*/
		function onCodeValidation(e){
			//得到输入的编码
			var val = e.value;
			if(val == "" || val == null){
				e.isValid = false;
				return;
			}
			
			/* var regu = "^[0-9a-zA-Z]*$"
			var re = new RegExp(regu);
			if(!re.test(val)){
				e.isValid = false;//输入的特殊字符
				nui.alert("编码由数字字母组成，请重新填写");
				return;
			} */
			//需要校验的编码
			var json = nui.encode({vo:{eventtype_code:val}});
			nui.ajax({
				url:"com.vbm.grc.basic.loseevent.loseeventtype.validatecode.biz.ext",
				cache:false,
				data:json,
				type:"POST",
				contentType:"text/json",
				success: function(action){
					if(action.count > 0){
						e.isValid = false;
						nui.alert("编码 "+val+" 重复，请重新输入");
					}
					if(action.count < 0){
						e.isValid = false;
					}
				}
			});
			
		}
		
		//初始化数据，得到parentCode
		function SetData(data){
			//跨页面的数据，必须克隆才可以使用
			data = nui.clone(data);
			pid = data.parentCode;
			var level = data.level;
			level = Number(level) + 1;//层级+1
			
			nui.get("eventtype_level").enabled= false;//层级不可编辑
			nui.get("eventtype_level").setValue(level);//设置pid
			nui.get("parent_code").setValue(pid);//设置pid
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
        function onOk(e) {
            SaveData();
        }
        function onCancel(e) {
            CloseWindow("cancel");
        }
		
	</script>
</html>