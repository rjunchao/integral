<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): lwl
  - Date: 2016-08-03 16:33:54
  - Description:
-->
<head>
<title>编辑</title>
</head>
<body>
	<div id="form1" method="post" class="nui-fit" style="height:80%;">
		<input id="pk_risk_level" name="pk_risk_level" class="nui-hidden" />
		<table style="width:100%;align:center;" class="nui-form-table_new">
			<tr>
			    <td class="nui-form-label">开始值：</td>
				<td style="width:130px;">
					<input id="down_val" name="down_val"  class="nui-textbox nui-form-input" 
						onvalidation="validateVal" required="true" vtype="maxLength:8"/>
				</td>
				<td class="nui-form-label">结束值：</td>
				<td style="width:130px;">
					<input id="up_val" name="up_val" class="nui-textbox nui-form-input"
						 onvalidation="validateVal2" required="true" vtype="maxLength:8"/>
				</td>
			</tr>
			
			<tr>
			    <td class="nui-form-label">风险等级：</td>
				<td>	
					<input id="risk_level" name="risk_level" class="nui-textbox nui-form-input" onvalidation="validateLevel" required="true" vtype="maxLength:40"/>
				</td>
				<td class="nui-form-label">图示：</td>
				<td style="width:150px;">
					<input id="colour" name="colour" valueField="id" textField="text" 
						data="colour" ondrawCell="onDrawCell" class="nui-combobox nui-form-input" required="true"/>
				</td>
				<!-- 
					<input id="colour" name="colour" valueField="id" textField="text" 
						data="colour" ondrawCell="onDrawCell" class="nui-combobox nui-form-input" required="true"/>
				</td> -->
			</tr>
		</table>
	</div>
	<div class="nui-toolbar" style="text-align:center;padding:5px;height:20%;" borderStyle="border:0;">
        <a class="nui-button" style="width:60px;" iconCls="icon-save" onclick="saveData()">保存</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="nui-button" style="width:60px;" iconCls="icon-cancel" onclick="cancel()">取消</a>
    </div>  
</body>
</html>
<script type="text/javascript">
	var colour = [{id:'red',text:'红色'},{id:'yellow',text:'黄色'},
		{id:'violet',text:'紫色'},{id:'blue',text:'蓝色'},{id:'green',text:'绿色'}];
	nui.parse();
	var form = new nui.Form("#form1");

	//设置背景颜色
	function onDrawCell(e){
		var item = e.record,field=e.field,value=e.value;
		if(value == "红色"){
			e.cellStyle = "background:#FF3333";
		}else if(value == "黄色"){
			 e.cellStyle = "background:#FFFF66";
		}else if(value == "紫色"){
			 e.cellStyle = "background:#990099";
		}else if(value == "蓝色"){
			 e.cellStyle = "background:#0066FF";
		}else if(value == "绿色"){
			e.cellStyle = "background:#00FF66";
		}
	}
	function SetData(data){
   		//设置数据
		data = nui.clone(data);
		//设置表单数据
		form.setData(data);
		//var colour = data.colour;
    }
    //校验风险等级
	function validateLevel(e){
		var val = e.value;
		var reg = new RegExp("^[\u2E80-\u9FFF]*$");
		if(!reg.test(val)){
			//输入的特殊字符
			e.isValid = false;
			nui.alert("等级描述只能输入中文！");
			return;
		}
	}
	//校验开始值
    function validateVal(e){
		var val = e.value;
		var reg = new RegExp("^[0-9]*$");
		if(!reg.test(val)){
			//输入的特殊字符
			e.isValid = false;
			nui.alert("只能输入数字");
			return;
		}	
    }
    //校验结束值 
    function validateVal2(e){
		var val = e.value;
		var reg = new RegExp("^[0-9]*$");
		if(!reg.test(val)){
			//输入的特殊字符
			e.isValid = false;
			nui.alert("只能输入数字");
			return;
		}
		var down_val = nui.get("down_val").getValue();	
		var up_val = nui.get("up_val").getValue();	
		if(down_val>up_val){
			nui.alert("结束值不能小于开始值");
			return;
		}  
    }  
    //保存
    function saveData(){
    	var o = form.getData(true,true); 
    	form.validate();
		if (form.isValid() == false) return;
		var json = nui.encode({vo:o});
		    nui.ajax({
			    url:"com.vbm.grc.basic.risk.level.risklevel.editRiskLevel.biz.ext",
			    type:'POST',
			    data:json,
			    cache:false,
			    contentType:'text/json',
			    success:function(msg){
			    	if(msg.msg.flag){
						nui.alert(msg.msg.message);
						CloseWindow("ok");
						return;
					}else{
						nui.alert(msg.msg.message);
						CloseWindow("close");
						return;
					} 
			    },
			    error: function (jqXHR, textStatus, errorThrown) {
	                alert(jqXHR.responseText);
	                CloseWindow();
	            }
		  }); 
    }
    function onReset(){
      form.reset();
      form.setChanged(false);
    }
    //取消
    function cancel(e){
    	CloseWindow("cancel");
    }
    //关闭窗口
	function CloseWindow(action) {            
        if (action == "close" && form.isChanged()) {
            if (confirm("数据被修改了，是否先保存？")) {
                return false;
            }
        }
        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
        else window.close();            
    }
</script>