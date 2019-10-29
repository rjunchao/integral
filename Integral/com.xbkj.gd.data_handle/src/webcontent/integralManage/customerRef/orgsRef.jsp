<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2019-10-27 20:24:22
  - Description: 机构参照树
-->
<head>
<title>机构参照树</title>
</head>
<body>
	<!-- <div id="form1" style="padding:10px 10px 0px 10px;">
		<div class="nui-toolbar" style="padding:2px;border-bottom:0;">
	        <table>
	            <tr>
		            <td>
		                	姓名：<input class="nui-textbox" name="customer_name" emptyText="请输入..." style="width:140px;" />
		            </td>
		            <td>
		                	身份证号：<input class="nui-textbox" name="customer_idcard" 
		                		emptyText="请输入..." style="width:150px;" />
		            </td>
		            <td>
		                	<a class="nui-button" iconCls="icon-search" onclick="search()" plain="true">查询</a>
		            </td>
	            </tr>
	        </table>
	    </div>
    </div> -->
	
    <div class="nui-fit" style="padding:0px 10px 10px 10px;">
	    <div id="grid" class="nui-datagrid" style="width:100%;height:100%;" 
		    url="com.xbkj.gd.data_handle.common.Organization.orgRef.biz.ext"  showPager="false"
		    	idField="id" dataField="vos" allowResize="false" multiSelect="false" 
		    	allowCellSelect="false" onrowdblclick="onRowDblClick">
		    <div property="columns">
		        <div type="checkcolumn"></div>
		        <!-- <div field="id" width="60px" headerAlign="center" allowSort="false">机构id</div> -->
		        <div field="text" width="80px" headerAlign="center" allowSort="false">机构名称</div> 
		    </div>
		</div>
	</div>
	 <div class="nui-toolbar" style="text-align:center;padding:0px;height:20%;" borderStyle="border:0;">
        <a id="savedata" class="nui-button" style="width:60px;" iconCls="icon-save" onclick="ok()">确定</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="nui-button" style="width:60px;" iconCls="icon-cancel" onclick="cancel()">取消</a>
    </div>
	
	<script type="text/javascript">
		nui.parse();
		var grid = nui.get("grid");
		/* var form = new nui.Form("#form1"); */
		grid.load();
		function onRowDblClick(e){
			CloseWindow("ok");
		}
		
		function GetData(){
			var data = grid.getSelected();
			return data;
		}
		function cancel(){
			CloseWindow("cancel");
		}
		
		function ok(){
			CloseWindow("ok");
		}
		//关闭窗口
		function CloseWindow(action) {            
	        if(window.CloseOwnerWindow){
				return window.CloseOwnerWindow(action);
			}else{
				window.close();
			}   
	    }	
	    
	    /* function search(){
			//查询
			var params = form.getData();
			grid.load({params:params});
		} */
			
	</script>
</body>
</html>