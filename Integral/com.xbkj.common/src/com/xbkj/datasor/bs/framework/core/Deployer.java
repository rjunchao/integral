package com.xbkj.datasor.bs.framework.core;

import java.io.InputStream;
import java.net.URL;

import com.xbkj.datasor.bs.framework.exception.DeployException;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: 2005-1-22
 * Time: 16:18:28
 */
public interface Deployer {

    public void deploy(URL[] urls) throws DeployException;

    public void undeploy(URL[] urls) throws DeployException;

    public void update(URL[] urls) throws DeployException;

    public void start(URL[] urls) throws DeployException;

    public void stop(URL[] urls) throws DeployException;

    public void deploy(String flag, Container c, InputStream input) throws DeployException;

}
