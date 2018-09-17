package com.xbkj.datasor.bs.framework.core;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: 2005-1-22
 * Time: 16:27:47
 *
 * Represented the server status
 */
public enum State {
    NOT_INIT("not_init"), INITING("initing"), INITED("stoped"), STARTING("starting"), RUNING("running"), STOPPING(
            "stopping"), STOPPED("stopped");

    private String value;

    private State(String s) {
        value = s;
    }

    public String toString() {
        return value;
    }
}
