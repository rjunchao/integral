/*
 * �������� 2005-8-5
 *
 * TODO Ҫ��Ĵ���ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package com.xbkj.common.jdbc.framework.util;

import java.io.*;

//import com.grc.common.bs.framework.common.Serializer;

/**
 * @nopublish User: ���� Date: 2005-6-2 Time: 15:20:52 InOutUtil���˵��
 */
public class InOutUtil {

	public static int readLine(InputStream in, OutputStream out)
			throws IOException {
		int count = 0;
		for (;;) {
			int b = in.read();

			if (b == -1) {
				break;
			}
			count++;
			out.write(b);
			if (b == '\n') {
				break;
			}
		}
		return count;
	}

	public static byte[] serialize(Serializable s) throws IOException {
		if (s == null)
			return null;
		return null;
//		return Serializer.serialize(s);
	}

	public static Serializable deserialize(byte[] ba) {
//		return Serializer.deserialize(ba);
		return null;
	}
}
