<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2017-09-06 10:23:07
  - Description: 添加客户信息
-->
<head>
<title>添加客户信息</title>
<body>
	<div id="form1" method="post" class="nui-fit" style="height:80%; width:100%;">
		<center>
		<table >
			<tr>           
				<td style="text-align:right;">客户姓名：</td>
				<td style="text-align:left;">
					 <input id="customer_name" name="customer_name"  class="nui-textbox"
					 	required="true" style="width:150px;"/>
				</td>
				<td style="text-align:right;">身份证号：</td>
				<td style="text-align:left;">
					 <input id="customer_idcard"  name="customer_idcard" class="nui-textbox"
					 	 required="true" style="width:150px;" />
				</td>
			</tr>
			<tr>
				<td style="text-align:right;">手机号：</td>
				<td style="text-align:left;">
					<input id="customer_phone" name="customer_phone" 
						class="nui-textbox" vtype="int" style="width:150px;" required="true"/>
				</td>
				<td style="text-align:right;">当前可用积分：</td>
				<td style="text-align:left;">
					<input id="now_usable_integral" enabled="false" name="now_usable_integral" class="nui-textbox" 
						vtype="float" style="width:150px;"/>
				</td>
				
				
			</tr>
			<!-- 添加的时候不能添加积分吧 -->
			<tr>
				<td style="text-align:right;">备注：</td>
				<td style="text-align:left;" colspan="3">
					<input id="recommend_phone" name="recommend_phone" 
						class="nui-textarea"  style="width:400px;height:60px;"/>
				</td>
					<!-- <td style="text-align:right;">当前可用积分：</td>
				<td style="text-align:left;">
					<input id="now_usable_integral" name="now_usable_integral" class="nui-textbox" 
						vtype="float" style="width:150px;"/>
				</td>
				
				<td style="text-align:right;">建档机构：</td>
				<td style="text-align:left;">
					<input id="input_org" name="input_org" class="nui-buttonedit" onbuttonclick="choseOrg" 
						style="width:150px;"/>
					<input id="from_number" name="number" class="nui-hidden" />
				</td>
				 -->
			</tr>
		</table>
		</center>
	</div>
	 <div class="nui-toolbar" style="text-align:center;padding:0px;height:20%;" borderStyle="border:0;">
        <a id="savedata" class="nui-button" style="width:60px;" iconCls="icon-save" onclick="onOk()">保存</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="nui-button" style="width:60px;" iconCls="icon-cancel" onclick="onCancel()">取消</a>
    </div>
</body>
	 <script type="text/javascript">
		nui.parse();
		var form = new nui.Form("#form1");
		
		//保存损失事件类型记录
		function SaveData(){
			nui.get("savedata").enabled = false;//防止反复提交
			//校验
			form.validate();
			if(form.isValid() == false){
				nui.get("savedata").enabled = true;
				return;
			}
			//得到数据
			var o = form.getData(true, true);
			
			var json = nui.encode({vo:o});
			nui.ajax({
				url:"com.xbkj.gd.data_handle.cust.CustomerManager.addCustomer.biz.ext",
				cache:false,
				data:json,
				type:"POST",
				contentType:"text/json",
				success: function(text){
					var msg = nui.decode(text);
					nui.alert(msg.msg.message);
					if(msg.msg.flag){//添加成功关闭，添加不成功，不关闭窗口
						CloseWindow("ok");
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