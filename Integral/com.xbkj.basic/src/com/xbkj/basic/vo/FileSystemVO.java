package com.xbkj.basic.vo;

import com.xbkj.basic.vo.pub.XbkjSuperVO;

/**
 *@author xjm
 *@email xjm@ronhe.com.cn
 *@date 2016-8-30
 *@version 1.0.0
 *@desc 附件实体类
 */
public class FileSystemVO extends XbkjSuperVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String pk_filesystem;//附件主键
	private String parent_file_id;//上级附件id
	private String belong_to;//所属id
	private String file_name;//附件名
	private String is_folder;//是否为文件夹  Y是  N否
	
	private String creator;//创建人
	private String path;//附件逻辑路径
	private String content_data;//附件内容
	private int file_length;//附件大小
	private String time;//创建时间
	private String ts; // 时间戳
	private int dr; // 删除标志位
	
	public static final String PK_FILESYSTEM = "pk_filesystem";
	public static final String PARENT_FILE_ID = "parent_file_id";
	public static final String BELONG_TO = "belong_to";
	public static final String FILE_NAME = "file_name";
	public static final String IS_FOLDER = "is_folder";
	
	public static final String CREATOR = "creator";
	public static final String PATH = "path";
	public static final String CONTENT_DATA = "content_data";
	public static final String FILE_LENGTH = "file_length";
	public static final String TIME = "time";
	public static final String TS = "ts";
	public static final String DR = "dr";

	@Override
	public String getParentPKFieldName() {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public String getPKFieldName() {
		// TODO 自动生成的方法存根
		return "pk_filesystem";
	}

	@Override
	public String getTableName() {
		// TODO 自动生成的方法存根
		return "grc_filesystem";
	}

	//setter_getter
	public String getPk_filesystem() {
		return pk_filesystem;
	}

	public void setPk_filesystem(String pk_filesystem) {
		this.pk_filesystem = pk_filesystem;
	}

	public String getParent_file_id() {
		return parent_file_id;
	}

	public void setParent_file_id(String parent_file_id) {
		this.parent_file_id = parent_file_id;
	}

	public String getBelong_to() {
		return belong_to;
	}

	public void setBelong_to(String belong_to) {
		this.belong_to = belong_to;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getIs_folder() {
		return is_folder;
	}

	public void setIs_folder(String is_folder) {
		this.is_folder = is_folder;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getContent_data() {
		return content_data;
	}

	public void setContent_data(String content_data) {
		this.content_data = content_data;
	}


	public int getFile_length() {
		return file_length;
	}

	public void setFile_length(int file_length) {
		this.file_length = file_length;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public int getDr() {
		return dr;
	}

	public void setDr(int dr) {
		this.dr = dr;
	}

	@Override
	public String toString() {
		return "FileSystemVO [pk_filesystem=" + pk_filesystem
				+ ", parent_file_id=" + parent_file_id + ", belong_to="
				+ belong_to + ", file_name=" + file_name + ", is_folder="
				+ is_folder + ", creator=" + creator + ", path=" + path
				+ ", content_data=" + content_data + ", file_length="
				+ file_length + ", time=" + time + ", ts=" + ts + ", dr=" + dr
				+ "]";
	}
	
	
	
}
