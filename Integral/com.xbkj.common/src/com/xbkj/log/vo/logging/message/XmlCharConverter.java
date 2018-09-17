/*
 * Created on 2005-1-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.xbkj.log.vo.logging.message;

import com.xbkj.log.vo.logging.patterns.LogPattern;

/**
 * @author cc
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class XmlCharConverter {
	
	public static String buildByTag(String tag, String body) {
		StringBuffer buf = new StringBuffer();
		// <tag>
		buf.append(LogPattern.XML_ENDL);
		buf.append(LogPattern.XML_TAB);
		buf.append(LogPattern.XML_TAG_BEGIN);
		buf.append(tag);
		buf.append(LogPattern.XML_TAG_END);
		// Parameter body is just the message.
		if (!(null == body || body.trim().equals(""))) {
			//			buf.append(LogPattern.XML_ENDL);
			//			buf.append(LogPattern.XML_TAB);
			//			buf.append(LogPattern.XML_TAB);
			// Convert Special Chars
			buf.append(XmlCharConverter.getXMLString(body)); 
			//			buf.append(LogPattern.XML_ENDL);
			//			buf.append(LogPattern.XML_TAB);
		}
		// </tag>
		buf.append(LogPattern.XML_TAG_BEGIN);
		buf.append(LogPattern.XML_SLASH);
		buf.append(tag);
		buf.append(LogPattern.XML_TAG_END);

		return buf.toString();
	}

	public static String buildDefault(String defaultMsg) {
		StringBuffer buf = new StringBuffer();
		// <MSG>
		buf.append(LogPattern.XML_TAB);
		buf.append(LogPattern.XML_TAG_BEGIN);
		buf.append(LogPattern.XML_TAG_MSG);
		buf.append(LogPattern.XML_TAG_END);
		//buf.append(LogPattern.XML_ENDL);
		// Parameter defaultMsg is just the message.
		//buf.append(LogPattern.XML_TAB);
			//buf.append(LogPattern.XML_TAB);
			// Convert Special Chars
			buf.append(XmlCharConverter.getXMLString(defaultMsg)); 
			//buf.append(LogPattern.XML_ENDL);
		// </MSG>
		//buf.append(LogPattern.XML_TAB);
		buf.append(LogPattern.XML_TAG_BEGIN);
		buf.append(LogPattern.XML_SLASH);
		buf.append(LogPattern.XML_TAG_MSG);
		buf.append(LogPattern.XML_TAG_END);
		//buf.append(LogPattern.XML_ENDL);
		
		return buf.toString();
	}

	public static String getXMLString(String[] values) {
	   
	    StringBuffer sb = new StringBuffer();
	    sb.append(LogPattern.XML_TAB);
        sb.append(LogPattern.XML_TAB);
	    sb.append("<![CDATA[");
	    
	    sb.append(LogPattern.XML_ENDL);
	    for (int i = 0; i < values.length; i++) {
            sb.append(LogPattern.XML_TAB);
            sb.append(LogPattern.XML_TAB);
            sb.append(values[i]);
            sb.append(LogPattern.XML_ENDL);
        }
	    sb.append(LogPattern.XML_TAB);
        sb.append(LogPattern.XML_TAB);
	    sb.append("]]>");
	    sb.append(LogPattern.XML_ENDL);
	    return sb.toString();
	}
	public static String getXMLString(String value) {
	    StringBuffer sb = new StringBuffer();
	    sb.append("<![CDATA[");
	    sb.append(value);
	    sb.append("]]>");
	    return sb.toString();
//		if (value != null) {
//			value = value.trim();
//			char[] cVals = value.toCharArray();
//			StringBuffer sb = new StringBuffer();
//			// ��ΪXML������ֵ���ܰ��ַ����̶ֹ���ʵ�����õ��ַ�
//			int  offset = 0;
//			char[] amp = {'&', 'a', 'm', 'p', ';'};
//			char[] lt = {'&', 'l', 't', ';'};
//			char[] gt = {'&', 'g', 't', ';'};
//			char[] quot = {'&', 'q', 'u', 'o', 't', ';'};
//			char[] apos = {'&', 'a', 'p', 'o', 's', ';'};
//			for (int i = 0; i < cVals.length; ++i) {
//				switch (cVals[i]) {
//				case '&' :
//					sb.append(cVals, offset, i - offset);
//					sb.append(amp);
//					offset = i + 1;
//					break;
//				case '<' :
//					sb.append(cVals, offset, i - offset);
//					sb.append(lt);
//					offset = i + 1;
//					break;
//				case '>' :
//					sb.append(cVals, offset, i - offset);
//					sb.append(gt);
//					offset = i + 1;
//					break;
//				case '\"' :
//					sb.append(cVals, offset, i - offset);
//					sb.append(quot);
//					offset = i + 1;
//					break;
//				case '\'' :
//					sb.append(cVals, offset, i - offset);
//					sb.append(apos);
//					offset = i + 1;
//					break;
//				default:
//					break;
//				}
//			}
//			if (offset < cVals.length) sb.append(cVals, offset, cVals.length - offset);
//			value = sb.toString();
//		}
//		return value;
	}
}
