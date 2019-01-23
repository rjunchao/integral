<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2018-09-07 21:23:57
  - Description:
-->
<head>
<title>vip积分赠送</title>

<body>
	<div id="form1" method="post" class="nui-fit" style="height:80%; width:100%;">
		<center>
		<table >
			<br/>
			<br/>
			<tr>           
				<td style="text-align:right;">兑换商品：</td>
				<td>
					<input id="def1" name="def1" width="140px" class="nui-textbox" required="true" 
						value="提前支取" allowInput="false"/>
				</td>
				<td style="text-align:right;">支取积分：</td>
				<td>
					<input id="customer_integral" name="customer_integral" width="140px" class="nui-textbox" required="true" vtype="float"/>
				</td>
			</tr>
			<tr>           
				<td style="text-align:right;">账号：</td>
				<td>
					<input id="customer_account" name="customer_account" width="140px" class="nui-textbox" required="true" />
				</td>
				<td style="text-align:right;">账号序号：</td>
				<td>
					<input id="def7" name="def7" width="140px" class="nui-textbox" required="true" />
				</td>
				
			</tr>
			<tr >
				<td style="text-align:right;">备注：</td>
				<td style="text-align:left;" colspan="3">
					 <input id="remark" name="remark" class="nui-textarea" style="width:280px;"/>
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
			
			if(o.def7.length != 3){
				nui.alert("账号序号必须为3位");
				return;
			}
			
			o.customer_idcard=pk_customer_info;
			o.def4=4;//类型
			o.def5=1;//兑换数量
			o.def2=o.customer_integral;//兑换类型积分
			//值在def2中
			debugger;
			var json = nui.encode({vo:o});
			nui.get("savedata").setEnabled(false);//防止反复提交
			nui.ajax({
				url:"com.xbkj.gd.data_handle.cust.integralNew.leadIntegral.biz.ext",
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