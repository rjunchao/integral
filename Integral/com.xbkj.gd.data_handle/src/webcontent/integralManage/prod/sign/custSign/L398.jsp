<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta charset="utf-8">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>签名</title>

    <link rel="apple-touch-icon" href="apple-touch-icon.png">
    <!-- Place favicon.ico in the root directory -->

    

    <link rel="stylesheet" href="styles/main.css">
    <link rel="stylesheet" href="styles/l398s.css">

    <script src="scripts/vendor/modernizr.js"></script>
  </head>
  <body>
   

    <div class="container">
     
      <div class="row-canvas">
        <canvas id="ppCanvas" width="600" height="375"></canvas>
        <div class="shutdown"></div>
      </div>


      <div class="row">
        <div class="col-md-12">

          <!-- Table 1 -->
          <table width="100%" class="functions">
            <tbody>
              <!-- Cols 1 -->
              <tr>
                <td width="16.65%">
                  <button type="button" id="initBtn" class="btn btn-block btn-success btn-sm" onclick="initDevice()">初始化设备</button>
                </td>
               
                <td width="16.65%">
                  <input type="text" value="设备类型：L398" readonly="readonly" class="form-control"></input>
                </td>
                <td width="16.65%">
                  <button type="button" class="btn btn-block btn-success btn-sm init" onclick="getAbout()" disabled>关于</button>
                </td>
                <td width="16.65%">
                  <button type="button" class="btn btn-block btn-success btn-sm init" onclick="clearInk()" disabled>清楚</button>
                </td>
                <td>
                <button type="button" class="btn btn-block btn-success btn-sm init" onclick="encode()" disabled>保存</button>
              </td>
              </tr>


    <script src="scripts/vendor.js"></script>
    <script src="scripts/plugins.js"></script>
    <script src="scripts/l398s.js"></script>
<script type="text/javascript">
		debugger;
		initDevice();//初始化设备
		nui.parse();
		
		//var ids = null;
		//明细id
		function SetData(){
			//ids = nui.clone(data);
		}
		
		var signPk = null;
		function GetData(){
			return signPk;
		}
		function saveSign(sign){
			//保存签名
			var json = nui.encode({params:{sign:sign}});
			nui.ajax({
				url:"com.xbkj.gd.data_handle.prod.sign.saveSign.biz.ext",
				cache:false,
				data:json,
				type:"POST",
				contentType:"text/json",
				success: function(text){
					debugger;
					var msg = nui.decode(text);
					if(msg.msg.flag){//添加成功关闭，添加不成功，不关闭窗口
						signPk = msg.msg.obj;
						CloseWindow("ok");
					}else{
						nui.alert(msg.msg.message);
					}
				},
				error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                    CloseWindow();
                }
			});
		}
		
		function CloseWindow(action) {            
            if(window.CloseOwnerWindow){
				return window.CloseOwnerWindow(action);
			}else{
				window.close();
			}   
                   
        }
	</script>
</body>
</html>
