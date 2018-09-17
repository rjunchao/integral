package com.xbkj.datasor.bs.framework.component;


/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: 2005-1-15
 * Time: 11:10:55
 *
 * the singletonObj worked as an active service
 *
 * <br/>
 * the idear form yangyong
 */
public interface ServiceComponent extends ActiveComponent {
    /**
     * startModule the service and allocate resources
     *
     * @throws Exception
     */
    public void start() throws Exception;

    /**
     * stopModule the service and release resources
     *
     * @throws Exception
     */
    public void stop() throws Exception;

    /**
     * check whether the service is started
     */
    public boolean isStarted();
}
