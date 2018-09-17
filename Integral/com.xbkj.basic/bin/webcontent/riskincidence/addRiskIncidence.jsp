<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): lwl
  - Date: 2016-07-13 16:44:39
  - Description:
-->
<head>
<title>新建</title>
</head>
<body>
	<div id="form1" method="post" class="nui-fit" style="height:60%;">
		<table style="width:100%;height:100%;table-layout:fixed;" class="nui-form-table_new">
			<tr>
				<td class="nui-form-label">概率等级编码：</td>
				<td>	
					<input id="incidence_code" name="incidence_code" class="nui-textbox nui-form-input" 
						onvalidation="validateCode" required="true" vtype="maxLength:20"/>
				</td>
				<td class="nui-form-label">概率等级名称：</td>	
				<td>
					<input id="incidence_name" name="incidence_name" class="nui-textbox nui-form-input"
						 onvalidation="validateName" required="true" vtype="maxLength:100"/>
				</td>
			</tr>
			<tr>
				<td class="nui-form-label">概率说明：</td>
				<td colspan="3">
					<input id="describe" name="describe" class="nui-textarea nui-form-input" 
						style="width:300px;height:60px;" vtype="maxLength:200"/>
				</td>
			</tr>
		</table>
	</div>
	<div class="nui-toolbar" style="text-align:center;padding:5px;height:15%;" borderStyle="border:0;">
        <a class="nui-button" style="width:60px;" iconCls="icon-save" onclick="saveData()">保存</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="nui-button" style="width:60px;" iconCls="icon-cancel" onclick="cancel()">取消</a>
    </div>  
</body>
</html>
<script type="text/javascript">
	nui.parse();
    var form = new nui.Form("#form1");
    
    //校验名称
	function validateName(e){
		var val = e.value;
		var reg = new RegExp("^[\u2E80-\u9FFF]*$");
		if(!reg.test(val)){
			//输入的特殊字符
			e.isValid = false;
			nui.alert("制度名称只能输入中文！");
			return;
		}
	}
    //编码查重
    function validateCode(e){
    	//得到输入的编码
		var val = e.value;
		if(val == "" || val == null){
			e.isValid = false;
		}
		var regu = "^[0-9a-zA-Z]*$"
		var re = new RegExp(regu);
		if(!re.test(val)){
			//输入的特殊字符
			e.isValid = false;
			nui.alert("概率等级编码由数字字母组成，请重新填写");
			return;
		}	
		var json = nui.encode({vo:{incidence_code:val}});
		$.ajax({
			url:"com.vbm.grc.basic.risk.incidence.riskincidence.validateIncidenceCode.biz.ext",
			cache:false,
			data:json,
			type:"POST",
			contentType:"text/json",
			success: function(action){
				if(action.result > 0){
					e.isValid = false;
					nui.alert("概率等级编码 "+val+" 重复");
					return;
				}
				if(action.result == -1){
					e.isValid = false;
					nui.alert("概率等级编码为空");
					return;
				}
			}
		});
    }      
    //保存
    function saveData(){
    	//校验
    	form.validate();
		  if(form.isValid() == false){
		  	return;
		  }
		  var o = form.getData(true,true); 
		  var json = nui.encode({vo:o});
		  nui.ajax({
		    url:"com.vbm.grc.basic.risk.incidence.riskincidence.addRiskIncidence.biz.ext",
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