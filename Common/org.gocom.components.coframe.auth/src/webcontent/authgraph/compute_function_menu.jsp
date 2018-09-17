<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<!-- 
  - Author(s): yangyong
  - Date: 2013-02-28 10:14:50
  - Description:
-->
<head>
<title>参与者权限</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<%@include file="/coframe/tools/skins/common.jsp" %>
	<script type="text/javascript" src="<%=request.getContextPath()%>/coframe/auth/authgraph/js/raphael-min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/coframe/auth/authgraph/js/coframe_graph.js"></script>
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


node_images["role"] = image_path + "authorization.png";
node_images["function"] = image_path + "menu_root.png";
node_images["menu"] = image_path + "menu.png";

node_border_colors["role"] = "red";
node_border_colors["menu"] = "#bf0000";
node_border_colors["function"] = "#bfac00";
node_border_colors["result"] = "#7cbf00";



var partyData = {};

function SetData(data){
	//跨页面传递的数据对象，克隆后才可以安全使用
    partyData = mini.clone(data);
    loadData();
}
 function loadData(){
	 $.ajax({
	 url: "org.gocom.components.coframe.auth.party.graph.service.getFunctionMenuGraph.biz.ext",
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
   
};
</script>
</body>
</html>
