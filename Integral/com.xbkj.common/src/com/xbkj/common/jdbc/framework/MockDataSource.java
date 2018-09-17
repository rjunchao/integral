/*
 * �������� 2005-10-9
 *
 * TODO Ҫ��Ĵ���ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package com.xbkj.common.jdbc.framework;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * @nopublish
 * @author hey
 * 
 * TODO Ҫ��Ĵ���ɵ�����ע�͵�ģ�壬��ת�� ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class MockDataSource {

    private static boolean ismock = false;

    private static String filePath;
    static {
        String param = System.getProperty("mocklocation", "false");
        if (!param.equalsIgnoreCase("false"))
        ismock = true;
        filePath = param;
    }

    static public Connection getConnection() {
        if (!ismock)
            return null;
        System.out.println("��ʼ��ȡ��������");
        Properties prop = new Properties();

        try {
            prop.load(new FileInputStream(filePath));

            String driver = prop.getProperty("driver");
            String url = prop.getProperty("url");
            Class.forName(driver);
            return DriverManager.getConnection(url, prop);
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
    }

    static public boolean isMockDataBase() {
        return ismock;
    }

}
