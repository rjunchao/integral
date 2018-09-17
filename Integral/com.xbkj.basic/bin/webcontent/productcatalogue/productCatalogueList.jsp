<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): heFei
  - Date: 2016-07-04 16:50:41
  - Description:
-->
<head>
<title>产品目录列表</title>
</head>
<body>
<div style="padding:10px 10px 0px 10px;">
		<div class="nui-toolbar" style="padding:2px;border-bottom:0;">
	        <table style="width:100%;">
	            <tr>
		            <td style="width:100%;">
		                <a class="nui-button" iconCls="icon-add" onclick="add()">增加</a>
		            	<a class="nui-button" iconCls="icon-edit" onclick="edit()" id="edit_btn">编辑</a>
		            	<a class="nui-button" iconCls="icon-remove" onclick="remove()" >删除</a>
		            </td>
	            </tr>
	        </table>
	    </div>
    </div>
    <div class="nui-fit" style="padding:0px 10px 10px 10px;">
		<input id="pid" name="pk_product" class="nui-hidden" />
	    <div id="grid" class="nui-datagrid" style="width:100%;height:100%;" 
		    url="com.vbm.grc.basic.product.productcontrol.productcatalogue.productcataloguelist.biz.ext"  
		    idField="pk_product" dataField="vos" allowResize="false" multiSelect="true" 
		    onrowdblclick="edit()" onselectionchanged="selectionChanged" allowCellSelect="false">
		    <div property="columns">
		        <div type="checkcolumn"></div>
		        <div field="prod_code" width="80" headerAlign="center" allowSort="false">产品目录编码</div>
		        <div field="prod_name" width="80" headerAlign="center" allowSort="false">产品目录名称</div>
		        <div field="product_level" width="80" headerAlign="center" allowSort="false">产品目录层级</div>   
		        <div field="isleaf" width="80" headerAlign="center" allowSort="false" renderer="onIsLeafRenderer">是否叶子菜单</div>
		    </div>
		</div>
	</div>
</body>
  <script type="text/javascript">
      nui.parse();
      var grid = nui.get("grid");
     //alert("产品目录列表");
     //获取请求的参数：pid=主键
      var pid = "<%=request.getParameter("pk_product") %>";
      var level = "<%=request.getParameter("product_level") %>";
     // nui.alert(level);
    //加载叶子产品目录
      grid.load({params:{parentCode:pid}});
    //增加的操作
    function add(){
    	//nui.alert("添加数据");
    	nui.open({
    	  url: "<%=request.getContextPath() %>/grc/basic/productcatalogue/productcatalogueAdd.jsp",
    	  title: '添加产品目录',
    	  width: 600,
    	  height: 120,
    	  onload: function(){//加载
    	  	 //把父节点转过来
    	  	 var rows = grid.getSelecteds();
    	  	 if(rows != null && rows.length > 0){//选择了列，在点击了添加
						level = rows[0].product_level;
					}else{
						//没选，直接点击添加
						if(level == "null" || level == ""){
							level = 0;
						}
					}
    	  	 var data = {parentCode:pid,level:level};
    	  	 var ifram = this.getIFrameEl();
    	  	 //父窗口向子窗口传值调用productcatalogueAdd.jsp页面
    	  	 ifram.contentWindow.SetData(data);
    	  },
    	  ondestroy: function(action){//注销
    	  	//下面是对保存成功与否的判断
    	  	if(action == "ok"){
    	  	 	nui.alert("添加成功");
    	  	 	//刷新树节点，调用productcatalogueMain.jsp的方法
    	  	    parent.refresh();
    	  	    //重新加载列表
    	  	    grid.reload();
    	  	}
    	  }
    	});
    }
   //编辑的操作
   function edit(){
   		//获取行数据
   		var data = grid.getSelecteds();
        var len = data.length;
        //nui.alert(len);
        if(len > 0){
        	nui.open({
        		url: "<%=request.getContextPath() %>/grc/basic/productcatalogue/productcatalogueEdit.jsp",
        		title: "修改产品目录",
        		width:600,
        		height:120,
        		onload:function(){
        			var ifram = this.getIFrameEl();
        			 //父窗口向子窗口传值调用productcatalogueEdit.jsp页面
    	  	 		ifram.contentWindow.SetData(data[0]);
        		},
        		ondestroy:function(action){
        			//下面是对保存成功与否的判断
    	  			if(action == "ok"){
    	  	 		nui.alert("修改成功");
    	  	 		//刷新树节点，调用productcatalogueMain.jsp的方法
    	  	    	parent.refresh();
    	  	    	//重新加载列表
    	  	    	grid.reload();
    	  		}
        		}
        	});
        }else{
          nui.alert("请先选择要编辑的行");
          return;
        }
   }
   //删除按钮的操作
   function remove(){
      //选中行的操作
      var rows = grid.getSelecteds();
      if(rows.length > 0){
      	var ids = [];
      	nui.confirm("将一起删除该节点下的子节点，确认？","确认删除", function(action){
      		if(action == "ok"){
      			//具体的删除的操作
      			 var json = nui.encode({vos: rows});
      			 nui.ajax({
      			  url: "com.vbm.grc.basic.product.productcontrol.productcatalogue.dellproductcatalogue.biz.ext",
      			  cache: false,
      			  async:true,//异步请求
      			  data: json,
      			  type: "POST",
      			  cotentType: "text/json",
      			  success: function(msg){
      			  	nui.alert(msg.msg.message);
      			  	//刷新树节点
      			  	parent.refresh();
      			  	//加载树节点
      			  	grid.load();
      			  	return;
      			  },
      			  error: function(jqXHR, textStatus, errorThrown){
      			  		 alert(jqXHR.responseText);
      			  }
      			 });
      		}
      	});
      }else{
          nui.alert("请先选择要删除的产品目录！");
      }
      
   }
   //多选按钮的操作
   function selectionChanged(){
   		var rows = grid.getSelecteds();//获取行操作
   		if(rows.length > 1){
   			nui.get("edit_btn").setEnabled(false);
   		}else{
   			nui.get("edit_btn").setEnabled(true);
   		}
   }
   //是否是叶子节点的显示
   function onIsLeafRenderer(e){
   		if(e.vlaue == "1"){
   		  return "是";
   		}else{
   		  return "否";
   		}
   }
  </script>
</html>