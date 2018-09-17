<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): lwl
  - Date: 2016-08-03 16:23:09
  - Description:
-->
<head>
<title>风险等级</title>
</head>
<body>
	<div class="nui-toolbar" style="border-bottom:0;padding:0px;">
    	<table style="width:100%;">
			<tr> 
				<td style="width:100%;">
		            <a class="nui-button" iconCls="icon-add" id="addRow" plain="false" onclick="addRow()">新建</a>
					<a class="nui-button" iconCls="icon-edit" id="editRow" plain="false" onclick="editRow()">修改</a> 
					<a class="nui-button" iconCls="icon-remove" id="removeRow" plain="false" onclick="removeRow()">删除</a>     
	       		 </td> 
	       		 <td style="white-space:nowrap;">
	        		<input id="key" class="nui-textbox" style="width:150px;" emptyText="请输入风险等级"/>
					<a class="nui-button" onclick="search()">查询</a>
				 </td>
			</tr> 
		</table>
   	</div>
   	<div id="grid1" class="nui-datagrid" url="com.vbm.grc.basic.risk.level.risklevel.queryRiskLevel.biz.ext" 
    	style="width:100%;height:90%;"  
        dataField="datas" 
        multiSelect="true"
        idField="orgid"
        onselectionchanged="selectionChanged"
        >
    	<div property="columns">
        	<div field="pk_risk_level" type="checkcolumn" width="5px">选择</div>
        	<div field="down_val"  headerAlign="center" align="center" width="20px" allowSort="true">开始值</div>
        	<div field="up_val"  headerAlign="center" align="center" width="20px" allowSort="true">结束值</div>  
            <div field="risk_level"  headerAlign="center" align="center" width="20px" allowSort="true" >风险等级</div>
            <div field="colour"  headerAlign="center" align="center" width="35px" allowSort="true" renderer="onColour">图示</div>    
     	</div>    
    </div>
</body>
</html>
<script type="text/javascript">
	nui.parse();
	var grid = nui.get("grid1");
	grid.load();
	
	//查询
	function search(){
		var key = nui.get("key").getValue();
        key = trim(key);
        grid.load({params:{key:key}});
	}
	function trim(str){
    	var result = "";
    	result = str.replace(/(^\s+)|(\s+$)/g,"");
    	result = result.replace(/\s/g,"");
    	return result;
	}
	//新建
	function addRow(){
		nui.open({
			url:"<%= request.getContextPath() %>/grc/basic/risklevel/addRiskLevel.jsp",
			title:'新建',
			width:600,
			height:150,
			showCloseButton:false,
			onload:function(){
			},
			ondestroy:function(action){
				//判断是否成功与否
				if(action == "ok"){
					grid.reload();
				}
			}
		});
	}
	//修改
	function editRow(){
		var data = grid.getSelected();
			if(data !=null){
				 nui.open({
					url:"<%= request.getContextPath() %>/grc/basic/risklevel/editRiskLevel.jsp",
					width:680,
					height:150,
					showCloseButton:false,
					onload:function(){
					var iframe = this.getIFrameEl();
					iframe.contentWindow.SetData(data);
					},
					ondestroy:function(action){
						if(action == "ok"){
							nui.alert("修改成功");
							grid.reload();
						}
					}
				}); 
			}else{
				nui.alert("请先选中行");
			}
	}
	//删除
	function removeRow(){
		var rows = grid.getSelecteds();
			if(rows.length > 0){
				nui.confirm("是否删除?","确定", function(action){
					if(action == "ok"){
						var json = nui.encode({vos:rows});
						nui.ajax({
							url:"com.vbm.grc.basic.risk.level.risklevel.removeRiskLevel.biz.ext",
							cache:false,
							async:true,
							data:json,
							type:"POST",
							contentType:"text/json",
							success: function (msg) {
			                	nui.alert(msg.msg.message);
								grid.reload();
								return;
				                },				                
			                error: function (jqXHR, textStatus, errorThrown) {
			                    nui.alert(jqXHR.responseText);
			                }
						});
					}
				});
			}else{
				nui.alert("请先选中行");
			}
	}
	//显示图示颜色
	function onColour(e){
		if(e.value == "red") return "红色";
		if(e.value == "yellow") return "黄色";
		if(e.value == "violet") return "紫色";
		if(e.value == "blue") return "蓝色";
		if(e.value == "green") return "绿色";
	} 
	function selectionChanged(){
		//多选禁止编辑按钮
		var rows = grid.getSelecteds();
		if(rows.length > 1){
			nui.get("editRow").setEnabled(false);
		}else{
			nui.get("editRow").setEnabled(true);
		}
	}
	//设置背景颜色
	grid.on("drawcell", function (e) {
            var record = e.record,column = e.column, field = e.field,value = e.value;

            if (column.field == "colour") {
                if (e.value == "red") {
                    e.cellStyle = "background:#FF3333";
                } else if (e.value == "blue") {
                   e.cellStyle = "background:#0066FF";
                } else if (e.value == "yellow") {
                   e.cellStyle = "background:#FFFF66";
                }else if (e.value == "violet") {
                   e.cellStyle = "background:#990099";
                }else if (e.value == "green") {
                   e.cellStyle = "background:#00FF66";
                }
            }

        });
    		
</script>