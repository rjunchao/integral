<%@page pageEncoding="UTF-8"%>
<%@include file="/vbm/vbmcommon.jsp"%> 
<html>
<head>
    <title></title>
    <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }
    </style>
</head>
<body>
    <div class="nui-toolbar" style="text-align:center;line-height:30px;" borderStyle="border:0;">
          <form id = "form1">
	             	<div>
		               <input id="empname"  name="criteria._entity" class="nui-hidden" value="org.gocom.components.coframe.auth.queryentity.QueryEmployee"/>
		               <input name="criteria._expr[0]._property" class="nui-hidden" value="empname"/>
		               <input name="criteria._expr[0]._op" class="nui-hidden" value="like"/>
		               <input name="criteria._expr[0]._likeRule" class="nui-hidden" value="all"/>
			           <label >名称：</label>
		               <input name="criteria._expr[0]._value" class="nui-textbox" />
		               <input type="hidden" name="_criteria/_order[0]/_asc" value="empname" />
		               <a class="nui-button" onclick="search()">查询</a>
	            	</div>
	            </form>
    </div>
    <div class="nui-fit">
        <div id="grid1" class="nui-datagrid" style="width:100%;height:100%;" 
            idField="empid" allowResize="true"
            borderStyle="border-left:0;border-right:0;" onrowdblclick="onRowDblClick" dataField="emps">
            <div property="columns">
                <div type="indexcolumn" ></div>
                <div field="empcode" width="150" headerAlign="center" allowSort="true">员工编码</div>
                <div field="empname" width="150" headerAlign="center" allowSort="true">员工姓名</div>
                <div field="orgname" width="100%" headerAlign="center"  allowSort="true">所属机构</div> 
            </div>
        </div>
    </div>                
    <div class="nui-toolbar" style="text-align:center;padding-top:8px;padding-bottom:8px;" borderStyle="border:0;">
        <a class="nui-button" style="width:60px;" onclick="onOk()">确定</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="nui-button" style="width:60px;" onclick="onCancel()">取消</a>
    </div>
	
	<script type="text/javascript"> 
	    nui.parse();
		var orgname = new nui.Form("#form1");
	    var grid = nui.get("grid1");
	    //动态设置URL
	    var url="com.pam.coframe.org.newcomponent.queryEmployee.biz.ext";
	    grid.setUrl(url);
	    //也可以动态设置列 grid.setColumns([]);
		var form1Data = orgname.getData(false, true);
		onloadGrid();
		function onloadGrid(){
			var rows = grid.getData();
			if(rows.length != 0){
				return;
			}
			grid.load(form1Data);
		}
	    //grid.load();
	
	    function GetData() {
	        var row = grid.getSelected();
	        return row;
	    }
	    
	    function SetData(data) {
			data = nui.clone(data);
			form1Data.empType=data.empType;
			var rows = grid.getData();
			if(rows.length != 0){
				grid.clear;
			}
			grid.load(form1Data);
	    }
	    function search() {
	        form1Data = orgname.getData(false, true);
			grid.load(form1Data);
	    }
	    function onKeyEnter(e) {
	        search();
	    }
	    function onRowDblClick(e) {
	        onOk();
	    }
	    //////////////////////////////////
	    function CloseWindow(action) {
	        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
	        else window.close();
	    }
	
	    function onOk() {
	        CloseWindow("ok");
	    }
	    function onCancel() {
	        CloseWindow("cancel");
	    }
 
 
</script>
</body>
</html>

