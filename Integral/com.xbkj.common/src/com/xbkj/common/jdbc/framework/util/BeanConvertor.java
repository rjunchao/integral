/*
 * �������� 2005-9-16
 *
 * TODO Ҫ��Ĵ���ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package com.xbkj.common.jdbc.framework.util;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.grc.common.jdbc.framework.type.BlobObject;
import com.grc.basic.vo.pub.lang.UFBoolean;
import com.grc.basic.vo.pub.lang.UFDate;
import com.grc.basic.vo.pub.lang.UFDateTime;
import com.grc.basic.vo.pub.lang.UFDouble;
import com.grc.basic.vo.pub.lang.UFTime;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.BigIntegerConverter;
import org.apache.commons.beanutils.converters.BooleanArrayConverter;
import org.apache.commons.beanutils.converters.ByteArrayConverter;
import org.apache.commons.beanutils.converters.ByteConverter;
import org.apache.commons.beanutils.converters.CharacterArrayConverter;
import org.apache.commons.beanutils.converters.CharacterConverter;
import org.apache.commons.beanutils.converters.ClassConverter;
import org.apache.commons.beanutils.converters.DoubleArrayConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
//import org.apache.commons.beanutils.converters.FileConverter;
import org.apache.commons.beanutils.converters.FloatArrayConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerArrayConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongArrayConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.ShortArrayConverter;
import org.apache.commons.beanutils.converters.ShortConverter;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.beanutils.converters.SqlTimeConverter;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;
//import org.apache.commons.beanutils.converters.URLConverter;

/**
 * @nopublish
 * @author hey <p/> TODO Ҫ��Ĵ���ɵ�����ע�͵�ģ�壬��ת�� ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */

public class BeanConvertor {
    static Map<Class, Converter> converts = new HashMap<Class, Converter>();

    static {
        boolean booleanArray[] = new boolean[0];
        byte byteArray[] = new byte[0];
        char charArray[] = new char[0];
        double doubleArray[] = new double[0];
        float floatArray[] = new float[0];
        int intArray[] = new int[0];
        long longArray[] = new long[0];
        short shortArray[] = new short[0];
        String stringArray[] = new String[0];
//        converts.put(UFDate.class, new UFDateConvertor());
//        converts.put(UFTime.class, new UFTimeConvertor());
//        converts.put(Calendar.class, new CalenderConvertor());
//        converts.put(UFDateTime.class, new UFDateTimeConvertor());
//        converts.put(UFDouble.class, new UFDoubleConvertor());
//        converts.put(UFBoolean.class, new UFBooleanConvertor());
//        converts.put(BlobObject.class, new BlobConvertor());
//        converts.put(Object.class, new ObjectConvertor());
        converts.put(BigDecimal.class, new BigDecimalConverter());
        converts.put(BigInteger.class, new BigIntegerConverter());
//        converts.put(Boolean.TYPE, new BooleanConverter());
//        converts.put(Boolean.class, new BooleanConverter());
        converts.put(booleanArray.getClass(), new BooleanArrayConverter());
        converts.put(Byte.TYPE, new ByteConverter());
        converts.put(Byte.class, new ByteConverter());
        converts.put(byteArray.getClass(), new ByteArrayConverter(byteArray));
        converts.put(Character.TYPE, new CharacterConverter());
        converts.put(Character.class, new CharacterConverter());
        converts.put(charArray.getClass(), new CharacterArrayConverter(charArray));
        converts.put(Class.class, new ClassConverter());
        converts.put(Double.TYPE, new DoubleConverter());
        converts.put(Double.class, new DoubleConverter());
        converts.put(doubleArray.getClass(), new DoubleArrayConverter(doubleArray));
        converts.put(Float.TYPE, new FloatConverter());
        converts.put(Float.class, new FloatConverter());
        converts.put(floatArray.getClass(), new FloatArrayConverter(floatArray));
        converts.put(Integer.TYPE, new IntegerConverter());
        converts.put(Integer.class, new IntegerConverter());
        converts.put(intArray.getClass(), new IntegerArrayConverter(intArray));
        converts.put(Long.TYPE, new LongConverter());
        converts.put(Long.class, new LongConverter());
        converts.put(longArray.getClass(), new LongArrayConverter(longArray));
        converts.put(Short.TYPE, new ShortConverter());
        converts.put(Short.class, new ShortConverter());
        converts.put(shortArray.getClass(), new ShortArrayConverter(shortArray));
//        converts.put(stringArray.getClass(), new StringArrayConverter(stringArray));
        converts.put(Date.class, new SqlDateConverter());
        converts.put(Time.class, new SqlTimeConverter());
        converts.put(Timestamp.class, new SqlTimestampConverter());
//        converts.put(File.class, new FileConverter());
//        converts.put(URL.class, new URLConverter());
//        converts.put(List.class, new ObjectConvertor());
//        converts.put(String.class, new StringConvertor());
    }

    static public Object convert(Object obj, Class theClass) {
        Converter converter = converts.get(theClass);
        if (converter != null) {
            return converter.convert(theClass, obj);
        }
        return obj;
    }

    public static Converter getConVerter(Class<?> name) {
        return converts.get(name);
    }

}
