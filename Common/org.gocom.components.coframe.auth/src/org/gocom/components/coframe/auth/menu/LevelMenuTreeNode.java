/*
 * Copyright 2013 Primeton Technologies Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gocom.components.coframe.auth.menu;

import com.primeton.cap.auth.MenuTree.MenuTreeNode;

/**
 * TODO 此处填写 class 信息
 * 
 * @author caozw (mailto:caozw@primeton.com)
 */
public class LevelMenuTreeNode extends MenuTreeNode {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	private int level;

	private String menuSeq;

	private String menuPrimeKey;

	private String functionCode;

	private int displayOrder;

	public String getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getMenuPrimeKey() {
		return menuPrimeKey;
	}

	public void setMenuPrimeKey(String menuPrimeKey) {
		this.menuPrimeKey = menuPrimeKey;
	}

	public String getMenuSeq() {
		return menuSeq;
	}

	public void setMenuSeq(String menuSeq) {
		this.menuSeq = menuSeq;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

}
