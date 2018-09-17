package com.xbkj.common.jdbc.framework.mapping;

import java.util.ArrayList;
import java.util.List;

import com.pub.xbkj.pubapp.JavaType;


/**
 * @author hey
 *
 */
public class MappingMeta implements IMappingMeta,IAttributeMapping {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String tableName;

    private String primaryKey;
    
    /*
     * 增加属性与javatype的对应关系
     */
    
    private List<String> attributeList = new ArrayList<String>();
    
    private List<JavaType> javaTypeList = new ArrayList<JavaType>();;


    private AttributeMapping mapping = new AttributeMapping();

    /**
     * @param tableName
     * @param primaryKey
     * @param mapping
     */
    public MappingMeta(String tableName, String primaryKey) {
        super();
        this.tableName = tableName;
        this.primaryKey = primaryKey;

    }

    /**
     * @param tableName
     * @param primaryKey
     * @param corpPK
     */
    public MappingMeta(String tableName, String primaryKey, String corpPK) {
        super();
        this.tableName = tableName;
        this.primaryKey = primaryKey;
      
    }

    public void addMapping(String attributeName, String columnName) {
        mapping.addMapping(attributeName, columnName);
    }

    public void addMapping(String[] attributes, String[] columns) {
        if (attributes.length != columns.length)
            throw new IllegalArgumentException("参数长度不一致");
        for (int i = 0; i < attributes.length; i++) {
            mapping.addMapping(attributes[i], columns[i]);
        }
    }

    

    public String getPrimaryKey() {
        return primaryKey;
    }

    public String getTableName() {
        return tableName;
    }

    public void clearMapping() {
        mapping.clearMapping();
    }

    public boolean equals(Object obj) {
        return mapping.equals(obj);
    }

    public String getAttributeName(String columnName) {
        return mapping.getAttributeName(columnName);
    }

    public String[] getAttributes() {
        return mapping.getAttributes();
    }

    public String getColumnName(String attributeName) {
        return mapping.getColumnName(attributeName);
    }

    public String[] getColumns() {
        return mapping.getColumns();
    }

    public void removeMapping(String attributeName) {
        mapping.removeMapping(attributeName);
    }

    public boolean isValid() {
        return mapping.isValid();
    }

    public String getAttributeNameByPK() {
        return mapping.getAttributeName(primaryKey);
    }

 

    public void modifyMapping(String attribute, String column) {
        mapping.modifyMapping(attribute, column);

    }
    
	public AttributeMapping getAttributeMapping() {
		return mapping;
	}

	public List<String> getAttributeList() {
		return attributeList;
	}

	public void addAttribute(String attribute) {
		this.attributeList.add(attribute);
	}

	public List<JavaType>  getJavaTypeList() {
		return javaTypeList;
	}

	public void addJava(JavaType javaType) {
		this.javaTypeList.add(javaType);
	}
	
	
	public void clearAttributeList(){
		this.attributeList.clear();
	}
	
	public void clearJavaTypeList(){
		this.javaTypeList.clear();
	}
}
