
<%
	String skin="skin1";
	String contextPath=request.getContextPath();
%>


<link id="css_skin" rel="stylesheet" type="text/css" href="<%=contextPath%>/coframe/tools/skins/<%=skin %>/css/style.css"/>
<link id="css_icon" rel="stylesheet" type="text/css" href="<%=contextPath%>/coframe/tools/icons/icon.css"/>
<script type="text/javascript" src="<%=contextPath%>/common/nui/nui.js"></script>
<script>
	$(function(){
		nui.context='<%=contextPath %>'
	});
	
	var data=[];
	
	nui.DataTree.prototype.dataField='data';//兼容改造
</script>
