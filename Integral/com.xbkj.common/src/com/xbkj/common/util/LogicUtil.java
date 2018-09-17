package com.xbkj.common.util;

import com.eos.das.entity.criteria.CriteriaType;
import com.eos.foundation.data.DataObjectUtil;
import com.eos.foundation.database.DatabaseExt;
import com.eos.foundation.database.DatabaseUtil;
import com.eos.system.annotation.Bizlet;
import com.primeton.access.client.impl.UUIDGenerator;
import commonj.sdo.DataObject;
import java.io.PrintStream;


public class LogicUtil
{
  @Bizlet("逻辑删除表数据,设置dr状态")
  public DataObject[] setDrDel(DataObject[] data)
  {
    for (int i = 0; i < data.length; i++) {
      data[i].set("dr", Character.valueOf('Y'));
      data[i].set("ts", DateUtil.getCurrDateTime());
    }
    return data;
  }


  public void saveEntityOneToOne(String dsName, String pkMainFild, String fkMainFild, DataObject mainDO, DataObject[] childDOArr)
  {
    DatabaseExt.updateEntityCascade(dsName, mainDO, new String[0]);
  }

  
  public DataObject[] structureTreeQuery(String dsName, CriteriaType criteria, String treeNameField, String treeCodeField, String treePkField, String rootName)
  {
    DataObject[] dataArr = DatabaseUtil.queryEntitiesByCriteriaEntity(dsName, criteria);
    DataObject[] newDataArr = null;
    if (ToolUtil.isNullArr(dataArr))
      newDataArr = new DataObject[1];
    else {
      newDataArr = new DataObject[dataArr.length + 1];
    }

    newDataArr[0] = DataObjectUtil.createDataObject(criteria.get_entity());
    newDataArr[0].set(treeNameField, rootName);
    newDataArr[0].set(treePkField, "root");

    if (!ToolUtil.isNullArr(dataArr)) {
      for (int i = 0; i < dataArr.length; i++) {
        Object code = dataArr[i].get(treeCodeField);
        Object name = dataArr[i].get(treeNameField);
        dataArr[i].set(treeNameField, code + "\t" + name);
        dataArr[i].set("desc", name);
        if (ToolUtil.isNull(dataArr[i].get("fatherPk"))) {
          dataArr[i].set("fatherPk", "root");
        }
        newDataArr[(i + 1)] = dataArr[i];
      }
    }
    return newDataArr;
  }


  public DataObject[] structureTreeQuery(String dsName, CriteriaType criteria, String treeNameField, String treeCodeField, String treePkField, String parentField, String rootName)
  {
    DataObject[] dataArr = DatabaseUtil.queryEntitiesByCriteriaEntity(dsName, criteria);
    DataObject[] newDataArr = null;
    if (ToolUtil.isNullArr(dataArr))
      newDataArr = new DataObject[1];
    else {
      newDataArr = new DataObject[dataArr.length + 1];
    }

    newDataArr[0] = DataObjectUtil.createDataObject(criteria.get_entity());
    newDataArr[0].set(treeNameField, rootName);
    newDataArr[0].set(treePkField, "root");

    if (!ToolUtil.isNullArr(dataArr)) {
      for (int i = 0; i < dataArr.length; i++) {
        Object code = dataArr[i].get(treeCodeField);
        Object name = dataArr[i].get(treeNameField);
        dataArr[i].set(treeNameField, code + " " + name);
        dataArr[i].set("desc", name);
        dataArr[i].set("treeCode", code);
        if (ToolUtil.isNull(dataArr[i].get(parentField))) {
          dataArr[i].set(parentField, "root");
        }
        newDataArr[(i + 1)] = dataArr[i];
      }
    }
    return newDataArr;
  }

  public DataObject[] initStructureTreeQuery(String dsName, CriteriaType criteria, String treeNameField, String treeCodeField, String treePkField, String parentField, String rootName) {
    DataObject[] dataArr = DatabaseUtil.queryEntitiesByCriteriaEntity(dsName, criteria);
    DataObject[] newDataArr = null;
    if (ToolUtil.isNullArr(dataArr))
      newDataArr = new DataObject[1];
    else {
      newDataArr = new DataObject[dataArr.length + 1];
    }

    newDataArr[0] = DataObjectUtil.createDataObject(criteria.get_entity());
    newDataArr[0].set(treeNameField, rootName);
    newDataArr[0].set(treePkField, Integer.valueOf(-1));

    if (!ToolUtil.isNullArr(dataArr)) {
      for (int i = 0; i < dataArr.length; i++) {
        Object code = dataArr[i].get(treeCodeField);
        Object name = dataArr[i].get(treeNameField);
        dataArr[i].set(treeNameField, code + " " + name);
        dataArr[i].set("desc", name);
        if (ToolUtil.isNull(dataArr[i].get(parentField))) {
          dataArr[i].set(parentField, Integer.valueOf(-1));
        }
        newDataArr[(i + 1)] = dataArr[i];
      }
    }
    return newDataArr;
  }

  @Bizlet("设置数据实体主键")
  public DataObject[] setDateObjArrPKs(DataObject[] dObjArr, String pkField)
  {
    for (int i = 0; i < dObjArr.length; i++) {
      if (ToolUtil.isNull(dObjArr[i].get(pkField))) {
        dObjArr[i].set(pkField, UUIDGenerator.generate());
      }
    }
    return dObjArr;
  }

  @Bizlet("设置数据实体主键")
  public DataObject setDateObjPK(DataObject dObj, String pkField)
  {
    if (ToolUtil.isNull(dObj.get(pkField))) {
      dObj.set(pkField, UUIDGenerator.generate());
    }
    return dObj;
  }

  public static void main(String[] args)
  {
    System.out.println();
  }
}