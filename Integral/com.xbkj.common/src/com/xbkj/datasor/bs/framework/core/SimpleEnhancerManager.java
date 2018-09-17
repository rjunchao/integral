package com.xbkj.datasor.bs.framework.core;


/**
 * 
 * @author �ι���
 *
 * Date: 2008-3-6
 */
public class SimpleEnhancerManager implements EnhancerManager {

	private Enhancer[] pres = {};

	private Enhancer[] posts = {};

	public SimpleEnhancerManager() {

	}

	public Enhancer[] getPreEnhancers() {
		return pres;
	}

	public Enhancer[] getPostEnhancers() {
		return posts;
	}

	public void addPreEnhancer(Enhancer enhancer) {
		pres = addEnhancer(pres, enhancer);
	}

	public void addPostEnhancer(Enhancer enhancer) {
		posts = addEnhancer(posts, enhancer);
	}

	public void removePreEnhancer(Enhancer enhancer) {
		pres = removeEnhancer(pres, enhancer);
	}

	public void removePostEnhancer(Enhancer enhancer) {

		posts = removeEnhancer(posts, enhancer);
	}

	private Enhancer[] addEnhancer(Enhancer[] es, Enhancer e) {
		if (e == null)
			return es;
		Enhancer[] nes = new Enhancer[es.length + 1];
		System.arraycopy(es, 0, nes, 0, es.length);
		nes[es.length] = e;
		return nes;
	}

	private Enhancer[] removeEnhancer(Enhancer[] es, Enhancer e) {
		if (e == null)
			return es;
		else {
			int pos = -1;
			for (int i = 0; i < es.length; i++) {
				if (e.equals(es[i])) {
					pos = i;
					break;
				}
			}

			if (pos == -1) {
				return es;
			} else {
				Enhancer[] nes = new Enhancer[es.length - 1];
				for (int i = 0; i < pos; i++) {
					nes[i] = es[i];
				}

				for (int i = pos + 1; i < es.length; i++) {
					nes[i - 1] = es[i];
				}
				return nes;
			}
		}
	}
}
