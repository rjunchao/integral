package com.xbkj.common.util;

import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.crypto.Data;

import org.apache.commons.lang.time.FastDateFormat;

import com.informix.msg.cals_en_US;

public class DateUtil
{
  public static final String DATE_FORMAT_10 = "yyyy-MM-dd";
  public static final String DATE_FORMAT_8 = "yyyyMMdd";
  public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
  private static Pattern PATTERN_DATA_10 = Pattern.compile("\\d{4}+[-]\\d{2}+[-]\\d{2}+");

  private static int DAY_SECONDS = 86400000;
  private static int HOUR_SECONDS = 3600000;

  private static final int[] monthDays = { 0, 31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

  private static Map<Integer, String> WEEK_MAP = null;

  //得到年份
  public static String getYear(){
	  return Calendar.getInstance().get(Calendar.YEAR)+"";
  }
  //得到月
  public static String getMonth(){
	  return Calendar.getInstance().get(Calendar.MONTH)+"";
  }
  //得到日
  public static String getDay(){
	  return Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+"";
  }
  
  
  public static Calendar parseCalendar(Date date)
  {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar;
  }

  public static Calendar parseCalendar(String data_date)
  {
    int[] yMDArr = parseDateYMDArr(data_date);
    Calendar calendar = Calendar.getInstance();
    calendar.set(1, yMDArr[0]);
    calendar.set(2, yMDArr[1]);
    calendar.set(5, yMDArr[2]);

    return calendar;
  }

  public static FastDateFormat formatData(String formatStyle)
  {
    return FastDateFormat.getInstance(formatStyle);
  }

  public static String parseDateStr(Calendar calen)
  {
    return formatData("yyyy-MM-dd").format(calen);
  }

  public static String getCurrDateTime(String fomatStr)
  {
    return FastDateFormat.getInstance(fomatStr).format(Calendar.getInstance());
  }

  public static String getCurrDateTime()
  {
    return FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance());
  }

  public static String getCurrDate()
  {
    return FastDateFormat.getInstance("yyyy-MM-dd").format(Calendar.getInstance());
  }
  //得到时间
  public static String getCurrTime()
  {
	  return FastDateFormat.getInstance("HH:mm:ss").format(Calendar.getInstance());
  }

  public static String getCurrDate(String dateFormat)
  {
    return FastDateFormat.getInstance(dateFormat).format(Calendar.getInstance());
  }

  public static String moveDate(String data_date, int moveNum, char unit)
  {
    if ((ToolUtil.isNullStr(data_date)) || (data_date.length() != 10)) {
      return "";
    }
    Calendar gc = parseCalendar(data_date);
    switch (unit) {
    case 'M':
    case 'm':
      gc.add(2, moveNum);
      break;
    case 'D':
    case 'd':
      gc.add(5, moveNum);
      break;
    case 'Y':
    case 'y':
      gc.add(1, moveNum);
    }

    return parseDateStr(gc);
  }

  public static Date moveDate(Date date, int moveNum, char unit)
  {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);

    switch (unit) {
    case 'M':
    case 'm':
      cal.add(2, moveNum);
      break;
    case 'D':
    case 'd':
      cal.add(5, moveNum);
      break;
    case 'Y':
    case 'y':
      cal.add(1, moveNum);
    }

    return cal.getTime();
  }

  public static boolean isLastDay(String data_date, char unit)
    throws ParseException
  {
    if ((ToolUtil.isNullStr(data_date)) || (data_date.length() < 10)) {
      return false;
    }
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Date date = format.parse(data_date);
    Calendar gc = Calendar.getInstance();
    gc.setTime(date);

    if (gc.get(5) != gc.getActualMaximum(5)) {
      return false;
    }

    switch (unit) {
    case 'M':
    case 'm':
      return true;
    case 'Q':
    case 'q':
      if ((gc.get(2) + 1) % 3 != 0) break;
      return true;
    case 'Y':
    case 'y':
      if (gc.get(2) + 1 != 12) break;
      return true;
    default:
      return false;
    }
    return false;
  }

  public static int getDaysBetweenTwoDates(String startDate, String endDate)
  {
    if ((ToolUtil.isNullStr(startDate)) || (ToolUtil.isNullStr(endDate))) {
      return -1;
    }
    Calendar startCalen = parseCalendar(startDate);
    Calendar endCalen = parseCalendar(endDate);
    long quot = 0L;
    quot = endCalen.getTimeInMillis() - startCalen.getTimeInMillis();
    quot = quot / 1000L / 60L / 60L / 24L;
    return Integer.valueOf(String.valueOf(quot)).intValue();
  }

  public static int compareToDate(String startDate, String endDate)
  {
    return getDaysBetweenTwoDates(startDate, endDate);
  }

  private static int[] parseDateYMDArr(String data_date)
  {
    int year = Integer.valueOf(data_date.substring(0, 4)).intValue();
    int month = Integer.valueOf(data_date.substring(5, 7)).intValue() - 1;
    int day = Integer.valueOf(data_date.substring(8, 10)).intValue();
    int[] yMDArr = { year, month, day };
    return yMDArr;
  }

  public static boolean isValidDate10(String dataDate)
  {
    boolean flag = true;
    if ((ToolUtil.isNullStr(dataDate)) || (dataDate.length() != 10)) {
      flag = false;
    } else {
      Matcher match = PATTERN_DATA_10.matcher(dataDate);
      if (match.matches()) {
        int[] yMDArr = parseDateYMDArr(dataDate);
        int year = yMDArr[0];
        int month = yMDArr[1] + 1;
        int day = yMDArr[2];
        if ((month < 1) || (month > 12)) {
          flag = false;
        } else {
          if (isLeapYear(year))
            monthDays[2] = 29;
          else {
            monthDays[2] = 28;
          }
          int monthLength = monthDays[month];
          if ((day < 1) || (day > monthLength))
            flag = false;
        }
      }
      else {
        flag = false;
      }
    }
    return flag;
  }

  private static boolean isLeapYear(int year)
  {
    return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
  }

  public static String[] getDataDatePeriod(String dataDate, int type)
  {
    String[] dataDateArr = new String[2];
    String start = null;
    String end = null;
    String year = dataDate.substring(0, 4);
    String month = dataDate.substring(5, 7);

    if (type == 1) {
      Calendar calen = Calendar.getInstance();
      calen.setFirstDayOfWeek(2);
      calen.setTime(parseCalendar(dataDate).getTime());
      calen.set(7, 2);
      start = parseDateStr(calen);
      end = moveDate(start, 6, 'D');
    }
    else if (type == 2) {
      start = year + "-" + month + "-" + "01";
      end = year + "-" + month + "-" + getMonthLastDay(dataDate);
    } else if (type == 3) {
      start = year + "-" + month + "-" + "01";
      switch (Integer.parseInt(month)) {
      case 1:
      case 2:
      case 3:
        start = year + "-" + "01" + "-" + "01";
        end = year + "-" + "03" + "-" + "31";
        break;
      case 4:
      case 5:
      case 6:
        start = year + "-" + "04" + "-" + "01";
        end = year + "-" + "06" + "-" + "30";
        break;
      case 7:
      case 8:
      case 9:
        start = year + "-" + "07" + "-" + "01";
        end = year + "-" + "09" + "-" + "30";
        break;
      case 10:
      case 11:
      case 12:
        start = year + "-" + "10" + "-" + "01";
        end = year + "-" + "12" + "-" + "31";
      default:
        break;
      } } else if (type == 4) {
      switch (Integer.parseInt(month)) {
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
        start = year + "-" + "01" + "-" + "01";
        end = year + "-" + "06" + "-" + "30";
        break;
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
        start = year + "-" + "07" + "-" + "01";
        end = year + "-" + "12" + "-" + "31";
      default:
        break;
      } } else if (type == 5) {
      start = year + "-" + "01" + "-" + "01";
      end = year + "-" + "12" + "-" + "31";
    } else {
      start = dataDate;
      end = dataDate;
    }
    dataDateArr[0] = start;
    dataDateArr[1] = end;
    return dataDateArr;
  }

  public static int getMonthLastDay(String dataDate)
  {
    int[] yearMD = parseDateYMDArr(dataDate);
    int monthDay = 0;
    int month = yearMD[1] + 1;
    if (month == 2) {
      if (isLeapYear(yearMD[0]))
        monthDay = 29;
      else
        monthDay = 28;
    }
    else {
      monthDay = monthDays[month];
    }
    return monthDay;
  }

  public static Date getCurDate()
  {
    Calendar cal = parseCalendar(new Date());
    cal.set(11, 0);
    cal.set(12, 0);
    cal.set(13, 0);
    cal.set(14, 0);
    return cal.getTime();
  }

  public static int compareToDate(Date date1, Date date2)
  {
    long dateTime1 = date1.getTime();
    long dateTime2 = date2.getTime();
    if (dateTime1 > dateTime2)
      return 1;
    if (dateTime1 == dateTime2) {
      return 0;
    }
    return -1;
  }

  public static String getDaysTime(Date startDate, Date endDate)
  {
    long day = 0L;
    long hour = 0L;
    long min = 0L;
    long sec = 0L;
    long startTime = startDate.getTime();
    long endTime = endDate.getTime();
    long diff;
    if (startTime < endTime)
      diff = endTime - startTime;
    else {
      diff = startTime - endTime;
    }
    day = diff / DAY_SECONDS;
    hour = diff / HOUR_SECONDS - day * 24L;
    min = diff / 60000L - day * 24L * 60L - hour * 60L;
    sec = diff / 1000L - day * 24L * 60L * 60L - hour * 60L * 60L - min * 60L;
    StringBuffer strTime = new StringBuffer(56);
    if (day > 0L) {
      strTime.append(day).append("天");
      strTime.append(hour).append("小时");
      strTime.append(min).append("分");
      strTime.append(sec).append("秒");
    }
    else if (hour > 0L) {
      strTime.append(hour).append("小时");
      strTime.append(min).append("分");
      strTime.append(sec).append("秒");
    }
    else if (min > 0L) {
      strTime.append(min).append("分");
      strTime.append(sec).append("秒");
    } else {
      strTime.append(sec).append("秒");
    }

    return strTime.toString();
  }

  public static String getDayOfWeekCn(Integer dayOfWeek)
  {
    if (ToolUtil.isNull(WEEK_MAP)) {
      WEEK_MAP = new HashMap<Integer,String>();
      WEEK_MAP.put(Integer.valueOf(1), "星期日");
      WEEK_MAP.put(Integer.valueOf(2), "星期一");
      WEEK_MAP.put(Integer.valueOf(3), "星期二");
      WEEK_MAP.put(Integer.valueOf(4), "星期三");
      WEEK_MAP.put(Integer.valueOf(5), "星期四");
      WEEK_MAP.put(Integer.valueOf(6), "星期五");
      WEEK_MAP.put(Integer.valueOf(7), "星期六");
    }
    return (String)WEEK_MAP.get(dayOfWeek);
  }

  public static String getCurrMonth()
  {
    return String.valueOf(getCurDate().getMonth());
  }

  public static void main(String[] args)
    throws ParseException
  {
    System.out.println(getCurrDateTime("yyyyMMddHHmmss"));
  }
}