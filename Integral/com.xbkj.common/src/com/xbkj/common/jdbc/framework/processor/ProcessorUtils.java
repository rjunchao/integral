package com.xbkj.common.jdbc.framework.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.beanutils.Converter;

import com.xbkj.basic.vo.pub.BeanHelper;
import com.xbkj.basic.vo.pub.SuperVO;
import com.xbkj.common.jdbc.framework.mapping.IMappingMeta;
import com.xbkj.common.jdbc.framework.mapping.MappingMetaManager;
import com.xbkj.common.jdbc.framework.util.BeanConvertor;
import com.xbkj.common.jdbc.framework.util.InOutUtil;

/**
 * 
 */
public class ProcessorUtils {

	private static class MethodAndTypes {
		public Method[] invokes = null;

		public int[] types = null;

		public Converter[] converters = null;

	}

	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	static public Object[] toArray(ResultSet rs) throws SQLException {
		ResultSetMetaData meta = rs.getMetaData();
		int cols = meta.getColumnCount();
		Object[] result = new Object[cols];

		for (int i = 0; i < cols; i++) {
			result[i] = rs.getObject(i + 1);
		}

		return result;
	}

	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	static public Map toMap(ResultSet rs) throws SQLException {
		// Map result = new HashMap();
		// ResultSetMetaData rsmd = rs.getMetaData();
		// int cols = rsmd.getColumnCount();
		// for (int i = 1; i <= cols; i++) {
		// result.put(rsmd.getColumnName(i).toLowerCase(), rs.getObject(i));
		// }
		//
		// return result;

		// modify by cch,becaz here getValue just use getObject
		ResultSetMetaData metaData = rs.getMetaData();
		int cols = metaData.getColumnCount();
		Map<String, Object> rsValues = new HashMap<String, Object>();
		for (int i = 1; i <= cols; i++) {
			Object value = getColumnValue(metaData.getColumnType(i), rs, i);
			rsValues.put(metaData.getColumnName(i).toLowerCase(), value);
		}
		return rsValues;
	}

	/**
	 * 锟斤拷锟斤拷转锟斤拷锟斤拷java bean
	 * 
	 * @param resultSet
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	static public Object toBean(ResultSet resultSet, Class type)
			throws SQLException {
		if (resultSet == null)
			throw new SQLException("toBean(resultSet,type) : resultset is null");
		return toBeanInner(resultSet, type, null);
	}

	/**
	 * 锟斤拷锟斤拷转锟斤拷锟斤拷java bean
	 * 
	 * @param resultSet
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	static public Object toBean(ResultSet resultSet, Class type,
			IMappingMeta meta) throws SQLException {

		if ((meta == null) || (resultSet == null))
			throw new SQLException(
					"toBean(resultSet,type,meta): meta is null or resultSet is null");
		return toBeanInner(resultSet, type, meta);
	}

	/*
	 * 锟斤拷锟斤拷Bean锟斤拷转锟斤拷
	 */
	private static Object toBeanInner(ResultSet resultSet, Class type,
			IMappingMeta meta) throws SQLException {
		Object target = null;
		MethodAndTypes methodAndTypes = getBeanInvokeAndTypes(type, resultSet,
				meta, null);
		if (resultSet.next()) {
			target = newInstance(type);
			for (int i = 1; i <= methodAndTypes.types.length; i++) {
				Object value = getColumnValue(methodAndTypes.types[i - 1],
						resultSet, i);
				if (value == null)
					continue;
				Method invoke = methodAndTypes.invokes[i - 1];
				if (invoke == null) {
					// 锟斤拷锟斤拷锟�ull锟津不革拷值
					continue;
				}
				Converter converter = methodAndTypes.converters[i - 1];
				if (converter != null)
					value = converter.convert(invoke.getParameterTypes()[0],
							value);
				BeanHelper.invokeMethod(target, invoke, value);
			}
		} // end if
		return target;
	}

	/**
	 * 锟斤拷锟阶�拷锟斤拷锟�ava bean 锟斤拷锟斤拷
	 * 
	 * @param resultSet
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	static public List toBeanList(ResultSet resultSet, Class type)
			throws SQLException {
		if (resultSet == null)
			throw new SQLException(
					"toBeanList(resultSet,type) : resultset is null");
		return toBeanListInner(resultSet, type, null, null);
	}

	@SuppressWarnings("unchecked")
	private static List toBeanListInner(ResultSet resultSet, Class type,
			IMappingMeta meta, String columns[]) throws SQLException {
		MethodAndTypes methodAndTypes = getBeanInvokeAndTypes(type, resultSet,
				meta, columns);
		List list = new ArrayList();
		while (resultSet.next()) {
			Object target = newInstance(type);
			for (int i = 1; i <= methodAndTypes.types.length; i++) {
				Object value = getColumnValue(methodAndTypes.types[i - 1],
						resultSet, i);
				if (value == null)
					continue;
				Method invoke = methodAndTypes.invokes[i - 1];
				if (invoke == null) {
					continue;
				}
				Converter converter = methodAndTypes.converters[i - 1];
				if (converter != null)
					value = converter.convert(invoke.getParameterTypes()[0],
							value);
				BeanHelper.invokeMethod(target, invoke, value);
			}
			list.add(target);
		}
		return list;
	}

	private static MethodAndTypes getBeanInvokeAndTypes(Class type,
			ResultSet resultSet, IMappingMeta meta, String[] columns)
			throws SQLException {
		MethodAndTypes retResult = new MethodAndTypes();

		Object bean = newInstance(type);
		boolean isSuperBean = false;
		if (bean instanceof SuperVO) {
			isSuperBean = true;
		}
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols = metaData.getColumnCount();
		Method[] invokes = new Method[cols];//瀛���规�
		String[] colName = new String[cols];//瀛�����
		Converter[] converters = new Converter[cols];
		int[] types = new int[cols];
		for (int i = 0; i < cols; i++) {
			types[i] = metaData.getColumnType(i + 1);//�峰������� SQL 绫诲�
			if (columns != null)
				colName[i] = columns[i].toLowerCase();
			else
				colName[i] = metaData.getColumnName(i + 1).toLowerCase();//杩���ヨ�������
			String propName = colName[i];
			if (meta != null) {
				propName = MappingMetaManager.getMapingMeta(meta)
						.getAttributeName(colName[i]);
				if (propName == null)
					continue;
				propName = propName.toLowerCase();
			}

			if (isSuperBean) {
				invokes[i] = getSuperBeanInvokeMethod(bean, propName);
			} else {
				invokes[i] = BeanHelper.getMethod(bean, propName);
			}
			if (invokes[i] != null)
				converters[i] = BeanConvertor.getConVerter(invokes[i]
						.getParameterTypes()[0]);
		}
		retResult.invokes = invokes;
		retResult.types = types;
		retResult.converters = converters;
		return retResult;
	}

	private static Method getSuperBeanInvokeMethod(Object bean, String colName) {

		Method m = BeanHelper.getMethod(bean, colName);
		if (m != null)
			return m;
		String pkFiledName = ((SuperVO) bean).getPKFieldName();
		if (pkFiledName == null)
			return null;
		pkFiledName = pkFiledName.toLowerCase();
		if (pkFiledName.equals(colName)) {
			return BeanHelper.getMethod(bean, "primarykey");
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	static public List toBeanList(ResultSet resultSet, Class type, int skip,
			int size) throws SQLException {
		MethodAndTypes methodAndTypes = getBeanInvokeAndTypes(type, resultSet,
				null, null);
		List list = new ArrayList();
		int index = -1;
		while (index < skip && resultSet.next()) {
			index++;
		}
		if (index < skip)
			return list;
		int offset = 0;
		do {
			offset++;
			Object target = newInstance(type);
			for (int i = 1; i <= methodAndTypes.types.length; i++) {
				Object value = getColumnValue(methodAndTypes.types[i - 1],
						resultSet, i);
				if (value == null)
					continue;
				Method invoke = methodAndTypes.invokes[i - 1];
				if (invoke == null) {
					// 锟斤拷锟斤拷锟�ull锟津不革拷值
					continue;
				}
				Converter converter = methodAndTypes.converters[i - 1];
				if (converter != null)
					value = converter.convert(invoke.getParameterTypes()[0],
							value);
				BeanHelper.invokeMethod(target, invoke, value);
				// populate(target, value, methodAndTypes.invokes[i - 1]);
			}
			list.add(target);
		} while (resultSet.next() && offset < size);

		return list;
	}

	/**
	 * 锟斤拷锟阶�拷锟斤拷锟�ava bean 锟斤拷锟斤拷
	 * 
	 * @param resultSet
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	static public List toBeanList(ResultSet resultSet, Class type,
			IMappingMeta meta) throws SQLException {
		if ((meta == null) || (resultSet == null))
			throw new SQLException(
					"toBeanList(resultSet,type,meta): meta is null or resultSet is null");
		return toBeanListInner(resultSet, type, meta, null);
	}

	/**
	 * 锟斤拷锟阶�拷锟斤拷锟�ava bean 锟斤拷锟斤拷
	 * 
	 * @param resultSet
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	static public List toBeanList(ResultSet resultSet, Class type,
			IMappingMeta meta, String columns[]) throws SQLException {
		if ((meta == null) || (resultSet == null))
			throw new SQLException(
					"toBeanList(resultset,type,meta,columns): meta is null or resultSet is null");
		return toBeanListInner(resultSet, type, meta, columns);
	}

	/**
	 * 锟斤拷锟阶�拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	static public Vector toVector(ResultSet rs) throws SQLException {
		Vector v = new Vector();
		ResultSetMetaData rsmd = rs.getMetaData();
		int nColumnCount = rsmd.getColumnCount();

		while (rs.next()) {
			Vector tmpV = new Vector();
			for (int i = 1; i <= nColumnCount; i++) {
				Object o;
				if (rsmd.getColumnType(i) == Types.CHAR
						|| rsmd.getColumnType(i) == Types.VARCHAR) {
					o = rs.getString(i);

				} else {
					o = rs.getObject(i);
				}

				tmpV.addElement(o);
			}
			v.addElement(tmpV);
		}
		return v;
	}

	public static Object getColumnValue(int type, ResultSet resultSet, int i)
			throws SQLException {
		Object value;
		switch (type) {
		case Types.VARCHAR:
		case Types.CHAR:
			value = resultSet.getString(i);
			break;
		// case Types.INTEGER:
		// case Types.DECIMAL:
		// value = new Integer(resultSet.getInt(i));
		// break;
		case Types.BLOB:
		case Types.LONGVARBINARY:
		case Types.VARBINARY:
		case Types.BINARY:
			value = InOutUtil.deserialize(resultSet.getBytes(i));
			break;
		case Types.CLOB:
			value = getClob(resultSet, i);
			break;
		default:
			value = resultSet.getObject(i);
			break;
		}
		return value;
	}

	/**
	 * @param c
	 * @return
	 * @throws SQLException
	 */
	static private Object newInstance(Class c) throws SQLException {
		try {
			return c.newInstance();

		} catch (InstantiationException e) {
			throw new SQLException("Cannot create " + c.getName() + ": "
					+ e.getMessage());

		} catch (IllegalAccessException e) {
			throw new SQLException("Cannot create " + c.getName() + ": "
					+ e.getMessage());
		}
	}

	/**
	 * @param rs
	 * @param columnIndex
	 * @return
	 * @throws SQLException
	 */
	static public String getClob(ResultSet rs, int columnIndex)
			throws SQLException {
		Reader rsReader = rs.getCharacterStream(columnIndex);
		if (rsReader == null)
			return null;
		BufferedReader reader = new BufferedReader(rsReader);
		// Reader reader = rs.getCharacterStream(columnIndex);
		StringBuffer buffer = new StringBuffer();
		try {
			while (true) {
				String c = reader.readLine();
				if (c == null) {
					break;
				}
				buffer.append(c);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	/**
	 * @param rs
	 * @param columnIndex
	 * @return
	 * @throws SQLException
	 */
	static public byte[] getBlob(ResultSet rs, int columnIndex)
			throws SQLException {
		return rs.getBytes(columnIndex);
	}
}
