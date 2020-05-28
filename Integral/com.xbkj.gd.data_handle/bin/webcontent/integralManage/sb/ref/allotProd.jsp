<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2020-05-13 10:52:45
  - Description:
-->
<head>
<title>分理处多余礼品提取</title>
</head>
<body>
	<div id="form1" method="post" class="nui-fit" style="height:80%; width:100%;">
		<center>
			<table>
				<tr>
					<td style="text-align:right;">申请记录：</td>
					<td style="text-align:left;">
						<input type="hidden" id="pk_org_apply_product" name="pk_org_apply_product" style="width:150px;" enabled="false" class="nui-textbox"/>
					</td>
				</tr>
				<tr>
					<td style="text-align:right;">分理处：</td>
					<td style="text-align:left;">
						<input id="allot_org" name="allot_org" enabled="false"style="width:150px;" class="nui-textbox" />
					</td>
				</tr>
				<tr>
					<td style="text-align:right;">礼品：</td>
					<td style="text-align:left;">
						<input id="allot_prod" name="allot_prod" class="nui-textbox" enabled="false"
							 style="width:150px;"/>
					</td>
				</tr>
				<tr>
					<td style="text-align:right;">数量：</td>
					<td style="text-align:left;" colspan="3">
						<input id="org_sub_num" name="org_sub_num" required="true"
							class="nui-textbox" vtype="int" style="width:150px;"/>
					</td>
				</tr>
				<tr>
					<td style="text-align:right;">备注：</td>
					<td style="text-align:left;" colspan="3">
						<input id="remark" name="remark" required="true"
							class="nui-textarea"  style="width:150px;height:60px;"/>
					</td>
				</tr>
			</table>
		</center>
	</div>
	 <div class="nui-toolbar" style="text-align:center;padding:0px;height:20%;" borderStyle="border:0;">
        <a id="savedata" class="nui-button" style="width:60px;" iconCls="icon-save" onclick="onOk()">提取</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="nui-button" style="width:60px;" iconCls="icon-cancel" onclick="onCancel()">取消</a>
    </div>
</body>
	 <script type="text/javascript">
	 
		nui.parse();
		var totalnum = 0;
		var form = new nui.Form("#form1");
		//保存损失事件类型记录
		function SaveData(){
			
			//校验
			form.validate();
			if(form.isValid() == false){
				nui.get("savedata").enabled = true;
				return;
			}
			let osn = nui.get("org_sub_num").getValue();
			if(totalnum <= 0 ){
				nui.alert("现有数量为0");
				return;
			}
			if(totalnum < osn){
				nui.alert("不能超过现有数量");
				return;
			}
			//得到数据
			let data = form.getData(true, true);
			debugger;
			nui.get("savedata").enabled = false;//防止反复提交
			nui.confirm("确定提取礼品？","确认", function(action){
				if(action == "ok"){
					var json = nui.encode({params:data});
					nui.ajax({
						url:"com.xbkj.gd.data_handle.prod.subBranch.allotAp.biz.ext",
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
			nui.get("savedata").enabled = false;//防止反复提交
			
		}
		
		function SetData(data){
			let datas = nui.clone(data);
			debugger;
			if(data){
				nui.get("pk_org_apply_product").setValue(datas.pk_org_apply_product);
				nui.get("allot_prod").setValue(datas.apply_product_name);
				nui.get("allot_org").setValue(datas.empname);
				nui.get("org_sub_num").setValue(datas.apply_product_num);
				totalnum = datas.apply_product_num;
			}
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