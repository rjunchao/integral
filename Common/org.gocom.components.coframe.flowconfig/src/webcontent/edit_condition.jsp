<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<%@include file="/coframe/tools/skins/common.jsp" %>
<!-- 
  - Author(s): wanghl
  - Date: 2013-03-07 18:25:15
  - Description:
-->
<head>
<title>Title</title>
</head>
<body>
	<div style="padding:5px">
		<div id="form1">
	        <table style="width:100%;height:100%;table-layout:fixed;" class="nui-form-table" >
	            <tr>
	                <td class="nui-form-label">左值：</td>
	                <td>    
	                    <input id="deptCombo" showNullItem="true" class="nui-combobox" textField="name" 
		        		onvaluechanged="onDeptChanged" />
	                </td>
	            </tr>
	            <tr>
	            	<td class="nui-form-label">条件：</td>
	                <td>
	                	<input id="con" class="nui-combobox" textField="name" valueField="id" 
						data="[{id: '=', name: '等于'}]" />   
	                </td>
	            </tr>
	            <tr>
	                <td class="nui-form-label">右值：</td>
	                <td> 
	                	<input id="positionCombo" class="nui-combobox" textField="name" valueField="id" 
	    				onitemclick="onclick"/> 
	                </td>
	            </tr>
	        </table>
	    </div>
	</div>
	<div class="nui-fit">
	<div id="panel1" class="mini-panel" title="属性设置" iconCls="icon-edit" style="width:100%;height:100%;" 
	    showToolbar="false" showCollapseButton="false" showFooter="true" >
	    <!--footer-->
	    <div property="footer">
	        <div class="nui-toolbar" style="text-align:center;padding-top:5px;padding-bottom:5px;" borderStyle="border:0;">
	            <a class="nui-button" iconCls="icon-ok" onclick="onOk">确定</a>
	            <span style="display:inline-block;width:25px;"></span>
	            <a class="nui-button" iconCls="icon-cancel" onclick="onCancel">取消</a>
		    </div>
	    </div>
	    <!--body-->
	    <iframe id="extendFrame" class="nui-iframe" scrolling="auto" style="width:100%;height:100%;" frameborder="0" src="" name="main" >
		    </iframe>
	</div>
	</div>
</body>
 <script type="text/javascript">
        nui.parse();
        
        var iframe = document.getElementById("extendFrame");
		iframe.src = "";

        var deptCombo = nui.get("deptCombo");
        var conCombo = nui.get("con");
        conCombo.select(0);
        var positionCombo = nui.get("positionCombo");
        var comboData = [];
        
        //绑定子页面加载完后执行事件
		if (iframe.attachEvent){
		    iframe.attachEvent("onload", function(){
		    	iframeSetData();
		    });
		} else {
		    iframe.onload=function(){
		        iframeSetData();
		    };
		}
		
		//向选人选机构组件传入参数
		function iframeSetData(){
			if(iframe.contentWindow.setData){
				var data = {}; //与选择参与者页面交互数据
				var root = "user|role{1}|org{1}";  //参与者类型（可以为空，空代表为全部类型）
				//var maxNum = "3"; //可选择参与者的最大数，默认为Integer最大值
				//var selectTypes = "org"; //可选的参与者类型,用“，”分隔
				
				data.root = root;
				//data.maxNum = maxNum;
				//data.selectTypes = selectTypes;
				data.extension = clickNode.extension;
	    		iframe.contentWindow.setData(data);
	    	}
		}
        
        
        function setComboValue(){
        	$.ajax({
        		url: "org.gocom.components.coframe.flowconfig.processMgr.getFlowCondtions.biz.ext",
        		type: "POST",
        		contentType: "text/json",
        		async:false,
        		success: function(text){
        			comboData = nui.decode(text).flowContions;
        			deptCombo.setData(comboData);
        		}
        	});
        }
        
        function onclick(e){
        	var item = e.item;
        	if(item.pageUrl != "null"){
        		var url = "<%=contextPath%>/widget/select_participants.jsp";
        		iframe.src = url;
        	}else{
        		iframe.src = "";
        	}
        }
        
        function onDeptChanged(e) {
            var id = deptCombo.getValue();
			
			for(var i = 0; i < comboData.length; i++){
				if(id == comboData[i].id){
					positionCombo.setData(comboData[i].rightModels);
				}
			}
            positionCombo.select(0);
        }
        
        
        
        
        
        /////////////////////////////////////////
        var clickNode = {};
        function setData(node){
        	var node = nui.clone(node);
        	setComboValue();
        	if(node){
        		clickNode = node;
        		deptCombo.setValue(node.leftId);
        		if(deptCombo.getSelected()){
	        		var rightModels = deptCombo.getSelected().rightModels;
        		}
        		
        		positionCombo.setData(rightModels);
				positionCombo.setValue(node.rightId);
				conCombo.setValue(node.condition);
				if(positionCombo.getSelected() && positionCombo.getSelected().pageUrl != "null"){
					var url = "<%=contextPath%>/widget/select_participants.jsp";
	        		iframe.src = url;
				}
        	}else{
	        	deptCombo.select(1);
        		var rightModels = deptCombo.getSelected().rightModels;
        		positionCombo.setData(rightModels);
        		positionCombo.select(0);
        	}
        }
        
        //父页面获取数据
        function getData(){
        	var node = clickNode;
        	var condition = conCombo.getValue();
        	var leftId = deptCombo.getValue();
        	var rightId = positionCombo.getValue();
        	var name = deptCombo.text + "_" + conCombo.text + "_" + positionCombo.text;
        	node.condition = condition;
        	node.leftId = leftId;
        	node.rightId = rightId;
        	node.name = name;
        	if(iframe.contentWindow.getData){
        		var participants = iframe.contentWindow.getData();
        		participants = nui.clone(participants);
        		var extension = new Array();
        		for(var i = 0; i < participants.length; i++){
					var key = participants[i].id;
					var value = participants[i].typeCode + "." + participants[i].name;
					extension.push({key: key, value: value});
				}
	        	node.extension = extension;
        	}else{
        		node.extension = [ ];
        	}
        	return node;
        }
        
        function CloseWindow(action) {
	        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
	        else window.close();
	    }
	
		//保存条件前先检查条件完整性，条件不能为空。
	    function onOk() {
	    	var condition = conCombo.getValue();
        	var leftId = deptCombo.getValue();
        	var rightId = positionCombo.getValue();
        	if(condition && leftId && rightId){
		        CloseWindow("ok");
        	}else{
        		alert("请选择完整条件！");
        	}
        	
	    }
	    function onCancel() {
	        CloseWindow("cancel");
	    }

    </script>
 
</html>