package com.xbkj.datasor.bs.framework.core;

import java.util.Comparator;

/**
 * 
 * Created by UFSoft.
 * User: �ι���
 *
 * Date: 2007-11-13
 * Time: ����01:26:09
 *
 */
public class PriorityComparator implements Comparator<Priority> {

	private boolean asc;

	public PriorityComparator() {
		this(true);
	}

	public PriorityComparator(boolean asc) {
		this.asc = asc;
	}

	public int compare(Priority p, Priority pother) {
		if (asc) {
			return p.getPriority() - pother.getPriority();
		} else {
			return pother.getPriority() - p.getPriority();

		}
	}

}