package cn.com.hiss.www.multilib.utils;
import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class HissDate {

    /**
     * 服务器默认Locale
     */
    private  static Locale serverLocale = Locale.CHINA;

    /**
     * 服务器默认的时区
     */
    private static TimeZone tz = TimeZone.getTimeZone("GMT+8");

    /**
     * 用户选择的时区
     */
    private static TimeZone userTz = TimeZone.getTimeZone("GMT+8");

    /**
     * 格式：yyyy-MM-dd
     */
    public static final String DATE_FORMAT_YMD_DEFAULT = "yyyy-MM-dd";

    /**
     * 格式：yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_FORMAT_YMDHMS_DEFAULT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT_IM_RECENT = "MM-dd HH:mm";

    public static final String DATE_FORMAT_GROUP_CREATE_TIME = "yyyy年MM月dd日";

    /**
     * 格式：12小时 yyyyMMddhhmmss
     */
    public static final String DATE_FORMAT_YMDhMS = "yyyyMMddhhmmss";

    /**
     * 格式：24小时 yyyyMMddHHmmss
     */
    public static final String DATE_FORMAT_YMDHMS = "yyyyMMddHHmmss";

    public static final String DATE_FORMAT_YM = "yyyy-MM";

    public static Locale getServerLocale() {
        return serverLocale;
    }

    public static void setServerLocale(Locale serverLocale) {
        HissDate.serverLocale = serverLocale;
    }

    public static TimeZone getTz() {
        return tz;
    }

    public static void setTz(TimeZone tz) {
        HissDate.tz = tz;
    }

    public static TimeZone getUserTz() {
        return userTz;
    }

    public static void setUserTz(TimeZone userTz) {
        HissDate.userTz = userTz;
    }

    /**
     * 此方法描述的是：判断该日期是否符合输入的格式
     *
     * @author: tjl
     * @version: 2015年7月20日 上午11:39:58
     */
    public static boolean checkFormat(String dateString, String format) {
        boolean ret = false;
        try {
            DateFormat df = new SimpleDateFormat(format);
            Date date = df.parse(dateString);
            ret = true;
        } catch (Exception e) {
        }
        return ret;
    }

    /**
     * 输入long时间，返回yyyy-MM-dd HH:mm:ss格式时间
     *
     * @return
     */
    public static String getFormatedDateByLong(long timeLong, String toFormat, TimeZone zone) {
        try {
            Date fromDate = new Date(timeLong);
            SimpleDateFormat formatter = new SimpleDateFormat(toFormat);
            formatter.setTimeZone(zone);
            String toDateStr = formatter.format(fromDate);
            return toDateStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static long getLongByFormatedDate(String dateStr, String fromFormat, Locale fromLocale) {
        try {
            DateFormat df = new SimpleDateFormat(fromFormat,fromLocale);
            Date date = df.parse(dateStr);
            long ret = date.getTime();
            return  ret;
        } catch (Exception e) {
            e.printStackTrace();
            return System.currentTimeMillis();
        }
    }

    /**
     * 获取用户所设置时区的时间
     * @param toFormat
     * @return
     */
    public static String getCurrentDate(String toFormat) {
        try {
            Date currenDate = new Date(System.currentTimeMillis());
            SimpleDateFormat formatter = new SimpleDateFormat(toFormat);
            formatter.setTimeZone(userTz);
            String toDateStr = formatter.format(currenDate);
            return toDateStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据用户所要求的日期格式和时区，返回字符串
     * @param toFormat
     * @param zone
     * @return
     */
    public static String getCurrentDate(String toFormat, TimeZone zone) {
        try {
            Date currenDate = new Date(System.currentTimeMillis());
            SimpleDateFormat formatter = new SimpleDateFormat(toFormat);
            formatter.setTimeZone(zone);
            String toDateStr = formatter.format(currenDate);
            return toDateStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getDateByCardNo(String cardNo){
        try {
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            Date date = df.parse(cardNo.substring(6,14));
            return date;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static int getAge(Date birthDay) throws Exception {
        //获取当前系统时间
        Calendar cal = Calendar.getInstance();
        //如果出生日期大于当前时间，则抛出异常
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        //取出系统当前时间的年、月、日部分
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
//        System.out.println(yearNow + " ; " + monthNow + " ; " + dayOfMonthNow);
        //将日期设置为出生日期
        cal.setTime(birthDay);
        //取出出生日期的年、月、日部分
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
//        System.out.println(yearBirth + " ; " + monthBirth + " ; " + dayOfMonthBirth);
        //当前年份与出生年份相减，初步计算年龄
        int age = yearNow - yearBirth;
        //当前月份与出生日期的月份相比，如果月份小于出生月份，则年龄上减1，表示不满多少周岁
        if (monthNow <= monthBirth) {
            //如果月份相等，在比较日期，如果当前日，小于出生日，也减1，表示不满多少周岁
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            }else{
                age--;
            }
        }
//        System.out.println("age:"+age);
        return age;
    }

//    /**
//     * 此方法描述的是： 获得某一格式时间几秒前后的标准格式时间
//     *
//     * @author: tjl
//     * @version: 2015年6月25日 上午9:08:04
//     */
//    public static String getSecondAroundTime(String fromFormat, String fromDateStr, String toFormat, int sec) {
//        try {
//            SimpleDateFormat df = new SimpleDateFormat(fromFormat);
//            java.util.Date fromDate = df.parse(fromDateStr);
//            Date toDate = new Date(fromDate.getCreateDate() - sec * 1000);
//            SimpleDateFormat formatter = new SimpleDateFormat(toFormat);
//            String toDateStr = formatter.format(toDate);
//            return toDateStr;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 此方法描述的是： 获得某一格式时间几天前后的标准格式时间
//     *
//     * @author: tjl
//     * @version: 2015年6月25日 上午9:08:04
//     */
//    public static String getDayAroundTime(String fromFormat, String fromDateStr, String toFormat, int day) {
//        try {
//            java.util.Calendar calendar = java.util.Calendar.getInstance();
//            java.text.SimpleDateFormat fromFormatter = new java.text.SimpleDateFormat(fromFormat);
//            java.util.Date fromDate = fromFormatter.parse(fromDateStr);
//            calendar.setCreateDate(fromDate);
//            calendar.add(java.util.Calendar.DAY_OF_MONTH, day);
//            java.text.SimpleDateFormat toFormatter = new java.text.SimpleDateFormat(toFormat);
//            String toDateStr = toFormatter.format(calendar.getCreateDate());
//            return toDateStr;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    /**
     * 直接获得当前星期几
     * @param tz
     * @return
     */
    public static int getDayOfWeek(Context mContext,TimeZone tz) {
        Calendar c = Calendar.getInstance(tz);
        int w = c.get(Calendar.DAY_OF_WEEK);
        return w;
    }

    /**
     * <pre>
     * 根据指定的日期字符串（格式为yyyyMMdd）获取星期几
     * </pre>
     *
     * @param strDate 指定的日期字符串(yyyyMMdd打头的日期)
     * @return week
     * 星期几(MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY)
     */
    public static String getWeekByDateStr(String strDate, TimeZone zone) {
        try {
            int year = Integer.parseInt(strDate.substring(0, 4));
            int month = Integer.parseInt(strDate.substring(4, 6));
            int day = Integer.parseInt(strDate.substring(6, 8));

            Calendar c = Calendar.getInstance(zone);

            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month - 1);
            c.set(Calendar.DAY_OF_MONTH, day);

            String week = "";
            int weekIndex = c.get(Calendar.DAY_OF_WEEK);

            switch (weekIndex) {
                case 1:
                    week = "星期日";
                    break;
                case 2:
                    week = "星期一";
                    break;
                case 3:
                    week = "星期二";
                    break;
                case 4:
                    week = "星期三";
                    break;
                case 5:
                    week = "星期四";
                    break;
                case 6:
                    week = "星期五";
                    break;
                case 7:
                    week = "星期六";
                    break;
            }
            return week;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获得当前年份
     * @param tz
     * @return
     */
    public static String getYear(TimeZone tz) {
        Calendar c = Calendar.getInstance(tz);
        return String.valueOf(c.get(Calendar.YEAR));
    }

    /**
     * 获得月份
     * @param tz
     * @return
     */
    public static int getMonth(TimeZone tz) {
        Calendar c = Calendar.getInstance(tz);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 获得具体几号
     * @param tz
     * @return
     */
    public static int getDay(TimeZone tz) {
        Calendar c = Calendar.getInstance(tz);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static String getHour(TimeZone tz) {
        Calendar c = Calendar.getInstance(tz);
        int day = c.get(Calendar.HOUR_OF_DAY);
        return day/10 >0 ? ""+day :"0"+day ;
    }

    public static String getMinute(TimeZone tz) {
        Calendar c = Calendar.getInstance(tz);
        int minute = c.get(Calendar.MINUTE);
        return minute/10 >0 ? ""+minute :"0"+minute ;
    }
}
