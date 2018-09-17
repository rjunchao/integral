<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): HEFEI
  - Date: 2016-08-04 16:54:47
  - Description:
-->
<head>
<title>控制等级</title>
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
	        		<input id="key" class="nui-textbox" style="width:150px;" emptyText="请输入控制等级"/>
					<a class="nui-button" onclick="search()">查询</a>
				 </td>
			</tr> 
		</table>
   	</div>
   	<div id="grid1" class="nui-datagrid" url="com.vbm.grc.basic.control.level.ControlLevel.queryControlLevel.biz.ext" 
    	style="width:100%;height:90%;"  
        dataField="vos" 
        allowResize="true" 
        multiSelect="true"
        onselectionchanged="selectionChanged">
    	<div property="columns">
        	<div type="checkcolumn" width="10px">选择</div>
        	<div field="down_val"  headerAlign="center" align="center" width="22px" allowSort="true">开始值</div>
        	<div field="up_val"  headerAlign="center" align="center" width="22px" allowSort="true">结束值</div>  
            <div field="control_level"  headerAlign="center" align="center" width="22px" allowSort="true" >控制等级</div>
            <div field="colour"  headerAlign="center" align="center" width="22px" allowSort="true" renderer="onColour">图示</div>    
     	</div>    
    </div>
</body>
    <script type="text/javascript">
        nui.parse();
        var grid = nui.get("#grid1");
        grid.load();//加载数据
        //nui.alert(grid);
       //查询的操作
       function search(){
         var key = nui.get("key").value;
         //nui.alert(key);
         key = trim(key, "g");
         grid.load({params:{key:key}});
       }
      //去除所有空格的操作
     function trim(str, is_global){
		 var result;
		 result = str.replace(/(^\s+)|(\s+$)/g,"");
		 if(is_global.toLowerCase()=="g"){
			result = result.replace(/\s/g,"");
		 }
		return result;
	}
	//多选的操作
	 function selectionChanged(){
       	//禁止多选的操作
       	var rows = grid.getSelecteds();//获取操作的行
       	if(rows.length > 1){
       		nui.get("#editRow").setEnabled(false);
       	}else{
       		nui.get("#editRow").setEnabled(true);
       	}
       }
     //图示列属性样式的设置
     function onColour(e){
       if(e.value == "red"){return "红色"};
       if(e.value == "orange"){return "橙色"};
       if(e.value == "yellow"){return "黄色"};
       if(e.value == "green"){return "绿色"};
       if(e.value == "blue"){return "蓝色"};
       if(e.value == "violet"){return "紫色"};
     }
     //设置图示的背景样式:drawcell属性设置样式
     grid.on("drawcell",function(e){
       if(e.field == "colour"){
        if(e.value == "red"){
    	     e.cellStyle = "background:red";//cellStyle设置单元格样式
    	  }else if(e.value == "orange"){
    	     e.cellStyle = "background:orange"
    	  }else if(e.value == "yellow"){
    	     e.cellStyle = "background:yellow"
    	  }else if(e.value == "green"){
    	     e.cellStyle = "background:green"
    	  }else if(e.value == "blue"){
    	     e.cellStyle = "background:blue"
    	  }else if(e.value == "violet"){
    	     e.cellStyle = "background:violet"
    	  }
       }
     });
    //在增加的时候：点击新增按钮进入新增的页面
       function addRow(){
         	//删除和编辑按钮不可用
         	nui.get("editRow").setEnabled = false;
         	nui.get("removeRow").setEnabled = false;
         	//打开新增保存的页面
         	nui.open({
         		url: "<%=request.getContextPath() %>/grc/basic/controllevel/addControlLevel.jsp",
         		title: "新增控制等级",
         		width:600,
         		height:150,
         		ondestroy: function(action){
         		   //成功后删除和编辑有效
         		   nui.get("editRow").setEnabled = true;
         		   nui.get("removeRow").setEnabled = true;
         		   if(action == "ok"){
         		   		grid.load();//加载数据
         		   }
         		}
         	});
       }
     //删除的操作：进行的是假删的操作
     function removeRow(){
        var rows = grid.getSelecteds();//获取操作的行
        //nui.alert(rows);
        if(rows.length > 0){
           nui.confirm("确定要删除吗？","确定",function(action){
              if(action == "ok"){
                 var json = nui.encode({vos:rows});
                 nui.ajax({
                   url: "com.vbm.grc.basic.control.level.ControlLevel.delControlLevel.biz.ext",
                   data: json,
                   cache: false,
                   type: "POST",
                   contentType: "text/json",
                   success: function(msg){
                     nui.alert(msg.msg.message);
                     grid.reload();//重新加载数据
                     return;
                   },
                   error: function(jqXHR, textStatus, errorThrown){
                      nui.alert(jqXHR.responseText);
                   }
                 });
              }
           });
        }
     }  
     //修改的操作
     function editRow(){
       //获取操作的行
       var row = grid.getSelected();
       if(row != null){
         nui.open({
					url:"<%= request.getContextPath() %>/grc/basic/controllevel/editControlLevel.jsp",
					width:600,
					height:150,
					showCloseButton:false,
					onload:function(){
					var iframe = this.getIFrameEl();
					iframe.contentWindow.SetData(row);//跨页面的传值克隆后才可以安全使用
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
    </script>
</html>