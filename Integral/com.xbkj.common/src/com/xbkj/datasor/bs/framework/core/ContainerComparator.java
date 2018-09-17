package com.xbkj.datasor.bs.framework.core;

import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: 2005-1-24
 * Time: 10:26:38
 */
public class ContainerComparator implements Comparator<Container> {
    public boolean asc;

    public ContainerComparator() {
        this(true);
    }

    public ContainerComparator(boolean asc) {
        this.asc = asc;
    }

    public int compare(Container m1, Container m2) {
        int retValue = m1.getPriority() - m2.getPriority();
        if (retValue == 0) {

            boolean nameWithUap1 = m1.getName().startsWith("uap");
            boolean nameWithUap2 = m2.getName().startsWith("uap");

            if (nameWithUap1 ^ nameWithUap2) {
                retValue = nameWithUap1 ? -1 : 1;
            } else {
                retValue = m1.getName().compareTo(m2.getName());
            }
        }
        if (asc) {
            return retValue;
        } else {
            return retValue * -1;
        }

    }
}
