<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2017-09-06 11:23:07
  - Description: 添加客户积分
-->
<title>添加客户积分</title>
	<style type="text/css">
		.hiddenTr {
			display:none;
		}
	</style>
<head>

<body>
	<div id="form1" method="post" class="nui-fit" style="height:90%; width:100%;">
		<center>
		<table >
			<tr>
				<div id="customerIntegral">当前总积分为：</div> 
			</tr>
			<tr>  
				<td style="text-align:right;">账号：</td>
				<td style="text-align:left;">
					 <input id="customer_account"  name="customer_account" class="nui-textbox"
					 	 required="true" style="width:150px;" />
				</td>
				<td style="text-align:right;">是否存续期：</td>
				<td style="text-align:left;">
					 
					 	 <input property="editor" class="nui-combobox" data="durationData" valueField="id" textField="text"
						  id="duration" name="duration" style="width:150px;" required="true" value="N"
	                		style="width:100%;" minWidth="200" 
	                		/>
				</td>
			</tr>
			<tr>  
				<td style="text-align:right;">账号序号：</td>
				<td style="text-align:left;">
					 <input id="def7"  name="def7" class="nui-textbox"
					 	 required="true" style="width:150px;" />
				</td>
			</tr>
			<tr>  
				<td style="text-align:right;">存单号：</td>
				<td style="text-align:left;">
					<input id="deposit_receipt_num" name="deposit_receipt_num" 
						class="nui-textbox" required="true"  style="width:150px;"/>
				</td>
				<td style="text-align:right;">存单金额：</td>
				<td style="text-align:left;">
					<input id="deposit_receipt_amt" name="deposit_receipt_amt" 
					onvaluechanged="computeIntegral" 
						class="nui-textbox" required="true" vtype="float" style="width:150px;" />
				</td>
			</tr>
			<tr>
				<td style="text-align:right;">积分类型：</td>
				<td style="text-align:left;">
					  <input property="editor" class="nui-combobox" valueField="id" textField="text"
					  id="def2" name="def2" style="width:150px;" required="true"
	                	url="com.xbkj.gd.data_handle.cust.integralConfig.configQuery.biz.ext?integral_type=1"
	                		onvaluechanged="computeIntegral" 
	                		dataField="vos" style="width:100%;" minWidth="200" 
	                		/>
				</td>
				<td style="text-align:right;">积分：</td>
				<td style="text-align:left;">
					<input id="customer_idcard" class="nui-hidden" name= "customer_idcard" required="true"/>
					 <input id="customer_integral" name="customer_integral" vtype="float"  class="nui-textbox"
					 	enabled="false"
					 	required="true" style="width:150px;"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td style="text-align:center;">
					<input id="isShowCap23" class="nui-checkbox" text="显示资金来源2、3" onvaluechanged="isShowCap23"/> 
				</td>
				<td></td>
			</tr>
			<tr>  
				<td style="text-align:right;">资金来源1：</td>
				<td style="text-align:left;">
					  <input property="editor" class="nui-combobox" valueField="id" textField="text" style="width:150px;"
					  id="capital_source1" name="capital_source1" required="true"
	                	url="com.xbkj.gd.data_handle.cust.integralConfig.configQuery.biz.ext?integral_type=4"
	                		dataField="vos" style="width:100%;" minWidth="200" />
				</td>
				<td style="text-align:right;">资金来源金额1：</td>
				<td style="text-align:left;">
					<input id="capital_source_amt1" name="capital_source_amt1" required="true" 
						class="nui-textbox" required="true" vtype="float" style="width:150px;" />
				</td>
			</tr>
			
			<tr id="hiddenCapTr2" class="hiddenTr">  
				<td style="text-align:right;">资金来源2：</td>
				<td style="text-align:left;">
					  <input property="editor" class="nui-combobox" valueField="id" textField="text" style="width:150px;"
					  id="capital_source2" name="capital_source2" showNullItem="true"
	                	url="com.xbkj.gd.data_handle.cust.integralConfig.configQuery.biz.ext?integral_type=4"
	                		dataField="vos" style="width:100%;" minWidth="200" />
				</td>
				<td style="text-align:right;">资金来源金额2：</td>
				<td style="text-align:left;">
					<input id="capital_source_amt2" name="capital_source_amt2" 
						class="nui-textbox"  vtype="float" style="width:150px;" />
					
				</td>
			</tr>
			
			<tr id="hiddenCapTr3" class="hiddenTr">  
				<td style="text-align:right;">资金来源3：</td>
				<td style="text-align:left;">
					  <input property="editor" class="nui-combobox" valueField="id" textField="text" style="width:150px;"
					  id="capital_source3" name="capital_source3" showNullItem="true"
	                	url="com.xbkj.gd.data_handle.cust.integralConfig.configQuery.biz.ext?integral_type=4"
	                		dataField="vos" style="width:100%;" minWidth="200" />
				</td>
				<td style="text-align:right;">资金来源金额3：</td>
				<td style="text-align:left;">
					<input id="capital_source_amt3" name="capital_source_amt3" 
						class="nui-textbox"  vtype="float" style="width:150px;" />
					
				</td>
			</tr>
			<tr>
				<td></td>
				<td style="text-align:center;">
					<input id="isShowPeople23" class="nui-checkbox" text="显示营销人2、3" onvaluechanged="isShowPeople23"/> 
				</td>
				<td></td>
			</tr>
			<tr>
				<td style="text-align:right;">营销人1：</td>
				<td style="text-align:left;">
					<input id="marketing_people1" name="marketing_people1" required="true"vtype="int" 
						class="nui-textbox" style="width:150px;"/>
				</td>
				<td style="text-align:right;">营销金额1：</td>
				<td style="text-align:left;">
					<input id="marketing_people_amt1" name="marketing_people_amt1" 
						class="nui-textbox" required="true" vtype="float" style="width:150px;" />
				</td>
			</tr>
			
			<tr id="hiddenPeopTr2" class="hiddenTr">  
				<td style="text-align:right;">营销人2：</td>
				<td style="text-align:left;">
					<input id="marketing_people2" name="marketing_people2" vtype="int" 
						class="nui-textbox" style="width:150px;"/>
				</td>
				<td style="text-align:right;">营销金额2：</td>
				<td style="text-align:left;">
					<input id="marketing_people_amt2" name="marketing_people_amt2" 
						class="nui-textbox"  vtype="float" style="width:150px;" />
					
				</td>
			</tr>
			<tr id="hiddenPeopTr3" class="hiddenTr">  
				<td style="text-align:right;">营销人3：</td>
				<td style="text-align:left;">
					<input id="marketing_people3" name="marketing_people3" vtype="int" 
						class="nui-textbox" style="width:150px;"/>
				</td>
				<td style="text-align:right;">营销金额3：</td>
				<td style="text-align:left;">
					<input id="marketing_people_amt3" name="marketing_people_amt3" 
						class="nui-textbox"  vtype="float" style="width:150px;" />
					
				</td>
			</tr>
			<tr >
				<td style="text-align:right;">备注：</td>
				<td style="text-align:left;" colspan="4">
					 <input id="def8" name="def8" class="nui-textarea" style="width:380px;"/>
				</td>
			</tr>
		</table>
		</center>
	</div>
	 <div class="nui-toolbar" style="text-align:center;padding:0px;height:10%;" borderStyle="border:0;">
        <a id="savedata" class="nui-button" style="width:60px;" iconCls="icon-save" onclick="SaveData()">保存</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="nui-button" style="width:60px;" iconCls="icon-cancel" onclick="onCancel()">取消</a>
    </div>
</body>
	 <script type="text/javascript">
	 	var durationData=[{id:'Y', text:'Y'}, {id:'N', text:'N'}];
		nui.parse();
		var form = new nui.Form("#form1");
		
		var def1 = "";
		function computeIntegral(e){
			debugger;
			var def2 = 0;
			if(e.selected){
				def1 = e.selected.text;
				var vals = e.value.split("_");
				def1 = vals[0];
				def2 = vals[1];	
			}else{
				var temp = nui.get("def2").getValue();
				var vals = temp.split("_");
				def1 = vals[0];
				def2 = vals[1];
			}
			var type = def2;
			var amt = Number(nui.get("deposit_receipt_amt").getValue());
			if(type > 0 && amt > 0){
				var integral = Math.round(Number(amt * type));
				nui.get("customer_integral").setValue(integral);
				nui.get("marketing_people_amt1").setValue(amt);
			}
			if(amt > 0){
				nui.get("marketing_people_amt1").setValue(amt);
				nui.get("capital_source_amt1").setValue(amt);
			}
		}
		
		
		/**
		显示资金来源
		*/
		function isShowCap23(e){
			debugger;
			if(nui.get("isShowCap23").checked){
				/* nui.get("hiddenPeopTr2").hidden();
				nui.get("hiddenPeopTr3").hidden(); */
				
				$("#hiddenCapTr2").removeClass("hiddenTr");
				$("#hiddenCapTr3").removeClass("hiddenTr");
			}else{
				$("#hiddenCapTr2").addClass("hiddenTr");
				$("#hiddenCapTr3").addClass("hiddenTr");
			}
		}
		
		/**
		控制显示营销人
		*/
		function isShowPeople23(e){
			debugger;
			if(nui.get("isShowPeople23").checked){
				/* nui.get("hiddenPeopTr2").hidden();
				nui.get("hiddenPeopTr3").hidden(); */
				
				$("#hiddenPeopTr2").removeClass("hiddenTr");
				$("#hiddenPeopTr3").removeClass("hiddenTr");
			}else{
				$("#hiddenPeopTr2").addClass("hiddenTr");
				$("#hiddenPeopTr3").addClass("hiddenTr");
			}
		}
		
		function onValueChanged(e){
			//nui.alert(e.value);
			var val = e.value;
			if(val <= 0){
				nui.alert("积分或者金额不能小于等于0！");
				return;
			}
		}
		
		var pk_customer_info = "";
		function SetData(data){
			var row = nui.clone(data);
			if(row){
				pk_customer_info = row.pk_customer_info;
				nui.get("customer_idcard").setValue(pk_customer_info);
				var json = {idcard:pk_customer_info};
				nui.ajax({
					url:"com.xbkj.gd.data_handle.cust.integralManager.countCustInegral.biz.ext",
					data:json,
					cache:false,
					contentType:"text/json",
					success:function(data){
						var data = nui.decode(data);
						var integral = data.count;
						/* $("#customerIntegral").val("当前总积分为：asas"); 
						nui.get("customerIntegral").setValue("当前总积分为：" + data.count); */
						document.getElementById("customerIntegral").innerHTML = "当前总积分为：" + parseFloat(integral).toFixed(2);
					}
				}); 
				
			}else{
				nui.alert("身份证号为空");
			}
		}
		
		/**
			校验营销金额
		*/
		function validPeopAmt(){
			//校验金额
			var amt1 = Number(nui.get("marketing_people_amt1").getValue());
			//总金额
			var amt = Number(nui.get("deposit_receipt_amt").getValue());
			if(nui.get("isShowPeople23").checked){
				var amt2 = Number(nui.get("marketing_people_amt2").getValue());
				var amt3 = Number(nui.get("marketing_people_amt3").getValue());
				if(!(amt == (amt1 + amt2 + amt3))){
					if(!(amt == Number(parseFloat(amt1+ amt2 + amt3).toFixed(2)))){
						nui.alert("营销金额之和必须等于存单金额");
						return false;
					}
				}
				var name2 = nui.get("marketing_people2").getValue();
				if(amt2 > 0){
					if(name2 == null || name2 == "" || name2 == undefined){
						nui.alert("已有营销金额，营销人必须输入");				
						return false;
					}else{
						if(name2.length != 8){
							//营销人长度
							nui.alert("营销人长度只能为8位");					
							return false;
						}
					}
				}else{
					if(name2.length > 0){
						nui.alert("已有营销人，营销金额必须输入");				
						return false;
					}
				}
				var name3 = nui.get("marketing_people3").getValue();
				if(amt3 > 0){
					if(name3 == null || name3 == "" || name3 == undefined){
						nui.alert("已有营销金额，营销人必须输入");					
						return false;
					}else{
						if(name3.length != 8){
							//营销人长度
							nui.alert("营销人长度只能为8位");					
							return false;
						}
					}
				}else{
					if(name3.length > 0){
						nui.alert("已有营销人，营销金额必须输入");				
						return false;
					}
				}
			}else{
				if(!(amt == (amt1))){
					nui.alert("营销人金额之和必须等于存单金额");
					return false;
				}
			}
			return true;
		}
		/**
			校验資金来源金额
		*/
		function validCapAmt(){
			//校验金额
			var amt1 = Number(nui.get("capital_source_amt1").getValue());
			//总金额
			var amt = Number(nui.get("deposit_receipt_amt").getValue());
			if(nui.get("isShowCap23").checked){
				var amt2 = Number(nui.get("capital_source_amt2").getValue());
				var amt3 = Number(nui.get("capital_source_amt3").getValue());
				if(!(amt == (amt1 + amt2 + amt3))){
					if(!(amt == Number(parseFloat(amt1+ amt2 + amt3).toFixed(2)))){//防止四捨五入
						nui.alert("资金来源金额之和必须等于存单金额");
						return false;
					}
					
				}
				var name2 = nui.get("capital_source2").getValue();
				if(amt2 > 0){
					if(name2 == null || name2 == "" || name2 == undefined){
						nui.alert("填了资金来源金额，资金来源必须选择");					
						return false;
					}
				}else{
					if(name2.length > 0){
						nui.alert("选了资金来源，资金来源金额必须输入");						
						return false;
					}
				}
				
				var name3 = nui.get("capital_source3").getValue();
				if(amt3 > 0){
					if(name3 == null || name3 == "" || name3 == undefined){
						nui.alert("填了资金来源金额，资金来源必须选择")				
						return false;
					}
				}else{
					if(name3.length > 0){
						nui.alert("选了资金来源，资金来源金额必须输入");				
						return false;
					}
				}
			}else{
				if(!(amt == (amt1))){
					nui.alert("资金来源金额之和必须等于存单金额");
					return false;
				}
			}
			return true;
		}
		
		//保存损失事件类型记录
		function SaveData(){
			
			if(!validPeopAmt()){
				return false;
			}
			
			if(!validCapAmt()){
				return false;
			}
			
			//校验
			form.validate();
			if(form.isValid() == false){
				nui.get("savedata").setEnabled(true);
				return;
			}
			
			var name = nui.get("marketing_people1").getValue();
			if(name.length != 8){
				//营销人长度
				nui.alert("营销人长度只能为8位");					
				return false;
			}
			var depositNum = nui.get("deposit_receipt_num").getValue();
			if(!(depositNum.length == 3 || depositNum.length == 11 || depositNum.length == 10)){
				//存单号长度
				nui.alert("存单号长度是3位、10位、11位");					
				return false;
			}
			
			var depositAmt = Number(nui.get("deposit_receipt_amt").getValue());
			if(!(depositAmt > 0 && depositAmt < 100000000)){
				//金额
				nui.alert("金额必须小于1亿");					
				return false;
			}
			
			//得到数据
			var o = form.getData(true, true);
			
			var vals = o.def2.split("_");
				o.def1 = vals[0];
				o.def2 = vals[1];
			
			if(o.customer_integral <= 0){
				nui.alert("积分必须大于0");
				return;
			}
			if(o.deposit_receipt_amt <= 0){
				nui.alert("金额必须大于0");
				return;
			}
			if(o.def7.length != 3){
				nui.alert("账号序号必须为3位");
				return;
			}
			o.customer_idcard=pk_customer_info;
			o.def1= def1;//类型标题
			o.def4 = 1;//类型，添加
			/* nui.alert("pk " + o.customer_idcard); */
			var json = nui.encode({flag:"0",vo:o});
			debugger;
			nui.get("savedata").setEnabled(false);//防止反复提交
			nui.ajax({
				url:"com.xbkj.gd.data_handle.cust.integralNew.addIntegral.biz.ext",
				cache:false,
				data:json,
				type:"POST",
				contentType:"text/json",
				success: function(text){
					var msg = nui.decode(text);
					nui.alert(msg.msg.message);
					if(msg.msg.flag){//添加成功关闭，添加不成功，不关闭窗口
						CloseWindow("ok");
					}
					nui.get("savedata").setEnabled(true);//防止反复提交
				},
				error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                    CloseWindow();
                }
			});
			nui.get("savedata").setEnabled(true)
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
				url:".biz.ext",
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
        
        //机构选择
		function choseOrg(e){
			//nui.alert("机构选择");
			var btnEdit = this;
			nui.open({
				url:"<%=request.getContextPath() %>/coframe/auth/login/login_org.jsp",
				showMaxButton: false,
			  	title: "选择机构",
			 	width: 350,
			  	height: 350,
			  	ondestroy: function (action) {             
			  		if (action == "ok") {
			      		var iframe = this.getIFrameEl();
			      		var data = iframe.contentWindow.GetData();
			      		data = nui.clone(data);
			      		if (data) {
			       			btnEdit.setValue(data.orgcode);
				   			btnEdit.setText(data.orgname);
			       		}
			      	}
				}                                                          
			});		
		}
		
	</script>
</html>