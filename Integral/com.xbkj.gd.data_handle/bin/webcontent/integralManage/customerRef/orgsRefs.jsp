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
	
    <div class="nui-fit" >
	    <div id="grid" class="nui-datagrid" style="width:100%;height:100%;" 
		    url="com.xbkj.gd.data_handle.common.Organization.orgRef.biz.ext"  showPager="false"
		    	idField="id" dataField="vos" allowResize="false" multiSelect="true" 
		    	allowCellSelect="false" onrowdblclick="onRowDblClick">
		    <div property="columns">
		        <div type="checkcolumn"></div>
		        <div field="text" width="80px" headerAlign="center" allowSort="false">机构</div> 
		    </div>
		</div>
	</div>
	 <div class="nui-toolbar" style="text-align:center;padding:0px;height:10%;" borderStyle="border:0;">
        <a id="savedata" class="nui-button" style="width:60px;" iconCls="icon-save" onclick="ok()">确定</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="nui-button" style="width:60px;" iconCls="icon-cancel" onclick="cancel()">取消</a>
    </div>
	
	<script type="text/javascript">
		nui.parse();
		var grid = nui.get("grid");
		grid.load();
		function onRowDblClick(e){
			CloseWindow("ok");
		}
		
		function GetData(){
			var data = grid.getSelecteds();
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