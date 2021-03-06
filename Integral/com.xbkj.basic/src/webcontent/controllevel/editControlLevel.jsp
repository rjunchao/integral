<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): HEFEI
  - Date: 2016-08-04 16:58:26
  - Description:
-->
<head>
<title>修改控制等级</title>
</head>
<div id="form1" method="post" class="nui-fit" style="height:80%;">
      <input id="pk_control_level" name="pk_control_level" class="nui-hidden" />
		<table style="width:100%;align:center;" class="nui-form-table_new">
			<tr style="width:100%;hegiht:40px">
			    <td class="nui-form-label">开始值：</td>
				<td style="width:150px;">
					<input id="down_val" name="down_val"  class="nui-textbox nui-form-input" onvalidation="validateVal" required="true" vtype="maxLength:8"/>
				</td>
				<td class="nui-form-label">结束值：</td>
				<td style="width:150px;">
					<input id="up_val" name="up_val" class="nui-textbox nui-form-input" onvalidation="validateVal" required="true" vtype="maxLength:8"/>
				</td>
			</tr>
			
			<tr style="width:100%;hegiht:40px">
			
			    <td class="nui-form-label">控制等级：</td>
				<td style="width:150px;">	
					<input id="control_level" name="control_level" class="nui-textbox nui-form-input" onvalidation="validateLevel" required="true" vtype="maxLength:40"/>
				</td>
				
				<td class="nui-form-label">图示：</td>
				<td style="width:150px;">
					<input id="colour" name="colour" valueField="id" textField="text" data="colour" ondrawCell="onDrawCell" class="nui-combobox nui-form-input" required="true"/>
				</td>
			</tr>
		</table>
		</br>
	</div>
	<div class="nui-toolbar" style="text-align:center;padding:0px;height:20%;"borderStyle="border:0;">
        <a class="nui-button" style="width:60px;" iconCls="icon-save" onclick="saveData()">保存</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="nui-button" style="width:60px;" iconCls="icon-cancel" onclick="cancel()">取消</a>
    </div>  
<body>
</body>
   <script type="text/javascript">
          var colour = [{id:'red',text:'红色'},{id:'orange',text:'橙色'},{id:'yellow',text:'黄色'},{id:'green',text:'绿色'},{id:'blue',text:'蓝色'},{id:'violet',text:'紫色'}];
          nui.parse();
          var form = new nui.Form("#form1");
          //控制等级只能是中文的控制
		 function validateLevel(e){
			var val = e.value;
			var reg = new RegExp("^[\u2E80-\u9FFF]*$");//只能输入中文的正则表达式
			if(!reg.test(val)){
				//输入的特殊字符:验证不通过
				e.isValid = false;
				nui.alert("等级描述只能输入中文！");
				return;
			}
		}
		//对开始值和结束值做验证：只能是数字
    	function validateVal(e){
			var val = e.value;
			var reg = new RegExp("^[0-9]*$");//数字的正则表达式
			if(!reg.test(val)){
				//输入的特殊字符：验证不通过
				e.isValid = false;
				nui.alert("只能输入数字");
				return;
			}	
    	} 
    	//对单元格样式的操作
    	function onDrawCell(e){
    	  if(e.value == "红色"){
    	     e.cellStyle = "background:red";//cellStyle设置单元格样式
    	  }else if(e.value == "橙色"){
    	     e.cellStyle = "background:orange"
    	  }else if(e.value == "黄色"){
    	     e.cellStyle = "background:yellow"
    	  }else if(e.value == "绿色"){
    	     e.cellStyle = "background:green"
    	  }else if(e.value == "蓝色"){
    	     e.cellStyle = "background:blue"
    	  }else if(e.value == "紫色"){
    	     e.cellStyle = "background:violet"
    	  }
    	}
       //保存数据的操作
       
    function saveData(){
    	var o = form.getData(true,true); 
    	form.validate();//数据验证
		if (form.isValid() == false) 
		{//验证不通过
		return;
		}
		var down_val = nui.get("down_val").value;
		var up_val = nui.get("up_val").value;
		if(down_val > up_val){
		  nui.alert("开始值不能大于结束值");
		  return;
		}
		var json = nui.encode({vo:o});
		    nui.ajax({
			    url:"com.vbm.grc.basic.control.level.ControlLevel.updateControlLevel.biz.ext",
			    type: "POST",
			    data: json,
			    cache: false,
			    contentType: "text/json",
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
      //取消按钮的操作
      function cancel(e){
    	CloseWindow("cancel");
      }
     //关闭窗口
	 function CloseWindow(action) {            
        if (action == "close" && form.isChanged()) {
            if (nui.confirm("数据被修改了，是否先保存？")) {
                return false;
            }
        }
        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
        else window.close();            
    }
   
     function onReset(){
      form.reset();
      form.setChanged(false);
    } 
     //设置数据
     function SetData(data){
   		//设置数据
		data = nui.clone(data);
		//设置表单数据
		form.setData(data);
    }
   
   </script>
</html>