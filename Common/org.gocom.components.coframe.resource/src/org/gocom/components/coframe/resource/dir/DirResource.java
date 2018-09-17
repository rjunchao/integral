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
package org.gocom.components.coframe.resource.dir;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * 资源树节点的基础类
 *
 * @author caozw (mailto:caozw@primeton.com)
 */
public class DirResource {

	String status = null;

	String type = null;

	String id = null;

	String name = null;

	String moduleId = null;

	List<DirResource> child = new ArrayList<DirResource>();

	public DirResource() {

	}

	public DirResource(String moduleId, String id, String type, String name) {
		this.id = id;
		this.type = type;
		this.name = name;
		this.moduleId = moduleId;
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	public void addChild(DirResource r) {
		child.add(r);
	}
	
	public List<DirResource> fetchChildList()
	{
		return child;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result
				+ ((moduleId == null) ? 0 : moduleId.hashCode());
		result = PRIME * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final DirResource other = (DirResource) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (moduleId == null) {
			if (other.moduleId != null)
				return false;
		} else if (!moduleId.equals(other.moduleId))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	/**
	 * @return Returns the moduleId.
	 */
	public String getModuleId() {
		return moduleId;
	}

	/**
	 * @param moduleId
	 *            The moduleId to set.
	 */
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public List<DirResource> getChild() {
		return child;
	}

	public void setChild(List<DirResource> child) {
		this.child = child;
	}

}