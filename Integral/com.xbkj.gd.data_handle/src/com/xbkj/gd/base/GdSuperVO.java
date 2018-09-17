package com.xbkj.gd.base;

import com.xbkj.basic.vo.pub.SuperVO;

/**
 *@author rjc
 *@email rjc@ronhe.com.cn
 *@date 2017-9-5
 *@version 1.0.0
 *@desc
 */
public abstract class GdSuperVO extends SuperVO{

	// 普元前端默认生成字段
	
		/**
	 * 
	 */
	private static final long serialVersionUID = 1104412772436206043L;
	// 公共字段
		private String createtime;// 创建时间
		private String modifier;// 修改人
		private String modifiedtime;// 修改时间
		private String dr;
		private String def1;// 修改时间
		private String def2;// 
		private String def3;// 
		private String def4;// 
		private String def5;// 
		private String def6;// 
		private String def7;// 
		private String def8;// 
		private String def9;// 
		private String def10;// 
		

		// 普元前端默认生成字段
		private String _id;
		private String _pid;
		private String _state;
		private String _uid;
		private String _editing;
		private String expanded;
		private String entityName;
		private String tableName;
		private String parentPKFieldName;
		private String pKFieldName;
		private String PKFieldName;
		private String _level;
		private String checked;
		private String flag;
		
		private String empname;
		private String orgname;
		
		public String get_id() {
			return _id;
		}
		public void set_id(String _id) {
			this._id = _id;
		}
		public String get_pid() {
			return _pid;
		}
		public void set_pid(String _pid) {
			this._pid = _pid;
		}
		public String get_state() {
			return _state;
		}
		public void set_state(String _state) {
			this._state = _state;
		}
		public String get_uid() {
			return _uid;
		}
		public void set_uid(String _uid) {
			this._uid = _uid;
		}
		public String get_editing() {
			return _editing;
		}
		public void set_editing(String _editing) {
			this._editing = _editing;
		}
		public String getExpanded() {
			return expanded;
		}
		public void setExpanded(String expanded) {
			this.expanded = expanded;
		}
		public String getEntityName() {
			return entityName;
		}
		public void setEntityName(String entityName) {
			this.entityName = entityName;
		}
		
		public void setTableName(String tableName) {
			this.tableName = tableName;
		}
		
		public void setParentPKFieldName(String parentPKFieldName) {
			this.parentPKFieldName = parentPKFieldName;
		}
		public String getpKFieldName() {
			return pKFieldName;
		}
		public void setpKFieldName(String pKFieldName) {
			this.pKFieldName = pKFieldName;
		}
		
		public void setPKFieldName(String pKFieldName) {
			PKFieldName = pKFieldName;
		}
		public String get_level() {
			return _level;
		}
		public void set_level(String _level) {
			this._level = _level;
		}
		public String getChecked() {
			return checked;
		}
		public void setChecked(String checked) {
			this.checked = checked;
		}
		public String getFlag() {
			return flag;
		}
		public void setFlag(String flag) {
			this.flag = flag;
		}
		public String getCreatetime() {
			return createtime;
		}
		public void setCreatetime(String createtime) {
			this.createtime = createtime;
		}
		public String getModifier() {
			return modifier;
		}
		public void setModifier(String modifier) {
			this.modifier = modifier;
		}
		public String getModifiedtime() {
			return modifiedtime;
		}
		public void setModifiedtime(String modifiedtime) {
			this.modifiedtime = modifiedtime;
		}
		public String getEmpname() {
			return empname;
		}
		public void setEmpname(String empname) {
			this.empname = empname;
		}
		public String getOrgname() {
			return orgname;
		}
		public void setOrgname(String orgname) {
			this.orgname = orgname;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		
		public String getDef1() {
			return def1;
		}
		public void setDef1(String def1) {
			this.def1 = def1;
		}
		public String getDef2() {
			return def2;
		}
		public void setDef2(String def2) {
			this.def2 = def2;
		}
		public String getDef3() {
			return def3;
		}
		public void setDef3(String def3) {
			this.def3 = def3;
		}
		public String getDef4() {
			return def4;
		}
		public void setDef4(String def4) {
			this.def4 = def4;
		}
		
		public String getDef5() {
			return def5;
		}
		public void setDef5(String def5) {
			this.def5 = def5;
		}
		public String getDef6() {
			return def6;
		}
		public void setDef6(String def6) {
			this.def6 = def6;
		}
		public String getDef7() {
			return def7;
		}
		public void setDef7(String def7) {
			this.def7 = def7;
		}
		public String getDef8() {
			return def8;
		}
		public void setDef8(String def8) {
			this.def8 = def8;
		}
		public String getDef9() {
			return def9;
		}
		public void setDef9(String def9) {
			this.def9 = def9;
		}
		public String getDef10() {
			return def10;
		}
		public void setDef10(String def10) {
			this.def10 = def10;
		}
		public String getDr() {
			return dr;
		}
		
		public void setDr(String dr) {
			this.dr = dr;
		}
}
