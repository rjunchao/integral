package com.xbkj.datasor.bs.framework.common;

import java.io.Serializable;
 
/**
 * Created by UFSoft.
 * User: �ι���
 * Date: 2005-1-22
 * Time: 15:12:06
 * 
 * the meta info of the component. it now only contains the information for remote call.
 * it may be extended to support more semantics
 */
public class ComponentMetaVO implements Serializable {

    private static final long serialVersionUID = -6772182283831182886L;

    private String name;

    private String[] alias;

    private String[] itfs;

    private String module;

    public ComponentMetaVO() {

    }

    /**
     * construct with name,alias and the interface infromations
     * @param name
     * @param alias
     * @param itfs
     */
    public ComponentMetaVO(String module, String name, String[] alias, String[] itfs) {
        this.module = module;
        this.name = name;
        this.alias = alias;
        this.itfs = itfs;
    }

    /**
     * construct with name,alias and the interface classes
     * 
     * @param name
     * @param alias
     * @param interfaces
     */
    public ComponentMetaVO(String module, String name, String[] alias, Class[] interfaces) {
        this.module = module;
        this.name = name;
        this.alias = alias;
        itfs = new String[interfaces.length];

        for (int i = 0; i < itfs.length; i++) {
            itfs[i] = interfaces[i].getName();
        }
    }
    
    public ComponentMetaVO(String name, String[] alias, Class[] interfaces) {
        this(null, name, alias, interfaces);
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAlias(String[] alias) {
        this.alias = alias;
    }

    public String[] getAlias() {
        return alias;
    }

    public String[] getInterfaces() {
        return itfs;
    }

    public void setInterfaces(String interfaces[]) {
        this.itfs = interfaces;
    }

    public String toString() {
        if (module != null)
            return module + "/" + name;
        else
            return name;
    }

    public int hashCode() {
        if (module != null)
            return module.hashCode() + 27 * name.hashCode();
        else
            return name.hashCode();
    }

    /**
     * @return Returns the module.
     */
    public String getModule() {
        return module;
    }

    /**
     * @param module The module to set.
     */
    public void setModule(String module) {
        this.module = module;
    }

}
