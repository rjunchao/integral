package com.pub.xbkj.common;

public class EnumConst {

	/****
	 * 审批流程状态
	 */
	public static enum WorkFlowStatus {
		
		NO_COMMIT("自由态", 0), COMMIT("提交态", 1), AUDIT("待审批", 2), AUDIT_PASS("审批通过", 3);

		private String name;
		private int index;

		private WorkFlowStatus(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public String getName() {
			return name;
		}

		public int getIndex() {
			return index;
		}

		public static String getName(int index) {
			for (WorkFlowStatus workFlowStatus : WorkFlowStatus.values()) {
				if (workFlowStatus.getIndex() == index) {
					return workFlowStatus.getName();
				}
			}
			return "---";
		}
	}
	
	public static enum FilePath{
		UPLOAD_PATH("\\fileUpload", 0);

		private String name;
		private int index;

		private FilePath(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public String getName() {
			return name;
		}

		public int getIndex() {
			return index;
		}

		public static String getName(int index) {
			for (FilePath filePath : FilePath.values()) {
				if (filePath.getIndex() == index) {
					return filePath.getName();
				}
			}
			return "---";
		}
	}

}