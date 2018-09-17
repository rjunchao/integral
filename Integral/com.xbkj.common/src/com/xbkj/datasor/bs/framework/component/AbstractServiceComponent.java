package com.xbkj.datasor.bs.framework.component;

import com.xbkj.log.bs.logging.Log;

/**
 * Created by UFSoft.
 * User: �ι���
 * Date: 2005-1-22
 * Time: 15:12:06
 */
public abstract class AbstractServiceComponent extends AbstractContextAwareComponent implements ServiceComponent {
    boolean bStarted;

    public boolean isStarted() {
        return bStarted;
    }

    public void setStarted(boolean bStarted) {
        this.bStarted = bStarted;
    }

    protected Log getLog() {
        return Log.getInstance(getClass());
    }

    public void start() throws Exception {
        setStarted(true);
    }

    public void stop() throws Exception {
        setStarted(false);
    }

}
