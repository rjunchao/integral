package com.xbkj.datasor.bs.framework.core;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import com.grc.datasor.bs.framework.exception.DeployException;
import com.grc.datasor.bs.framework.util.Messages;
import com.grc.log.bs.logging.Log;

/**
 * 
 * Created by UFSoft.
 * User: �ι���
 *
 * Date: 2007-12-19
 * Time: ����09:50:39
 *
 */
public abstract class AbstractDeployer implements Deployer {

    protected static Log logger = Log.getInstance("nc.bs.framework.server.deploy");

    protected Server server;

    public AbstractDeployer(Server appServer) {
        this.server = appServer;
    }

    public void deploy(URL[] urls) throws DeployException {
        for (URL url : urls) {
            if (server.getContainer(url) != null) {
                throw new DeployException(String.format(Messages.dupModule, url));
            }
            File md = new File(url.getFile());
            Container c = newContainer(url, md);
            if (c != null) {
                server.addContainer(c);
            }
        }
    }

    public void undeploy(URL[] urls) throws DeployException {
        for (URL url : urls) {
            server.removeContainer(url);
        }
    }

    public void update(URL[] urls) throws DeployException {
        for (URL url : urls) {
            Container c = server.getContainer(url);
            if (c instanceof Updatable) {
                ((Updatable) c).update();
            }
        }
    }

    abstract protected Container newContainer(URL url, File md);

    public void start(URL[] urls) throws DeployException {
        ArrayList<Container> cl = getContainers(urls);
        //        ArrayList<URL> successUrls = new ArrayList<URL>();

        Collections.sort(cl, new ContainerComparator());
        for (Container c : cl) {
            try {
                logger.debug(String.format(Messages.bfOpModule, "start", c.getName()));
                c.start();
                //                successUrls.add(c.getUrl());
                logger.debug(String.format(Messages.afOpModule, "start", c.getName()));
            } catch (Exception exp) {
                logger.error(String.format(Messages.startErr, c.getName()), exp);
            }
        }

        //        return successUrls.toArray(new URL[successUrls.size()]);

    }

    public void stop(URL[] urls) throws DeployException {
        ArrayList<Container> cl = getContainers(urls);
        //        ArrayList<URL> successUrls = new ArrayList<URL>();

        Collections.sort(cl, new ContainerComparator(false));

        for (Container c : cl) {
            try {
                logger.debug(String.format(Messages.bfOpModule, "stop", c.getName()));
                c.stop();
                //                successUrls.add(c.getUrl());
                logger.debug(String.format(Messages.afOpModule, "stop", c.getName()));
            } catch (Exception exp) {
                logger.error(String.format(Messages.stopErr, c.getName()), exp);
            }
        }
        //        return successUrls.toArray(new URL[successUrls.size()]);

    }

    private ArrayList<Container> getContainers(URL[] urls) {
        ArrayList<Container> ml = new ArrayList<Container>();
        for (int i = 0; i < urls.length; i++) {
            Container c = server.getContainer(urls[i]);
            if (c != null) {
                ml.add(c);
            }
        }
        return ml;
    }

}
