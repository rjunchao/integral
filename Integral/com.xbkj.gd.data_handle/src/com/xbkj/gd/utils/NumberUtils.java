package com.xbkj.gd.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.eos.common.connection.SimpleConnectionHelper;
import com.eos.system.annotation.Bizlet;
import com.xbkj.common.bs.dao.BaseDAO;

/**
 *@author rjc
 *@email rjc@ronhe.com.cn
 *@date 2017-7-8
 *@version 1.0.0
 *@desc
 */
public class NumberUtils {
	//private static Map<String, Integer> numMap = new ConcurrentHashMap<String, Integer>();
	/*static {
		numMap = new HashMap<String, Integer>();
		numMap.put(new SimpleDateFormat("yyyyMMdd").format(new Date()), 1);
	}*/
	/**
	 * 规则
	 * 日期+序号（三位，不够左边补0）
	 * @return
	 */
	/**
	 * 规则
	 * 日期+序号（三位，不够左边补0）
	 * @return
	 */
/*	public static String getNumber(){
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		if(numMap.containsKey(date)){
			//序号每天都从1开始，如果已经有了，在原来的数上+1
			Integer num = numMap.get(date);
			num += 1;
			numMap.put(date, num);
			int len = num.toString().length();
			String numStr = leftZero(num, (3-len));
			return date + numStr;
		}else{
			numMap.clear();
			numMap.put(date, new Integer(1));
			return date + "001";
		}
	}*/
	/**
	 * 规则
	 * 日期+序号（三位，不够左边补0）
	 * @return
	 */
	public static String getNumber(Integer num){
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		int len = num.toString().length();
		String numStr = leftZero(num, (3-len));
		return date + numStr;
	}
	
	//左边补0
	public static String leftZero(int number, int len){
		if(len == 0){
			return number+"";
		}else{
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < len; i++){
				sb.append("0");
			}
			return sb.toString() + number;
		}
	}
	
	
	/**
	 * 从数据库中获取序号
	 * @param 是否第一次加载
	 * @return
	 */
	@Bizlet
	public static String getDBNumber(){
		String sql = "SELECT G.NUMBER_DATE, G.NUM FROM GD_NUMBER G";
		String now_date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		BaseDAO dao = new BaseDAO();
		String numStr = now_date+"001";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			String[] os = getData(ps, rs);
			if(os == null){
				//不是同一天，按当前日期为准
				dao.executeUpdate("TRUNCATE TABLE gd_number");
				dao.executeUpdate("INSERT INTO GD_NUMBER (NUMBER_DATE, NUM)VALUES('"+now_date+"', 1)");
			}else{
				String date = os[0];//日期
				String num = os[1];//字符串
				if(date.equals(now_date)){//
					//原来的数上加1,还要更新回数据库
					//页面初始化不加1
					numStr = getNumber(new Integer(num));
					dao.executeUpdate("UPDATE gd_number g SET g.num = g.num + 1 WHERE g.number_date='"+now_date+"'");
				}else{
					//不是同一天，按当前日期为准
					dao.executeUpdate("TRUNCATE TABLE gd_number");
					dao.executeUpdate("INSERT INTO GD_NUMBER (NUMBER_DATE, NUM)VALUES('"+now_date+"', 1)");
				}
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			close(conn, ps, rs);
		}
		return numStr;
	}
	
	private static String[] getData(PreparedStatement ps, ResultSet rs) {
		try {
			synchronized (ps) {
				rs = ps.executeQuery();
			} 
			if(rs != null){
				while(rs.next()){
					String date = rs.getString(1);
					String num = rs.getString(2);
					return new String[]{date, num};
				}
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Connection getConnection() {
		return SimpleConnectionHelper.getConnection();
	}

	private static void close(Connection conn, PreparedStatement ps,
			ResultSet rs){
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(ps != null){
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println(NumberUtils.getDBNumber());
	}
	

}
