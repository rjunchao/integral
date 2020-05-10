/**
 * 
 */
package com.xbkj.gd.integral.report.vos;


import com.xbkj.gd.base.GdSuperVO;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2020-5-10
 *@version 1.0.0
 *@desc 报表显示VO类
 */

public class IntegralDetailReportVO extends GdSuperVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 221840586550506152L;
	
	private String tran_date;//交易日期
	private long tran_integral; 
	
	
	public IntegralDetailReportVO() {
		super();
	}
	public IntegralDetailReportVO(String tran_date, long tran_integral) {
		super();
		this.tran_date = tran_date;
		this.tran_integral = tran_integral;
	}

	public String getTran_date() {
		return tran_date;
	}

	public void setTran_date(String tran_date) {
		this.tran_date = tran_date;
	}

	public long getTran_integral() {
		return tran_integral;
	}

	public void setTran_integral(long tran_integral) {
		this.tran_integral = tran_integral;
	}
	
	@Override
	public String toString() {
		return "IntegralDetailVO [tran_date=" + tran_date + ", tran_integral="
				+ tran_integral + "]";
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getPKFieldName() {
		return null;
	}

	@Override
	public String getTableName() {
		return null;
	}

}
