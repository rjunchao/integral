/*
 * Copyright 2013 Primeton.
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
package org.gocom.components.coframe.resource.view;

import java.util.List;
import java.util.Map;

/**
 * Form权限树服务
 *
 * @author liuzn (mailto:liuzn@primeton.com)
 */
public interface IViewService {

	/**
	 * 制作模块视图树
	 * 
	 * @return 返回全部的模块及其下视图、状态和控件的树形结构
	 */
	public List<Map<String, Object>> getViewTree();

}
