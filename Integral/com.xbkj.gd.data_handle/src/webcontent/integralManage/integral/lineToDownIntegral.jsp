<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2020-02-25 19:18:16
  - Description: 线上积分转线下
-->
<head>
<title></title>
</head>
<body>
	<div id="form1" method="post" class="nui-fit" style="height:80%; width:100%;"vtype="int">
		<center>
		<table >
			<br/>
			<tr>           
				<td style="text-align:right;">积分：</td>
				<td>
					 <input id="ap_integral" name="other_integral" class="nui-textbox" style="width:140px;" vtype="int" required="true"/>
					 <input name="integral_type" class="nui-hidden" style="width:140px;" value="1"/>
				</td>
			</tr>
			<tr>
				<td style="text-align:right;">备注：</td>
				<td style="text-align:left;">
					 <input id="remark" name="remark" class="nui-textarea" style="width:140px;"/>
				</td>
			</tr>
		</table>
		</center>
	</div>
	 <div class="nui-toolbar" style="text-align:center;padding:0px;height:20%;" borderStyle="border:0;">
        <a id="savedata" class="nui-button" style="width:60px;" iconCls="icon-save" onclick="SaveData()">保存</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="nui-button" style="width:60px;" iconCls="icon-cancel" onclick="onCancel()">取消</a>
    </div>
</body>
	 <script type="text/javascript">
		nui.parse();
		var form = new nui.Form("#form1");
      	var pk_customer_info = "";
		function SetData(data){
			var row = nui.clone(data);
			if(row){
				pk_customer_info = row.pk_customer_info;
			}else{
				nui.alert("身份证号为空");
			}
		}
		
		//
		function SaveData(){
			debugger;
			//需要判断积分减少后不能为负数。
			var o = form.getData(true, true);
			o.customer_idcard=pk_customer_info;
			if(o.ap_integral <= 0){
				nui.alert("积分必须大于0");
				return ;
			}
			debugger;
			var json = nui.encode({vo:o});
			nui.get("savedata").setEnabled(false);//防止反复提交
			nui.ajax({
				url:"com.xbkj.gd.data_handle.cust.otherIntegral.addOtherIntegral.biz.ext",
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
					nui.get("savedata").setEnabled(true)
				},
				error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                    CloseWindow();
                }
			});
			nui.get("savedata").setEnabled(true)
		}
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