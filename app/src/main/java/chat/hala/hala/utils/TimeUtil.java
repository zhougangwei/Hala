package chat.hala.hala.utils;

import android.text.TextUtils;

import com.blankj.utilcode.utils.TimeUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

public class TimeUtil {
    public static String dealDateFormat(String oldDate) {
        Date date1 = null;
        DateFormat df2 = null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = df.parse(oldDate);
            SimpleDateFormat df1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            date1 = df1.parse(date.toString());
            df2 = new SimpleDateFormat("HH:mm");
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        return df2.format(date1);
    }

    public static String dealDateFormat2(String oldDate) {
        Date date1 = null;
        DateFormat df2 = null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = df.parse(oldDate);
            SimpleDateFormat df1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            date1 = df1.parse(date.toString());
            df2 = new SimpleDateFormat("MM dd");
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        return df2.format(date1);
    }

    public static String dealDateFormat3(String oldDate) {
        Date date1 = null;
        DateFormat df2 = null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = df.parse(oldDate);
            SimpleDateFormat df1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            date1 = df1.parse(date.toString());
            df2 = new SimpleDateFormat("yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        return df2.format(date1);
    }

    public static String stringForTime(int timeMs) {

        StringBuilder mFormatBuilder;
        mFormatBuilder = new StringBuilder();
        Formatter mFormatter;
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }


    /*
     * 昨天今天
     * */
    public static String getTextTime(String time) {
        SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (time == null || "".equals(time)) {
            return "";
        }
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar current = Calendar.getInstance();

        Calendar today = Calendar.getInstance();    //今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        //  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance();    //昨天

        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);

        if (current.after(today)) {
            return "今天 ";
        } else if (current.before(today) && current.after(yesterday)) {
            return "昨天 ";
        } else {
            try {
                return TimeUtils.date2String(TimeUtils.string2Date(time, new SimpleDateFormat("yyyy-MM-dd HH:mm")), new SimpleDateFormat("MMMdd")) + "号";
            } catch (Exception e) {
                return time;
            }
        }
    }

    private final static int[] dayArr = new int[]{20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22};
    private final static String[] constellationArr = new String[]{"摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座"};

    public static String getConstellation(int month, int day) {
        return day < dayArr[month - 1] ? constellationArr[month - 1] : constellationArr[month];
    }
    public static String getConstellation(String birth) {
        try {
            String[] split = birth.split("-");
            return getConstellation(Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        }catch (Exception e){
            return "";
        }

    }

    public static String getTextTime2(String lastMessageTime) {
        if(TextUtils.isEmpty(lastMessageTime)){
            return "";
        }
        String textTime = getTextTime(lastMessageTime);
        if ("今天".equals(textTime)) {
            return TimeUtils.date2String(TimeUtils.string2Date(lastMessageTime, new SimpleDateFormat("HH:mm")));
        }else{
            return textTime;
        }

    }


    public static  String getTodayStart(){
        return TimeUtils.getCurTimeString(new SimpleDateFormat("yyyy-MM-dd"))+" 00:00:00";
    }
    public static  String getTodayEnd(){
        return TimeUtils.getCurTimeString(new SimpleDateFormat("yyyy-MM-dd"))+" 23:59:59";
    }
}
