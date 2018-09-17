
package com.xbkj.common.jdbc.framework.mapping;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class AttributeMapping {

    Map<String,String>  meta= new HashMap<String,String>();
    Map<String,String> columnMeta=new HashMap<String,String>();

    
    
    public void addMapping(String attributeName, String columnName) {
        if (attributeName == null || columnName == null)
            throw new IllegalArgumentException("参数为空~");

        meta.put(attributeName, columnName.toLowerCase());
        columnMeta.put(columnName.toLowerCase(),attributeName);

    }

   public void addMapping(String []attributes,String[] columns)
   {
	   if (attributes.length != columns.length)
           throw new IllegalArgumentException("参数长度不一致~");
       for (int i = 0; i < attributes.length; i++) {
            addMapping(attributes[i], columns[i]);
       }
   }    

    public void removeMapping(String attributeName) {
        meta.remove(attributeName.toLowerCase());
        columnMeta.remove(getColumnName(attributeName));
    }

    public void clearMapping() {
        meta.clear();
        columnMeta.clear();
    }

    /*
     * ���� Javadoc��
     * 
     * @see nc.jdbc.framework.mapping.AttributeMapping#getColumns()
     */
    public String[] getColumns() {
        Collection<String> Columns = meta.values();
        return Columns.toArray(new String[Columns.size()]);
    }

    /*
     * ���� Javadoc��
     * 
     * @see nc.jdbc.framework.mapping.AttributeMapping#getAttributes()
     */
    public String[] getAttributes() {
        Set<String> attributes = meta.keySet();
        return attributes.toArray(new String[attributes.size()]);
    }

    /*
     * ���� Javadoc��
     * 
     * @see nc.jdbc.framework.mapping.AttributeMapping#getAttributeName(java.lang.String)
     */
    public String getAttributeName(String columnName) {
        return (String)columnMeta.get(columnName.toLowerCase());
    }

    /*
     * ���� Javadoc��
     * 
     * @see nc.jdbc.framework.mapping.AttributeMapping#getColumnName(java.lang.String)
     */
    public String getColumnName(String attributeName) {
        return (String) meta.get(attributeName);
    }

    public void modifyMapping(String attribute, String column) {
        meta.put(attribute, column.toLowerCase());
        columnMeta.put(column.toLowerCase(),attribute);

    }

    public boolean isValid() {
        return meta.size() > 0;
    }

}
