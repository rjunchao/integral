<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): fangwl (mailto:fangwl@primeton.com)
  - Date: 2013-03-08 14:14:46
  - Description:
-->
<head>
</head>
<style>
#table1 .tit{
	height: 10px;
    line-height: 10px;
}
#table1 td{
	height: 10px;
    line-height: 10px;
}
</style>
<body>
	<div class="search-condition">
		<div class="list">
			<div id="form1">
				<table id="table1" class="table">
					<tr>
						<td  class="tit">功能编号：</td>
						<td>
						  <input name="criteria/_expr[1]/funccode" class="nui-textbox"/>
			              <input type="hidden" class="nui-hidden" name="criteria/_expr[1]/_op" value="like"/>
							<input type="hidden" class="nui-hidden" name="criteria/_expr[1]/_likeRule" value="all"/>
				        </td>
						<td  class="tit">功能名称：</td>
						<td>
							<input name="criteria/_expr[2]/funcname" class="nui-textbox"/>
							<input type="hidden" class="nui-hidden" name="criteria/_expr[2]/_op" value="like"/>
							<input type="hidden" class="nui-hidden" name="criteria/_expr[2]/_likeRule" value="all"/>
				        </td>
				        <td class="btn-wrap"><a class="nui-button" style="width:60px;" iconCls="icon-search" onclick="search()">查询</a></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
    <div class="nui-fit"style="padding:5px">
        <div id="grid1" class="nui-datagrid" style="width:100%;height:100%;" 
            url="org.gocom.components.coframe.framework.FunctionManager.queryFunction.biz.ext" onrowdblclick="onOk" pageSize="15" idField="funccode" allowResize="false">
            <div property="columns">
                <div type="checkcolumn"></div>
                <div field="funccode" width="100" headerAlign="center" allowSort="true">功能编号</div>    
                <div field="funcname" width="100" headerAlign="center" allowSort="true">功能名称</div>                                            
                <div field="functype" width="100" headerAlign="center" allowSort="true">功能类型</div> 
                <div field="funcaction" width="300" headerAlign="center" allowSort="true">功能入口地址</div>                
            </div>
        </div>
    </div>  
    <div class="nui-toolbar" style="text-align:center;padding-top:8px;padding-bottom:8px;" borderStyle="border:0;">
        <a class="nui-button" style="width:60px;" iconCls="icon-save" onclick="onOk()">保存</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="nui-button" style="width:60px;" iconCls="icon-cancel" onclick="onCancel()">取消</a>
    </div>
</body>
</html>
<script type="text/javascript">
    nui.parse();

    var grid = nui.get("grid1");

    grid.load();
	var form = new nui.Form("#form1"); 
	function search() {
		var data = form.getData(false,false);      //获取表单多个控件的数据
        grid.load(data);
    }
    
    function getData() {
        var row = grid.getSelected();
        return row;
    }

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