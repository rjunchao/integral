<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/coframe/tools/skins/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
  - Author(s): rjc
  - Date: 2019-10-29 19:57:13
  - Description:
-->
<head>
	<title>签名</title>
	<link rel="stylesheet" href="styles/main.css">
    <link rel="stylesheet" href="styles/e560.css">
    <script src="scripts/vendor/modernizr.js"></script>
	<script src="<%=request.getContextPath() %>/gd/data_handle/integralManage/swfupload/swfupload.js" type="text/javascript"></script>
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
                <td width="15.65%">
                  <button type="button" id="uninitBtn" class="btn btn-block btn-success btn-sm init" onclick="uninitDevice()" disabled>关闭设备</button>
                </td>
                <td width="19.65%">
                  	设备型号:E550/E560
                </td>
               <!--  <td width="16.65%">
                  <input type="text" value="E550/E560" readonly="readonly" class="form-control"></input>
                </td> -->
                <td width="15.65%">
                  <button type="button" class="btn btn-block btn-success btn-sm init" onclick="getAbout()" disabled>关于</button>
                </td>
                <td width="15.65%">
                  <button type="button" class="btn btn-block btn-success btn-sm init" onclick="clearInk()" disabled>清除</button>
                </td>
                <td width="16.65%">
                  <button type="button" class="btn btn-block btn-success btn-sm init" onclick="encode()" disabled>保存</button>
                </td>
              </tr>
            </tbody>
          </table>

          <!-- Table 2 -->
         <!--  <Table class="encoding" align="center">
            <tr>
              <td>
                
                <select class="form-control init" id="encodeType" disabled>
                  <option value="3">PNG</option>
                </select>
              </td>
              <td>
                <button type="button" class="btn btn-block btn-success btn-sm init" onclick="encode()" disabled>保存</button>
              </td>
            </tr>

          </table> -->

        </div>
      </div>

      <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title" id="myModalLabel">PointInfo</h4> </div>
            <div class="modal-body" id="pointInfo">
                <table>
                  <thead>
                    <tr>
                        <td class="head">X</td>
                        <td class="head">Y</td>
                        <td align="right" class="head">Pressure</td>
                        <td align="right" class="head">bStrokeEnd</td>
                        <td align="right" class="head">Time</td>
                    </tr>
                  </thead>
                  <tbody id="pointContant">

                  </tbody>
                </table>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
          </div>
        </div>
      </div>

      <div class="modal fade" id="playbackModal" tabindex="-1" role="dialog" aria-labelledby="playbackLabel" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title" id="playbackLabel">Playback Drawing Video</h4>
            </div>
            <div class="modal-body">
                <video src="" id="playback-video" autoplay="false" controls style="width: 100%;">

                </video>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <script src="scripts/vendor.js"></script>
    <script src="scripts/plugins.js"></script>
    <script src="scripts/e560.js"></script>
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