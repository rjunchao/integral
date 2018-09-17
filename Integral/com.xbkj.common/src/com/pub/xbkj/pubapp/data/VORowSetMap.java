package com.pub.xbkj.pubapp.data;



import java.lang.reflect.Method;
import java.sql.SQLException;

import org.apache.commons.beanutils.Converter;

import com.pub.xbkj.pubapp.Constructor;
import com.xbkj.basic.vo.pub.BeanHelper;
import com.xbkj.basic.vo.pub.SuperVO;
import com.xbkj.common.jdbc.framework.mapping.IMappingMeta;
import com.xbkj.common.jdbc.framework.mapping.MappingMetaManager;
import com.xbkj.common.jdbc.framework.util.BeanConvertor;

/**
 * RowSet到实体VO的映射
 * 
 */
public class VORowSetMap<E extends SuperVO> implements IRowSetMap<E> {
	
	private static class MethodAndTypes {
		public Method[] invokes = null;

		public int[] types = null;

		public Converter[] converters = null;

	}
  /**
   * 实体VO的类型
   */
  private Class<E> clazz;

  /**
   * rowset对应的实体属性名
   */
  private String[] filedNames;

  /**
   * owSet到实体VO的映射构造函数
   * 
   * @param clazz 实体VO的类型
   * @param attributeMetas 实体属性元数据
   */
  public VORowSetMap(Class<E> clazz, String[] filedNames) {
    this.clazz = clazz;
    this.filedNames =  filedNames;    
  }

 
  public E[] convert(IRowSet rowset) {
	  //E对象数目
    int size = rowset.size();
    //创建size个对象返回
    E[] vos = Constructor.construct(this.clazz, size);
    int cursor = 0;
    int length = this.filedNames.length;
//    while (rowset.next()) {
//      for (int i = 0; i < length; i++) {
//        Object value = rowset.getObject(i);
//        
//       vos[cursor].setAttributeValue(this.filedNames[i], value);
//      }
//      cursor++;
//    }
    //=====================
    
	
	try {
		MethodAndTypes methodAndTypes = getBeanInvokeAndTypes(this.clazz, rowset,
				null, this.filedNames);

	while (rowset.next()) {
		for (int i = 0; i < length; i++) {
//			Object value = getColumnValue(methodAndTypes.types[i - 1],
//					resultSet, i);
			 Object value = rowset.getObject(i);
			if (value == null)
				continue;
			Method invoke = methodAndTypes.invokes[i];
			if (invoke == null)
				continue;
			
			Converter converter = methodAndTypes.converters[i];
		
			//進行类型转换，将数据库类型与Java类型进行转换
			if (converter != null)
				value = converter.convert(invoke.getParameterTypes()[0],
						value);
			BeanHelper.invokeMethod(vos[cursor], invoke, value);
	  }
		 cursor++;
    }

	}catch (SQLException e) {
		
		e.printStackTrace();
	}
	
    
    //=====================
    return vos;
  }
  

	private static MethodAndTypes getBeanInvokeAndTypes(Class<?> type,
			IRowSet rowset, IMappingMeta meta, String[] fields)
			throws SQLException {
		
		MethodAndTypes retResult = new MethodAndTypes();

		Object bean = newInstance(type);//创建对象
		boolean isSuperBean = false;
		if (bean instanceof SuperVO) {
			isSuperBean = true;
		}
	
		int cols = fields.length;
		Method[] invokes = new Method[cols];
		String[] colName = new String[cols];
		Converter[] converters = new Converter[cols];
		int[] types = new int[cols];
		for (int i = 0; i < cols; i++) {

			colName[i] = fields[i];
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
	
	/**
	 * @param c
	 * @return
	 * @throws SQLException
	 */
	static private Object newInstance(Class<?> c) throws SQLException {
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
}
