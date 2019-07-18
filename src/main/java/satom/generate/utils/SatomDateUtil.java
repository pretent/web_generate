package satom.generate.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * 日期操作的工具类，建议优先使用 {@link org.apache.commons.lang3.time.DateUtils} 和
 * {@link org.apache.commons.lang3.time.DateFormatUtils} 中的方法对日期对象和日历对象进行操作
 *
 * @author chenweiming
 * @date 2016年12月9日 下午3:24:40
 * @since jdk 1.8
 */
@SuppressWarnings("all")
public class SatomDateUtil {


    /** 日期格式：{@value} */
    public static final String   DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /** 日期格式：{@value} */
    public static final String   DEFAULT_DATE_FORMAT     = "yyyy-MM-dd";

    /** 日期格式：{@value} */
    public static final String   ZH_DATETIME_FORMAT      = "yyyy年M月d日 H时m分s秒";

    /** 日期格式：{@value} */
    public static final String   ZH_DATE_FORMAT          = "yyyy年M月d日";

    /** 日期格式：{@value} */
    public static final String   RFC3339_FORMAT          = "yyyy-MM-dd'T'HH:mm:ss";

    /** 日期格式：{@value} */
    public static final String   RFC3339_0Z_FORMAT       = "yyyy-MM-dd'T'HH:mm:ss.S'Z'";

    /** 日期格式：{@value} */
    public static final String   RFC3339_SSS_FORMAT      = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    /** 日期格式：{@value} */
    public static final String   SHORT_DATE_FORMAT       = "yyMMdd";

    /** 日期格式：{@value} */
    public static final String   YYYYMMDD_FORMAT         = "yyyyMMdd";

    /** 日期格式：{@value} */
    public static final String   SECOND_FORMAT           = "yyyyMMddHHmmss";

    /** 日期格式：{@value} */
    public static final String   YYMMDDHHMMSS            = "yyMMddHHmmss";

    /** 日期格式：{@value} */
    public static final String   MILLISECOND_FORMAT      = "yyyyMMddHHmmssSSS";

    /** 日期格式：{@value} */
    public static final String   YYYY_FORMAT             = "yyyy";

    /**
     * 将字符串转换成 {@link java.util.Date} 对象时，支持的日期格式：
     * <ul>
     * <li>yyyy-MM-dd HH:mm:ss.SSS</li>
     * <li>yyyy-MM-dd HH:mm:ss</li>
     * <li>yyyy-MM-dd HH:mm</li>
     * <li>yyyy-MM-dd</li>
     * <li>yyyy-MM-dd'T'HH:mm:ss.S'Z'</li>
     * <li>yyyy-MM-dd'T'HH:mm:ss.SSS</li>
     * <li>yyyy-MM-dd'T'HH:mm:ss</li>
     * <li>yyyy-MM-dd'T'HH:mm</li>
     * <li>yyyy/MM/dd HH:mm:ss.SSS</li>
     * <li>yyyy/MM/dd HH:mm:ss</li>
     * <li>yyyy/MM/dd HH:mm</li>
     * <li>yyyy/MM/dd</li>
     * <li>yyyy年MM月dd日 HH时mm分ss秒</li>
     * <li>yyyy年MM月dd日 HH时mm分</li>
     * <li>yyyy年MM月dd日</li>
     * <li>yyyyMMddHHmmssSSS</li>
     * <li>yyyyMMddHHmmss</li>
     * <li>yyyyMMddHHmm</li>
     * <li>yyyyMMdd</li>
     * </ul>
     */
    public static final String[] SUPPORT_FORMATS         = new String[] { "yyyy-MM-dd HH:mm:ss.SSS",
                                                                          "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
                                                                          "yyyy-MM-dd", "yyyy-MM-dd'T'HH:mm:ss.S'Z'",
                                                                          "yyyy-MM-dd'T'HH:mm:ss.SSS",
                                                                          "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm",
                                                                          "yyyy/MM/dd HH:mm:ss.SSS",
                                                                          "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
                                                                          "yyyy/MM/dd", "yyyy年MM月dd日 HH时mm分ss秒",
                                                                          "yyyy年MM月dd日 HH时mm分", "yyyy年MM月dd日",
                                                                          "yyyyMMddHHmmssSSS", "yyyyMMddHHmmss",
                                                                          "yyyyMMddHHmm", "yyyyMMdd", "yyyy" };

    /**
     * 将日期格式化为 {@value #DEFAULT_DATETIME_FORMAT} 形式的字符串
     * 
     * @param date 格式化后的日期字符串，如果值为 {@code null}，则返回 {@code null}
     * @return 格式化后的日期字符串
     * @since 2.1.2-SNAPSHOT
     * @see org.apache.commons.lang3.time.DateFormatUtils#format(Date, String)
     */
    public static String format(Date date) {
        return (date == null) ? null : DateFormatUtils.format(date, DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 将日期格式化为 {@value #DEFAULT_DATE_FORMAT} 形式的字符串
     * 
     * @param date 要格式化的日期对象，值为 <code>null</code> 时，返回“无”
     * @return 格式化后的日期字符串，如果传入的 {@code date} 为 <code>null</code>，则，返回“无”
     * @since 2.1.2-SNAPSHOT
     */
    public static String formatWithDefault(Date date) {
        return format(date, DEFAULT_DATE_FORMAT, "无");
    }

    /**
     * 日期增加
     * 
     * @param date
     * @param year
     * @param month
     * @param day
     * @return
     * @author chenweiming
     * @date 2017年4月22日 下午5:35:40
     */
    public static Date addDate(Date date, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(c.get(c.YEAR) + year, c.get(c.MONTH) + month, c.get(c.DATE) + day);
        return c.getTime();
    }

    /**
     * 格式化日期
     * 
     * @param date 要格式化的日期对象，为 <code>null</code> 时，返回 {@code defaultValue}
     * @param pattern 日期格式
     * @return 格式化后的日期字符串，如果传入的 {@code date} 为 <code>null</code>，则返回 {@code defaultValue}
     * @since 2.1.2-SNAPSHOT
     * @see org.apache.commons.lang3.time.DateFormatUtils#format(Date, String)
     */
    public static String format(Date date, String pattern) {
        return format(date, pattern, null);
    }

    /**
     * 格式化日期
     * 
     * @param date 要格式化的日期对象，为 <code>null</code> 时，返回 {@code defaultValue}
     * @param pattern 日期格式
     * @param defaultValue {@code date} 为 {@code null} 时返回的默认值
     * @return 格式化后的日期字符串，如果传入的 {@code date} 为 <code>null</code>，则返回 {@code defaultValue}
     * @since 2.1.2-SNAPSHOT
     * @see org.apache.commons.lang3.time.DateFormatUtils#format(Date, String)
     */
    public static String format(Date date, String pattern, String defaultValue) {
        return (date == null) ? defaultValue : DateFormatUtils.format(date, pattern);
    }

    /**
     * 将日期字符串解析成日期对象
     * 
     * @param date 日期字符串，允许的格式为：
     * <ul>
     * <li>yyyy-MM-dd HH:mm:ss</li>
     * <li>yyyy-MM-dd</li>
     * <li>yyyy年M月d日H点m分s秒</li>
     * <li>yyyy年M月d日</li>
     * <li>yyyy-MM-dd'T'HH:mm:ss</li>
     * <li>yyyy-MM-dd'T'HH:mm:ss.S'Z'</li>
     * <li>yyyy-MM-dd'T'HH:mm:ss.SSS</li>
     * <li>yyMMdd</li>
     * <li>yyyyMMdd</li>
     * <li>yyyyMMddHHmmss</li>
     * <li>yyMMddHHmmss</li>
     * <li>yyyyMMddHHmmssSSS"</li>
     * </ul>
     * @return 对应的日期对象，如果解析失败，则返回 <code>null</code>
     * @see #parse(String, String...)
     * @since 2.1.2-SNAPSHOT
     * @see #parse(String, String...)
     */
    public static Date parse(String date) {
        return parse(date, DEFAULT_DATETIME_FORMAT, DEFAULT_DATE_FORMAT, ZH_DATETIME_FORMAT, ZH_DATE_FORMAT,
                     RFC3339_FORMAT, RFC3339_0Z_FORMAT, RFC3339_SSS_FORMAT, SHORT_DATE_FORMAT, YYYYMMDD_FORMAT,
                     SECOND_FORMAT, YYMMDDHHMMSS, MILLISECOND_FORMAT);
    }

    /**
     * 解析日期
     * 
     * @param date 指定的日期字符串
     * @param format 日期格式，可以使用本类中的一些静态变量，如 ：<code>DEFAULT_DATETIME_FORMAT</code>
     * @return Date 返回对应的日期对象，如果解析失败，则返回 <code>null</code>
     * @see org.apache.commons.lang3.time.DateUtils#parseDate(String, String...)
     */
    public static Date parse(String date, String... format) {
        Date dt = null;
        try {
            dt = DateUtils.parseDate(date, format);
        } catch (ParseException e) {
        }
        return dt;
    }

    /**
     * 取得当月第一天0时0分0秒
     * 
     * @return 当月第一天0时0分0秒
     * @since 2.1.2-SNAPSHOT
     * @see #getFirstDayOfMonth(Date)
     */
    public static Date getFirstDayOfMonth() {
        return getFirstDayOfMonth(Calendar.getInstance().getTime());
    }

    /**
     * 取得指定日期所属月份的第一天0时0分0秒
     * 
     * @param date 日期对象
     * @return {@code date} 所属月份第一天0时0分0秒
     * @since 2.1.2-SNAPSHOT
     * @see org.apache.commons.lang3.time.DateUtils#truncate(Date, int)
     */
    public static Date getFirstDayOfMonth(Date date) {
        return DateUtils.truncate(date, Calendar.MONTH);
    }

    /**
     * 取得当月第一天当月第一天0时0分0秒的字符串形式，返回的格式为 {@value #DEFAULT_DATETIME_FORMAT}
     * 
     * @return 当月第一天当月第一天0时0分0秒的字符串形式
     * @since 2.1.2-SNAPSHOT
     * @see #getFirstDayOfMonthAsString(Date)
     */
    public static String getFirstDayOfMonthAsString() {
        return getFirstDayOfMonthAsString(Calendar.getInstance().getTime());
    }

    /**
     * 取得指定日期所属月份的第一天0时0分0秒的字符串形式，返回的格式为 {@value #DEFAULT_DATETIME_FORMAT}
     * 
     * @param date 日期对象
     * @return {@code date} 所属月份第一天0时0分0秒的字符串形式
     * @since 2.1.2-SNAPSHOT
     * @see #getFirstDayOfMonth(Date)
     * @see org.apache.commons.lang3.time.DateFormatUtils#format(Date, String)
     */
    public static String getFirstDayOfMonthAsString(Date date) {
        return DateFormatUtils.format(getFirstDayOfMonth(date), DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 取得当月最后一天23时59分59秒
     * 
     * @return 当月最后一天23时59分59秒
     * @since 2.1.2-SNAPSHOT
     * @see #getLastDayOfMonth(Date)
     */
    public static Date getLastDayOfMonth() {
        return getLastDayOfMonth(Calendar.getInstance().getTime());
    }

    /**
     * 取得指定日期所属月份的最后一天23时59分59秒
     * 
     * @param date 日期对象
     * @return 当月最后一天23时59分59秒
     * @since 2.1.2-SNAPSHOT
     * @see org.apache.commons.lang3.time.DateUtils#ceiling(Calendar, int)
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        // 获取到下月第一天0时0分0秒
        c = DateUtils.ceiling(c, Calendar.MONTH);
        // 往前推移一微秒，即获取到了本月最后一天23时59分59秒
        c.add(Calendar.MILLISECOND, -1);
        return c.getTime();
    }

    /**
     * 取得当月最后一天23时59分59秒的字符串形式，返回的格式为 {@value DEFAULT_DATETIME_FORMAT}
     * 
     * @return 当月最后一天23时59分59秒的字符串形式
     * @since 2.1.2-SNAPSHOT
     * @see #getLastDayOfMonthAsString(Date)
     */
    public static String getLastDayOfMonthAsString() {
        return getLastDayOfMonthAsString(Calendar.getInstance().getTime());
    }

    /**
     * 取得当月最后一天23时59分59秒的字符串形式，返回的格式为 {@value DEFAULT_DATETIME_FORMAT}
     * 
     * @param date 日期对象
     * @return {@code date} 所属月份最后一天23时59分59秒的字符串形式
     * @since 2.1.2-SNAPSHOT
     * @see #getLastDayOfMonth(Date)
     * @see org.apache.commons.lang3.time.DateFormatUtils#format(Date, String)
     */
    public static String getLastDayOfMonthAsString(Date date) {
        return DateFormatUtils.format(getLastDayOfMonth(), DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 返回 begin 到 end 相隔天数 1.只比较日期，不管时分秒 2.当begin在end之后时候返回负数
     * 
     * @param begin 开始日期
     * @param end 结束日期
     * @return 开始日期和结束日期之间相隔的天数
     * @author chenweiming
     **/
    public static int getDaysBetween(Date begin, Date end) {
        if ((null != begin) && (null != end)) {
            long dateFromTime = DateUtils.truncate(begin, Calendar.DATE).getTime();
            long dateToTime = DateUtils.truncate(end, Calendar.DATE).getTime();
            long dateRange = (dateToTime - dateFromTime) / DateUtils.MILLIS_PER_DAY;
            return (int) dateRange;
        }
        return 0;
    }

    /**
     * 获取某年某月某一周第一天
     * 
     * @param year
     * @param month
     * @param week
     * @return
     * @author pretent
     * @date 2017年4月7日 下午1:52:23
     */
    public static Date getWeekFirstDay(int year, int month, int week) {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, year);// 设置年
        cal.set(Calendar.MONTH, month - 1);// 设置月
        if (week > 0) {
            cal.set(Calendar.WEEK_OF_MONTH, week);// 设置星期
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        } else {
            cal.set(Calendar.DAY_OF_MONTH, 1);// 设置月
        }
        return cal.getTime();
    }

    /**
     * 返回 begin 到 end 之间的时间差
     * 
     * @param begin 开始日期
     * @param end 结束日期
     * @return 开始日期和结束日期之间时间差 , 如：1天 1小时 1分 1秒
     * @author chenweiming
     * @date 2017年4月22日 下午5:34:15
     */
    public static String getTimesBetween(Date begin, Date end) {
        String timeRangeStr = "";
        if ((null != begin) && (null != end)) {
            if (begin.after(end)) {
                timeRangeStr = "时间比较错误，开始时间小于结束时间！";
                return timeRangeStr;
            }
            long dateFromTime = DateUtils.truncate(begin, Calendar.SECOND).getTime();
            long dateToTime = DateUtils.truncate(end, Calendar.SECOND).getTime();
            long timeRange = (dateToTime - dateFromTime) / DateUtils.MILLIS_PER_SECOND; // 转换成秒
            long day = timeRange / (24 * 3600);
            long hour = timeRange % (24 * 3600) / 3600;
            long minute = timeRange % 3600 / 60;
            long second = timeRange % 60 / 60;

            if (0 == day) {
                if (0 == hour) {
                    if (0 == minute) {
                        timeRangeStr = "" + second + "秒";
                    } else {
                        timeRangeStr = "" + minute + "分" + second + "秒";
                    }
                } else {
                    timeRangeStr = "" + hour + "小时" + minute + "分" + second + "秒";
                }
            } else {
                timeRangeStr = "" + day + "天" + hour + "小时" + minute + "分" + second + "秒";
            }
            return timeRangeStr;
        } else {
            timeRangeStr = "时间为空值！";
        }
        return timeRangeStr;
    }

    /**
     * 返回 begin 到 end 之间的时间差
     * 
     * @param begin 开始日期
     * @param end 结束日期
     * @return 开始日期和结束日期之间时间差，为妙
     * @author chenweiming
     * @date 2017年4月22日 下午5:29:41
     */
    public static long getTimesBetweenIsSecond(Date begin, Date end) {
        if ((null != begin) && (null != end)) {
            if (begin.after(end)) {
                return -1;
            }
            long dateFromTime = DateUtils.truncate(begin, Calendar.SECOND).getTime();
            long dateToTime = DateUtils.truncate(end, Calendar.SECOND).getTime();
            long timeRange = (dateToTime - dateFromTime) / DateUtils.MILLIS_PER_SECOND; // 转换成秒

            return timeRange;
        } else {
            return -1;
        }
    }

    /**
     * 日期转化为汉字格式
     * 
     * @param date 要进行格式化的日期
     * @return 传入日期的汉字版（eg.二O一四年十二月五日）
     * @author chenweiming
     * @date 2017年4月22日 下午5:29:16
     */
    public static String dataToUpper(Date date) {
        if (date == null) {
            return "";
        }
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        int year = ca.get(Calendar.YEAR);
        int month = ca.get(Calendar.MONTH) + 1;
        int day = ca.get(Calendar.DAY_OF_MONTH);
        return numToUpper(year) + "年" + monthToUppder(month) + "月" + dayToUppder(day) + "日";
    }

    // 将数字转化为大写
    private static String numToUpper(int num) {
        // String u[] = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
        String[] u = { "O", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        char[] str = String.valueOf(num).toCharArray();
        String rstr = "";
        for (int i = 0; i < str.length; i++) {
            rstr = rstr + u[Integer.parseInt(str[i] + "")];
        }
        return rstr;
    }

    // 月转化为大写
    private static String monthToUppder(int month) {
        if (month < 10) {
            return numToUpper(month);
        } else if (month == 10) {
            return "十";
        } else {
            return "十" + numToUpper(month - 10);
        }
    }

    // 日转化为大写
    private static String dayToUppder(int day) {
        if (day < 20) {
            return monthToUppder(day);
        } else {
            char[] str = String.valueOf(day).toCharArray();
            if (str[1] == '0') {
                return numToUpper(Integer.parseInt(str[0] + "")) + "十";
            } else {
                return numToUpper(Integer.parseInt(str[0] + "")) + "十" + numToUpper(Integer.parseInt(str[1] + ""));
            }
        }
    }
}
