package com.xbkj.datasor.bs.framework.util;

/**
 * 
 * Created by UFSoft.
 * User: �ι���
 *
 * Date: 2007-11-22
 * Time: ����11:36:10
 *
 */
public final class Messages {

    public static final String populateErr = "populate component property: %s withe value: %s error";

    public static final String populateErrCause = "populate component property: %s with value: %s error: %s";

    public static final String resoleRefErr = "populate component property: %s with reference: %s error";

    public static final String resolveRefErrCause = "populate component property: %s with reference: %s error: %s";

    public static final String noCtorFound = "class: %s has no constoructor mach: %s";

    public static final String noFm = "public %s factory method: %s with arg type: %s is not found in class: %s";

    public static final String nfdInMthds = "no appropriate %s method found in [%s]";

    public static final String instErr = " invoke factory method: %s in class: %s error, args type: %s";

    public static final String instErrWithCtor = " %s construct error with arg type: %s";

    public static final String noSvrFnd = "server with name:  %s is not found";

    public static final String nfdMeta = "The component(meta): %s is not found in %s %s";

    public static final String nfdTxCmnt = "The tx component: %s is not found in %s %s}";

    public static final String notSupportBO = "The BO(3.x) is not supported, please rewrite the component %s";

    public static final String newObjErr = "Instantiate object error, class: %s, module: <%s>";

    public final static String notExistMsg = "%s: %s is not exist";

    public final static String notDirMsg = "%s: %s is  not a directory";

    public final static String initErrMsg = "ESA server has been inited or is initing now, current thread: %s";

    public final static String cantConvert = "can't convert %s" + " to %s";

    public static final String ignoreRemote = "ignore remote attribute of private component: %s";

    public static final String ignoreSingleton = "ignore singleton attribute for component inherited from Singleton: %s";

    public static final String notInterface = "%s is not a interface";

    public static final String abstractImpl = "implementation class: %s is abstact";

    public static final String errCmntState = "error component state: %s";

    public static final String errInstFromFc = "%s getComponent error(FactoryComponent)";

    public static final String duplicateErr = "component alread exist at module : %s";

    public static final String nfdModule = "not found module: %s";

    public static final String bfOpModule = "before %s module: %s";

    public static final String afOpModule = "after %s module: %s";

    public static final String opModuleErr = " %s module  %s error";

    public static final String updLoader = "update classloader with url %s";

    public static final String dupModule = "duplicate module: %s";

    public static final String dupModuleDetail = "%s has the same module name with module at: %s";

    public static final String deployErr = "deploy error: %s";

    public static final String deployModuleErr = "deploy: %s error, some modules may not work properly";

    public static final String deployCmpErr = "deploy component: %s at module: %s error";

    public static final String invalidHierarchy = "%s dose not implement the interface %s";

    public static final String invalidConcreteClass = "%s is not an concrete implementation";

    public static final String gpmErr = "parse file <%s> error when deploy module <%s>";

    public static final String startErr = "start <%s> error";

    public static final String stopErr = "stop <%s> error";

    public static final String beginMethodFormat = "begin method: %s, on target: %s with args: %s ";
    //TODO:NEED HGY AUDIT.
    public static final String endMethodFormat = "end method: %s, on target: %s with args: %s, |costtime=%d|";

//    public static final String endMethodFormat = "end method: %s, on target: %s with args: %s, spend time: %d";

    public static final String errorEndMethodFormat = "error end method: %s, on target: %s with args: %s, spend time: %d";

    public static final String invokeExpMsgFormat = "invoke ejb method(Remote Component) un expected exception, %s on target: %s with args:  %s";

    public static final String invokeErrorMsgFormat = "invoke ejb method(Remote Component) error, %s on target: %s with args:  %s, "
            + "free memory: %d , total memory: %d";

    public static final String stateCantStart = " %s not  inited or is running";

    public static final String lflErr = "life cycle listener callback  %s error: %s ";

    public static final String alreadStop = "conteiner: %s alread stopped";

    public static final String cbStop = "conteiner: %s begin stop...";

    public static final String ceStop = "conteiner: %s end stop";

    public static final String sbStop = "service component: %s at container: %s begin stop...";

    public static final String seStop = "service component: %s at container: %s end stop";
   
    public static final String sstopErr = "service component: %s at container: %s stop error";
    
    public static final String sbStart = "service component: %s at container: %s begin start...";

    public static final String seStart = "service component: %s at container: %s end start";
   
    public static final String sstartErr = "service component: %s at container: %s tart error";
    
    public static final String epStartErr = "extension process for component: %s at container: %s error when start";
    
    public static final String epCmntStartErr = "extension process for component: %s at container: %s error before component start";
    
    public static final String epStopErr = "extension process for component: %s at container: %s error when stop";
    
    public static final String epCmntStopErr = "extension process for component: %s at container: %s error after component stop";

    public static final String errAt = "info: %s, error at %s <%s>, catch root <%s>";
    
    public static final String preDestoryErr = "PreDestroy error for  component: %s at container: %s";
    
    public static final String postConstructErr = "PostContruct error for  component: %s at container: %s";
    
    public static final String resNameNotSpec = "class level(%s) resource name not specified for component: %s, at container: %s ";
    
    public static final String resNotFind = "resource: %s not resolved for component: %s, at container: %s ";
    
    public static final String resNotAsType = " resource %s is not type of %s";
    
    public static final String resInjectError = "resource: %s inject error for component: %s, at container: %s ";
     
    
}
