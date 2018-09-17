
package com.xbkj.common.jdbc.framework.mapping;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xbkj.basic.vo.pub.BeanHelper;
import com.xbkj.common.jdbc.framework.SQLParameter;
import com.xbkj.common.jdbc.framework.util.SQLHelper;


public class BeanMapping {

	private Object bean;

	private IMappingMeta metaInfo;

	private List<String> validAttributes;

	private List<String> validColumns;

	private Map<String,Object> cache = new HashMap<String,Object>();

	private Map<String,Integer> types = new HashMap<String,Integer>();

	private AttributeMapping metaMap = new AttributeMapping();

	/**
	 * @param bean
	 * @param meatInfo
	 */
	public BeanMapping(Object bean, IMappingMeta metaInfo) {
		super();
		this.bean = bean;
		this.metaInfo = metaInfo;
		validAttributes = new ArrayList<String>();
		validColumns = new ArrayList<String>();
		metaMap = MappingMetaManager.getMapingMeta(metaInfo);
		// metaMap.addMapping(metaInfo.getAttributes(),metaInfo.getColumns());
		validAttributes();
	}

	public boolean isNullTS() {
		String ts = metaMap.getAttributeName("ts");
		Object pkValue = cache.get(ts);
		return pkValue == null ? true : false;
	}

	public boolean isNullPK() {
		String pkName = metaMap.getAttributeName(metaInfo.getPrimaryKey());
		Object pkValue = cache.get(pkName);
		return pkValue == null ? true : false;
	}

	public String getInsertSQL() {
		String[] columns = (String[]) validColumns.toArray(new String[] {});
		String tableName = metaInfo.getTableName();
		return SQLHelper.getInsertSQL(tableName, columns);
	}

	public String getDeleteSQL() {
		String[] columns = (String[]) validColumns.toArray(new String[] {});
		return SQLHelper.getDeleteSQL(metaInfo.getTableName(), columns);
	}

	public String getUpdateSQL() {

		// String[] columns = (String[]) validColumns.toArray(new String[] {});
		String[] columns = metaInfo.getColumns();
		if (!isNullPK())
			return SQLHelper.getUpdateSQL(metaInfo.getTableName(), columns,
					metaInfo.getPrimaryKey());
		else
			return SQLHelper.getUpdateSQL(metaInfo.getTableName(), columns);
	}

	public String getSelectSQL() {
		String[] columns = metaInfo.getColumns();
		return SQLHelper.getSelectSQL(metaInfo.getTableName(), columns);
	}

	public String getSelectwithParamSQL() {
		String[] columns = metaInfo.getColumns();
		String[] criterias = (String[]) validColumns.toArray(new String[] {});

		return SQLHelper.getSelectSQL(metaInfo.getTableName(), columns,
				criterias);
	}

	public SQLParameter getSelectParameter() {
		SQLParameter param = new SQLParameter();
		String attributes[] = (String[]) validAttributes
				.toArray(new String[] {});
		for (int i = 0; i < attributes.length; i++) {
			param.addParam(cache.get(attributes[i]));
		}
		return param;
	}

	public Object getPKValue() {
		String pkName = metaMap.getAttributeName(metaInfo.getPrimaryKey());
		Object pkValue = cache.get(pkName);
		return pkValue;
	}

	public SQLParameter getUpdateParamter() {
		SQLParameter param = new SQLParameter();
		// String attributes[] = (String[]) validAttributes
		// .toArray(new String[] {});
		String attributes[] = metaInfo.getAttributes();
		// String columns[] = (String[]) validColumns.toArray(new String[] {});
		String columns[] = metaInfo.getColumns();
		String pkName = metaMap.getAttributeName(metaInfo.getPrimaryKey());
		Object pkValue = cache.get(pkName);
		// Object values[] = new Object[attributes.length - 1];
		for (int i = 0; i < attributes.length; i++) {
			if (attributes[i].equalsIgnoreCase("ts"))
				continue;
			// values[i] = ;
			int type = Types.INTEGER;
			if (types.get(columns[i].toUpperCase()) != null)
				type = (new Integer(types.get(columns[i].toUpperCase())
						.toString())).intValue();

			if (type == Types.BLOB || type == Types.LONGVARBINARY
					|| type == Types.VARBINARY || type == Types.BINARY)
				param.addBlobParam(cache.get(attributes[i]));
			else if (type == Types.CLOB || type == Types.LONGVARCHAR)
				param.addClobParam(String.valueOf(cache.get(attributes[i])));
			else {
				if (cache.get(attributes[i]) == null) {
					param.addNullParam(type);
				} else {
					param.addParam(cache.get(attributes[i]));
				}
			}
		}
		if (pkValue != null)
			param.addParam(pkValue);
		return param;

	}

	public SQLParameter getInsertParamter() {
		SQLParameter param = new SQLParameter();
		String attributes[] = (String[]) validAttributes
				.toArray(new String[] {});
		String columns[] = (String[]) validColumns.toArray(new String[] {});
		// String pkName = metaInfo.getAttributeName(metaInfo.getPrimaryKey());

		for (int i = 0; i < attributes.length; i++) {
			// values[i] = ;
			if (attributes[i].equalsIgnoreCase("ts"))
				continue;
			int type = Types.INTEGER;
			if (types.get(columns[i]) != null)
				type = types.get(columns[i]);
			if (type == Types.BLOB || type == Types.LONGVARBINARY
					|| type == Types.VARBINARY || type == Types.BINARY)
				param.addBlobParam(cache.get(attributes[i]));
			else if (type == Types.CLOB || type == Types.LONGVARCHAR)
				param.addClobParam(String.valueOf(cache.get(attributes[i])));
			else {
				if (cache.get(attributes[i]) == null) {
					param.addNullParam(type);
				} else {
					param.addParam(cache.get(attributes[i]));
				}
			}

		}
		return param;

	}

	public SQLParameter getDeleteParamter() {
		SQLParameter param = new SQLParameter();
		String attributes[] = (String[]) validAttributes
				.toArray(new String[] {});
		// String pkName = metaInfo.getAttributeName(metaInfo.getPrimaryKey());

		for (int i = 0; i < attributes.length; i++) {
			param.addParam(cache.get(attributes[i]));
		}
		return param;

	}

	private void validAttributes() {
		// if (!metaInfo.isValid())
		// throw new IllegalArgumentException("û������ӳ����Ϣ��");
		String attributes[] = metaInfo.getAttributes();
		for (int i = 0; i < attributes.length; i++) {
			Object value = BeanHelper.getProperty(bean, attributes[i].toLowerCase());
			if (value != null)// ȥ����ֵ������
			{
				validAttributes.add(attributes[i]);
				validColumns.add(metaMap.getColumnName(attributes[i])
						.toUpperCase());
				cache.put(attributes[i], value);
			}

		}

	}

	public void setType(Map<String,Integer> types) {
		this.types = types;
	}

}
