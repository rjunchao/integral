<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): heFei
  - Date: 2016-07-13 17:06:45
  - Description:对风险影响程度的新增操作
-->
<head>
<title>新增风险影响程度</title>
</head>
<body>
	<div id="form1" method="post" class="nui-fit" style="height:60%;">
		<input id="pk_risk_effect_level" name="pk_risk_effect_level" class="nui-hidden" />
		<table style="width:100%;height:100%;table-layout:fixed;" class="nui-form-table_new">
			<tr>
				<td class="nui-form-label">影响程度编码：</td>
				<td>
					<input id="effect_level_code"  name="effect_level_code" class="nui-textbox nui-form-input"
						required="true"  onvalidation="validateCode"/>
				</td>
				<td class="nui-form-label">影响程度名称：</td>
				<td>
					<input id="effect_level_name"  name="effect_level_name" class="nui-textbox nui-form-input"
						required="true"  onvalidation="validateName" vtype="rangeLength:1,200"/>
				</td>
			</tr>
			<tr>
				<td class="nui-form-label">影响程度说明：</td>
				<td colspan="3">
					<input id="describe"  name="describe" class="nui-textarea nui-form-input" required="true"
						style="width:300px;height:60px;"/>
				</td>
			</tr>
		</table>
	</div>
	<div class="nui-toolbar" style="text-align:center;border:0;height:15%;padding:5px;">
	  	<a id="savedata" class="nui-button" style="width:60px;" iconCls="icon-save" onclick="SavaData()">保存</a>
		<span style="display:inline-block;width:25px;"></span>
		<a class="nui-button" style="width:60px;" iconCls="icon-cancel" onclick="onCancel()">取消</a>
	</div>
   <script type="text/javascript">
     nui.parse();
     var form = new nui.Form("#form1");//获取对象
     //编码的校验 
      function validateCode(e){
       	//得到输入的编码
       	var code = e.value;
       	//nui.alert(code);
       	//对空和null的校验
       	if(code == "" || code == null){
       	  e.isValid = false;
       	  //nui.alert("编码不能为空");
       	  return;
       	}
		var reg = new RegExp("^[0-9a-zA-Z]*$");
		if(!reg.test(code)){
			e.isValid = false;//输入的特殊字符
			nui.alert("编码由数字字母组成，请重新填写");
			return;
		}
       	//获取主键
       	var pk = nui.get("pk_risk_effect_level").getValue();
       	//alert(pk);
       	var json = nui.encode({vo:{effect_level_code:code,pk_risk_effect_level:pk}});
       	//编码唯一校验
       	nui.ajax({
       		url: "com.vbm.grc.basic.risk.effectlevel.riskeffectlevel.validateCode.biz.ext",
       		data: json,
       		cache: false,
       		type: "POST",
       		contentType: "text/json",
       		success:function(action){
       			if(action.count > 0){
       				e.isValid = false;
       				nui.alert("编码" + code + " 重复，请重新输入");
       			}
       			if(action.count == -1){
       				e.isValid = false;
       			}
       		},
       		error: function(jqXHR, textStatus,errorThorw){
       		 		alert(jqXHR.responseText);
       		}
       	}); 
      }
      //名称的校验
      function validateName(e){
         var value = e.value;
        //正则表达式
        var reg = new RegExp("^[\u2E80-\u9FFF]*$");
        if(!reg.test(value)){
        	//输入特殊字符，验证不通过
        	e.isValid = false;
        	nui.alert("名称只能是中文！");
        	return;
        }
      }
      //取消按钮的操作：取消时如果数据修改提示保存，没有修改直接关闭窗口
      function onCancel(action){
          CloseWindow(action);
      }
      //关闭弹窗的操作
      function CloseWindow(action){
            //关闭窗口的判断
      		/* if(action == "close" && form.isChanged){
      		  if(nui.confirm("数据被修改了，是否先保存？")){
      		  		return false;
      		  }
      		} */
      		if(window.CloseOwnerWindow){
      			return window.CloseOwnerWindow(action);
      		}else{
      		  window.close();//关闭窗口
      		}
      }
      //保存数据的操作
      function SavaData(){
      		//校验
      		//nui.alert("保存数据");
      		form.validate();
      		//验证没有通过
      		if(form.isValid == false){
      			return;
      		}
      		var o = form.getData(true, true);//获取数据
      		var json = nui.encode({vo:o});
      		nui.ajax({
      		   url: "com.vbm.grc.basic.risk.effectlevel.riskeffectlevel.addRiskEffectLevel.biz.ext",
      		   cache: false,
      		   data: json,
      		   type: "POST",
      		   contentType: "text/json",
      		   success: function(msg){
      		       	nui.alert(msg.msg.message);
					if(msg.msg.flag){
					CloseWindow("ok");
				}   
      		   },
      		   error: function(jqXHR, textStatus, errorThows){
      		    	nui.alert(jqXHR.responseText);
      		   }
      		});
      }
      //设置数据
      function setData(data){
      		data = nui.decode(data);
      		form.setData(data);
      }
   </script>
</body>
</html>