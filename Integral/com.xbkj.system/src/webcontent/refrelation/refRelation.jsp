<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): heFei
  - Date: 2016-06-28 10:01:27
  - Description: 引用关系设置
  -              单表：在增删改查，表中的字段有没有被其它表所引用，
  -              在删除的时候，如果表中的字段被其它表引用，则不被删除
  
-->
<head>
<title>引用关系设置</title>
</head>
<body>
   <!-- 这个div里是增删改查的按钮 -->
  <div class="nui-toolbar" style="border-bottom:0;padding:0px;">
  		<table style="width:100%;">
  			<tr>
  				<td style="width:100%;">
  				 	<a class="nui-button" iconCls="icon-add" id="addRow" onclick="addRow()">增加</a>
  				 	<a class="nui-button" iconCls="icon-edit" id="editRow" onclick="editRow()" enabled="false">编辑</a> 
					<a class="nui-button" iconCls="icon-remove" id="removeRow" onclick="removeRow()" enabled="false">删除</a>     
					<span class="separator"></span>             
					<a class="nui-button" iconCls="icon-save" id="saveGrid" onclick="saveData()" enabled="false">保存</a> 
					<a class="nui-button" iconCls="icon-cancel" id="cancelGrid" onclick="cancel()" enabled="false">取消</a>
  				</td>
  				<td style="white-space:nowrap;">
  					<input id="key" class="nui-textbox" style="width:150px;" emptyText="被引用表名或引用表名" onkeyenter="onKeyEnter"/>
					<a class="nui-button" onclick="search">查询</a>
  				</td>
  			</tr>
  		</table>
  </div>
    <!--这个div是数据的显示-->
  <div style="width:100%;height:100%;">
  		<div id="grid1" class="nui-datagrid" 
  			url="com.vbm.grc.system.refrelation.refrelation.queryRefRelation.biz.ext"
  			style="width:100%;height:90%;" dataField="datas" multiSelect="true">
  		<!-- 这个div里数据显示的列 -->
  		<div property="columns">
  			<div type="checkcolumn" width="5px">选择</div>
  			<div field="refed_table_name" headerAlign="center" width="22px">被引用表名
  				<input property="editor" class="nui-textbox" style="width:100%;" required="true"/>
  			</div>
  			<div field="refed_table_key" headerAlign="center" width="22px">被引用表字段
  				<input property="editor" class="nui-textbox" style="width:100%;" required="true"/>
  			</div>
  			<div field="refing_table_name" headerAlign="center" width="22px">引用表名
  				<input property="editor" class="nui-textbox" style="width:100%;" required="true"/>
  			</div>
  			<div field="refing_table_colum" headerAlign="center" width="22px">引用字段名
  				<input property="editor" class="nui-textbox" style="width:100%;" required="true" />
  			</div>
  		</div>	
  		</div>
  </div>
</body>
</html>
 <script type="text/javascript">
    nui.parse();//解析nui
    //获取对象grid1
    var grid = nui.get("grid1");
    setButtonEnabled(false);
    setButtonEnabled1(true);  
    //这是增删改查的标示   
	var flag = ""; 
	//数据加载显示
	grid.load();
	
	//数据搜索的操作:查询方法
	function search(){
		//nui.alert("查询方法");
		var key =  nui.get("key").getValue();
		//alert(key);
		//alert(key);
		grid.load({params:{key:key}});
	}
	
	function onKeyEnter(){
		search();
	}
	
	//新增方法
	function addRow(){
	    //nui.alert("add");
	    //标志为新增
		flag = "add";
		//新的数据行
		var newRow = {};
		//在新增的时候从表格的最低行新增数据
		var size = grid.getData().length;
		//新增数据行的添加
		grid.addRow(newRow , size);
		//选中新添加的行
		grid.select(newRow);
		//表格处在编辑状态
		grid.beginEditRow(newRow);
		setButtonEnabled(true);
		//编辑和删除
		nui.get("#editRow").setEnabled(false);
		nui.get("#removeRow").setEnabled(false);
		
	}
	
	//保存方法
	/**
	在新增的时候用判断是新增还是修改(编辑)，用flag作为标示
	*/
	function saveData(){
		//nui.alert("保存");
		//数据启动处于编辑
		grid.commitEdit();
		var rows = grid.getChanges();
		//将改变行设置为编辑状态
		setSelectedRowsEdting();
		var form = new nui.Form("grid1");
		//nui.alert(form);
		form.validate();
		if(!form.isValid()){
			nui.alert("红色输入框不能为空！");
			return;
		}
		//在默认的情况下数据是验证通过的
		var isOk = true;
		for(var i=0; i<rows.length; i++){
			var refed_table_name = rows[i].refed_table_name;
			var refed_table_key = rows[i].refed_table_key;
			var refing_table_name = rows[i].refing_table_name;
			var refing_table_colum = rows[i].refing_table_colum;
			//字母和下划线的验证
			var ref = RegExp("^[A-Za-z_]+$");//字母和下划线的正则表达式
			//nui.alert(ref);
			var msg = "";
			if(!ref.test(refed_table_name)){
				msg = "被引用表名只能是英文或下划线";
				isOk = false;
			}
			if(!ref.test(refed_table_key)){
				msg = "被引用表字段只能是英文或下划线";
				isOk = false;
			}
			if(!ref.test(refing_table_name)){
				msg = "引用表名只能是英文或下划线";
				isOk = false;
			}
			
			if(!ref.test(refing_table_colum)){
				msg+=msg!=""?",引用字段名只能是英文或下划线":"引用字段名只能是英文或下划线";
				isOk = false;
			}
			if(isOk == false){
				nui.alert(msg);
				return;
			}
		}
		   nui.confirm("是否保存？","确定",function(action){
			 if(action == "ok"){
			 	var json = nui.encode({rows: rows});
			 	//nui.alert(json);
			 	if(flag == "add"){
			 		nui.ajax({
			 		   url: "com.vbm.grc.system.refrelation.refrelation.saveRefRelation.biz.ext",
			 		   type: "POST",
			 		   data: json,
			 		   contentType: "text/json",
			 		   success: function (msg){
			 		   		nui.alert(msg.msg.message);
			 		   		setButtonEnabled1(true);
			 		   		setButtonEnabled(false);
			 		   		grid.load();
			 		   },
			 		   error: function (jqXHR, textStatus, errorThrown){
			 		   		nui.alert(jqXHR.responseText);
			 		   }
			 		});
			 	}
			 	//编辑
			 	if(flag == "edit"){
			 		//nui.alert("编辑");
			 		nui.ajax({
			 		  url: "com.vbm.grc.system.refrelation.refrelation.updateRefRelation.biz.ext",
			 		  type: "POST",
			 		  data: json,
			 		  contentType: "text/json",
			 		  success: function (msg){
			 		    //更新成功的信息提示
			 		     nui.alert(msg.msg.message);
			 		     setButtonEnabled1(true);
			 		     setButtonEnabled(false);
			 		     //nui.get("#addRow").setEnable(true);
			 		     //重新加载数据
			 		     grid.reload();
			 		  },
			 		  error: function (jqXHR, textStatus, errorThrown){
			 		  	nui.alert(jqXHR.responseText);
			 		  }
			 		});
			 	}
			 }
		 }); 
	} 
	
	//删除按钮的操作
	function removeRow(){
		//nui.alert("删除的操作");
		//获取grid对象
		var rows = grid.getSelecteds();
		//nui.alert(rows);
		if(rows.length>0){
			nui.confirm("数据库字段存在引用,是否删除吗？","确定", function (action){
				if(action == "ok"){
					var json = nui.encode({rows: rows});//将数据rows序列化
					nui.ajax({
						url: "com.vbm.grc.system.refrelation.refrelation.deleteRefRelation.biz.ext",
						type: "POST",
						data: json,
						contentType: "text/json",
					    success: function(msg){
					    	//nui.alert(msg);
					    	nui.alert(msg.msg.message);
					    	grid.removeRows(rows, false);//removeRows（）miniui的函数
					    	grid.reload();
					    },
					    error: function(jqXHR, textStatus, errorThrown){
					    	nui.alert(jqXHR.responseText);
					    }
					});
				}
			});
		}else{
			nui.alert("请先选中一行数据！");
		}
	}
	
	//编辑按钮的操作:在编辑的时候保存和取消按钮是可用的
	function editRow(){
		flag = "edit";
		//获取grid对象
		var rows = grid.getSelecteds();
		//所选的行何以编辑
		if(rows.length>0){
			for(var i=0; i<rows.length; i++){
				grid.beginEditRow(rows[i]);
			}
			setButtonEnabled(true);
			setButtonEnabled1(false);
			//这里设置多行可以编辑
			nui.get("#editRow").setEnabled(true);
			
		}else{
			nui.alert("请先选中一行数据");
		}
	}
	
	//将当前选中的行设置为编辑状态
	function setSelectedRowsEdting(){
	    //获取选中的行
		var rows = grid.getChanges();
		for(var i=0; rows.length>0 && i<rows.length; i++){
		    //启动编辑
			grid.beginEditRow(rows[i]);
		}
	}
	//相关按钮的操作
	function setButtonEnabled(st){
	    //保存按钮
		nui.get("#saveGrid").setEnabled(st);
		//取消按钮
		nui.get("#cancelGrid").setEnabled(st);
		
	}
	
	function setButtonEnabled1(st){
		//新增按钮
		nui.get("#addRow").setEnabled(st);
		//编辑按钮
		nui.get("#editRow").setEnabled(st);
		//删除按钮
		nui.get("#removeRow").setEnabled(st);
	}
	//取消按钮的操作:保存和新增行
	function cancel(){
			grid.reload();
	    	setButtonEnabled(false);
	        setButtonEnabled1(true);
	}
 </script>