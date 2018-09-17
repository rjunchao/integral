<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): lqy
  - Date: 2016-08-02 10:06:03
  - Description:
-->
<head>
<title>损失形态类型</title>
</head>
<body>
   <!-- 这个div里是增删改查的按钮 -->
  <div class="nui-toolbar" style="border-bottom:0;padding:0px;">
  		<table style="width:100%;">
  			<tr>
  				<td style="width:100%;">
  				 	<a class="nui-button" iconCls="icon-add" id="addRow" plain="false" onclick="addRow()">增加</a>
  				 	<a class="nui-button" iconCls="icon-edit" id="editRow" plain="false" onclick="editRow()" enabled="true">修改</a> 
					<a class="nui-button" iconCls="icon-remove" id="removeRow" plain="false" onclick="removeRow()" enabled="true">删除</a>     
					<!--<span class="separator"></span>  -->           
  				</td>
  				<td style="white-space:nowrap;">
  					<input id="key" class="nui-textbox" style="width:150px;" emptyText="损失形态类型编码或名称"/>
					<a class="nui-button" onclick="search()">查询</a>
  				</td>
  			</tr>
  		</table>
  </div>
  <div id="datagrid" class="nui-datagrid" style="width:100%;height:90%;"
		url="com.vbm.grc.basic.losetype.losetype.queryLoseType.biz.ext" 
		dataField="vos" allowCellValid="true" allowCellEdit="false" onselectionchanged="selectionChanged"
		allowCellSelect="true" multiSelect="true" editNextOnEnterKey="true">
		
		<input id="pk_loss_type" name="pk_loss_type" class="nui-hidden" />
		<div property="columns">
			<div type="checkcolumn" width="5px"></div>
			<div field="losstype_code" allowResize="true" width="10px" headerAlign="center">损失形态编码</div>
			<div field="lossname_name" allowResize="true" width="10px" headerAlign="center">影响程度名称</div>
			<div field="describe" allowResize="true" width="80px" headerAlign="center" vtype="rangeLength:1,200">损失形态说明</div>
		</div>
	
	</div>
</body>
       <script type="text/javascript">
       nui.parse();
       var grid = nui.get("#datagrid");
       grid.load();//加载数据
       //查询的操作
       function search(){
       		//查询的参数
       		var key = nui.get("key").getValue();
       		grid.load({params:{key:key}});
       }
       function selectionChanged(){
       		//禁止编辑多选
       		var rows = grid.getSelecteds();//获取操作的行
       		if(rows.length > 1){
       		  nui.get("editRow").setEnabled(false);
       		}
       		else{
       		nui.get("editRow").setEnabled(true);
       		}
       }
     //在增加的时候：点击新增按钮进入新增的页面
       function addRow(){
         	//打开新增保存的页面
         	nui.open({
         		url: "<%=request.getContextPath() %>/grc/basic/losetype/saveTypeList.jsp",
         		title: "新增损失形态类型",
         		width:600,
         		height:160,
         		ondestroy: function(action){
         		   if(action == "ok"){
         		   		grid.load();//加载数据
         		   }
         		}
         	});
       
       }
      //点击修改的时候：进入修改的界面进行修改，修改后保存
      function editRow(){
         	var row = grid.getSelected();
         	//打开新增保存的页面
         	if(row != null){
         	nui.open({
         		url: "<%=request.getContextPath() %>/grc/basic/losetype/saveTypeList.jsp",
         		title: "修改损失形态类型",
         		width:600,
         		height:160,
         		onload:function(){
					var iframe = this.getIFrameEl();
					iframe.contentWindow.setData(row);//跨页面的传值
				},
				ondestroy:function(action){
					if(action == "ok"){
						nui.alert("修改成功");
						grid.reload();
					}
				}
         	});
         	}else{
         	   nui.alert("请先选择一行！");
         	}
      }
      //点击删除的时候：先选择要删除的行数据，在确定删除
      function removeRow(){
        var rows = grid.getSelecteds();//获取要操作的行
        if(rows.length > 0){
          //删除数据
          var json = nui.encode({vos:rows});
          nui.confirm("确定要删除吗？", "提示", function(action){
            if(action == "ok"){
              //确定删除
              nui.ajax({
                url: "com.vbm.grc.basic.losetype.losetype.removeLoseType.biz.ext",
                data: json,
                cache: false,
                type: "POST",
                contentType: "text/json",
                success:function(msg){
                  nui.alert(msg.msg.message);
                  if(msg.msg.flag){
                  	 grid.load();
                  }
                }
              });
            }
          });
        }else{
          //提示选择要删除的数据
          nui.alert("请选择要删除的数据行");
        }
      
      }
      </script>
</html>