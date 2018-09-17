<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="/coframe/tools/skins/common.jsp" %>
<!-- 
  - Author(s): wanghl
  - Date: 2013-03-22 10:48:55
  - Description:
-->
<head>
<title>Title</title>
</head>
<body>
<style type="text/css">

a.levmenu{
	font-size:14px;
	color:#104E8B;
	font-weight:bold;
}

.lev2menu a{
	font-size:14px;
	color:#104E8B;
	
}

a:hover {
	color: #FF7F24;
	font-weight:bold;
}

.lev2menu{
	margin-top:0px;
	width:25%;
}

a.lev3menu{
	padding: 0 10px 0 10px;
	font-size:12px;
	padding-right:10px;
	color:#1C86EE;
	border-left: 1px solid #DEDEDE;
}

a.lev3menu:hover {
	color: #FF7F24;
	font-weight:bold;
}

</style>
<div id="allmenu">
	<table style="width:100%">
		<tr>
			<td id="td1" style="width:25%;border-right:1px dashed gray;padding-left:10px;" valign='top'></td>
			<td id="td2" style="width:25%;border-right:1px dashed gray;padding-left:10px;" valign='top'></td>
			<td id="td3" style="width:25%;border-right:1px dashed gray;padding-left:10px;" valign='top'></td>
			<td id="td4" style="width:25%;" valign='top'></td>
		</tr>
	</table>
</div>
</body>
<script tyle="text/javascript">

	getMenuData();
		nui.parse();
	function getMenuData(){
		$.ajax({
			url: "org.gocom.components.coframe.auth.LoginManager.getMenuData.biz.ext",
			type: "POST",
			success: function(text){
				alert(nui.encode(text.treeNodes));
				var treeNodes = text.treeNodes;
				setMenuData(treeNodes);
			}
		});
	}
	
	function setMenuData(data){
		if(data){
			var levmenus = ["","","",""];
			for(var i = 0; i < data.length; i++){
				var levmenu = "";
				var menuName = data[i].menuName;
				var linkAction = data[i].linkAction ? data[i].linkAction: "";
				var menuPrimeKey = data[i].menuPrimeKey;
				var menuSeq = data[i].menuSeq;
				if(linkAction == ""){
					levmenu += "<ul style='margin-top:10px;'><a class='levmenu' href='#' id='" + menuPrimeKey + "' menuSeq='" + menuSeq + "'" + ">" + menuName + "</a></ul>";
				}else{
					levmenu += "<ul style='margin-top:10px;'><a class='levmenu' href='" + "<%=contextPath %>" + linkAction +"' id='" + menuPrimeKey + "' menuSeq='" + menuSeq + "'" + ">" + menuName + "</a></ul>";
				}
				var secondChilds = "";
				if(data[i].childrenMenuTreeNodeList){
					var Lev2childrens = data[i].childrenMenuTreeNodeList;
					for(var j = 0; j < Lev2childrens.length; j++){
						var menuName = Lev2childrens[j].menuName;
						var linkAction = Lev2childrens[j].linkAction ? Lev2childrens[j].linkAction : "";
						var menuPrimeKey = Lev2childrens[j].menuPrimeKey;
						var menuSeq = Lev2childrens[j].menuSeq;
						var otherChildrens = getThirdOrMoreChild(Lev2childrens[j]);
						if(otherChildrens && (otherChildrens != "")){
							secondChilds += "<tr><td class='lev2menu'><a href='#' id='" + menuPrimeKey + "' menuSeq='" + menuSeq + "'" + ">" + menuName + "</a><ul style='padding-left:10px'>" + otherChildrens + "</ul></td></tr>";
						}else{
							secondChilds += "<tr><td class='lev2menu'><a href='" + "<%=contextPath %>" + linkAction +"' id='" + menuPrimeKey + "' menuSeq='" + menuSeq + "'" + ">" + menuName + "</a></td></tr>";
						}
					}
				}
				if(secondChilds){
					levmenu += "<table style='width:100%;margin-top:5px;padding-left:30px;'>" +  secondChilds + "</table>";
				}
				levmenu = "<div class='lev1'>" + levmenu + "</div>";
				levmenus[i%4] += levmenu;
			}
			insertMenuToHtml(levmenus);
		}
	}
	
	function getThirdOrMoreChild(lev2Childrens){
		var results = "";
		if(lev2Childrens.childrenMenuTreeNodeList){
			var childrens = lev2Childrens.childrenMenuTreeNodeList;
			for(var i = 0; i < childrens.length; i++){
				var menuName = childrens[i].menuName;
				var linkAction = childrens[i].linkAction ? childrens[i].linkAction : "";
				var menuPrimeKey = childrens[i].menuPrimeKey;
				var menuSeq = childrens[i].menuSeq;
				if(linkAction == ""){
					results += "<a class='lev3menu' href='#' id='" + menuPrimeKey + "' menuSeq='" + menuSeq + "'" + ">" + menuName + "</a>";
				}else{
					results += "<a class='lev3menu' href='" + "<%=contextPath %>" + linkAction +"' id='" + menuPrimeKey + "' menuSeq='" + menuSeq + "'" + ">" + menuName + "</a>";
				}
				var ret = getThirdOrMoreChild(childrens[i]);
				if(ret){
					results += ret;
				}
			}
			if(results){
				return results;
			}
		}
	}
	
	function insertMenuToHtml(levmenu){
		$("#td1").html(levmenu[0]);
		$("#td2").html(levmenu[1]);
		$("#td3").html(levmenu[2]);
		$("#td4").html(levmenu[3]);
	}

</script>
</html>