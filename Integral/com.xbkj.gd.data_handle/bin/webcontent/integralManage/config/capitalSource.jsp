<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<!-- 
  - Author(s): rjc
  - Date: 2018-09-06 22:38:38
  - Description:
-->
<head>
<title>资金来源</title>
<script type="text/javascript" src="<%=contextPath%>/gd/data_handle/integralManage/config/config.js"></script>
    
</head>

<body>
    <div style="width:600px">
		<div class="nui-toolbar" style="text-align:left;line-height:20px;color:blue;" 
		 borderStyle="border-bottom:0px;border-top:1px solid #999999;border-left:1px solid #999999;border-right:1px solid #999999">
		     <span>资金来源：</span>
		     <input id="params" width="180px" class="nui-textbox" allowInput="true" emptyText="请输入..."/>
    		<a class="nui-button" style="width:60px;" onclick="search('4')">查询</a>	
    	</div> 
	</div>
	
	
	
    <div style="width:600px">
        <div class="nui-toolbar" style="border-bottom:0;padding:0px;" >
            <table style="width:600px;">
                <tr>
                    <td>
                        <a class="nui-button" iconCls="icon-add" onclick="addRow('4')" plain="true" id="addGrid">增加</a>
                        <a class="nui-button" iconCls="icon-edit" onclick="editRow()" plain="true" id="editGrid">编辑</a>
                        <a class="nui-button" iconCls="icon-remove" onclick="del()" plain="true" id="del">删除</a>
                        <span class="separator"></span>
                        <a class="nui-button" iconCls="icon-save" onclick="saveData()" plain="true" id="save">保存</a>
                        <a class="nui-button" onclick="cancelRows" plain="true" id="cancel">取消</a>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    
     <div class="nui-fit" style="width:600px;">
		<input id="pk_gd_integral_config" name="pk_gd_integral_config" class="nui-hidden" />
	    <div id="datagrid1" class="nui-datagrid" style="width:100%;height:100%;" 
		    url="com.xbkj.gd.data_handle.cust.integralConfig.queryAddIntegralConfig.biz.ext"  
		    	idField="pk_gd_integral_config" dataField="vos" allowResize="false" multiSelect="false" 
		    	allowCellSelect="false">
		    	<input name="pk_gd_integral_config" class="nui-hidden"></input>
	           		<div property="columns">
		           	<div type="checkcolumn" width="10px"></div>	
		           	<div field="integral_type_name"  allowResize="true" headerStyle="color:blue;" 
		           		width="150px" headerAlign="center" allowSort="true" >资金来源
			            <input property="editor" class="nui-textbox" style="width:100%;"  allowInput="true" required="true" />
					</div>
		           <!-- <div field="integral_coefficient" headerStyle="color:blue;" width="100px" headerAlign="center" allowSort="false">系数
		              <input property="editor" class="nui-textbox" style="width:100%;" vtype="float" required="true"/>
		            </div>-->
		          
		    </div>
		</div>
	</div>
			
    <script type="text/javascript">
        nui.parse();
        var grid = nui.get("datagrid1");
        setButtonState(0);/**初始化按钮状态**/
        search(4);
        
        
       
    </script>

</body>
</html>