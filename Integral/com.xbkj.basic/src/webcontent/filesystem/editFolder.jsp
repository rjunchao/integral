<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): XJM
  - Date: 2016-09-05 16:49:22
  - Description:
-->
<head>
<title>编辑文件夹</title>
</head>
<body>
	<div style="padding-top:5px">
		<div id="form1">
			<input id="vo.pk_filesystem" name="vo.pk_filesystem" class="nui-hidden" />
			<input id="vo.parent_file_id" name="vo.parent_file_id" class="nui-hidden" />
			<input id="vo.belong_to" name="vo.belong_to" class="nui-hidden" />
	        <table style="width:100%;height:100%;table-layout:fixed;" class="nui-form-table_new" >
	            <tr>
	               	<td style="width:25%" class="nui-form-label"><label for="vo.file_name">文件夹名称：</label></td>
	                <td style="width:50%">     
	                    <input id="vo.file_name" name="vo.file_name" class="nui-textbox nui-form-input" required="true" vtype="rangeLength:1,20"/>
	                </td>
	                <td style="width:25%"><span id="resultMsg" style="color:red"></span></td>
	            </tr>
	        </table>
	        <div class="nui-toolbar" style="text-align:center;margin-top:12px;padding-top:3px;padding-bottom:3px;" borderStyle="border:0;">
		        <a class="nui-button" style="width:60px;" iconCls="icon-save" onclick="onOk()">保存</a>
		        <span style="display:inline-block;width:25px;"></span>
		        <a class="nui-button" style="width:60px;" iconCls="icon-cancel" onclick="onCancel()">取消</a>
		    </div> 
	    </div>
	</div>
	<script type="text/javascript">
        nui.parse();
        var form = new nui.Form("form1");
        //保存数据
        function SaveData() {
        	//校验
            form.validate();
            if (form.isValid() == false) return;
        	//得到数据
            var o = form.getData(true,true);            
            var json = nui.encode(o);
            nui.ajax({
                url: "com.vbm.grc.basic.filesystem.filemanage.updateFolder.biz.ext",
                type: 'POST',
                data: json,
                cache: false,
                contentType:'text/json',
                success: function (msg) {
                    if(msg.msg.flag){
						CloseWindow("ok");
						return;
					}else{
						$("#resultMsg").html(msg.msg.message);
						return;
					}
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                    CloseWindow();
                }
            });
        }
        
        function CloseWindow(action) {            
            if (action == "close" && form.isChanged()) {
                if (confirm("数据被修改了，是否先保存？")) {
                    return false;
                }
            }
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
        function onOk(e) {
            SaveData();
        }
        function onCancel(e) {
            CloseWindow("cancel");
        }
        
        function SetData(data) {
            //跨页面传递的数据对象，克隆后才可以安全使用
            data = nui.clone(data);
           	nui.get("vo.pk_filesystem").setValue(data.pk_filesystem);
           	nui.get("vo.parent_file_id").setValue(data.parent_file_id);
           	nui.get("vo.file_name").setValue(data.file_name);
           	nui.get("vo.belong_to").setValue(data.belongTo);
        }
    </script>
</body>
</html>