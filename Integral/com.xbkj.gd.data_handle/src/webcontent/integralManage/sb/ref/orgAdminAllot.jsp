<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2019-10-26 08:44:16
  - Description: 支行管理员他行调拨
-->
<head>
<title>支行管理员他行调拨</title>
<body>
	<div id="form1" method="post" class="nui-fit" style="height:80%; width:100%;">
		<center>
			<table >
				<tr>
					<td style="text-align:right;">分理处：</td>
					<td style="text-align:left;" colspan="3">
						<input property="editor" class="nui-combobox" valueField="id" textField="text"
	                		url="com.xbkj.gd.data_handle.prod.combobox.getNowOrgUser.biz.ext" required="true"
	                		dataField="vos" style="width:100%;" minWidth="200" onvaluechanged="loadFift" />
					</td>
				</tr>
				<tr>
					<td style="text-align:right;">调拨礼品：</td>
					<td style="text-align:left;" colspan="3">
						<input id="gifts" name ="gifts" property="editor" class="nui-combobox" valueField="id" textField="text"
	                		url="" required="true" dataField="vos" style="width:100%;" minWidth="200" />
					</td>
				</tr>
			</table>
		</center>
	</div>
	 <div class="nui-toolbar" style="text-align:center;padding:0px;height:20%;" borderStyle="border:0;">
        <a id="savedata" class="nui-button" style="width:60px;" iconCls="icon-save" onclick="onOk()">调拨</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="nui-button" style="width:60px;" iconCls="icon-cancel" onclick="onCancel()">取消</a>
    </div>
</body>
	 <script type="text/javascript">
	 
		nui.parse();
		var form = new nui.Form("#form1");
		var row = null;
		
		function loadFift(e){
		
			let val = e.value;
			let vals = val.split("_");
			let url = "com.xbkj.gd.data_handle.prod.combobox.queryProdByUser.biz.ext?user=" + vals[0];
			let $gifts = nui.get("gifts");
			$gifts.load(url);
			
		}
		
		function SetData(data){
			
			row = nui.clone(data);
		}
		//保存损失事件类型记录
		function SaveData(){
			nui.get("savedata").enabled = false;//防止反复提交
				debugger;
			form.validate();
			if(form.isValid() == false){
				nui.get("savedata").enabled = true;
				return;
			}
			//得到数据
			let o = form.getData(true, true);
			let gift = o.gifts;
			if(gift == null || gift == "" || gift == undefined){
				nui.alert("请选择调拨礼品");
				nui.get("savedata").enabled = true;
				return 
			}
			var json = nui.encode({vo:row,sub:{pk_org_apply_product:gift}});
			nui.ajax({
				url:"com.xbkj.gd.data_handle.prod.giftAudit.allot.biz.ext",
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