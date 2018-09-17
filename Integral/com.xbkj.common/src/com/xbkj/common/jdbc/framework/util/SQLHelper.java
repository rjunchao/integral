/*
 * �������� 2005-10-10
 *
 * TODO Ҫ��Ĵ���ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package com.xbkj.common.jdbc.framework.util;

import com.xbkj.datasor.bs.framework.common.InvocationInfoProxy;

/**
 * @nopublish
 * @author hey
 * 
 * TODO Ҫ��Ĵ���ɵ�����ע�͵�ģ�壬��ת�� ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class SQLHelper {

	/**
	 * ��ݱ��������Ƶõ��������
	 * 
	 * @param table
	 * @param names
	 * @return
	 */
	public static String getInsertSQL(String table, String names[]) {
		StringBuffer buffer = new StringBuffer("INSERT INTO " + table + " (");
		for (int i = 0; i < names.length; i++) {
			/*if (names[i].equalsIgnoreCase("ts"))
				continue;*/ //修改于2017:07:25 rjc
			buffer.append(names[i] + ",");
		}
		buffer.setLength(buffer.length() - 1);
		buffer.append(") VALUES (");
		for (int i = 0; i < names.length; i++) {
			/*if (names[i].equalsIgnoreCase("ts"))
				continue;*/ //修改于2017:07:25 rjc
			buffer.append("?,");
		}
		buffer.setLength(buffer.length() - 1);
		buffer.append(")");
		return buffer.toString();
	}

	/**
	 * ��ݱ��������Ƶõ��������
	 * 
	 * @param table
	 * @param names
	 * @return
	 */
	public static String getUpdateSQL(String tableName, String[] names,
			String pkName) {
		StringBuffer sql = new StringBuffer("UPDATE " + tableName + " SET  ");
		for (int i = 0; i < names.length; i++) {
			if (names[i].equalsIgnoreCase("ts"))
				continue;
			sql.append(names[i] + "=?,");
		}
		sql.setLength(sql.length() - 1);
		sql.append(" WHERE ").append(pkName).append("=?");
		return sql.toString();
	}

	public static String getUpdateSQL(String tableName, String[] names) {
		StringBuffer sql = new StringBuffer("UPDATE " + tableName + " SET  ");
		for (int i = 0; i < names.length; i++) {
			if (names[i].equalsIgnoreCase("ts"))
				continue;
			sql.append(names[i] + "=?,");
		}
		sql.setLength(sql.length() - 1);
		return sql.toString();
	}

	public static String getDeleteByPKSQL(String tableName, String pkName) {
		return "DELETE FROM " + tableName + " WHERE " + pkName + "=?";
	}

	public static String getDeleteSQL(String tableName, String[] names) {
		StringBuffer sql = new StringBuffer("DELETE FROM " + tableName
				+ " WHERE ");
		for (int i = 0; i < names.length; i++) {
			sql.append(names[i] + "=? AND ");
		}
		sql.setLength(sql.length() - 4);
		return sql.toString();
	}

	/**
	 * @param tableName
	 * @param names
	 * @param isAnd
	 * @param fields
	 * @return
	 */
	public static String getSelectSQL(String tableName, String[] names,
			boolean isAnd, String[] fields) {
		StringBuffer sql = new StringBuffer();
		if (fields == null)
			sql.append("SELECT * FROM " + tableName);
		else {

			sql.append("SELECT ");
			for (int i = 0; i < fields.length; i++) {
				sql.append(fields[i] + ",");

			}
			sql.setLength(sql.length() - 1);
			sql.append(" FROM " + tableName);
		}
		String append = "AND ";
		if (!isAnd)
			append = "OR ";
		if (names == null || names.length == 0)
			return sql.toString();
		sql.append(" WHERE ");
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			sql.append(name + "=? ");
			if (i != names.length - 1)
				sql.append(append);
		}
		return sql.toString();

	}

	public static String getSelectSQL(String tableName, String[] fields) {
		StringBuffer sql = new StringBuffer();
		if (fields == null)
			sql.append("SELECT * FROM " + tableName);
		else {

			sql.append("SELECT ");
			for (int i = 0; i < fields.length; i++) {
				sql.append(fields[i] + ",");

			}
			sql.setLength(sql.length() - 1);
			sql.append(" FROM " + tableName);
		}

		return sql.toString();

	}

	/**
	 * �õ���˾��PK
	 * 
	 * @return
	 */
	static public String getCorpPk() {
		String pk_corp;

		pk_corp = InvocationInfoProxy.getInstance().getCorpCode();
		if (pk_corp == null || (pk_corp = pk_corp.trim()).equals("")
				|| pk_corp.equals("null")) {
			pk_corp = "0001";
		}
		return pk_corp;

	}

	public static String getSelectSQL(String tableName, String[] fields,
			String[] names) {
		StringBuffer sql = new StringBuffer();
		if (fields == null)
			sql.append("SELECT * FROM " + tableName);
		else {

			sql.append("SELECT ");
			for (int i = 0; i < fields.length; i++) {
				sql.append(fields[i] + ",");

			}
			sql.setLength(sql.length() - 1);
			sql.append(" FROM " + tableName);
		}
		String append = "AND ";

		if (names == null || names.length == 0)
			return sql.toString();
		sql.append(" WHERE ");
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			sql.append(name + "=? ");
			if (i != names.length - 1)
				sql.append(append);
		}
		return sql.toString();

	}
}
