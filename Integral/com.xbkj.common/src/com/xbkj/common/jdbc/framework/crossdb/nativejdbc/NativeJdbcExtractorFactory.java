package com.xbkj.common.jdbc.framework.crossdb.nativejdbc;

import com.xbkj.datasor.bs.framework.common.RuntimeEnv;

/**
 * @nopublish
 * @author hey
 * @author hgy
 *
 */
public class NativeJdbcExtractorFactory {

	private static NativeJdbcExtractor nativeJdbcExtractor = null;

	static public NativeJdbcExtractor createJdbcExtractor() {

		if (nativeJdbcExtractor == null) {
			synchronized (NativeJdbcExtractorFactory.class) {
				if (nativeJdbcExtractor == null) {
					if (RuntimeEnv.isRunningInWebSphere()) {
						nativeJdbcExtractor = new WebSphereNativeJdbcExtractor();
					} else if(RuntimeEnv.isRunningInWeblogic()) {
                        nativeJdbcExtractor = new WeblogicNativeJdbcExtractor();
                    }else {
						nativeJdbcExtractor = new NCNativeJDBCExtractor();
					}
				}
			}
		}

		return nativeJdbcExtractor;
	}
}
