package com.xbkj.common.bs.mw.sqltrans;

import java.util.Vector;

import com.xbkj.log.bs.logging.Logger;

/**
 * �˴���������˵����
 * �������ڣ�(2001-3-15 19:27:54)
 * @author���� ɭ
 */
public class Express_str {
	String[] m_definitie = null; //��ʽ���ʽ
	Expresion_str m_Arbore = null; //��ӵ�
	private int pozitie = 0; //ָ��λ��
	private int code = 0; //���صĴ������
	private int state = 0; //״̬�����㡢��ϵ���߼�
	private static int DIVISION_BY_0 = 1; //�����
	private static int ILEGAL_OPERATION = 2; //�Ƿ�������
	private static int UNDEFINED_VARIABLE = 3; //δ����ı���
	private static int INVALID_DOMAIN = 4; //�����ȡֵ��Χ
	private static int RELATION = 5; //��ϵ�Ƚ�ʽ
	//private java.util.Hashtable data=new java.util.Hashtable();//������
	//	String m_pvariabile=null;

	//String m_asOperationStr[] = { "=", "!=", "<>", "<", "<=", ">", ">=" }; //������
	//String m_asOperationStr[] = { "and", "or"}; //������

	//���������ַ�,//��ɭ 2001��3��17 �ģ����� >,!>,!<,=,<,
	/*
	String m_asSpecialStr[] =
		{ "!=", "!>", "!<", "<>", "<=", ">=", "=", "<", ">", "||", m_sLineSep };
	*/
	String m_asSpecialStr[] = { "and", "or" };
	//String m_asSpecialStr_new[] = { "and", "or"," ",m_sLineSep };

	static String m_sLineSep = "\r\n";
	//System.getProperty("line.separator"); //���з�
	//String m_sSpecialChar = "-+()*=,? <>;" + m_sLineSep;

	String m_sSpecialChar = "()";
	//String m_sSpecialChar_new = "() " +m_sLineSep;
	/**
	 * Express ������ע�⡣
	 */
	public Express_str() {
		super();
		Logger.setThreadState("nc.bs.mw.sqltrans.Express_str Over");
	}
	/**
	 * �˴����뷽��˵����
	 * ���ܣ�ȥ��ͬ����
	 * �������ڣ�(2001-3-26 14:09:10)
	 * @return java.util.Vector
	 * @param source java.util.Vector
	 */
	public Vector dealVector(Vector source) {
		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.dealVector");
		if (source == null || source.size() <= 0)
			return null;

		for (int i = 0; i < source.size() - 1; i++) {
			String st1 = source.elementAt(i).toString().trim();

			for (int j = i + 1; j < source.size(); j++) {
				String st2 = source.elementAt(j).toString().trim();

				if (st1.equalsIgnoreCase(st2)) {
					source.removeElementAt(j);
				}
			}
		}

		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.dealVector Over");
		return source;
	}
	/**
	 * �˴����뷽��˵����
	 * ���ܣ�ʹ�ڵ�Ϊ��
	 * �������ڣ�(2001-3-15 19:30:11)
	 * @Logger.setThreadState("nc.bs.mw.sqltrans.Express_str.elibmem Over");
	return test.Expresion
	 */
	private void elibmem(Expresion_str e) {
		Logger.setThreadState("nc.bs.mw.sqltrans.Express_str.elibmem");
		e = null;

	}
	/**
	 * �˴����뷽��˵����
	 * ���ܣ���ָ���λ�ÿ�ʼ���������ĳ���ͱ�����
	 * ���õݹ�ķ�������������ʽ
	 * �������ڣ�(2001-3-15 19:30:11)
	 * @return test.Expresion_str
	 * �������˳��
	 * expresize(+ -)->termen(* /)->putere(^ �˷�)
	 * ->logicalOP(�� ��=)->sgOp(��)
	 * ->factor(- ���� || )>-<identificator(�������)
	 */
	private Expresion_str expresie_or() {
		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.expresie_or");
		Expresion_str nod;
		Expresion_str arb1 = logicalOp_and(); // and
		Expresion_str arb2;
		if (arb1 == null)
			return null; // In caz de eroare terminate

		while (pozitie < m_definitie.length - 1
			&& m_definitie[pozitie].equalsIgnoreCase("or")) {
			nod = new Expresion_str();
			nod.left = arb1;
			//nod.operatie = m_definitie[pozitie];
			nod.operatie = '|';
			pozitie++;
			arb2 = logicalOp_and(); // and
			nod.right = arb2;
			if (arb2 == null) {
				elibmem(nod);
				return null; // In caz de eroare terminate
			}
			arb1 = nod;
		}
		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.expresie_or Over");
		return arb1;
	}
	/**
	 * �˴����뷽��˵����
	 * ���ܣ����?���ı��ʽ
	 * �������ڣ�(2001-3-15 19:30:11)
	 * @return test.Expresion_str
	 */
	private Expresion_str factor() {
		Logger.setThreadState("nc.bs.mw.sqltrans.Express_str.factor");
		Expresion_str nod = null, nod2 = null, left = null;

		if (m_definitie[pozitie].equals("(")) //�������������,˵���Ǻ���
			{
			pozitie++;
			nod = expresie_or(); //�����?ʽ��ĳ���ͱ���
			if (nod == null)
				return null;
			while (pozitie < m_definitie.length
				&& !m_definitie[pozitie].equals(")")) //�����������,˵����һ����Ȼ�Ǻ���Ĳ���
				{
				nod2 = new Expresion_str();
				pozitie++;
				nod2.right = expresie_or(); //�����?ʽ��ĳ���ͱ���
				nod2.left = nod;
				nod = new Expresion_str();
				nod = nod2;
				nod.operatie = ',';
			}
			/*
			if (m_definitie[pozitie].equals(")") )//���û������������,˵�� �ú���Ƿ�
			{
				elibmem(nod);//ȥ���ñ��ʽ
				return null;
			}
			*/
			if (pozitie >= m_definitie.length)
				return null;
			pozitie++;
			return nod;
		} else //������ ���� ����
			Logger.setThreadState(
				"nc.bs.mw.sqltrans.Express_str.factor Over");
		return identificator(); //ʶ��������

	}
	/**
	 * �˴����뷽��˵����
	 * �������ڣ�(2002-1-19 16:39:28)
	 * @return int
	 * @param stArray java.lang.String[]
	 * @param i int
	 */
	public int getEndIndex(String[] stArray, int index) {
		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.getEndIndex");
		int i = 0;
		int left = 0;
		int right = 0;

		for (i = index; i < stArray.length; i++) {
			if (stArray[i].trim().equals("(")) {
				left++;
			}
			if (stArray[i].trim().equals(")")) {
				right++;
			}
			//if(left==right && (stArray[i].trim().equalsIgnoreCase("or") || stArray[i].trim().equalsIgnoreCase("and")))
			if (left == right) {
				break;
			}
		}
		//i--;	
		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.getEndIndex Over");
		return i;
	}
	/**
	 * �˴����뷽��˵����
	 * �������ڣ�(2002-1-19 16:39:28)
	 * @return int
	 * @param stArray java.lang.String[]
	 * @param i int
	 */
	public int getKuoEndIndex(String[] stArray, int index) {
		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.getKuoEndIndex");
		int i = 0;
		int left = 0;
		int right = 0;

		for (i = index; i < stArray.length; i++) {
			if (stArray[i].trim().equals("(")) {
				left++;
			}
			if (stArray[i].trim().equals(")")) {
				right++;
			}
			if (right > left
				|| (right == left
					&& (stArray[i].trim().equalsIgnoreCase("or")
						|| stArray[i].trim().equalsIgnoreCase("and")))) {
				break;
			}
		}
		i--;
		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.getKuoEndIndex Over");
		return i;
	}
	/**
	 * �˴����뷽��˵����
	 * �������ڣ�(2001-3-15 21:34:19)
	 * @return double
	 */
	public String getValue() {
		Logger.setThreadState("nc.bs.mw.sqltrans.Express_str.getValue");
		Vector vec = vexp(m_Arbore);

		if (vec == null || vec.size() <= 0)
			return "";

		String re = "";

		for (int i = 0; i < vec.size(); i++) {
			String st = vec.elementAt(i).toString().trim();

			if (i < vec.size() - 1) {
				//re+="("+st+") "+"or"+" ";
				re += st + " or" + " ";
			} else {
				//re+="("+st+") ";
				re += st;
			}
		}

		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.getValue Over");
		return re;
	}
	/**
	 * �˴����뷽��˵����
	 * �������ڣ�(2001-3-15 21:34:19)
	 * @return double
	 */
	public String getValue(String sql) {
		Logger.setThreadState("nc.bs.mw.sqltrans.Express_str.getValue");
		setExpress(sql);

		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.getValue Over");
		return getValue();
	}
	/**
	 * �˴����뷽��˵����
	 * �������ڣ�(2001-3-15 21:34:19)
	 * @return double
	 */
	public Vector getValue_v() {
		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.getValue_v");
		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.getValue_v Over");
		return vexp(m_Arbore);
	}
	/**
	 * �˴����뷽��˵����
	 * �������ڣ�(2001-3-26 14:53:36)
	 * @return boolean
	 * @param sou java.lang.String[]
	 * @param de java.lang.String
	 */
	public boolean having(String[] sou, String de) {
		Logger.setThreadState("nc.bs.mw.sqltrans.Express_str.having");
		if (sou == null || de == null)
			return false;

		for (int i = 0; i < sou.length; i++) {
			if (sou[i].trim().equalsIgnoreCase(de.trim())) {
				return true;
			}
		}

		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.having Over");
		return false;
	}
	/**
	 * �˴����뷽��˵����
	 * ���ܣ�//ʶ��������,���?�����������
	 * �������ڣ�(2001-3-15 19:30:11)
	 * @return test.Expresion_str
	 */
	private Expresion_str identificator() {
		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.identificator");

		Expresion_str nod = null, nod2 = null;
		Expresion_str result = null;

		String newSt = m_definitie[pozitie];

		if (pozitie < m_definitie.length - 1
			&& m_definitie[pozitie + 1].equalsIgnoreCase("(")) {
			pozitie++; //��¼��ǰλ��
			int leftInclude = 0;
			int rightInclude = 0;
			String sub = "";
			while (pozitie < m_definitie.length
				&& (leftInclude != rightInclude
					|| (!m_definitie[pozitie].equalsIgnoreCase("and")
						&& !m_definitie[pozitie].equalsIgnoreCase("or")))) {
				if (m_definitie[pozitie].equals("(")) {
					leftInclude++;
				} else
					if (m_definitie[pozitie].equals(")")) {
						rightInclude++;
					}
				newSt += " " + m_definitie[pozitie];
				pozitie++;
			}
			pozitie--;
		}

		nod = new Expresion_str();
		nod.left = null;
		nod.right = null;
		nod.operatie = '@';
		Vector v = new Vector();
		//v.addElement(m_definitie[pozitie]);
		v.addElement(newSt);
		nod.valoare = v;
		result = nod;

		pozitie++;

		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.identificator Over");
		return result;

	}
	/**
	 * �˴����뷽��˵����
	 * �������ڣ�(2001-12-29 11:18:23)
	 * @return boolean
	 * @param st java.lang.String
	 */
	public boolean isBiJiaoFu(String st) {
		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.isBiJiaoFu");
		boolean isOprater = false;

		//û������as�� Ҳû������on,����
		if (st.equals("=")
			|| st.startsWith("=")
			|| st.equals("<=")
			|| st.startsWith("<=")
			|| st.equals(">=")
			|| st.startsWith(">=")
			|| st.equals("<")
			|| st.startsWith("<")
			|| st.equals(">")
			|| st.startsWith(">")
			|| st.equals("<>")
			|| st.startsWith("<>")
			|| st.equals("!=")
			|| st.startsWith("!=")
			|| st.equalsIgnoreCase("is")
			|| st.startsWith("is ")
			|| st.equalsIgnoreCase("+")
			|| st.startsWith("+")
			|| st.equalsIgnoreCase("-")
			|| st.startsWith("-")
			|| st.equalsIgnoreCase("*")
			|| st.startsWith("*")
			|| st.equalsIgnoreCase("/")
			|| st.startsWith("/")
			|| st.equalsIgnoreCase("!")
			|| st.startsWith("!")) {
			isOprater = true;
		}
		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.isBiJiaoFu Over");
		return isOprater;
	}
	/**
	 * �˴����뷽��˵����
	 * �������ڣ�(2002-1-19 18:37:16)
	 * @return boolean
	 * @param c char
	 */
	public boolean isSpecialChar(char c) {
		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.isSpecialChar");
		boolean isSpecial = false;

		for (int i = 0; i < m_sSpecialChar.length(); i++) {
			if (c == m_sSpecialChar.charAt(i)) {
				isSpecial = true;
			}
		}
		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.isSpecialChar Over");
		return isSpecial;
	}
	/**
	 * �˴����뷽��˵����
	 * �������ڣ�(2002-1-19 18:37:16)
	 * @return boolean
	 * @param c char
	 */
	public boolean isSpecialChar_all(char c) {
		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.isSpecialChar_all");
		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.isSpecialChar_all Over");
		return (c == ' ') || isSpecialChar(c);
	}
	/**
	 * �˴����뷽��˵����
	 * �������ڣ�(2002-1-19 18:37:16)
	 * @return boolean
	 * @param c char
	 */
	public boolean isSpecialSt(String st) {
		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.isSpecialSt");
		boolean isSpecial = false;

		for (int i = 0; i < m_asSpecialStr.length; i++) {
			if (st.equalsIgnoreCase(m_asSpecialStr[i])) {
				isSpecial = true;
			}
		}
		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.isSpecialSt Over");
		return isSpecial;
	}
	/**
	 * �˴����뷽��˵����
	 * �������ڣ�(2002-1-19 18:37:16)
	 * @return boolean
	 * @param c char
	 */
	public boolean isSpecialSt(String st, int index, String source) {
		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.isSpecialSt");
		boolean isSpecial = false;

		if (isSpecialSt(st)) {
			if (index <= 0 || (isSpecialChar_all(source.charAt(index - 1)))) {
				if (index + st.length() >= source.length() - 1
					|| (isSpecialChar_all(source.charAt(index + st.length())))) {
					isSpecial = true;
				}
			}
		}

		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.isSpecialSt Over");
		return isSpecial;
	}
	/**
	 * �˴����뷽��˵����
	 * ���ܣ��Ƚϴ�С��Ӧ���� = != <> <= >=�ıȽ�
	 * �������ڣ�(2001-3-15 19:30:11)
	 * @return test.Expresion_str
	 */
	private Expresion_str logicalOp_and() {
		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.logicalOp_and");
		Expresion_str nod;
		Expresion_str arb1 = factor(); //Ԫ��
		Expresion_str arb2;
		if (arb1 == null)
			return null; // In caz de eroare terminate

		//������� > < =
		while (pozitie < m_definitie.length - 1
			&& m_definitie[pozitie].equalsIgnoreCase("and")) {
			nod = new Expresion_str();
			nod.left = arb1;

			nod.operatie = '&';

			pozitie++;
			arb2 = factor(); //Ԫ��
			nod.right = arb2;
			if (arb2 == null) {
				elibmem(nod);
				return null; // In caz de eroare terminate
			}

			arb1 = nod;
		}
		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.logicalOp_and Over");
		return arb1;
	}
	/**
	 * �˴����뷽��˵����
	 * �������ڣ�(2001-3-25 19:33:15)
	 * @return java.util.Vector
	 * @param lrft java.util.Vector
	 * @param right java.util.Vector
	 */
	public Vector op_and(Vector left, Vector right) {
		Logger.setThreadState("nc.bs.mw.sqltrans.Express_str.op_and");
		if (left == null || right == null || left.size() <= 0 || right.size() <= 0) {
			return new Vector();
		}

		Vector re = new Vector();

		for (int i = 0; i < left.size(); i++) {
			String st_left = left.elementAt(i).toString().trim();

			for (int j = 0; j < right.size(); j++) {
				String st_right = right.elementAt(j).toString().trim();

				String st = "";

				//����
				if (st_left.compareToIgnoreCase(st_right) == 0
					|| having(parseSql(st_left), st_right)
					|| having(parseSql(st_right), st_left)) {
					st = st_left;

				} else {
					if (st_left.compareToIgnoreCase(st_right) < 0) {
						st = st_left + " " + "and" + " " + st_right;

					} else
						if (st_left.compareToIgnoreCase(st_right) > 0) {
							st = st_right + " " + "and" + " " + st_left;

						}

				}

				re.addElement(st);

			}
		}

		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.op_and Over");
		return dealVector(re);
	}
	/**
	 * �˴����뷽��˵����
	 * �������ڣ�(2001-3-25 19:33:15)
	 * @return java.util.Vector
	 * @param lrft java.util.Vector
	 * @param right java.util.Vector
	 */
	public Vector op_or(Vector left, Vector right) {
		Logger.setThreadState("nc.bs.mw.sqltrans.Express_str.op_or");
		if (left == null || right == null || left.size() <= 0 || right.size() <= 0) {
			return new Vector();
		}

		for (int i = 0; i < right.size(); i++) {
			String st = right.elementAt(i).toString().trim();
			left.addElement(st);
		}
		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.op_or Over");
		return dealVector(left);
	}
	/**
	 * ��SQL�����в�����,�õ�ÿһ����������ַ�
	 */
	String[] parseSql(String sql) {
		Logger.setThreadState("nc.bs.mw.sqltrans.Express_str.parseSql");
		//�������SQLΪ�գ��򷵻ؿ�
		if (sql == null || sql.trim().length() == 0)
			return null;
		//��ʼ��������
		String asKeyWords[] = null;
		//��ʼ������
		java.util.Hashtable table = new java.util.Hashtable();
		//��ʼ������
		int iCount = 0;
		sql = sql.trim();
		//�����ʳ��ȴ���0����ʼѰ�����൥��
		while (sql.length() > 0) {
			//�ҵ���һ������
			String sWord = parseWord(sql);

			sql = sql.substring(sWord.length()).trim();

			//�����ʳ��ȴ���0
			if (sWord.trim().length() > 0) {
				//�����µ���
				String s = sWord;
				//���������
				table.put(new Integer(iCount), s);
				//�����1
				iCount++;
			} else {
				break;
			}
		}
		//��ʼ�ַ�����
		asKeyWords = new String[iCount];
		//�ӹ���������ȡ��¼
		for (int i = 0; i < iCount; i++) {
			asKeyWords[i] = (String) table.get(new Integer(i));
		}
		//�����ַ���
		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.parseSql Over");
		return asKeyWords;
	}
	/**
	 * ��sql��� s ����ȡ��һ��������ĵ���
	 * word = (|)|*|=|,|?| |<|>|!=|<>|<=|>=|����
	 */
	public String parseWord(String s) {
		Logger.setThreadState("nc.bs.mw.sqltrans.Express_str.parseWord");
		//ע��˴������� s=s.trim();��䣬��������

		//�����뵥�ʳ���Ϊ0���򷵻�""
		if (s.length() == 0) {
			return "";
		}
		//��־:�Ƿ���''��,�Ƿ���""��,�Ƿ��ҵ�����
		boolean bInSingle = false;
		boolean bInDouble = false;
		boolean bFound = false;
		//��ʼ������
		int iOffSet = 0;
		//��ʼ�ַ��
		char c;
		//ȡ�������ַ��ڼ���λ�õ��ַ�
		c = s.charAt(iOffSet); //��һ����Ч�ַ�
		if (isSpecialChar(c)) {
			return "" + c;
		}
		if (s.length() > 1) {
			if (isSpecialSt(s.substring(0, 2), 0, s)) {
				return s.substring(0, 2);
			} else
				if (s.length() > 2 && isSpecialSt(s.substring(0, 3), 0, s)) {
					return s.substring(0, 3);
				}

		} else {
			return s;
		}
		//���������ַ�
		//��������1
		/*	iOffSet++;
		
			//������С�������ַ���
			if (iOffSet < s.length())
			{
				//ȡ�������ַ��ڼ���λ��2λ���ַ�
				if(isSpecialChar(s.charAt(iOffSet)))
				{
					return ""+c;
				}
				
				String ss = "" + c + s.charAt(iOffSet);
		
				if(iOffSet+1>=s.length())
				{
					return ss.trim();
				}
				String sss = ss + s.charAt(iOffSet+1);
		
				//���αȽ��Ƿ�Ϊ�����ַ�
				if(isSpecialSt(ss))
				{
					//���������ַ�
					return s.substring(0, iOffSet + 1);
				}
		
				if(isSpecialSt(sss))
				{
					//���������ַ�
					return s.substring(0, iOffSet + 2);
				}
				
				
			}
		
			//��������1
			iOffSet--;
		*/
		//���Ҳ����������ַ�
		if (isSpecialChar(c)) {
			return s.substring(0, iOffSet + 1);
		}

		//������С�������ַ�ĳ���
		while (iOffSet < s.length()) {
			//ȡ�������ַ��ڼ���λ�õ��ַ�
			c = s.charAt(iOffSet);
			//��Ϊ�����
			if (c == '\'') {
				//������˫�����
				if (!bInDouble) {
					//���ڵ������
					if (bInSingle) {
						//����''
						//�������1С�������ַ�ĳ��ȣ��������ַ��ڼ����1λ�õ��ַ�Ϊ�����
						if ((iOffSet + 1) < s.length() && s.charAt(iOffSet + 1) == '\'') {
							//�����1
							iOffSet++;
						} else {
							//���򣬼����1�����ѭ��
							iOffSet++;
							break;
						}
					}
					//�Ƿ��ڵ��������Ϊ��
					bInSingle = true;
				}
			}

			//��Ϊ˫���
			if (c == '"') {
				//�����ڵ������
				if (!bInSingle) {
					//����˫�����
					if (bInDouble) {
						//�����1�����ѭ��
						iOffSet++;
						break;
					}
					//�Ƿ���˫�������Ϊ��
					bInDouble = true;
				}
			}

			//���ڵ�������Ҳ���˫�����
			if ((!bInDouble) && (!bInSingle)) {

				//�����1
				iOffSet++;
				//������С�������ַ�ĳ���
				if (iOffSet < s.length()) {

					String ss = "" + c + s.charAt(iOffSet);
					String sss = null;

					if (iOffSet < s.length() - 1) {
						sss = ss + s.charAt(iOffSet + 1);
					}
					//ѭ���Ƚ��Ƿ�Ϊ�����ַ�//���ҵ��������ѭ��

					if (isSpecialSt(ss, iOffSet - 1, s)
						|| (sss != null && isSpecialSt(sss, iOffSet - 1, s))) {
						return s.substring(0, iOffSet - 1);
					}

				}
				//�����1
				iOffSet--;

				if (!bFound) {
					//ѭ�������Ƿ�Ϊ�����ַ�
					if (isSpecialChar(c)) {
						//���ҵ��������ѭ��
						bFound = true;
						break;
					}
				}

				//���ҵ��������ѭ��
				if (bFound) {
					break;
				}
			}
			//�����1
			iOffSet++;
		}

		//�������ַ��0������λ���з���
		// System.out.println(s.substring(0, iOffSet));
		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.parseWord Over");
		return s.substring(0, iOffSet);
	}
	/**
	 * �˴����뷽��˵����
	 * �������ڣ�(2001-3-15 21:15:43)
	 * @param param java.lang.String
	 */
	public void setExpress(String param) {
		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.setExpress");
		//this.data = data;//���ò����
		pozitie = 0;
		m_definitie = parseSql(param); //��ʽ���ʽ.,char[]��ʽ

		m_definitie = trim(m_definitie);

		m_Arbore = expresie_or(); //���ʽ

		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.setExpress Over");
	}
	/**
	 * �˴����뷽��˵����
	 * �������ڣ�(2002-1-19 16:32:05)
	 * @return java.lang.String
	 * @param stArray java.lang.String[]
	 */
	public String[] trim(String[] stArray) {
		Logger.setThreadState("nc.bs.mw.sqltrans.Express_str.trim");
		String s = "";
		Vector vec = new Vector();
		for (int i = 0; i < stArray.length; i++) {
			String st = stArray[i].trim();
			if (st.equalsIgnoreCase("and")
				|| st.equalsIgnoreCase("or")
				|| st.equals(")")) {
				if (s.length() > 0) {
					vec.addElement(s);
				}
				vec.addElement(st);
				s = "";
			} else
				if (st.equals("(")) {
					int end = getEndIndex(stArray, i);

					if (end < stArray.length - 1
						&& !stArray[end + 1].equalsIgnoreCase("and")
						&& !stArray[end + 1].equalsIgnoreCase("or")
						&& !stArray[end + 1].equalsIgnoreCase(")")) {
						end = getKuoEndIndex(stArray, i);
						for (int index = i; index <= end; index++) {
							s += stArray[index];
						}
						vec.addElement(s);
						s = "";
						i = end;
					} else {
						if (s.length() > 0) {
							vec.addElement(s);
						}
						vec.addElement(st);
						s = "";
					}
				} else {
					s += st;
				}
		}
		if (s.length() > 0) {
			vec.addElement(s);
		}
		String[] newArray = new String[vec.size()];
		vec.copyInto(newArray);
		Logger.setThreadState("nc.bs.mw.sqltrans.Express_str.trim Over");
		return newArray;
	}
	/**
	 * �˴����뷽��˵����
	 * �������ڣ�(2002-1-19 16:32:05)
	 * @return java.lang.String
	 * @param stArray java.lang.String[]
	 */
	public String[] trim_old(String[] stArray) {
		Logger.setThreadState("nc.bs.mw.sqltrans.Express_str.trim_old");
		String s = "";
		Vector vec = new Vector();
		for (int i = 0; i < stArray.length; i++) {
			String st = stArray[i].trim();
			if (st.equalsIgnoreCase("and") || st.equalsIgnoreCase("or")) {
				if (s.length() > 0) {
					vec.addElement(s);
				}
				vec.addElement(st);
				s = "";
			} else
				if (st.equals("(")) {
					if (i > 0
						&& !stArray[i - 1].equalsIgnoreCase("and")
						&& !stArray[i - 1].equalsIgnoreCase("or")) {
						int end = getEndIndex(stArray, i);

						for (int index = i; index <= end; index++) {
							s += stArray[index];
						}
						vec.addElement(s);
						s = "";
						i = end;
					} else {
						if (s.length() > 0) {
							vec.addElement(s);
						}
						vec.addElement(st);
						s = "";
					}
				} else {
					s += st;
				}
		}

		String[] newArray = new String[vec.size()];
		vec.copyInto(newArray);
		Logger.setThreadState(
			"nc.bs.mw.sqltrans.Express_str.trim_old Over");
		return newArray;
	}
	/**
	 * �˴����뷽��˵����
	 * �������ڣ�(2001-3-15 21:15:43)
	 * @param param java.lang.String
	 */
	private Vector vexp(Expresion_str a) {
		Logger.setThreadState("nc.bs.mw.sqltrans.Express_str.vexp");
		double v;
		//if (a.operatie=null) {code=10;return 0;}
		switch (a.operatie) {

			case '|' :
				{
					return op_or(vexp(a.left), vexp(a.right));
					/*
					if( vexp(a.left)>0 || vexp(a.right)>0 )
						return 1;
					else
						return -1;
					*/
				}
			case '&' :
				{
					return op_and(vexp(a.left), vexp(a.right));
					/*
					if( vexp(a.left)>0 && vexp(a.right)>0 )
						return 1;
					else
						return -1;
					*/
				}
			case '@' :
				return dealVector(a.valoare);
				//logical operations evaluation

			default :
				{
					/*
					if (a.left == null && a.right == null && a.operatie == '`')
					{
						double d = 0;
						if (a.valoarestr != null)
						{
							String str = (String) data.get(a.valoarestr.trim());
							if (str != null && str.length() != 0)//�����Ǻ�����Ĳ��� 
								d = Double.valueOf(str.trim()).doubleValue();
							else
								System.out.println("û��ʶ���:" + a.valoarestr);
							code = UNDEFINED_VARIABLE;
						}
						else
						{
							System.out.println("û��ʶ���:" + a.valoarestr);
							code = UNDEFINED_VARIABLE;
						}
						return d;
					}
					else
						return a.valoare;
					*/
					Logger.setThreadState("nc.bs.mw.sqltrans.Express_str.vexp Over");
					return a.valoare;
				}
		}
	}
}