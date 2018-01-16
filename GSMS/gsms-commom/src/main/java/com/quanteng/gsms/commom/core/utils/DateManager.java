/**
 * 文 件 名:  DateManager
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/20 0020
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsms.commom.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <时间处理工具类>
 *
 * @author dyc
 * @version 2017/9/20 0020
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DateManager {

    /**
     * 将字符串时间转化为指定的时间格式
     *
     * @param strTime
     *            时间字符串
     * @param format
     *            时间格式(yyyy-MM-dd HH:mm:ss )
     * @return Date:转化后的日期
     * @throws ParseException
     *             转化异常
     */
    public static Date strToDate(String strTime, String format)
            throws ParseException
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date returnDate = formatter.parse(strTime);
        return returnDate;
    }

    /**
     * 将时间转化为指定的字符串格式
     *
     * @param date
     *            需要转化的时间
     * @param format
     *            转化的格式
     * @return String：转化后的字符串
     */
    public static String dateToString(Date date, String format)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String returnValue = formatter.format(date);
        return returnValue;
    }

    /**
     * 比较两时间的先后
     *
     * @param firstDate
     *            第一个比较的时间
     * @param secondDate
     *            第二个比较的时间
     * @return boolean:第一个时间在第二个时间之后返回true
     */
    public static boolean afterDate(Date firstDate, Date secondDate)
    {
        Calendar firstC = Calendar.getInstance();
        firstC.setTime(firstDate);
        Calendar secondC = Calendar.getInstance();
        secondC.setTime(secondDate);
        if (firstC.getTime().after(secondC.getTime()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 比较两时间的先后
     *
     * @param firstDate
     *            第一个比较的时间
     * @param secondDate
     *            第二个比较的时间
     * @return boolean:第一个时间在第二个时间之前返回true
     */
    public static boolean beforeDate(Date firstDate, Date secondDate)
    {
        Calendar firstC = Calendar.getInstance();
        firstC.setTime(firstDate);
        Calendar secondC = Calendar.getInstance();
        secondC.setTime(secondDate);
        if (firstC.getTime().before(secondC.getTime()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 为指定的日期添加天数
     *
     * @param date
     *            日期
     * @param days
     *            添加的天数
     * @return 添加天数后日期
     */
    public static Date addDays(Date date, int days)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 为指定的日期添加秒数
     *
     * @param date
     *            日期
     * @param seconds
     *            添加的秒数
     * @return 添加秒数后日期
     */
    public static Date addSeconds(Date date, int seconds)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    /**
     * 取得当前系统时间（WEB服务器）
     *
     * @param format
     *            String : 时间显示的格式
     * @return String : 用字符串表示的时间串
     */
    public static String getSystemTime(final String format)
    {
        return dateToString(new Date(System.currentTimeMillis()), format);
    }

    /**
     * 格式化时间字符串
     *
     * @param strDate
     *            时间字符串(必须以yyyy-MM-dd HH:mm:ss格式传入)
     * @param format
     *            显示的时间格式
     * @return 时间字符串
     */
    public static String formatDate(String strDate, String format)
    {
        try
        {
            Date date = strToDate(strDate, "yyyy-MM-dd HH:mm:ss");
            return dateToString(date, format);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     *
     * 获取下一年的时间
     *
     * @return String
     */
    public static String getNextYear()
    {
        Calendar cald = Calendar.getInstance();

        cald.add(Calendar.YEAR, 1);

        String addYearDate = dateToString(cald.getTime(), "yyyyMMddHHmmss");

        return addYearDate;
    }

    /**
     *
     * 校验字符串是否日期格式
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static boolean isDateFormatStr(String dateStr, String format)
    {
        try
        {
            Date checkDate = DateManager.strToDate(dateStr, format);
            String fmStr = DateManager.dateToString(checkDate, format);
            if (fmStr.equalsIgnoreCase(dateStr))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
