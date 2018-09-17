<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): XJM
  - Date: 2016-09-05 16:55:52
  - Description:
-->
<head>
<title>编辑文件</title>
<script src="<%=request.getContextPath() %>/grc/basic/filesystem/swfupload/swfupload.js" type="text/javascript"></script>
</head>
<body>
		<br>
		<div id="form1" method="post">
			<input id="pk_filesystem" name="pk_filesystem" class="nui-hidden" />
			<input id="belong_to" name="belong_to" class="nui-hidden" />
			
			<table style="width:100%;height:100%;table-layout:fixed;" class="nui-form-table_new">
				<tr>
					<td class="nui-form-label">文件内容：</td>
					<td>
						<input id="fileupload1" class="nui-fileupload"  style="width:260px;height:24px;"
				 		flashUrl="swfupload/swfupload.swf"
						uploadUrl="./upload.jsp" onuploadsuccess="onUploadSuccess"
						onuploaderror="onUploadError" /> 
					</td>
					<td></td>
					<td>
						<a class="nui-button" iconCls="icon-upload" onclick="fileupload" id="filebutton">上传</a>
					</td>
				</tr>
				<tr>
					<td class="nui-form-label">文件名称：</td>
					<td colspan="2">	
						<input id="file_name" name="file_name" class="nui-textbox nui-form-input" required="true" vtype="maxLength:200"/>
					</td>
				</tr>
			</table>
			<div class="nui-toolbar" style="text-align:center;margin-top:30px;padding-top:3px;padding-bottom:3px;" borderStyle="border:0;">
		        <a class="nui-button" style="width:60px;" iconCls="icon-save" id="saveData" onclick="saveData()">保存</a>
		        <span style="display:inline-block;width:25px;"></span>
		        <a class="nui-button" style="width:60px;" iconCls="icon-cancel" onclick="cancel()">取消</a>
		    </div>  
		</div>
		
<script type="text/javascript">		
 	nui.parse();
	var form = new nui.Form("form1");
	nui.get("saveData").setEnabled(false);
	
	var filePk="";
	
	//设置参数
	function SetData(data) {
        //跨页面传递的数据对象，克隆后才可以安全使用
        data = nui.clone(data);
       	nui.get("pk_filesystem").setValue(data.pk_filesystem);
       	nui.get("file_name").setValue(data.file_name);
    }
    
    //保存数据
    function saveData(){
    	//校验
    	var data={};
    	form.validate();
    	if(form.isValid()==false){
    		return;
    	}
    	var data = form.getData(true,true); //获取表单的值
    	if(filePk==null||filePk==""){
    		nui.aert("上传文件为空,请上传！")
    		return;
    	}
    	var file_name=nui.get("file_name").getValue();
    	var json=nui.encode({vo:data,filePk:filePk[0]});
    	
    	//发送保存请求
    	 nui.ajax({
		    url:"com.vbm.grc.basic.filesystem.filemanage.editFile.biz.ext",
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
    
    //上传
    function fileupload(){
		var file = nui.get("fileupload1");
		var name = file.getText();
		if(name == null || name == ""){
			nui.alert("请选择需要上传的文件");
			return;
		}else{
		 	var file_name = nui.get("file_name");
			file_name.setValue(name);  
			file.setUploadUrl("upload.jsp");
			file.startUpload();
			nui.get("saveData").setEnabled(true);
			return;
		}
	}
    function onUploadError(e){
		var msg = e.serverData;
		var o = nui.decode(msg);//反序列化为对象
		nui.alert(o.msg);
		if(o.flag){
			filePk = o.pk;//保存文件上传记录的id
		}
	}
    function onUploadSuccess(e){
		var msg = e.serverData;
		var o = nui.decode(msg);//反序列化为对象
		nui.alert(o.msg);
		if(o.flag){
			filePk = o.pk;//保存文件上传记录的id
		}
	} 
	
	
	 //取消
    function cancel() {
    	if(filePk == "" || filePk == null){
			CloseWindow("cancel");
		}else{
			nui.confirm("是否取消？", "提示", function(action){
				if(action == "ok"){
					//删除文件
					delfile();
				}
			});
		} 
    }
    
     //删除上传文件
	function delfile(){
		if(filePk == ""){
			return;
		}
		var json=nui.encode({filePk:filePk[0]});
		nui.ajax({
			url:"com.vbm.grc.regulation.inner.base.innerregubase.delTempFile.biz.ext",
			cache:false,
			data:json,
			type:"POST",
			contentType:"text/json",
			success: function(msg){
				//关闭窗口
				CloseWindow("cancel");
			},
			error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.responseText);
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
        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
        else window.close();            
    }
      
    
</script>
</body>
</html>