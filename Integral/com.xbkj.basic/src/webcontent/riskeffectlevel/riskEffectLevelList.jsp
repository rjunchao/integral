<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): heFei
  - Date: 2016-07-13 17:06:03
  - Description:对风险影响程度的：查询，新增，修改，删除的操作。
-->
<head>
<title>风险影响程度</title>
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
					<!--<span class="separator"></span>    -->         
  				</td>
  				<td style="white-space:nowrap;">
  					<input id="key" class="nui-textbox" style="width:150px;" emptyText="影响程度编码或名称"/>
					<a class="nui-button" onclick="search()">查询</a>
  				</td>
  			</tr>
  		</table>
  </div>
  <div id="datagrid" class="nui-datagrid" style="width:100%;height:90%;"
		url="com.vbm.grc.basic.risk.effectlevel.riskeffectlevel.RiskEffectLevelList.biz.ext" 
		dataField="vos" onselectionchanged="selectionChanged"
		allowCellSelect="true" multiSelect="true" >
		
		<input id="pk_risk_effect_level" name="pk_risk_effect_level" class="nui-hidden" />
		<div property="columns">
			<div type="checkcolumn"width="10px">选择</div>
			<div field="effect_level_code" allowResize="true" align="center" width="20px" headerAlign="center">影响程度编码</div>
			<div field="effect_level_name" allowResize="true" align="center" width="20px" headerAlign="center">影响程度名称</div>
			<div field="describe" allowResize="true" width="160px" headerAlign="center">影响程度说明</div>
		</div>
	
	</div>
</body>
       <script type="text/javascript">
       nui.parse();
       var grid = nui.get("#datagrid");
       grid.load();//加载数据
       //nui.alert(grid);
       //查询的操作
       function search(){
       		//查询的参数
       		var key = nui.get("key").getValue();
       		//nui.alert(key);
       		grid.load({params:{key:key}});
       }
       function selectionChanged(){
       		//禁止多选的操作
       		var rows = grid.getSelecteds();//获取操作的行
       		if(rows.length > 1){
       		  nui.get("editRow").setEnabled(false);
       		}else{
       			nui.get("editRow").setEnabled(true);
       		}
       }
     //在增加的时候：点击新增按钮进入新增的页面
       function addRow(){
         	//删除和编辑按钮不可用
         	//nui.alert("添加数据");
         	nui.get("editRow").setEnabled = false;
         	nui.get("removeRow").setEnabled = false;
         	//打开新增保存的页面
         	nui.open({
         		url: "<%=request.getContextPath() %>/grc/basic/riskeffectlevel/addRiskEffectLevel.jsp",
         		title: "新增风险影响程度",
         		width:600,
         		height:160,
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
      //点击修改的时候：进入修改的界面进行修改，修改后保存
      function editRow(){
      		//修改稍微时候：新增和删除按钮不可用
      		nui.get("addRow").setEnabled = false;
         	nui.get("removeRow").setEnabled = false;
         	var row = grid.getSelected();
         	//打开新增保存的页面
         	if(row != null){
         	nui.open({
         		url: "<%=request.getContextPath() %>/grc/basic/riskeffectlevel/editRiskEffectLevel.jsp",
         		title: "修改风险影响程度",
         		width:600,
         		height:160,
         		onload:function(){
					var iframe = this.getIFrameEl();
					iframe.contentWindow.setData(row);//跨页面的传值
				},
				ondestroy:function(action){
					nui.get("addRow").setEnabled = true;
         	        nui.get("removeRow").setEnabled = true;
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
        //在删除的时候，新增和编辑不可用
        //nui.alert("删除数据");
        nui.get("addRow").setEnabled = false;
        nui.get("editRow").setEnabled = false;
        var rows = grid.getSelecteds();//获取要操作的行
        if(rows.length > 0){
          //删除数据
          var json = nui.encode({vos:rows});
          nui.confirm("确定要删除吗？", "提示", function(action){
            if(action == "ok"){
              //确定删除
              nui.ajax({
                url: "com.vbm.grc.basic.risk.effectlevel.riskeffectlevel.delRiskEffectLevelVO.biz.ext",
                data: json,
                cache: false,
                type: "POST",
                contentType: "text/json",
                success:function(msg){
                  nui.alert(msg.msg.message);
                  //删除成功后，新增和编辑可用
                   nui.get("addRow").setEnabled = true;
        		   nui.get("editRow").setEnabled = true;
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