package com.pub.xbkj.common;
/**
 *@date 
 *@version 1.0.0
 */
public class MsgResponse {

	private boolean flag;
	
	private String message;
	
	private Object obj;

	public MsgResponse(){
		
	}
   public MsgResponse(boolean flag){
		this.flag = flag;
	}
   public MsgResponse(String message){
		this.message = message;
	}
   public MsgResponse(String message,boolean flag){
	   this.message = message;
		this.flag = flag;
	}
	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean falg) {
		this.flag = falg;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	
	
	
}
