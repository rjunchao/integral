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
import java.util.LinkedHashMap;
import java.util.List;
/**
 * 
 * 授权时的模块，该处存放一个资源树，内容有表单和视图
 *
 * @author caozw (mailto:caozw@primeton.com)
 */
public class DirModule {

	String id = null;

	String name = null;

	LinkedHashMap<String, DirResource> view = new LinkedHashMap<String, DirResource>();

	LinkedHashMap<String, DirResource> form = new LinkedHashMap<String, DirResource>();

	public DirModule(String id, String name) {
		this.id = id;
		this.name = name;

	}


	public DirModule() {
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
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
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
		final DirModule other = (DirModule) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public void deleteView(DirResource r) {
		view.remove(r.getId());
	}
	
	public void deleteForm(DirResource r) {
		form.remove(r.getId());
	}
	
	public void deleteView(String viewId) {
		view.remove(viewId);
	}

	public void deleteForm(String formId) {
		form.remove(formId);
	}

	public void addView(DirResource r) {
		view.put(r.getId(), r);
	}

	public void addForm(DirResource r) {
		form.put(r.getId(), r);
	}

	public DirResource getView(String viewId) {
		return view.get(viewId);
	}

	public DirResource getForm(String formId) {
		return form.get(formId);
	}

	public List<DirResource> fetchViewList() {
		return new ArrayList<DirResource>(view.values());
	}
	
	public List<DirResource> fetchFormList() {
		return new ArrayList<DirResource>(form.values());
	}
	
}