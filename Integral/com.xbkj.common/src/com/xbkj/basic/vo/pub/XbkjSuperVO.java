package com.xbkj.basic.vo.pub;

/**
 * @author lwl
 * @date
 * @version 1.0.0
 */
public abstract class XbkjSuperVO extends SuperVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3813382785218132596L;

	// 公共字段
	private String ts; // 时间戳
	private int dr; // 删除标志位
	private String creater;// 创建人
	private String createtime;// 创建时间
	private String modifier;// 修改人
	private String modifiedtime;// 修改时间

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
	private String flowName;//对应制度流程名称
	private String eventtypeName;//损失事件类型名称
	private String classifyName;//风险成因分类名称
	private String riskIncidence;//风险发生概率
	private String riskEffectLevel;//风险影响程度
	private String controlDesign;//控制执行有效性
	private String controlExec;//控制执行有效性
	
	
	/**
	 * 公共属性
	 */
	public static final String CREATER = "creater";
	public static final String CREATETIME = "createtime";
	public static final String MODIFIER = "modifier";
	public static final String MODIFIEDTIME = "modifiedtime";
	public static final String TS = "ts";
	public static final String DR = "dr";

	public String getFlag() {
		return flag;
	}
	
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public String getChecked() {
		return checked;
	}
	
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String get_pid() {
		return _pid;
	}

	public void setExpanded(String expanded) {
		this.expanded = expanded;
	}
	
	public String getExpanded() {
		return expanded;
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
	
	public void setpKFieldName(String pKFieldName) {
		this.pKFieldName = pKFieldName;
	}

	public int getDr() {
		return dr;
	}

	public void setDr(int dr) {
		this.dr = dr;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getTs() {
		return ts;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
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

	public void set_level(String _level) {
		this._level = _level;
	}

	public String get_level() {
		return _level;
	}
	
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	
	public String getOrgname() {
		return orgname;
	}
	
	public String getEmpname() {
		return empname;
	}
	
	public void setPKFieldName(String PKFieldName) {
		this.PKFieldName = PKFieldName;
	}
	
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	
	public String getFlowName() {
		return flowName;
	}
	public String getEventtypeName() {
		return eventtypeName;
	}

	public void setEventtypeName(String eventtypeName) {
		this.eventtypeName = eventtypeName;
	}
	
	public String getClassifyName() {
		return classifyName;
	}
	
	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}
	
	public void setRiskEffectLevel(String riskEffectLevel) {
		this.riskEffectLevel = riskEffectLevel;
	}
	public String getRiskEffectLevel() {
		return riskEffectLevel;
	}
	
	public void setControlDesign(String controlDesign) {
		this.controlDesign = controlDesign;
	}
	
	public String getControlDesign() {
		return controlDesign;
	}
	
	public void setControlExec(String controlExec) {
		this.controlExec = controlExec;
	}
	
	public String getControlExec() {
		return controlExec;
	}
	public void setRiskIncidence(String riskIncidence) {
		this.riskIncidence = riskIncidence;
	}
	public String getRiskIncidence() {
		return riskIncidence;
	}
}
