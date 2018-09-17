<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<!-- 
  - Author(s): yangyong
  - Date: 2013-02-28 10:14:50
  - Description:
-->
<head>
<title>
参与者权限
</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/coframe/tools/skins/common.jsp"%>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/coframe/auth/authgraph/js/raphael-min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/coframe/auth/authgraph/js/coframe_graph.js"></script>
<style>
		*{
			font-family: "Microsoft YaHei", "宋体", "Segoe UI", sans-serif;
    		font-size: 13px;
		}
	</style>
</head>
<body style="">
<div id="canvas"></div>
<script type="text/javascript">
var image_path = "<%=request.getContextPath()%>/coframe/tools/icons/";

node_images["user"] = image_path + "user.png";
node_images["position"] = image_path + "position.png";
node_images["emp"] = image_path + "emp.png";
node_images["group"] = image_path + "group.png";
node_images["duty"] = image_path + "duty.png";
node_images["org"] = image_path + "organization.png";
node_images["role"] = image_path + "authorization.png";
node_border_colors["role"] = "red";
node_border_colors["user"] = "#bf0000";
node_border_colors["position"] = "#bfac00";
node_border_colors["emp"] = "#7cbf00";

var partyData = {};

(function(){
 	if(window.parent.getCurrentNode){
 		var node = window.parent.getCurrentNode();
 		var party = getPartyByNode(node);
 		partyData = {partyId:party.id,partyType:party.partyTypeID};
 		loadData();
 	}
})();

 function loadData(){
	 $.ajax({
	 url: "org.gocom.components.coframe.auth.party.graph.service.getPartyAuthGraph.biz.ext",
	 //url:"/default/coframe/auth/authgraph/test.txt",
	 type: 'POST',
	 data: nui.encode(partyData),
	 cache: false,
	 contentType:'text/json',
	 success: function (text) {
	 	var json = nui.decode(text);
	 	if(json.exception){
	 		nui.alert(json.exception.message || nui.encode(json.exception));
	 		return;
	 	}
		drawGraph(json);
	 },
	 error: function (jqXHR, textStatus, errorThrown) {
	     alert(jqXHR.responseText);
	 }
	});
}

/* only do all this when document has finished loading (needed for RaphaelJS) */
function drawGraph(json){
    if(!json){
    	return;
    }
    var width = $(document).width() - 20;
    var height = $(document).height() - 60;
    
    var coframeGraph = new CoframeGraph("canvas",width,height);
    
	var nodes = json["nodes"];
	
	for(var i=0;i<nodes.length;i++){
		var node = nodes[i];
		coframeGraph.addNode(node);
		if(node.connections){
			for(var j=0;j<node.connections.length;j++){
				coframeGraph.addEdge(node.id, node.connections[j], { directed : true } );
			}
		}
	}
	coframeGraph.draw();
   var result_shape = coframeGraph.getShape("auth_result");
    if(result_shape){
    	for(var i=0;i<result_shape.length;i++){
    		var item = result_shape[i];
    		if($.browser.msie){
    			item.node.style.cursor = "hand";     		
    		}else{
	    		item.node.style.cursor = "pointer";     		
    		}
    	}
    }
    result_shape.click(function(){
    	mini.open({
                url: "<%=request.getContextPath() %>/coframe/auth/authgraph/compute_function_menu.jsp",
                title: "功能菜单计算结果", width: 800, height: 600,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = partyData;
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {

                }
        });
    });
};

	function getPartyByNode(node){
		var party = {};
		if(!node) return party;
		if(node.nodeType=="OrgOrganization"){
	    	party = {
	    		id:node.orgid,
	    		partyTypeID:"org"
	    	}
	    }
	    if(node.nodeType=="OrgPosition"){
	    	party = {
	    		id:node.positionid,
	    		partyTypeID:"position"
	    	}
	    }
	    if(node.nodeType=="OrgEmployee"){
	    	party = {
	    		id:node.empid,
	    		partyTypeID:"emp"
	    	}
	    }
	    if(node.nodeType=="user"){
	        party = {
	            id:node.userId,
	            partyTypeID:"user"
	        }
	    }	
	    return party;	
	}
</script>
</body>
</html>
