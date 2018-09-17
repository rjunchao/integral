package org.gocom.components.coframe.resource.util;

import org.gocom.components.coframe.tools.tab.TabPageManager;

import com.eos.runtime.resource.IContributionEvent;
import com.eos.runtime.resource.IContributionListener;
/**
 * 加载资源的监听器
 * @author caozw@primeton.com
 *
 */
public class ResourceContributionListener implements IContributionListener {

	public void load(IContributionEvent arg0) {

	}

	public void loadFinished(IContributionEvent event) {
		TabPageManager.INSTANCE.loadTabPage("AuthTab", event
				.getContributionConfiguration());

	}

	public void unLoad(IContributionEvent arg0) {
	}

}
