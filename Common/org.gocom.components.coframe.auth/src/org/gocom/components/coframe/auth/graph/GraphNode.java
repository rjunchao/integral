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
package org.gocom.components.coframe.auth.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于图形化展示的节点
 * 
 * @author caozw (mailto:caozw@primeton.com)
 */
public class GraphNode {
	private String id;

	private String type;

	private String name;

	private int level;

	private List<String> connections = new ArrayList<String>();

	public List<String> getConnections() {
		return connections;
	}

	public void setConnections(List<String> connections) {
		this.connections = connections;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void addTargetId(String targetId) {
		this.connections.add(targetId);
	}
}
