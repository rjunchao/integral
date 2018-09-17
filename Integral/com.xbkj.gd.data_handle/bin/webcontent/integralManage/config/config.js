		var df = 0;
        function search(integral_type){
			//查询
			var params = {};
			params.integral_type=integral_type;
			params.config_name = nui.get("params").getValue();
			grid.load({params:params});
        	
        }
        
        /**当前页增行事件***/
        function addRow(integral_type) {
        	df = '1';
            var newRow = {integral_type: integral_type};
		    grid.addRow(newRow);
		    grid.beginEditRow(newRow);
            setButtonState(1);
        }

        /**当前页编辑事件***/
        function editRow() {
        	df = '2';
        	var size = grid.getData().length;
	        var row = grid.getSelected();
			if (row) {			
			 	grid.beginEditRow(row);
			 	setButtonState(1);
			} else {
				nui.alert("请选择一条记录！");
        	}
        }

       function cancelRows() {
          grid.cancelEdit();
          grid.reload();
          setButtonState(0);
          grid.setAllowCellEdit(false);
        }	
		
	   

        function saveData() {
        	var frorm = new nui.Form("#datagrid1");
            frorm.validate();
	        if (frorm.isValid() == false) {
	            nui.alert("类型为字符，系数为数字！");
	            return;
	        }
             grid.validate();
	        if (grid.isValid() == false) {
                var error = grid.getCellErrors()[0];
                nui.alert(error.errorText);
                grid.beginEditRow(error.record);
                grid.beginEditCell(error.record, error.column);
                return;
            } 
            grid.commitEdit();
          
            var size = grid.getData().length; 
            var rows = grid.getChanges();
            var newVO = {
	            	integral_coefficient:rows[0].integral_coefficient,//系数
	            	integral_type:rows[0].integral_type,//类型
	            	integral_type_name:rows[0].integral_type_name//名称
            	};
            var json = nui.encode({vo:rows[0]});
            //nui.alert(json);
            if(df == '1')
            {
	            nui.ajax({
	                url: "com.xbkj.gd.data_handle.cust.integralConfig.addConfig.biz.ext",
	                type: "post",
	                data: json,
	                cache: false,
	                contentType: 'text/json',
	                success: function (text) {
	                	if(!text){
	                		console.log("返回信息为空");
	                		return;
	                	}
	                	nui.alert(text.msg.message);
						if(!text.msg.flag){
                			setButtonState(1);
	                		var sr = grid.getSelected();
            				grid.beginEditRow(sr);
	                		return;
	                	}else{
	                		cancelRows();
	                		grid.reload();
	                	}
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                    nui.alert(jqXHR.responseText);
	                }
	            }); 
            }
            else if(df == '2')
            {
	        	nui.ajax({
	                url: "com.xbkj.gd.data_handle.cust.integralConfig.updateConfig.biz.ext",
	                type: "post",
	                data: json,
	                cache: false,
	                contentType: 'text/json',
	                success: function (text) {
	                	if(!text){
	                		console.log("返回信息为空");
	                		return;
	                	}
	                	nui.alert(text.msg.message);
						if(!text.msg.flag){
	                		setButtonState(1);
	                		grid.unmask(); 
                			/* grid.beginEditRow(data[0]); */
                			var sr = grid.getSelected();
            				grid.beginEditRow(sr);
                			
	                		return;
	                	}else{
	                		cancelRows();
	                		grid.reload();
	                	}
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                    nui.alert(jqXHR.responseText);
	                }
	            });
            }
        }
      
        /**设置按钮展示状态,0-初始化状态,1-增加/编辑状态 **/
        function setButtonState(OpState) {
        	var  isShow = true;
        	var  isNotShow = false;
			switch(OpState) {
				case 1:
					isShow = false;
					isNotShow = true;
				  	break;
			}
			nui.get("#addGrid").setEnabled(isShow);
			nui.get("#editGrid").setEnabled(isShow);
			nui.get("#del").setEnabled(isShow);
			nui.get("#cancel").setEnabled(isNotShow);
			nui.get("#save").setEnabled(isNotShow);
        }
        
     
       
           
        
        
	   /*
		   删除参数
	   */
       function del() {  
	    	var size = grid.getData().length;
	        var row = grid.getSelected();
			if (row) {
			
				var json = nui.encode({vo:row});
			//	nui.alert(json);
				//return ;
				nui.confirm("确定要删除添加积分配置项么？", "确定？",
	            function (action) {
	                if (action == "ok") {
	                	nui.ajax({
	                        url: "com.xbkj.gd.data_handle.cust.integralConfig.deleteConfig.biz.ext",
	                        type: "POST",
	                        data: json,
	                        cache: false,
	                        contentType: 'text/json',
	                        success: function (text) {
				            	nui.alert(text.msg.message);
								if(text.msg.flag){
									grid.reload();
			                		return;
			                	}
	                        },
							error: function () {
	                    		nui.alert("操作失败！");
	                    	}
	                	});
	                }
	            });
	            
			} else {
				 nui.alert("请选中一条记录");
			}
	    }