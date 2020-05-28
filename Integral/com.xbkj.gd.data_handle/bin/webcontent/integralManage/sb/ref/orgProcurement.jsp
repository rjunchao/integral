<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2020-05-10 16:56:11
  - Description:
-->
<head>
<title>礼品采购</title>
</head>
<body>
	<div id="form1" method="post" class="nui-fit" style="height:80%; width:100%;">
		<center>
			<table >
				<tr>
					<td style="text-align:right;"></td>
					<td style="text-align:left;" colspan="3">
					</td>
				</tr>
				<tr>
					<td style="text-align:right;">备注：</td>
					<td style="text-align:left;" colspan="3">
						<input id="remark" name="remark" required="true"
							class="nui-textarea"  style="width:140px;height:60px;"/>
					</td>
				</tr>
				<!-- <tr>  
					<td style="text-align:right;">实际采购数量：</td>
					<td style="text-align:left;">
						<input id="def2" name="def2" 
							class="nui-textbox" required="true" vtype="float" style="width:150px;" />
					</td>
				</tr> -->
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
		var datas = null;
		var opt_type = 2;
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
			let data = form.getData(true, true);
			let remark = data.remark;
			
			var ids = "";
			for(let i = 0 ; i < datas.length; i++){
				ids = datas[0].pk_org_apply_product + "," ;
			}
			debugger;
			ids = ids.substr(0, ids.length-1);
			var json = nui.encode({params:{"ids":ids,"remark":remark,"opt_type":opt_type}});
			
			nui.confirm("确定执行该操作？","确认", function(action){
				if(action == "ok"){
					nui.ajax({
						url:"com.xbkj.gd.data_handle.prod.procurement.prodProcStatus.biz.ext",
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
			});
			
		}
		
		function SetData(data){
			debugger;
			let tempData = nui.clone(data);
			datas = tempData.datas;
			opt_type = tempData.opt_type;
			
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