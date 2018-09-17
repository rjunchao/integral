<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<%@include file="/coframe/tools/skins/common.jsp" %>
<!-- 
  - Author(s): wanghl
  - Date: 2013-02-28 13:45:18
  - Description:
-->
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<title>processManager</title>
</head>
<body>
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
<div id="layout1" class="nui-layout" style="width:100%;height:100%;">
	
	<div title="流程配置" region="west" bodyStyle="overflow:hidden;" showHeader="true" width="300px;" showSplitIcon="true" >
		<div class="search-condition" >
			<div class="list">
				<div id="form1">
					<table id="table1" class="table">
						<tr>
							<td class="tit">流程名称：</td>
							<td>
								<input name="criteria/_expr[1]/processDefName" class="nui-textbox" />
		    					<input type="hidden" class="nui-hidden" name="criteria/_expr[1]/_op" value="like" />
		    					<input type="hidden" class="nui-hidden" name="criteria/_expr[1]/_likeRule" value="all" />
					        </td>
					        <td rowspan="2" class="btn-wrap">
								<input class="nui-button" text="查询" iconCls="icon-search" onclick="search" />
							</td>
					     </tr>
					     <tr>
							<td  class="tit">流程中文名：</td>
							<td>
								 <input name="criteria/_expr[2]/processChName" class="nui-textbox"/>
									<input type="hidden" class="nui-hidden" name="criteria/_expr[2]/_op" value="like"/>
					              <input type="hidden" class="nui-hidden" name="criteria/_expr[2]/_likeRule" value="all"/>
					        </td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	    <div class="nui-fit" style="padding:2px;">
	    <div id="datagrid1" class="nui-datagrid" url="org.gocom.components.coframe.flowconfig.processMgr.getFlows.biz.ext"  idField="processDefID" allowResize="true"
		    sizeList="[5,10,20,30,50]" pageSize="2"  style="height:100%;" allowResize="false"
		>
		    <div property="columns">
		        <div field="processDefID" width="10" >流程ID</div>
		        <div field="processDefName" width="20" headerAlign="center" allowSort="true">流程名称</div>    
		        <div field="processChName" width="30" headerAlign="center" allowSort="true">流程中文名</div>                            
		        <div field="versionSign" width="20" align="center" headerAlign="center" allowSort="true">版本号</div>
		        <div name="action" width="20" headerAlign="center">配置</div>
		    </div>
		</div>
		</div>
	</div>
	<div title="流程图" region="center" bodyStyle="overflow:hidden;" showHeader="false" showCollapseButton="false" showCloseButton="false">
		<iframe id="mainframe" frameborder="0" name="main" style="width:100%;height:100%;" border="0"></iframe>
	</div>
	
</div>

    <script type="text/javascript">
        nui.parse();
        
        var grid = nui.get("datagrid1");
        grid.load();
        grid.hideColumn(0);
        
        var iframe = document.getElementById("mainframe");
		iframe.src = "";
		
		grid.on("drawcell", function (e) {
			var record = e.record;
			var column = e.column;
			if (column.name == "action") {
                e.cellStyle = "text-align:center";
                e.cellHtml = '<a href="javascript:edit(\'' + record.id + '\')">配置</a>';
            }
		});
		
        function search(){
			var form = new nui.Form("#form1");
			var data = form.getData(false, false);
			var json = nui.encode(data);
			grid.load(data);
		}
        
        function edit(){
        	var row = grid.getSelected();
        	if (row) {
        		var param="?procId="+row.processDefID;
        		param += "&procName="+row.processDefName;
        		param+="&pchName="+row.processChName;
				param+="&version='"+row.versionSign + "'";
        		iframe.src = "<%=contextPath%>/coframe/flowconfig/show_process.jsp" + param;
        	}
        }
        
        
    </script>

</body>
</html>