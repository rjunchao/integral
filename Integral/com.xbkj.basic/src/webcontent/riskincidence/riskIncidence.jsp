<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): lwl
  - Date: 2016-07-13 16:44:06
  - Description:
-->
<head>
<title>风险发生概率</title>
</head>
<body>

	   <!-- 这个div里是增删改查的按钮 -->
  <div class="nui-toolbar" style="border-bottom:0;padding:0px;">
  		<table style="width:100%;">
  			<tr>
  				<td style="width:100%;">
  				 	<a class="nui-button" iconCls="icon-add" id="addRow" plain="false" onclick="addRow()">增加</a>
  				 	<a class="nui-button" iconCls="icon-edit" id="editRow" plain="false" onclick="editRow()">修改</a> 
					<a class="nui-button" iconCls="icon-remove" id="removeRow" plain="false" onclick="removeRow()">删除</a>     
				<!--	<span class="separator"></span>  -->           
  				</td>
  				<td style="white-space:nowrap;">
  					<input id="key" class="nui-textbox" style="width:150px;" emptyText="概率等级编码或名称"/>
					<a class="nui-button" onclick="search()">查询</a>
  				</td>
  			</tr>
  		</table>
  </div>
	<div style="width:100%;height:100%;"> 	
	    <div id="grid1" class="nui-datagrid" url="com.vbm.grc.basic.risk.incidence.riskincidence.queryRiskIncidence.biz.ext" 
        	style="width:100%;height:100%;"  
            dataField="datas" 
            multiSelect="true"
            allowCellValid="true"
            idField="orgid"
            onselectionchanged="selectionChanged"
            >
        	<div property="columns">
	        	<div field="pk_risk_incidence" type="checkcolumn" width="10px">选择</div>
	        	<div field="incidence_code"  headerAlign="center" align="center" width="20px" allowSort="true">概率等级编码</div>  
            	<div field="incidence_name"  headerAlign="center" align="center" width="20px" allowSort="true">概率等级名称</div>
       			<div field="describe"  headerAlign="center" width="180px" allowSort="true">概率说明</div>
         	</div>    
        </div> 
    </div>
</body>
</html>
<script type="text/javascript">
	nui.parse();
	var grid = nui.get("#grid1");
	grid.load();
	//nui.alert(grid);
	
	
	//查询的操作
	function search() {
		//查询的参数
		var key = nui.get("key").getValue();
		//nui.alert(key);
		//key = trim(key);
		grid.load({
			params : {
				key : key
			}
		});
	}

	function onRowDblClick(e) {
		onOk();
	}
	//新建
	function addRow() {
		nui.open({
					url : "<%= request.getContextPath() %>/grc/basic/riskincidence/addRiskIncidence.jsp",
			title:'新建',
			width:600,
			height:160,
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
					url:"<%= request.getContextPath() %>/grc/basic/riskincidence/editRiskIncidence.jsp",
					width:600,
					height:160,
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
							url:"com.vbm.grc.basic.risk.incidence.riskincidence.removeRiskIncidence.biz.ext",
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
	
	   

	function selectionChanged(){
			//多选禁止编辑按钮
			var rows = grid.getSelecteds();
			if(rows.length > 1){
				nui.get("editRow").setEnabled(false);
			}else{
				nui.get("editRow").setEnabled(true);
			}
		}	

</script>