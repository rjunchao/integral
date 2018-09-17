package com.xbkj.basic.vo.jcom.util;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.xbkj.log.bs.logging.Logger;

/**
 * Created by UFSoft. 
 * User: �ι��� 
 * Date: 2005-1-22 
 * Time: 15:12:06
 * 
 * This helper class maybe used by component and server
 */
public class ClassUtil {

    /**
     * empty class array
     */
    public final static Class[] EMPTY_CLASS_ARRAY = new Class[0];

    /**
     * array class  suffiex
     */
    public static final String ARRAY_SUFFIX = "[]";

    /**
     * the primitive class list
     */
    private static Class[] PRIMITIVE_CLASSES = { boolean.class, byte.class, char.class, short.class, int.class,
            long.class, float.class, double.class };

    /**
     * package separator
     */
    private static final char PACKAGE_SEPARATOR_CHAR = '.';

    /**
     * inner class separator
     */
    private static final char INNER_CLASS_SEPARATOR_CHAR = '$';

    /**
     * get the thread context classloader
     * @return
     */
    public static ClassLoader getContextClassLoader() {
        return (ClassLoader) AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
            public ClassLoader run() {
                return Thread.currentThread().getContextClassLoader();
            }
        });

    }

    /**
     * load a class specified by name. it extends to support load the array class
     * 
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    public static Class loadClass(final String name) throws ClassNotFoundException {
        if (name.length() <= 8) {
            for (int i = 0; i < PRIMITIVE_CLASSES.length; i++) {
                Class clazz = PRIMITIVE_CLASSES[i];
                if (clazz.getName().equals(name)) {
                    return clazz;
                }
            }
        }

        if (name.endsWith(ARRAY_SUFFIX)) {
            String elementClassName = name.substring(0, name.length() - ARRAY_SUFFIX.length());
            Class elementClass = loadClass(elementClassName);
            return Array.newInstance(elementClass, 0).getClass();
        }

        Class result = null;
        try {
            result = Class.forName(name);
        } catch (ClassNotFoundException e) {

        }

        if (result != null)
            return result;

        Object obj = AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
                ClassLoader threadCL = getContextClassLoader();
                if (threadCL != null) {
                    try {
                        return threadCL.loadClass(name);
                    } catch (ClassNotFoundException ex) {
                        return ex;
                    }
                }
                return null;

            }
        });

        if (obj instanceof Class) {
            return (Class) obj;
        } else {
            throw (ClassNotFoundException) obj;
        }

    }

    /**
     * get the class's interface but the excluded interfaces
     * 
     * @param clazz - the class which implemented some interfaces
     * @param excludes - the interfaces needed to be excluded
     * @return
     */
    public static Class[] getInterfaces(Class clazz, String[] excludes) {
        List<Class> interfaces = new ArrayList<Class>();
        interfaces = getAllInterfacesExclude(clazz, excludes, interfaces);
        return (Class[]) interfaces.toArray(new Class[interfaces.size()]);
    }

    /**
     * get all the interfaces of the class
     * 
     * @param clazz - the class which implemented some interfaces
     * @return
     */
    public static Class[] getInterfaces(Class clazz) {
        List<Class> interfaces = new ArrayList<Class>();
        interfaces = getAllInterfacesExclude(clazz, null, interfaces);
        return (Class[]) interfaces.toArray(new Class[interfaces.size()]);
    }

    private static List<Class> getAllInterfacesExclude(Class clazz, String[] itfNames, List<Class> list) {
        if (list == null)
            list = new ArrayList<Class>();
        if (clazz != null) {
            Class[] interfaces = clazz.getInterfaces();
            

            for (int i = 0; i < interfaces.length; i++) {
                boolean needAdded = true;
                if (list.contains(interfaces[i]) == false) {
                    if (itfNames != null) {
                        for (int j = 0; j < itfNames.length && needAdded; j++) {
                            if (interfaces[i].getName().startsWith(itfNames[j])) {
                                if (clazz.isInterface())
                                    list.remove(clazz);
                                needAdded = false;
                            }
                        }
                    }
                    if (needAdded) {
                        list.add(interfaces[i]);
                        getAllInterfacesExclude(interfaces[i], itfNames, list);
                    }
                }
            }

            clazz = clazz.getSuperclass();
            getAllInterfacesExclude(clazz, itfNames, list);
        }

        return list;
    }

    /**
     * chech whether the class specified by the array checedClasses can cast to the original class
     * 
     * @param original
     * @param checkedClasses
     * @return
     */
    public static boolean isAssignableFrom(Class<?> original, Class<?>[] checkedClasses) {
        if (checkedClasses == null)
            return false;
        for (int i = 0; i < checkedClasses.length; i++) {
            if (original.isAssignableFrom(checkedClasses[i]))
                return true;
        }
        return false;
    }

    /**
     * check whether the class indicated by cls can cast to the toClass
     * it extends to support array class and the primitive classes
     * 
     * @param cls
     * @param toClass
     * @return
     */
    public static boolean isAssignable(Class<?> cls, Class<?> toClass) {
        if (toClass == null) {
            return false;
        }
        // have to check for null, as isAssignableFrom doesn't
        if (cls == null) {
            return !(toClass.isPrimitive());
        }
        if (cls.equals(toClass)) {
            return true;
        }

        if (cls.isPrimitive()) {
            if (toClass.isPrimitive() == false) {
                return false;
            }
            if (Integer.TYPE.equals(cls)) {
                return Long.TYPE.equals(toClass) || Float.TYPE.equals(toClass) || Double.TYPE.equals(toClass);
            }
            if (Long.TYPE.equals(cls)) {
                return Float.TYPE.equals(toClass) || Double.TYPE.equals(toClass);
            }
            if (Boolean.TYPE.equals(cls)) {
                return false;
            }
            if (Double.TYPE.equals(cls)) {
                return false;
            }
            if (Float.TYPE.equals(cls)) {
                return Double.TYPE.equals(toClass);
            }
            if (Character.TYPE.equals(cls)) {
                return Integer.TYPE.equals(toClass) || Long.TYPE.equals(toClass) || Float.TYPE.equals(toClass)
                        || Double.TYPE.equals(toClass);
            }
            if (Short.TYPE.equals(cls)) {
                return Integer.TYPE.equals(toClass) || Long.TYPE.equals(toClass) || Float.TYPE.equals(toClass)
                        || Double.TYPE.equals(toClass);
            }
            if (Byte.TYPE.equals(cls)) {
                return Short.TYPE.equals(toClass) || Integer.TYPE.equals(toClass) || Long.TYPE.equals(toClass)
                        || Float.TYPE.equals(toClass) || Double.TYPE.equals(toClass);
            }
            // should never get here
            return false;
        }
        return toClass.isAssignableFrom(cls);
    }

    /**
     * bach check with the semantic of isAssignable(Class cls, Class toClass)
     * @param classArray
     * @param toClassArray
     * @return
     */
    public static boolean isAssignable(Class[] classArray, Class[] toClassArray) {
        if (classArray.length != toClassArray.length) {
            return false;
        }
        if (classArray == null) {
            classArray = EMPTY_CLASS_ARRAY;
        }
        if (toClassArray == null) {
            toClassArray = EMPTY_CLASS_ARRAY;
        }
        for (int i = 0; i < classArray.length; i++) {
            if (isAssignable(classArray[i], toClassArray[i]) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * ���Դ��?��Ĳ���Ϊ����������������
     * 
     * @param clz
     * @param methodName
     * @param expectedTypes
     * @return
     * @throws NoSuchMethodException
     */
    public static Method getMethod(Class clz, String methodName, Class[] expectedTypes) throws NoSuchMethodException {
        Method method = null;
        try {
            method = clz.getMethod(methodName, expectedTypes);
        } catch (NoSuchMethodException e) {
            Method[] methods = clz.getMethods();
            for (int i = 0; i < methods.length; i++) {
                Method _method = methods[i];
                if (_method.getName().equals(methodName)) {
                    if (isAssignable(expectedTypes, _method.getParameterTypes())) {
                        if (method == null) {
                            method = _method;
                        } else {
                            // �ҵ����ʺϵķ���
                            if (isAssignable(_method.getParameterTypes(), method.getParameterTypes())) {
                                method = _method;
                            }
                        }

                    }
                }
            }
            if (method == null)
                throw e;
        }
        return method;
    }

    public static Constructor getConstructor(Class clz, Class[] expectedTypes) {
        Constructor constructor = null;
        try {
            Constructor[] constructors = clz.getConstructors();
            for (int i = 0; i < constructors.length; i++) {
                Constructor creator = constructors[i];

                if (isAssignable(expectedTypes, creator.getParameterTypes())) {
                    if (constructor == null) {
                        constructor = creator;
                    } else {
                        // �ҵ����ʺϵķ���
                        if (isAssignable(creator.getParameterTypes(), constructor.getParameterTypes())) {
                            constructor = creator;
                        }
                    }

                }

            }
        } catch (Throwable thr) {
            String msg = "Class: " + clz.getName() + ", " + clz.getProtectionDomain().getCodeSource().getLocation()
            + ", " + clz.getClassLoader();
            Logger.error(msg, thr);
            throw new RuntimeException(msg, thr);
        }
        return constructor;

    }

    public static String getShortName(Class clazz) {
        return ClassUtil.getShortName(clazz.getName());
    }

    public static String getShortName(String className) {
        char[] charArray = className.toCharArray();
        int lastDot = 0;
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == PACKAGE_SEPARATOR_CHAR) {
                lastDot = i + 1;
            } else if (charArray[i] == INNER_CLASS_SEPARATOR_CHAR) {
                charArray[i] = PACKAGE_SEPARATOR_CHAR;
            }
        }
        return new String(charArray, lastDot, charArray.length - lastDot);
    }

    public static boolean hasAtLeastOneMethodWithName(Class clazz, String methodName) {
        do {
            for (int i = 0; i < clazz.getDeclaredMethods().length; i++) {
                Method method = clazz.getDeclaredMethods()[i];
                if (methodName.equals(method.getName())) {
                    return true;
                }
            }
            clazz = clazz.getSuperclass();
        } while (clazz != null);
        return false;
    }

    /**
     * get the static method whith name methodName and parameter types args
     * 
     * @param clazz
     * @param methodName
     * @param args
     * @return
     */
    public static Method getStaticMethod(Class clazz, String methodName, Class[] args) {
        try {
            Method method = clazz.getDeclaredMethod(methodName, args);
            if ((method.getModifiers() & Modifier.STATIC) != 0) {
                return method;
            }
        } catch (NoSuchMethodException ex) {
        }
        return null;
    }

    /**
     * convert the pakage to the representation of resource path
     * @see ResourceBundle
     * 
     * @param clazz
     * @return
     */
    public static String classPackageAsResourcePath(Class clazz) {
        if (clazz == null || clazz.getPackage() == null) {
            return "";
        }
        return clazz.getPackage().getName().replace('.', '/');
    }

    /**
     * add the package path of clazz as the parent path of the resourceName
     * 
     * @param clazz
     * @param resourceName
     * @return
     */
    public static String addResourcePathToPackagePath(Class clazz, String resourceName) {
        if (!resourceName.startsWith("/")) {
            return classPackageAsResourcePath(clazz) + "/" + resourceName;
        } else {
            return classPackageAsResourcePath(clazz) + resourceName;
        }
    }

}