package chat.hala.hala.utils;

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
            SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            date1 = df1.parse(date.toString());
            df2 = new SimpleDateFormat("HH:mm");
        } catch (ParseException e) {

            e.printStackTrace();
        }
        return df2.format(date1);
    }
    public static String dealDateFormat2(String oldDate) {
        Date date1 = null;
        DateFormat df2 = null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = df.parse(oldDate);
            SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            date1 = df1.parse(date.toString());
            df2 = new SimpleDateFormat("MM dd");
        } catch (ParseException e) {

            e.printStackTrace();
        }
        return df2.format(date1);
    }


    public static String stringForTime(int timeMs){

        StringBuilder mFormatBuilder; mFormatBuilder = new StringBuilder();
            Formatter mFormatter; mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        int totalSeconds = timeMs/1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds/60)%60;
        int hours = totalSeconds/3600;
        mFormatBuilder.setLength(0);
        if(hours>0){
            return mFormatter.format("%d:%02d:%02d",hours,minutes,seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d",minutes,seconds).toString();
        }
    }


    /*
    * 昨天今天
    * */
    public static String getTextTime(String time) {
        SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
        if(time==null ||"".equals(time)){
            return "";
        }
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar current = Calendar.getInstance();

        Calendar today = Calendar.getInstance();	//今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH));
        //  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set( Calendar.HOUR_OF_DAY, 0);
        today.set( Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance();	//昨天

        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH)-1);
        yesterday.set( Calendar.HOUR_OF_DAY, 0);
        yesterday.set( Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);

        if(current.after(today)){
            return "今天 ";
        }else if(current.before(today) && current.after(yesterday)){
            return "昨天 ";
        }else{
            try{
                return TimeUtils.date2String(TimeUtils.string2Date(time,new SimpleDateFormat("yyyy-MM-dd HH:mm")), new SimpleDateFormat("MMMdd"))+"号";
            }catch (Exception e){
                return time;
            }
        }
    }
}
