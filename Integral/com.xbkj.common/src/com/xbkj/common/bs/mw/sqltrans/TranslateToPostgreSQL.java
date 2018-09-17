package com.xbkj.common.bs.mw.sqltrans;

import java.util.ArrayList;

import com.xbkj.log.bs.logging.Logger;

/**
 * 
 * @author hgy
 * 
 */
public class TranslateToPostgreSQL extends TranslatorObject {

	// ������ձ�,�г�sqlServer������Oracle����Ķ�Ӧ��ϵ,
	private static final String[][] m_apsOracleFunctions = {
			{ "isnull", "coalesce" }, { "len", "length" } };

	Express_str express = new Express_str();

	public int INNERJOIN = 3;

	boolean isTrigger = false;

	public int LEFTJOIN = 1;

	public int RIGHTJOIN = 2;

	public TranslateToPostgreSQL() {
		super(POSTGRESQL);
		m_apsFunList = m_apsOracleFunctions;
		m_apiErrorList = m_apiOracleError;
	}

	public String getSql() throws Exception {
		Logger.setThreadState("postgresql translator : getSql");
		translateSql();
		String sResult = "";
		if (isTrigger) {
			sResult = m_sResorceSQL;
			sResult = sResult.replaceAll("\r\n", "\n");
		} else {
			if (m_sbDestinationSql == null)
				return null;
			sResult = m_sbDestinationSql.toString();
			if (sResult.endsWith(";")) {
				sResult = sResult.substring(0, sResult.length() - 1);
			}
			if (sResult.indexOf("ltrim_case") >= 0) {
				sResult = sResult.replaceAll("ltrim_case", "ltrim");
			}
			if (sResult.indexOf("rtrim_case") >= 0) {
				sResult = sResult.replaceAll("rtrim_case", "rtrim");
			}
		}
		Logger.setThreadState("postgresql translator : getSql Over");
		return sResult;
	}

	/**
	 * ת��Create���
	 */
	private void translateCreate() throws Exception {
		Logger.setThreadState("postgresql translator : translateCreate");
		m_sbDestinationSql = new StringBuffer("");
		for (int i = 0; i < m_asSqlWords.length; i++) {
			if (m_asSqlWords[i].equalsIgnoreCase("varchar")) {
				m_asSqlWords[i] = "varchar2";
			} else if (m_asSqlWords[i].equalsIgnoreCase("nvarchar")) {
				m_asSqlWords[i] = "varchar2";
			} else if (m_asSqlWords[i].equalsIgnoreCase("nchar")) {
				m_asSqlWords[i] = "char";
			} else if (m_asSqlWords[i].equalsIgnoreCase("float")) {
				m_asSqlWords[i] = "number";
			} else if (m_asSqlWords[i].equalsIgnoreCase("datetime")
					|| m_asSqlWords[i].equalsIgnoreCase("smalldatetime")) {
				m_asSqlWords[i] = "date";
			}
			m_sbDestinationSql.append(" " + m_asSqlWords[i]);
		}
		Logger.setThreadState("postgresql translator :translateCreate Over");
	}

	/**
	 * ת��Delete���
	 */
	private StringBuffer translateDelete(String[] asSqlWords) throws Exception {
		Logger.setThreadState("postgresql translator :translateDelete");
		Logger.setThreadState("postgresql translator :translateDelete Over");
		return translateSelect(asSqlWords);
	}

	/**
	 * ת��Drop���
	 */
	private void translateDrop() throws Exception {
		Logger.setThreadState("postgresql translator :translateDrop");
		m_sbDestinationSql = new StringBuffer(m_sResorceSQL);
		Logger.setThreadState("postgresql translator :translateDrop Over");
	}

	/**
	 * ��ݺ�����ձ���к���ת��
	 */
	private void translateFunction() throws Exception {
		Logger.setThreadState("postgresql translator :translateFunction");
		String sWord = null;

		int iOffSet = -1;

		while (iOffSet < m_asSqlWords.length) {
			iOffSet++;
			sWord = m_asSqlWords[iOffSet];
			if ((iOffSet + 1) >= m_asSqlWords.length)
				break;
			if (m_asSqlWords[iOffSet + 1].equals("(")) {
				m_asSqlWords[iOffSet] = getFunction(sWord);
				iOffSet++;
			}
		}
		Logger.setThreadState("postgresql translator :translateFunction Over");
	}

	/**
	 * ת��Insert���
	 */
	private StringBuffer translateInsert(String[] asSqlWords) throws Exception {
		Logger.setThreadState("postgresql translator :translateInsert");
		Logger.setThreadState("postgresql translator :translateInsert Over");
		return translateSelect(asSqlWords);
	}

	/**
	 * �������Ӹ��� �������ڣ�(00-6-9 8:38:35)
	 * 
	 * @return java.lang.String[]
	 * @param asSqlWords
	 *            java.lang.String[]
	 * 
	 *            ��ʽ�� ��һ������� update table1 set col1=b.col2 from table1 a, table2
	 *            b where a.col3=b.col3 -> update table1 a set col1=(select
	 *            b2.col2 from table2 b where a.col3=b.col3)
	 * 
	 *            �ڶ������ update table1 set col1=col1+����a, a.col2=b.col2+a.col2
	 *            from table1 a,table2 b
	 * 
	 *            where a.col3=b.col3 and a.col2=���� -> update table1 a set
	 *            col1=col1+����a,col2=(select b2.col2+a.col2 from table2 b
	 * 
	 *            where a.col3=b.col3 and a.col2=����b)
	 * 
	 *            where a.col2=����b
	 */
	public String[] translateJoinUpdate(String[] asSqlWords) throws Exception {
		Logger.setThreadState("postgresql translator :translateJoinUpdate");

		int iOffSet = 0; // ƫ����
		int iOffSet1 = 1; // ȡ�����ƫ����
		String[] asWords = asSqlWords;
		// String sSql = ""; //����ֵ
		boolean bFind = false;
		// String sLeftField = "";
		// String sRightField = "";
		// java.util.Vector vSetList = new java.util.Vector(); //���set���
		String sTableName = ""; // ����
		String sTableAlias = ""; // ��ı���
		// String s = ""; //�м����
		// Vector vecTable = new Vector();

		// �Ƿ�������Ӹ��£���ȡ�ø��±�ı���ͱ���
		if (iOffSet1 < asSqlWords.length && iOffSet1 + 5 < asSqlWords.length
				&& asSqlWords[iOffSet1].equals("/")
				&& asSqlWords[iOffSet1 + 1].equals("*")
				&& asSqlWords[iOffSet1 + 2].equals("+")) {
			iOffSet1 += 3;
			while (!asSqlWords[iOffSet1].equals("*")
					&& !asSqlWords[iOffSet1 + 1].equals("/")) {
				iOffSet1 += 1;
			}
			iOffSet1 += 2;
		}
		sTableName = asWords[iOffSet1];

		int iSelectNum = 0;
		int iFromNum = 0;

		// ����С�������ַ��ĳ���
		while (iOffSet < asWords.length) {
			// ����ǰ�ַ��ǡ�from��
			if (asWords[iOffSet].equalsIgnoreCase("from")) {
				iFromNum++;

				if (iFromNum > iSelectNum) {
					// ��¼��ǰλ��

					iOffSet++;
					// ����С�������ַ��ĳ���
					while (iOffSet < asWords.length) {
						// ����ǰ�ַ���ڱ���
						if (asWords[iOffSet].equalsIgnoreCase(sTableName)) {
							// ��ǰλ�ü�1
							// iOffSet++;
							// ����ǰ�ַ�Ϊ���ţ������ѭ��
							// if (iOffSet < asWords.length
							// && (asWords[iOffSet].equalsIgnoreCase(",")
							// || asWords[iOffSet].equalsIgnoreCase("where")))
							{
								if (iOffSet >= 1
										&& (asWords[iOffSet - 1]
												.equalsIgnoreCase(",") || asWords[iOffSet - 1]
												.equalsIgnoreCase("from"))) {
									// sTableAlias = asWords[iOffSet - 1];

									if (iOffSet + 1 < asWords.length) {
										if (asWords[iOffSet + 1]
												.equalsIgnoreCase("as")) {
											if (iOffSet + 2 < asWords.length) {
												sTableAlias = asWords[iOffSet + 2];
											}
										} else {
											if (!asWords[iOffSet + 1]
													.equals(",")
													&& !asWords[iOffSet + 1]
															.equalsIgnoreCase("where")
													&& !asWords[iOffSet + 1]
															.equalsIgnoreCase("left")
													&& !asWords[iOffSet + 1]
															.equalsIgnoreCase("right")
													&& !asWords[iOffSet + 1]
															.equals("(")
													&& !asWords[iOffSet + 1]
															.equals(")")) {
												sTableAlias = asWords[iOffSet + 1];
											}
										}
									}
								} /*
								 * else if (iOffSet >= 2 && (asWords[iOffSet -
								 * 2].equalsIgnoreCase(","))) { sTableAlias =
								 * ""; }
								 */
								else if (iOffSet >= 2
										&& (asWords[iOffSet - 2]
												.equalsIgnoreCase(",") || asWords[iOffSet - 2]
												.equalsIgnoreCase("from"))) {
									sTableName = asWords[iOffSet - 1];
									sTableAlias = asWords[iOffSet];
								} else if (iOffSet >= 3
										&& (asWords[iOffSet - 3]
												.equalsIgnoreCase(",") || asWords[iOffSet - 3]
												.equalsIgnoreCase("from"))) {
									sTableName = asWords[iOffSet - 2];
									sTableAlias = asWords[iOffSet];
								} /*
								 * else { //sTableName=asWords[iOffSet-2];
								 * sTableAlias = sTableName; }
								 */
								break;
							}

						} else {
							iOffSet++;
						}
					}
					break;
				}
			}

			if (asWords[iOffSet].equalsIgnoreCase("select")) {
				iSelectNum++;
			}

			iOffSet++;

		} // while����

		if (iFromNum > iSelectNum) {
			bFind = true;
		}
		if (!bFind) // û�з������Ӹ��»��Ӳ�ѯ
		{
			return asWords;
		} else {
			Logger
					.setThreadState("postgresql translator :translateJoinUpdate Over");
			return translateJoinUpdate(asSqlWords, sTableName, sTableAlias);
		}
	}

	public boolean isFunctionName(String sWord, String nextWord) {
		Logger
				.setThreadState("nc.bs.mw.sqltrans.TranslatorObject.isFunctionName");
		boolean isFunc = false;

		if (((sWord.equalsIgnoreCase("left") && !nextWord
				.equalsIgnoreCase("outer"))
				|| (sWord.equalsIgnoreCase("right") && !nextWord
						.equalsIgnoreCase("outer"))
				|| sWord.equalsIgnoreCase("square") // ��ǰ����Ϊ��square��
				|| sWord.equalsIgnoreCase("patindex")
				|| sWord.equalsIgnoreCase("convert")
				|| sWord.equalsIgnoreCase("dateadd") || sWord
				.equalsIgnoreCase("datediff")) // ����һ�������ǡ�(��
				&& nextWord.equals("(")) {
			isFunc = true;
		}
		Logger
				.setThreadState("nc.bs.mw.sqltrans.TranslatorObject.isFunctionName Over");
		return isFunc;
	}

	/**
	 * ����Select���,����: ����ת�� ����ת��(������) ת��CASE WHEN ��� ģʽƥ��
	 */
	private StringBuffer translateSelect(String[] asSqlWords) throws Exception {
		Logger.setThreadState("postgresql translator :translateSelect");
		int iOffSet = 0;
		String sSql = new String();
		String sWord = "";
		String sPreWord;

		boolean dontHaveWhere = true;

		// added by hegy
		int orderbyFrom = -1;
		int orderbyTo = -1;

		boolean ifTop = false;

		TransUnit aTransUnit = null;

		String rowNum = "";

		while (iOffSet < asSqlWords.length) {
			sPreWord = sWord;
			// ȡ�õ�ǰ����
			sWord = asSqlWords[iOffSet];

			// �ڴ˶Ժ�����д���
			// ���ǰ����Ϊ�������
			if ((iOffSet + 1 < asSqlWords.length)
					&& isFunctionName(sWord, asSqlWords[iOffSet + 1])) {
				aTransUnit = dealFunction(asSqlWords, sWord, iOffSet);

				iOffSet = aTransUnit.getIOffSet();

				if (iOffSet > asSqlWords.length - 1) {
					// ����Ƕ��
					return null;
				}
			}
			// ����Oracle�Ż��ؼ���
			else if (iOffSet < asSqlWords.length
					&& iOffSet + 5 < asSqlWords.length
					&& asSqlWords[iOffSet].equals("/")
					&& asSqlWords[iOffSet + 1].equals("*")
					&& asSqlWords[iOffSet + 2].equals("+")) {
				iOffSet += 3;
				m_sbDestinationSql.append(" /*+ ");
				while (!asSqlWords[iOffSet].equals("*")
						&& !asSqlWords[iOffSet + 1].equals("/")) {
					m_sbDestinationSql.append(asSqlWords[iOffSet] + " ");
					iOffSet += 1;
				}
				iOffSet += 2;
				m_sbDestinationSql.append(" */ ");
			}
			// (�ֶ�1,�ֶ�2) in (��)��֧��
			else if (sWord.equalsIgnoreCase("&&")) {
				m_sbDestinationSql.append(",");
				iOffSet += 1;
			}
			// ����PI()
			else if (iOffSet < asSqlWords.length
					&& sWord.equalsIgnoreCase("PI")
					&& asSqlWords[iOffSet + 1].equals("(")
					&& asSqlWords[iOffSet + 2].equals(")")) {
				m_sbDestinationSql.append(" 3.1415926535897931");
				iOffSet += 3;
			}
			// ����ȡģ%
			else if (iOffSet + 2 < asSqlWords.length
					&& asSqlWords[iOffSet + 1].equals("%")) {
				m_sbDestinationSql.append(" mod(" + sWord + ","
						+ asSqlWords[iOffSet + 2] + ")");
				iOffSet += 3;
			}
			// ����getdate()
			else if (iOffSet + 2 < asSqlWords.length
					&& sWord.equalsIgnoreCase("getdate")
					&& asSqlWords[iOffSet + 1].equals("(")
					&& asSqlWords[iOffSet + 2].equals(")")) {
				m_sbDestinationSql
						.append("  to_char(now(),'yyyy-mm-dd hh24:mi:ss')");
				iOffSet += 3;
			}
			// ����ģʽƥ���
			else if (iOffSet < asSqlWords.length
					&& sWord.equalsIgnoreCase("like")) {
				aTransUnit = dealLike(asSqlWords, sWord, iOffSet);
				iOffSet = aTransUnit.getIOffSet();
			}
			// ����top
			else if (sWord.equalsIgnoreCase("top")) {
				ifTop = true;
				rowNum = asSqlWords[iOffSet + 1];
				iOffSet += 2;

				// ����ǰһ������
				sPreWord = sWord;
				// ȡ�õ�ǰ����
				sWord = asSqlWords[iOffSet];
			}
			// �����Ӳ�ѯ
			else if (iOffSet < asSqlWords.length
					&& sWord.equalsIgnoreCase("select") && iOffSet > 0
					&& asSqlWords[iOffSet - 1].equalsIgnoreCase("(")) {
				aTransUnit = dealSelect(asSqlWords, sWord, iOffSet);

				iOffSet = aTransUnit.getIOffSet();
			} else if (sWord.equals(";")) {
				iOffSet++;
			} else if (sWord.equalsIgnoreCase("from")
					&& iOffSet < asSqlWords.length - 1
					&& asSqlWords[iOffSet + 1].equals("(")
					// && !asSqlWords[iOffSet + 2].equalsIgnoreCase("select")
					&& !getFirstTrueWord(asSqlWords, iOffSet).equalsIgnoreCase(
							"select")) {
				/*
				 * aTransUnit = dealFrom(asSqlWords, sWord, iOffSet);
				 * 
				 * iOffSet = aTransUnit.getIOffSet();
				 * 
				 * asSqlWords = aTransUnit.getSqlArray();
				 */
				asSqlWords = trimKuohao(asSqlWords, iOffSet + 1);
			} else {
				if (iOffSet < asSqlWords.length) {

					// added by hgy
					if (sWord.equals("order")
							&& (iOffSet + 1 < asSqlWords.length)) {
						if ("by".equals(asSqlWords[iOffSet + 1])) {
							orderbyFrom = m_sbDestinationSql.length();
						}
					}
					aTransUnit = dealOther(asSqlWords, sWord, iOffSet, ifTop,
							dontHaveWhere, rowNum, sPreWord);

					iOffSet = aTransUnit.getIOffSet();

					dontHaveWhere = aTransUnit.isDontHaveWhere();
					// added by hegy
					if (sWord.equals("by")
							&& "order".equals(asSqlWords[iOffSet - 1])) {
						orderbyTo = m_sbDestinationSql.length();
					}
				} else
					break;
			}
		}

		if (dontHaveWhere && ifTop) {
			m_sbDestinationSql.append(" limit "
					+ (Integer.valueOf(rowNum).intValue()));
		}

		Logger.setThreadState("postgresql translator :translateSelect Over");
		return m_sbDestinationSql;
	}

	/**
	 * ���������ͽ���ת��
	 */

	private void translateSql() throws Exception {
		Logger.setThreadState("postgresql translator :translateSql");
		if (m_asSqlWords == null) {
			m_sbDestinationSql = null;
			Logger.setThreadState("postgresql translator :translateSql Over");
			return;
		}

		m_sbDestinationSql = new StringBuffer();
		m_sLeftWhere = new String();
		m_sLeftTable = new String();

		isTrigger = (m_asSqlWords.length >= 2
				&& (m_asSqlWords[0].equalsIgnoreCase("create") || m_asSqlWords[0]
						.equalsIgnoreCase("replace")) && m_asSqlWords[1]
				.equalsIgnoreCase("trigger"))
				|| (m_asSqlWords.length >= 4 && m_asSqlWords[3]
						.equalsIgnoreCase("trigger"));

		if (!isTrigger) {
			translateFunction();
			switch (getStatementType()) {
			case SQL_SELECT:
				translateSelect(m_asSqlWords);
				break;
			case SQL_INSERT:
				translateInsert(m_asSqlWords);
				break;
			case SQL_CREATE:
				translateCreate();
				break;
			case SQL_DROP:
				translateDrop();
				break;
			case SQL_DELETE:
				translateDelete(m_asSqlWords);
				break;
			case SQL_UPDATE:
				translateUpdate(m_asSqlWords);
				break;
			}
		}
	}

	/**
	 * ת��Update���
	 */
	private StringBuffer translateUpdate(String[] asSqlWords) throws Exception {
		Logger.setThreadState("postgresql translator :translateUpdate");

		// �������Ӹ���
		//String[] asWords = translateJoinUpdate(asSqlWords);

		Logger.setThreadState("postgresql translator :translateUpdate Over");
		return translateSelect(asSqlWords);
	}

	private int[][] m_apiOracleError = { { 942, 208 }, // �����ͼ������
			{ 907, 2715 }, // �������
			{ 904, 207 }, // ��Ч������
			{ 398, 205 }, // ʹ��union������Ŀ���б�Ӧ������ͬ��Ŀ�ı��ʽ
			{ 516, 213 }, // ������ݺͱ�������Ͳ�һ��
			{ 2627, 1 }, // ���ܲ�����ͬ����ļ�¼
			{ 515, 1400 }, // ��ֵ����Ϊ��
			{ 8152, 1401 } // �����ֵ�����й��
	};

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-12-28 18:38:31)
	 * 
	 * @return nc.bs.mw.sqltrans.TransUnit
	 * @param asSqlWords
	 *            java.lang.String[]
	 * @param sWord
	 *            java.lang.String
	 * @param iOffSet
	 *            int
	 */
	public TransUnit dealFrom(String[] asSqlWords, String sWord, int iOffSet)
			throws Exception {
		Logger.setThreadState("postgresql translator :dealFrom");
		int includeIndex = iOffSet + 1;
		int includeNum = 0;

		while (asSqlWords[includeIndex].equals("(")
				&& !asSqlWords[includeIndex + 1].equalsIgnoreCase("select")) {
			includeNum++;
			includeIndex++;
		}

		int leftIndex = 0;
		int rightIndex = 0;

		includeIndex = iOffSet + 1;

		while (includeIndex < asSqlWords.length && includeNum > 0) {
			if (asSqlWords[includeIndex].equals("(")) {
				leftIndex++;
				if (leftIndex == 1) {
					asSqlWords[includeIndex] = "";
				}
			} else if (asSqlWords[includeIndex].equals(")")) {
				rightIndex++;
				if (rightIndex == leftIndex) {
					asSqlWords[includeIndex] = "";
					includeNum--;
					includeIndex = iOffSet + 1;
					leftIndex = 0;
					rightIndex = 0;
					break;
				}
			}
			includeIndex++;
		}

		m_sbDestinationSql.append(" " + asSqlWords[iOffSet]);

		iOffSet++;

		Logger.setThreadState("postgresql translator :dealFrom Over");
		return new TransUnit(asSqlWords, null, iOffSet);
	}

	private TransUnit dealFunction(String[] asSqlWords, String sWord,
			int iOffSet) throws Exception {
		Logger.setThreadState("postgres translator :dealFunction");

		ArrayList<String> vec = new ArrayList<String>();
		vec.add(asSqlWords[iOffSet]);
		iOffSet += 1;
		TransUnit aTransUnit = getSubSql(asSqlWords, "(", ")", iOffSet);
		String[] newFuncSql = aTransUnit.getSqlArray();
		iOffSet = aTransUnit.getIOffSet() + 1;

		for (int i = 0; i < newFuncSql.length; i++) {
			vec.add(newFuncSql[i]);
		}
		newFuncSql = new String[vec.size()];
		vec.toArray(newFuncSql);

		if (sWord.equalsIgnoreCase("left")) {
			translateFunLeft(newFuncSql);
		} else if (sWord.equalsIgnoreCase("right")) {
			translateFunRight(newFuncSql);
		} else if (sWord.equalsIgnoreCase("square")) {
			translateFunSquare(newFuncSql);
		} else if (sWord.equalsIgnoreCase("patindex")) {
			translateFunPatindex(newFuncSql);
		} else if (sWord.equalsIgnoreCase("convert")) {
			translateFunConvert(newFuncSql);
		} else if (sWord.equalsIgnoreCase("dateadd")) {
			translateFunDateAdd(newFuncSql);
		} else if (sWord.equalsIgnoreCase("datediff")) {
			translateFunDateDiff(newFuncSql);
		}

		Logger.setThreadState("postgresql translator :dealFunction Over");
		return new TransUnit(null, null, iOffSet);
	}

	private TransUnit dealLike(String[] asSqlWords, String sWord, int iOffSet)
			throws Exception {
		Logger.setThreadState("postgresql translator :dealLike");
		String s = "";

		if (iOffSet + 1 < asSqlWords.length) {
			s = asSqlWords[iOffSet + 1];
			m_sbDestinationSql.append(" like " + s);
			iOffSet += 2;
		} else {
			m_sbDestinationSql.append(" like ");
			iOffSet++;
		}

		Logger.setThreadState("postgresql translator :dealLike Over");
		return new TransUnit(null, null, iOffSet);
	}

	private TransUnit dealOther(String[] asSqlWords, String sWord, int iOffSet,
			boolean ifTop, boolean dontHaveWhere, String rowNum, String sPreWord)
			throws Exception {
		Logger.setThreadState("postgresql translator :dealOther");
		if (iOffSet < asSqlWords.length) {
			if (!sWord.equals(",")
					&& !(sWord.equals(")") && sPreWord.equals("("))
					&& !(sWord.equals("]") && sPreWord.equals("["))) {
				m_sbDestinationSql.append(" ");
			}
			// m_sbDestinationSql.append(" ");
			m_sbDestinationSql.append(asSqlWords[iOffSet]);

			iOffSet++;
		}
		TransUnit aTransUnit = new TransUnit(null, null, iOffSet);
		aTransUnit.setDontHaveWhere(dontHaveWhere);
		Logger.setThreadState("postgresql translator :dealOther Over");
		return aTransUnit;
	}

	private TransUnit dealSelect(String[] asSqlWords, String sWord, int iOffSet)
			throws Exception {
		Logger.setThreadState("postgresql translator :dealSelect");

		if (iOffSet < asSqlWords.length) {
			TransUnit aTransUnit = getSubSql(asSqlWords, "(", ")", iOffSet);
			String[] newCaseSql = aTransUnit.getSqlArray();
			iOffSet = aTransUnit.getIOffSet();

			String newSql[] = new String[newCaseSql.length - 1];

			for (int i = 0; i < newSql.length; i++) {
				newSql[i] = newCaseSql[i];
			}

			// �����Ӳ�ѯ
			TranslateToPostgreSQL nt = new TranslateToPostgreSQL();

			nt.setSqlArray(newSql);

			m_sbDestinationSql.append(nt.getSql());
		}

		Logger.setThreadState("postgresql translator :dealSelect Over");
		return new TransUnit(null, null, iOffSet);
	}

	private String dealWhenAnd(String[] whenSql) throws Exception {
		Logger.setThreadState("postgresql translator :dealWhenAnd");
		try {
			int offset = 0;
			ArrayList<String> vec = new ArrayList<String>();

			String str = "";

			// else
			if (offset < whenSql.length
					&& !whenSql[offset].equalsIgnoreCase("when")) {
				while (offset < whenSql.length) {
					str += whenSql[offset] + " ";
					offset++;
				}
				return str;
			}

			// ���� then ֮ǰ
			while (offset < whenSql.length
					&& !whenSql[offset].equalsIgnoreCase("then")) {
				if (!whenSql[offset].equalsIgnoreCase("when")
						&& !whenSql[offset].equalsIgnoreCase("and")) {
					str += whenSql[offset] + " ";
				} else {
					if (offset > 0) {
						vec.add(str);
						str = "";
					}
				}
				offset++;
			}

			// ���� then ֮��
			vec.add(str); // or then ֮��
			str = "";
			offset++;

			while (offset < whenSql.length) {
				str += whenSql[offset] + " ";
				offset++;
			}

			String re = "";
			for (int i = 0; i < vec.size(); i++) {
				String strin = vec.get(i).toString();

				if (i < vec.size() - 1) {
					re += "when" + " " + strin + " " + "then case ";
				} else {
					re += "when" + " " + strin + " " + "then " + str;
				}
			}

			for (int i = 0; i < vec.size() - 1; i++) {

				re += " end";

			}

			Logger.setThreadState("postgresql translator :dealWhenAnd Over");
			return re;
		} catch (Exception ex) {
			System.out.println(ex);
			throw ex;
		}
	}

	public String dealWhenOr(String[] whenSql) throws Exception {
		Logger.setThreadState("postgresql translator :dealWhenOr");
		try {
			int offset = 0;
			ArrayList<String> vec = new ArrayList<String>();

			String str = "";

			// else
			if (offset < whenSql.length
					&& !whenSql[offset].equalsIgnoreCase("when")) {
				while (offset < whenSql.length) {
					str += whenSql[offset] + " ";
					offset++;
				}
				return str;
			}
			// ���� then ֮ǰ
			while (offset < whenSql.length
					&& !whenSql[offset].equalsIgnoreCase("then")) {
				if (!whenSql[offset].equalsIgnoreCase("when")
						&& !whenSql[offset].equalsIgnoreCase("or")) {
					str += whenSql[offset] + " ";
				} else {
					if (offset > 0) // ���ǵ�һ�� when
					{
						vec.add(str);
						str = "";
					}
				}
				offset++;
			}

			// ���� then ֮��
			vec.add(str); // or then ֮��
			str = "";
			offset++;

			while (offset < whenSql.length) {
				str += whenSql[offset] + " ";
				offset++;
			}

			String re = "";
			for (int i = 0; i < vec.size(); i++) {
				String strin = vec.get(i).toString();

				re += "when" + " " + strin + " then " + str + " ";
			}
			Logger.setThreadState("postgresql translator :dealWhenOr Over");
			return re;
		} catch (Exception ex) {
			System.out.println(ex);
			throw ex;
		}
	}

	private String getFirstTrueWord(String[] asSqlWords, int iOffSet) {
		Logger.setThreadState("postgresql translator :getFirstTrueWord");
		String trueWord = "";

		for (int i = iOffSet + 1; i < asSqlWords.length; i++) {
			if (!asSqlWords[i].equals("(")) {
				trueWord = asSqlWords[i];
				break;
			}
		}
		Logger.setThreadState("postgresql translator :getFirstTrueWord Over");
		return trueWord;
	}

	private String preTranCaseWhen(String[] sql) throws Exception {
		Logger.setThreadState("postgresql translator :preTranCaseWhen");
		try {
			String re = "";
			int offset = 0;

			while (offset < sql.length) // ��ʽ�� when then֮������
			{
				if (sql[offset].equalsIgnoreCase("when")) {
					re += sql[offset] + " "; // +when

					offset++;

					String whenStr = "";

					while (offset < sql.length
							&& !sql[offset].equalsIgnoreCase("then")) {
						whenStr += sql[offset] + " ";

						offset++;
					}

					// translateSelect(parseSql(sSql)); //��ɭ��2001��10��18
					TranslateToOracle newTranslateToOracle = new TranslateToOracle();

					newTranslateToOracle.setSql(whenStr);

					whenStr = newTranslateToOracle.getSql();

					// Ԥ���?��ʽ��case and or��ʹ���Ϊָ���ĸ�ʽ
					whenStr = express.getValue(whenStr);

					re += whenStr + " ";

					re += sql[offset] + " "; // +then

					offset++;

				} else {
					if (offset < sql.length) {
						re += sql[offset] + " ";
					}

					offset++;
				}
			}

			try {
				String re_or = preTranCaseWhen_or(parseSql(re)); // �ȴ��� or
				// return preTranCaseWhen_and(parseSql(re_or)); //���� and
				Logger
						.setThreadState("postgresql translator :preTranCaseWhen Over");
				return re_or;
			} catch (Exception ex) {
				System.out.println(ex);
				throw ex;
			}
		} catch (Exception ex) {
			System.out.println(ex);
			throw ex;
		}
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-3-25 10:06:40)
	 * 
	 * @return java.lang.String//ԭ��䣬��case��ͷ ��end����
	 * @param sql
	 *            java.lang.String[]
	 * @exception java.lang.Throwable
	 *                �쳣˵����
	 */
	public String preTranCaseWhen_and(String[] sql) throws Exception {
		Logger.setThreadState("postgresql translator :preTranCaseWhen_and");
		try {
			int offset = 0;

			String result = "";

			while (offset < sql.length) // �����⣬else������û���
			{
				if (sql[offset].equalsIgnoreCase("when")
						|| sql[offset].equalsIgnoreCase("else")) {
					String when_str = sql[offset]; // �� when
					offset++;

					// δ������һ�� when else ǰ
					while ((offset < sql.length)
							&& (!sql[offset].equalsIgnoreCase("when"))
							&& (!sql[offset].equalsIgnoreCase("else"))) {
						// �������Ƕ��CASE WHEN
						if (sql[offset].equalsIgnoreCase("case")) {
							// ��ɭ��2001.3.23 �ӣ��ģ�����Ƕ�� case when
							int caseCount = 1; // case����
							int endCount = 0; // end����

							if (offset < sql.length) {
								String sSql = "";

								sSql += " " + sql[offset];

								// ��� case����= end����˵��Ƕ��û����
								while ((caseCount != endCount)
										&& (offset < sql.length - 1)) {
									offset++;

									if (sql[offset].equalsIgnoreCase("case")) {
										caseCount++;
									} else if (sql[offset]
											.equalsIgnoreCase("end")) {
										endCount++;
									}

									sSql += " " + sql[offset];

								}
								// sSql += " " + sql[offset];
								sSql = preTranCaseWhen_and(parseSql(sSql));
								when_str += (" " + sSql);
								offset++;

							} else
								break;
						} else // û������Ƕ��CASE WHEN
						{
							when_str += (" " + sql[offset]);
							offset++;
						}
					} // while ends
					result += (" " + dealWhenAnd(parseSql(when_str))); // deal
					// with
					// when
				} else {
					result += (" " + sql[offset]);
					offset++;
				}

			}
			Logger
					.setThreadState("postgresql translator :preTranCaseWhen_and Over");
			return result;
		} catch (Exception ex) {
			System.out.println(ex);
			throw ex;
		}
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-3-25 10:06:40)
	 * 
	 * @return java.lang.String//ԭ��䣬��case��ͷ ��end����
	 * @param sql
	 *            java.lang.String[]
	 * @exception java.lang.Throwable
	 *                �쳣˵����
	 */
	public String preTranCaseWhen_or(String[] sql) throws Exception {
		Logger.setThreadState("postgresql translator :preTranCaseWhen_or");
		try {
			int offset = 0;

			String result = "";

			while (offset < sql.length) // �����⣬else������û���
			{
				if (sql[offset].equalsIgnoreCase("when")
						|| sql[offset].equalsIgnoreCase("else")) {
					String when_str = sql[offset]; // �� when
					offset++;

					// δ������һ�� when else endǰ
					while ((offset < sql.length)
							&& (!sql[offset].equalsIgnoreCase("when"))
							&& (!sql[offset].equalsIgnoreCase("else"))
							&& (!sql[offset].equalsIgnoreCase("end"))) {
						// �������Ƕ��CASE WHEN
						if (sql[offset].equalsIgnoreCase("case")) {
							// ��ɭ��2001.3.23 �ӣ��ģ�����Ƕ�� case when
							int caseCount = 1; // case����
							int endCount = 0; // end����

							if (offset < sql.length) {
								String sSql = "";

								sSql += " " + sql[offset];

								// ��� case����= end����˵��Ƕ��û����
								while ((caseCount != endCount)
										&& (offset < sql.length - 1)) {
									offset++;

									if (sql[offset].equalsIgnoreCase("case")) {
										caseCount++;
									} else if (sql[offset]
											.equalsIgnoreCase("end")) {
										endCount++;
									}

									sSql += " " + sql[offset];

								}
								// sSql += " " + sql[offset];
								sSql = preTranCaseWhen_or(parseSql(sSql));
								when_str += (" " + sSql);
								offset++;

							} else
								break;
						} else // û������Ƕ��CASE WHEN
						{
							when_str += (" " + sql[offset]);
							offset++;
						}
					} // while ends
					result += (" " + dealWhenOr(parseSql(when_str))); // deal
					// with
					// when
				} else {
					result += (" " + sql[offset]);
					offset++;
				}

			}
			Logger.setThreadState("postgresql translator :preTranCaseWhen_or Over");
			return result;
		} catch (Exception ex) {
			System.out.println(ex);
			throw ex;
		}
	}

	/*
	 * ת��convert���� ����: asWords convert�����Ӿ�
	 * 
	 * ����: convert(char(n),f)->to_char(f) convert(date,f)->to_date(f)
	 */
	private void translateFunConvert(String[] asWords) throws Exception {
		Logger.setThreadState("postgresql translator :translateFunConvert");

		int iOff = 2;

		boolean charLenCtrl = false;
		boolean isDate = false;
		boolean isChar = false;
		String charLenth = null;
		String dataType = "";
		String col = "";
		// ȡ������Ĳ���
		String params[] = getFunParam(asWords, iOff, asWords.length - 1);
		dataType = params[0];
		col = params[1];
		dataType = dataType.trim();
		String oldDataType = dataType;
		// ȡ���������
		if (dataType.indexOf("(") > 0) {
			dataType = dataType.substring(0, dataType.indexOf("("));
		}
		// �����������
		if (isDateType(dataType)) {
			isDate = true;
			m_sbDestinationSql.append(" to_date(");
		} else if (isCharType(dataType)) {
			isChar = true;
			if (oldDataType.indexOf("(") > 0) {
				charLenCtrl = true;
				charLenth = oldDataType.substring(oldDataType.indexOf("(") + 1,
						oldDataType.length() - 1);
			}
			if (charLenCtrl)
				m_sbDestinationSql.append(" substring(to_char(");
			else
				m_sbDestinationSql.append(" to_char(");
		} else
			m_sbDestinationSql.append(" cast(");

		try {
			translateSelect(parseSql(col));

			if (isChar) {
				if (charLenCtrl)
					m_sbDestinationSql.append(") ,1," + charLenth + " )");
				else
					m_sbDestinationSql.append(" )");
			} else if (isDate) {
				m_sbDestinationSql.append(", 'yyyy-mm-dd')");
			} else
				m_sbDestinationSql.append(" as " + oldDataType + ")");

		} catch (Exception e) {
			throw e;
		}
		Logger
				.setThreadState("postgresql translator :translateFunConvert Over");
	}

	/*
	 * ת��DateAdd���� ����: asWords DateAdd�����Ӿ� ����: DATEADD ( datepart , number, date
	 * ) datepart��yy��mm��dd
	 */
	private void translateFunDateAdd(String[] asWords) throws Exception {
		Logger.setThreadState("postgresql translator :translateFunDateAdd");

		int iOff = 2;

		String params[] = getFunParam(asWords, iOff, asWords.length - 1);

		String dateType = params[0].trim();

		String theNumber = params[1].trim();

		String theDate = params[2].trim();

		TranslateToOracle newTranslateToOracle = new TranslateToOracle();

		newTranslateToOracle.setSql(theDate);

		theDate = newTranslateToOracle.getSql();
		theDate = theDate.trim();

		if (!(theDate.toLowerCase().startsWith("to_date(") && theDate
				.toLowerCase().indexOf("'yyyy-mm-dd'") > 0)) {
			if (theDate.toLowerCase().startsWith("sysdate"))
				theDate = "to_date(now())";
			else
				theDate = "to_date(to_char(" + theDate
						+ ",'yyyy-mm-dd'), 'yyyy-mm-dd')";
		}

		if (dateType.equalsIgnoreCase("yy")
				|| dateType.equalsIgnoreCase("yyyy")
				|| dateType.equalsIgnoreCase("year")) {
			m_sbDestinationSql.append(" ( " + theDate + " + interval '"
					+ theNumber + " years') ");
		} else

		if (dateType.equalsIgnoreCase("mm") || dateType.equalsIgnoreCase("m")
				|| dateType.equalsIgnoreCase("month")) {
			m_sbDestinationSql.append(" ( " + theDate + " + interval '"
					+ theNumber + " months') ");
		} else {
			m_sbDestinationSql.append(" ( " + theDate + " + interval '"
					+ theNumber + " days') ");
		}

		Logger
				.setThreadState("postgresql translator :translateFunDateAdd Over");
	}

	/*
	 * ת��DateDiff���� ����: asWords DateDiff�����Ӿ�
	 * 
	 * ����: DATEDIFF ( datepart , startdate , enddate )
	 * datepart֧�֣�yy��mm��dd��hh��mm��ss��
	 */
	private void translateFunDateDiff(String[] asWords) throws Exception {
		Logger.setThreadState("postgresql translator :translateFunDateDiff");

		int iOff = 2;

		String params[] = getFunParam(asWords, iOff, asWords.length - 1);

		String dateType = params[0].trim();

		String startDate = params[1].trim();

		String endDate = params[2].trim();

		TranslateToOracle newTranslateToOracle = new TranslateToOracle();

		newTranslateToOracle.setSql(startDate);

		startDate = newTranslateToOracle.getSql().trim();

		newTranslateToOracle.setSql(endDate);

		endDate = newTranslateToOracle.getSql().trim();

		if (!(startDate.toLowerCase().startsWith("to_date(") && startDate
				.toLowerCase().indexOf("'yyyy-mm-dd'") > 0)) {
			if (startDate.toLowerCase().startsWith("sysdate"))
				startDate = "to_date(now())";
			else
				startDate = "to_date(substring(" + startDate
						+ ",1,10), 'yyyy-mm-dd')";
		}

		if (!(endDate.toLowerCase().startsWith("to_date(") && endDate
				.toLowerCase().indexOf("'yyyy-mm-dd'") > 0)) {
			if (endDate.toLowerCase().startsWith("sysdate"))
				endDate = "to_date(now())";
			else
				endDate = "to_date(substring(" + endDate
						+ ",1,10), 'yyyy-mm-dd')";
		}

		if (dateType.equalsIgnoreCase("yy")
				|| dateType.equalsIgnoreCase("yyyy")
				|| dateType.equalsIgnoreCase("year")) {
			m_sbDestinationSql.append(" extract(year from  " + endDate
					+ ") - extract(year from  " + startDate + ")");
		} else if (dateType.equalsIgnoreCase("mm")
				|| dateType.equalsIgnoreCase("m")
				|| dateType.equalsIgnoreCase("month")) {
			m_sbDestinationSql.append(
					" extract(year from  age(" + endDate + "," + startDate
							+ ")) * 12").append(" + ");
			m_sbDestinationSql.append(" extract(month from  age(" + endDate
					+ "," + startDate + "))");
		} else {
			m_sbDestinationSql.append(" (" + endDate + "-" + startDate + ") ");
		}

		Logger
				.setThreadState("postgresql translator :translateFunDateDiff Over");
	}

	/**
	 * ת��left���� ����: asWords: left������� ����: left(str,n)->substr(str,1,4)
	 */
	private void translateFunLeft(String[] asWords) throws Exception {
		Logger.setThreadState("postgresql translator :translateFunLeft");

		String s = new String();
		int iOff = 2;
		int iLBracket = 0;
		int iRBracket = 0;

		s = "substring(";

		while (iOff < (asWords.length)) {
			if (asWords[iOff].equals("("))
				iLBracket++;
			if (asWords[iOff].equals(")"))
				iRBracket++;
			if ((iLBracket == iRBracket) && asWords[iOff + 1].equals(",")) {
				s += " " + asWords[iOff];
				iOff++;
				s += ",1";
			}
			s += " " + asWords[iOff];
			iOff++;
		}
		try {
			translateSelect(parseSql(s));
		} catch (Exception e) {
			System.out.println(e);
		}
		Logger.setThreadState("postgresql translator :translateFunLeft Over");
	}

	/**
	 * ת��patindex���� ����: asWords: patindex������� ����:
	 * patindex('%exp1%',exp2)->instr(exp2,'exp1',1,1)
	 */
	private void translateFunPatindex(String[] asWords) throws Exception {
		Logger.setThreadState("postgresql translator :translateFunPatindex");

		String sSql = new String();
		int iOff = 2;
		String sWord = new String();
		sSql = "position(";

		sWord = asWords[iOff];
		if (sWord.length() > 4 && asWords[iOff + 1].equals(",")) {
			String s = "";
			s += "'" + sWord.substring(2, sWord.length() - 2) + "'";
			sSql = sSql + s + " in ";
			iOff += 2;
			while (iOff < asWords.length) {
				if (!asWords[iOff].equals(")")) {
					sSql += " " + asWords[iOff];
					iOff++;
				} else {
					sSql += ")";
					break;
				}
			}
			try {
				translateSelect(parseSql(sSql));
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			for (int i = 0; i < asWords.length; i++) {
				m_sbDestinationSql.append(asWords[i]);
			}
		}
		Logger
				.setThreadState("postgresql translator :translateFunPatindex Over");
	}

	/**
	 * ת��right���� ����: asWords: right�����Ӿ� ����:
	 * right(f,n)->substr(f,length(f)-n+1,n)
	 */
	private void translateFunRight(String[] asWords) throws Exception {
		Logger.setThreadState("postgresql translator :translateFunRight");
		String s = new String();
		int iOff = 2;
		int iLBracket = 0;
		int iRBracket = 0;
		s = "substring(";
		while (iOff < (asWords.length)) {
			if (asWords[iOff].equals("("))
				iLBracket++;
			if (asWords[iOff].equals(")"))
				iRBracket++;
			if ((iLBracket == iRBracket) && asWords[iOff + 1].equals(",")) {
				s += " " + asWords[iOff];
				iOff++;
				s += ",length(";
				for (int i = 2; i < iOff; i++) {
					s += " " + asWords[i];
				}
				s += ")-";
				for (int j = iOff + 1; j < asWords.length - 1; j++) {
					s += " " + asWords[j];
				}
				s += "+1";
			}
			s += " " + asWords[iOff];
			iOff++;
		}
		try {
			translateSelect(parseSql(s));
		} catch (Exception e) {
			System.out.println(e);
		}
		Logger.setThreadState("postgresql translator :translateFunRight Over");
	}

	/**
	 * ת��Rtrim���� ����: asSqlWords: Ltrim�����Ӿ� ����: Ltrim(str)->Ltrim(str,' ')
	 */
	private void translateFunRound(String[] asSqlWords) throws Exception {
		Logger.setThreadState("postgresql translator :translateFunRound");
		int iOff = 2;
		int iLBracket = 0;
		int iRBracket = 0;
		int commaCount = 0;
		int doubleQuotationCount = 0;
		int singleQuotationCount = 0;

		int firstCommaIndex = 0;
		int secondCommaIndex = 0;

		String theNumber = "";
		String theLength = "";
		String theTyle = "";

		TranslateToOracle newTranslateToOracle = null;

		while (iOff < asSqlWords.length - 1) {
			if (asSqlWords[iOff].equals("(")) {
				iLBracket++;
			}
			if (asSqlWords[iOff].equals(")")) {
				iRBracket++;
			}
			if (asSqlWords[iOff].equals("\'")) {
				singleQuotationCount++;
			}
			if (asSqlWords[iOff].equals("\"")) {
				doubleQuotationCount++;
			}
			if (asSqlWords[iOff].equals(",") && iRBracket == iLBracket
					&& doubleQuotationCount % 2 == 0
					&& singleQuotationCount % 2 == 0) {
				commaCount++;
				if (commaCount == 1) {
					firstCommaIndex = iOff;

					for (int i = 2; i < iOff; i++) {
						theNumber += " " + asSqlWords[i];
					}

					if (iOff - 2 > 1) {
						if (newTranslateToOracle == null) {
							newTranslateToOracle = new TranslateToOracle();
						}

						newTranslateToOracle.setSql(theNumber);

						theNumber = newTranslateToOracle.getSql();
					}
				} else {
					secondCommaIndex = iOff;

					for (int i = firstCommaIndex + 1; i < iOff; i++) {
						theLength += " " + asSqlWords[i];
					}

					if (iOff - (firstCommaIndex + 1) > 1) {
						if (newTranslateToOracle == null) {
							newTranslateToOracle = new TranslateToOracle();
						}

						newTranslateToOracle.setSql(theLength);

						theLength = newTranslateToOracle.getSql();
					}
				}
			}
			iOff++;
		}

		String s = " ";

		if (commaCount == 0) {
			for (int i = 0; i < asSqlWords.length; i++) {
				s += asSqlWords[i];
			}
		}

		else if (commaCount == 1) {
			for (int i = firstCommaIndex + 1; i < asSqlWords.length - 1; i++) {
				theLength += " " + asSqlWords[i];
			}

			if ((asSqlWords.length - 1) - (firstCommaIndex + 1) > 1) {
				if (newTranslateToOracle == null) {
					newTranslateToOracle = new TranslateToOracle();
				}

				newTranslateToOracle.setSql(theLength);

				theLength = newTranslateToOracle.getSql();
			}

			s = " round(" + theNumber + ", " + theLength + ") ";
		} else {
			for (int i = secondCommaIndex + 1; i < asSqlWords.length - 1; i++) {
				theTyle += " " + asSqlWords[i];
			}

			if ((asSqlWords.length - 1) - (secondCommaIndex + 1) > 1) {
				if (newTranslateToOracle == null) {
					newTranslateToOracle = new TranslateToOracle();
				}

				newTranslateToOracle.setSql(theTyle);

				theTyle = newTranslateToOracle.getSql();
			}

			int tyle = Integer.valueOf(theTyle.trim()).intValue();

			if (tyle == 0) {
				s = " round(" + theNumber + ", " + theLength + ") ";
			} else {
				s = " floor(" + theNumber + "*(power(10," + theLength
						+ ")))/(power(10," + theLength + ")) ";
			}
		}
		m_sbDestinationSql.append(s);
		Logger.setThreadState("postgresql translator :translateFunRound Over");
	}

	/**
	 * ת��Rtrim���� ����: asSqlWords: Rtrim�����Ӿ� ����: Rtrim(str)->Rtrim(str,' ')
	 */
	private void translateFunRtrim(String[] asSqlWords) throws Exception {
		Logger.setThreadState("postgresql translator :translateFunRtrim");
		int iOff = 2;
		int iLBracket = 0;
		int iRBracket = 0;
		String s = new String();

		m_sbDestinationSql.append(" rtrim(");
		while (iOff < asSqlWords.length) {
			if (asSqlWords[iOff].equals("(")) {
				iLBracket++;
			}
			if (asSqlWords[iOff].equals(")")) {
				iRBracket++;
			}
			if (iLBracket == iRBracket && asSqlWords[iOff + 1].equals(")")) {
				if (iLBracket > 0) {
					for (int i = 2; i < iOff + 1; i++) {
						s += " " + asSqlWords[i];
					}
					try {
						translateSelect(parseSql(s));
					} catch (Exception e) {
						System.out.println(e);
					}
				} else {
					for (int i = 2; i < iOff + 1; i++) {
						m_sbDestinationSql.append(" " + asSqlWords[i]);
					}
				}
				m_sbDestinationSql.append(",' ')");
				break;
			} else if (asSqlWords[asSqlWords.length - 1].equals(")")) {
				// ����Ƕ��
				String[] newFunSql = new String[asSqlWords.length - (iOff + 1)];
				for (int index = 0; index < newFunSql.length; index++) {
					newFunSql[index] = asSqlWords[iOff];
					iOff++;
				}
				translateSelect(newFunSql);
				// zhangsd modify at 2002-09-01
				if (!newFunSql[newFunSql.length - 1].equalsIgnoreCase("' '")
						&& !newFunSql[newFunSql.length - 1]
								.equalsIgnoreCase(",")) {
					m_sbDestinationSql.append(",' '");
				}
				m_sbDestinationSql.append(" )");
				break;
			} else {
				m_sbDestinationSql.append(asSqlWords[iOff]);
				iOff++;
				throw new Exception(asSqlWords[0] + "��Ĳ���ƥ��!");
			}
		}
		Logger.setThreadState("postgresql translator :translateFunRtrim Over");
	}

	/**
	 * ת��Square���� ����: asWords square�����Ӿ� ����: square(f)->power(f,2)
	 */
	private void translateFunSquare(String[] asWords) throws Exception {
		Logger.setThreadState("postgresql translator :translateFunSquare");
		String s = new String();
		int iOff = 2;
		int iLBracket = 0;
		int iRBracket = 0;
		s += "power(";
		while (iOff < (asWords.length)) {
			if (asWords[iOff].equals("("))
				iLBracket++;
			if (asWords[iOff].equals(")"))
				iRBracket++;
			if ((iLBracket == iRBracket) && asWords[iOff + 1].equals(")")) {
				s += " " + asWords[iOff];
				iOff++;
				s += ",2";
			}
			s += " " + asWords[iOff];
			iOff++;
		}
		try {
			translateSelect(parseSql(s));
		} catch (Exception e) {
			System.out.println(e);
		}
		Logger.setThreadState("postgresql translator :translateFunSquare Over");
	}

	private String[] translateJoinUpdate(String[] asSqlWords,
			String sTableName, String sTableAlias) throws Exception {
		Logger.setThreadState("postgresql translator :translateJoinUpdate");

		int iOffSet = 0; // ƫ����
		String[] asWords = asSqlWords;
		String sSql = ""; // ����ֵ
		String m_Sql = ""; // ��ʱsql
		String sLeftField = "";
		String sRightField = "";
		ArrayList<String> vSetList = new ArrayList<String>(); // ���set���
		String s = ""; // �м����
		ArrayList<String> vecTable = new ArrayList<String>();
		String whereSql = "";
		String m_whereSql = "";
		String fromSt = "";
		// Vector vSql = new Vector(); //���sql���

		int iJoinCount = 0; // join�ĸ���
		int iSingleCount = 0; // ���������
		String subfromSt = "";
		ArrayList<String> vWhList = new ArrayList<String>(); // ���where���
		String inSql1 = "";
		String inSql2 = "";
		String andSql = "";
		boolean bExist = true;
		String[] asTables = null;
		iOffSet = -1; // ƫ�����˻�
		// ȡ��from��ı���
		asTables = parseTable(asSqlWords, sTableName, sTableAlias);
		// ����С�������ַ��ĳ��ȼ�1
		while (iOffSet < asWords.length - 1) {
			// �����1
			iOffSet++;

			// ����ǰ�ַ��ǡ�set��
			if (asWords[iOffSet].equalsIgnoreCase("set")) {
				String str = "";
				String setsql = "";
				int setcount = 0;
				// �������Ϊ�գ����¼֮

				if (!sTableAlias.equalsIgnoreCase("")
						&& !sTableAlias.equalsIgnoreCase(sTableName)) {
					sSql += " " + sTableAlias;
					// vSql.addElement(sTableAlias);
				}

				sSql += " set";
				// vSql.addElement("set");
				iOffSet++;

				int leftCount = 0; // ��������
				int rightCount = 0; // ��������

				// ����ǰ�ַ��ǡ�from������ѭ��
				while (iOffSet < asWords.length
						&& !asWords[iOffSet].equalsIgnoreCase("from")) {
					if (asWords[iOffSet].equalsIgnoreCase("(")) {
						leftCount++;
					} else if (iOffSet < asWords.length
							&& asWords[iOffSet].equalsIgnoreCase(")")) {
						rightCount++;
						if (leftCount == rightCount) {
							leftCount = 0;
							rightCount = 0;
						}
					}

					// ����ǰ�ַ��Ƕ���
					if (iOffSet < asWords.length
							&& asWords[iOffSet].equalsIgnoreCase(",")
							&& leftCount == rightCount) {
						// ��¼�ۼ���ݣ����ۼ������
						vSetList.add(str);
						str = "";
						iOffSet++;
					} else {
						// �ۼƵ�ǰ�ַ�
						str += " " + asWords[iOffSet];
						iOffSet++;
					}
				}
				// ��¼�ۼ����
				vSetList.add(str);

				// ֻ��һ�н��и���
				// ��ֻ��һ�����
				{ // ���и���
					int i0 = 0;
					// ����С�ڼ�¼�������ȣ���ѭ��
					while (i0 < vSetList.size()) {
						// ȡ�õ�ǰ�ַ�
						s = (String) vSetList.get(i0);
						// ȡ�õȺŵ�λ��
						int start = s.indexOf("=");
						if (haveTab(s.substring(start + 1), asTables)) { // �����ֱ���ͱ���
							iJoinCount++;
							if (iJoinCount > 1) {
								sLeftField += "," + s.substring(0, start); // ��ʱ�������������ƴ�ӵ�sql���
								sRightField += "," + s.substring(start + 1);
							} else {
								sLeftField += " " + s.substring(0, start);
								sRightField += " " + s.substring(start + 1);
							}
						} else {
							setcount++;
							if (setcount > 1) {
								setsql += "," + s;
							} else {
								setsql += " " + s;
							}
						}
						i0++;
					}
				} // //���и��� else ����

				// ������������ϼ�������0
				if (iSingleCount > 0 && iJoinCount > 0) {
					sSql += ",";
					// vSql.addElement(",");
				}

				// �����������0�����ϼ������0
				// if (iSingleCount > 0 || iJoinCount > 0)
				if (setcount == 0) {
					if (iJoinCount > 1)
						sSql += "(" + sLeftField + ")=(select " + sRightField; // ����ʱ������������䣬ƴ�ӵ�sql���
					else if (iJoinCount == 1)
						sSql += "" + sLeftField + "=(select " + sRightField;
				} else {
					if (iJoinCount > 1)
						sSql += setsql + ",(" + sLeftField + ")=(select "
								+ sRightField;
					else if (iJoinCount == 1)
						sSql += setsql + "," + sLeftField + "=(select "
								+ sRightField;
					else
						sSql += setsql;
				}
			} // if (asWords[iOffSet].equalsIgnoreCase("set"))����

			// �����±�����from�Ӿ����޳�oracle��֧�֣�
			// ����ǰ�ַ��ǡ�from��
			if (iOffSet < asWords.length
					&& asWords[iOffSet].equalsIgnoreCase("from")) {
				int f_leftCount = 1;
				int f_rightCount = 0;
				iOffSet++;
				ArrayList<String> aNewVec = new ArrayList<String>();
				while (iOffSet < asWords.length
						&& !asWords[iOffSet].equalsIgnoreCase("where")) {
					// /////////////

					if (asWords[iOffSet].equalsIgnoreCase("(")) {
						subfromSt = "(";
						while ((f_leftCount != f_rightCount)
								&& (iOffSet < asWords.length)) {
							iOffSet++;

							if (asWords[iOffSet].equalsIgnoreCase("(")) {
								f_leftCount++;
							} else if (asWords[iOffSet].equalsIgnoreCase(")")) {
								f_rightCount++;
							}

							subfromSt += " " + asWords[iOffSet];

						}

						aNewVec.add(subfromSt);
						iOffSet++;
					}
					// ////////////
					aNewVec.add(asWords[iOffSet]);
					iOffSet++;
				}

				for (int newIndex = 0; newIndex < aNewVec.size(); newIndex++) {
					String othtable = aNewVec.get(newIndex).toString();
					String trueName = othtable;
					boolean isOth = false;

					if (!othtable.equalsIgnoreCase(sTableName)) {
						isOth = true;
						if (vecTable.size() > 0) {
							fromSt += ",";
						}
						fromSt += " " + othtable;
						vecTable.add(othtable);
					}
					newIndex++;
					if (newIndex < aNewVec.size()) {
						othtable = aNewVec.get(newIndex).toString();
						if (othtable.equalsIgnoreCase("as")) {
							newIndex++;
							othtable = aNewVec.get(newIndex).toString();
						}

						if (!othtable.equalsIgnoreCase(",")) {
							if (isOth) {
								fromSt += " " + othtable;
								vecTable.add(othtable);
							} else {
								if (sTableAlias != null
										&& sTableAlias.trim().length() > 0) {
									if (!othtable.equalsIgnoreCase(sTableAlias)) {
										if (vecTable.size() > 0) {
											fromSt += ",";
										}
										fromSt += trueName + " " + othtable;

										vecTable.add(trueName);
										vecTable.add(othtable);
									}
								}
							}
							newIndex++;
						}
					}
				}

				if (fromSt.trim().length() > 0) {
					fromSt = " from " + fromSt;
				}

				if (fromSt.endsWith(",")) {
					fromSt = fromSt.substring(0, fromSt.length() - 1);
				}
				if (iJoinCount > 0) {
					sSql += fromSt;
				}

			}
			if (iOffSet < asWords.length
					&& asWords[iOffSet].equalsIgnoreCase("where")) {
				int w_leftCount = 0;
				int w_rightCount = 0;
				int inCount = 0;
				int andCount = 0;
				int whereCount = 0;
				int i1 = 0;
				boolean isExist = false;
				String sw = "";
				String s1 = "";
				String w_leftField = "";
				String w_rightField = "";
				iOffSet++;
				m_Sql = sSql;
				m_Sql += " where";
				m_whereSql += " where";
				while (iOffSet < asWords.length) {
					m_Sql += " " + asWords[iOffSet];
					m_whereSql += " " + asWords[iOffSet];
					if (asWords[iOffSet].equalsIgnoreCase("(")) {
						w_leftCount++;
					} else if (iOffSet < asWords.length
							&& asWords[iOffSet].equalsIgnoreCase(")")) {
						w_rightCount++;
						if (w_leftCount == w_rightCount) {
							w_leftCount = 0;
							w_rightCount = 0;
						}
					}

					// �ֲ�where�������
					if (iOffSet < asWords.length
							&& (asWords[iOffSet].equalsIgnoreCase("and") || asWords[iOffSet]
									.equalsIgnoreCase("or"))
							&& w_leftCount == w_rightCount) {
						// ��¼�ۼ���ݣ����ۼ������
						vWhList.add(sw);
						vWhList.add(asWords[iOffSet]);
						sw = "";
						iOffSet++;
					} else {
						// �ۼƵ�ǰ�ַ�
						sw += " " + asWords[iOffSet];
						iOffSet++;
					}
				}
				// ��¼�ۼ����
				vWhList.add(sw);

				// ����С�ڼ�¼�������ȣ���ѭ��
				while (i1 < vWhList.size()) {
					// ȡ�õ�ǰ�ַ�
					s1 = (String) vWhList.get(i1);
					// �ж��������������Ĵ���
					if (s1.indexOf("!=") > 0 || s1.indexOf("! =") > 0
							|| s1.indexOf("<") > 0 || s1.indexOf(">") > 0) {
						isExist = true;
					}

					if (!s1.trim().startsWith("(") && !isExist) {
						// ȡ�õȺŵ�λ��
						int start = s1.indexOf("=");

						if (start > 0) {
							// �����ֱ���ͱ���
							w_leftField = s1.substring(0, start);
							w_rightField = s1.substring(start + 1);
							if ((isMasterTab(w_leftField, sTableName) || isMasterTab(
									w_leftField, sTableAlias))
									&& isMasterTab(asTables, w_rightField)) {
								inCount++;
								if (inCount > 1) {
									inSql1 += "," + w_leftField; // ��ʱ�������������ƴ�ӵ�sql���
									inSql2 += "," + w_rightField;
								} else {
									inSql1 += " " + w_leftField;
									inSql2 += " " + w_rightField;
								}
							} else if ((isMasterTab(w_rightField, sTableName) || isMasterTab(
									w_rightField, sTableAlias))
									&& isMasterTab(asTables, w_leftField)) {
								inCount++;
								if (inCount > 1) {
									inSql1 += "," + w_rightField; // ��ʱ�������������ƴ�ӵ�sql���
									inSql2 += "," + w_leftField;
								} else {
									inSql1 += " " + w_rightField;
									inSql2 += " " + w_leftField;
								}
							} else if (isMasterTab(asTables, w_leftField)) {
								whereCount++;
								if (whereCount > 1) {
									whereSql += " "
											+ (String) vWhList.get(i1 - 1)
											+ " " + s1;
								} else {
									whereSql += " " + s1;
								}
							} else if (isMasterTab(w_leftField, sTableName)
									|| isMasterTab(w_leftField, sTableAlias)) {
								// else{
								andCount++;
								if (andCount > 1) {
									andSql += " "
											+ (String) vWhList.get(i1 - 1)
											+ " " + s1;
								} else {
									andSql += " " + s1;
								}
							} else {
								bExist = false;
								break;
							}
						} else { // ����"="��������"is ( not ) null"��
							String firstWord = parseWord(s1);
							if (haveTab(firstWord, asTables)) {
								whereCount++;
								if (whereCount > 1) {
									whereSql += " "
											+ (String) vWhList.get(i1 - 1)
											+ " " + s1;
								} else {
									whereSql += " " + s1;
								}
							} else if (haveTab(firstWord, sTableName)
									|| haveTab(firstWord, sTableAlias)) {
								// else{
								andCount++;
								if (andCount > 1) {
									andSql += " "
											+ (String) vWhList.get(i1 - 1)
											+ " " + s1;
								} else {
									andSql += " " + s1;
								}
							} else {
								bExist = false;
								break;
							}
						}
					} else { // ��"()"�����������������������������ڵ�����
						if ((haveTab(s1, sTableName) || haveTab(s1, sTableAlias))
								&& haveTab(s1, asTables)) {
							bExist = false;
							break;
						} else if (haveTab(s1, asTables)) {
							whereCount++;
							if (whereCount > 1) {
								whereSql += " " + (String) vWhList.get(i1 - 1)
										+ " " + s1;
							} else {
								whereSql += " " + s1;
							}

						} else if (haveTab(s1, sTableName)
								|| haveTab(s1, sTableAlias)) {
							andCount++;
							if (andCount > 1) {
								andSql += " " + (String) vWhList.get(i1 - 1)
										+ " " + s1;
							} else {
								andSql += " " + s1;
							}
						} else {
							bExist = false;
							break;
						}
					}
					i1 += 2;
				}
			}

			if (iOffSet < asWords.length) {
				if (asWords[iOffSet].equalsIgnoreCase(sTableName)
						|| asWords[iOffSet].equalsIgnoreCase(sTableAlias)) {
					sSql += " " + sTableName;
				} else {
					sSql += " " + asWords[iOffSet];
				}

			}
		}

		m_Sql += ")";
		if (iJoinCount > 0) {
			sSql = m_Sql;
		}
		if (bExist) {
			if (andSql.trim() != null && andSql.trim().length() > 0
					&& inSql1.trim() != null && inSql1.trim().length() > 0
					&& whereSql.trim() != null && whereSql.trim().length() > 0) {
				sSql += " where " + andSql + " and (" + inSql1
						+ ") in ( select " + inSql2 + " " + fromSt + " where "
						+ whereSql + " )";
			} else if (andSql.trim() != null && andSql.trim().length() > 0
					&& inSql1.trim() != null && inSql1.trim().length() > 0) {
				sSql += " where " + andSql + " and (" + inSql1
						+ ") in ( select " + inSql2 + " " + fromSt + " )";
			} else if (inSql1.trim() != null && inSql1.trim().length() > 0
					&& whereSql.trim() != null && whereSql.trim().length() > 0) {
				sSql += " where  (" + inSql1 + ") in ( select " + inSql2 + " "
						+ fromSt + " where " + whereSql + " )";
			} else if (inSql1.trim() != null && inSql1.trim().length() > 0) {
				sSql += " where  (" + inSql1 + ") in ( select " + inSql2 + " "
						+ fromSt + " )";
			} else if (andSql.trim() != null && andSql.trim().length() > 0) {
				sSql += " where " + andSql;
			}
		} else {
			if (m_whereSql != null && m_whereSql.trim().length() > 0) {
				sSql += " where exists( select 1 " + fromSt + " " + m_whereSql
						+ " )";
			}
		}
		asWords = parseSql(sSql);

		Logger
				.setThreadState("postgresql translator :translateJoinUpdate Over");
		return asWords;
	}

	/**
	 * ת�� CASE WHEN ��� ����: asSqlWords CaseWhen��� ����: destSql Ŀ����� ����: case s0
	 * when exp1 then exp2 when exp3 then exp4 ->
	 * decode(s0,exp1,exp2,exp3,exp4,exp5) else exp5 end
	 * 
	 * case when s0 is null then exp1 when s0=exp2 then exp3 when s0<=exp4 then
	 * exp5 ->
	 * decode(s0,null,exp1,exp2,exp3,least(s0,exp4),exp5,GREATEST(s0,exp6
	 * ),exp7,exp8) when s0>=exp6 then exp7 else exp8 end
	 */
	private String translateSimpleCaseWhen(String[] asSqlWords)
			throws Exception {
		Logger.setThreadState("postgresql translator :translateSimpleCaseWhen");
		int iOff = 0;

		String s1 = "decode(";
		iOff++;

		// ��һ���ַ����ڡ�when������һ�����

		// ����ǰλ�õ��ַ��ǡ�end��
		while ((iOff < asSqlWords.length)
				&& !asSqlWords[iOff].equalsIgnoreCase("end")) {
			// ����ǰ�ַ��ǡ�when������then����else��
			if ((asSqlWords[iOff].equalsIgnoreCase("when"))
					|| (asSqlWords[iOff].equalsIgnoreCase("then"))
					|| (asSqlWords[iOff].equalsIgnoreCase("else"))) {
				// ���϶���
				s1 = s1 + ",";

			} else { // ���򣬼��ϵ�ǰ�ַ�
				s1 = s1 + " " + asSqlWords[iOff];
			}
			iOff++;
		}
		// ��������
		s1 = s1 + ")";

		// return m_sbDestinationSql;
		Logger
				.setThreadState("postgresql translator : translateSimpleCaseWhen Over");
		return s1;
	}

	private String[] trimKuohao(String[] asSqlWords, int iOffSet) {
		Logger.setThreadState("postgresql translator : trimKuohao");
		int start = iOffSet;
		int left = 0;
		int right = 0;
		while (asSqlWords[iOffSet].equals("(")) {
			for (int i = iOffSet; i < asSqlWords.length; i++) {
				if (asSqlWords[i].equals("(")) {
					left++;
				}
				if (asSqlWords[i].equals(")")) {
					right++;
				}
				if (left == right) {
					asSqlWords[iOffSet] = "";
					asSqlWords[i] = "";
					iOffSet++;
					break;
				}
			}
		}
		ArrayList<String> vec = new ArrayList<String>();

		for (int i = 0; i < start; i++) {
			vec.add(asSqlWords[i]);
		}

		for (int i = start; i < asSqlWords.length; i++) {
			if (asSqlWords[i] != null && asSqlWords[i].trim().length() > 0) {
				vec.add(asSqlWords[i]);
			}
		}
		String[] stArray = new String[vec.size()];
		vec.toArray(stArray);
		Logger.setThreadState("postgresql translator : trimKuohao Over");
		return stArray;
	}

	String m_TabName = null;

}
