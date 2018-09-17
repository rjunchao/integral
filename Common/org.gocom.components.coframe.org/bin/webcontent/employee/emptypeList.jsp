<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): LJ
  - Date: 2013-03-01 11:09:20
  - Description:
-->
<%@include file="/nui/common.jsp" %>
<%@include file="/vbm/vbmcommon.jsp" %>
<head>
<title>人员类型参照</title>
<style type="text/css">
 body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }
</style>
</head> 
<body>
   
    <div style="width:100%;">
        <div class="nui-toolbar" style="text-align:center;line-height:30px;">
            <table style="width:100%;">
                <tr>
                    <td style="white-space:nowrap;">
                        <input id="key" class="nui-textbox" emptyText="" style="width:150px;" onenter="onKeyEnter"/>   
                        <a class="nui-button" onclick="search()">查询</a>
                    </td>
                </tr>
            </table>           
        </div>
    </div> 
        <div id="datagrid1" class="nui-datagrid" style="width:100%;height:76%;" 
        url="com.vbm.mas.emptype.emptype.impl.emptype.queryDict.biz.ext" dataField="emptypes" 
          allowResize="false" allowCellValid="true"  allowCellValid="true" showPager="false" multiSelect="true" 
        allowCellEdit="false" allowCellSelect="true" multiSelect="true" editNextOnEnterKey="true" onrowdblclick="onRowDblClick"
        >
        <div property="columns">
            <div field="emptypeCode" allowResize="false" width="120" headerAlign="center" allowSort="true" >人员类型编码
            <input property="editor" class="nui-textbox" style="width:100%;" allowInput="false"  required="true"/>
            </div>
            <div field="emptypeName" allowResize="false" width="120" headerAlign="center" allowSort="true">人员类型名称
             <input property="editor" class="nui-textbox"  style="width:100%;" allowInput="false" required="true"/>
            </div>
            </div>
            </div>
            <div style="width:100%;">
        		<div class="nui-toolbar" style="text-align:center;padding-top:8px;padding-bottom:8px;" borderStyle="border:0;">
			        <a class="nui-button" style="width:60px;" onclick="onOk()">确定</a>
			        <span style="display:inline-block;width:25px;"></span>
			        <a class="nui-button" style="width:60px;" onclick="onCancel()">取消</a>
    			</div>
    		</div> 
</body>
</html>
<script type="text/javascript">
    nui.parse();
    var grid = nui.get("datagrid1");
    grid.load();
    
    function GetData() {
        var row = grid.getSelected();
        row.pk=row.emptypeCode;
        row.name=row.emptypeName;
        return row;
    }
    function search() {
        var key = nui.get("key").getValue();
        grid.load({ emptypeName: key });
    }
    function onKeyEnter(e) {
        search();
    }
    function onRowDblClick(e) {
        onOk();
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