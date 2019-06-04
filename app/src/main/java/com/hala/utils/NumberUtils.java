package com.hala.utils;

import java.util.regex.Pattern;

/**
 * Created by zhougangwei on 2018/7/17.
 */

public class NumberUtils {


    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static String getNumberFormatText(String  text){
        try {
        if (NumberUtils.isInteger(text)){
            int v = Integer.parseInt(text);
            //千分位
            if (((v/1000))>0&&((v/1000000)==0)){
                text = String.format("%.1f", ((v / 1000f)))+"k";
            }else if(v/1000000>0) {
                text = String.format("%.1f", ((v / 1000000f) ))+"m";
            }
            return text;
        }else {
            return text;
        }
        }catch(Exception e){
            return text;
        }
    }
    public static String getNumberFormatThoundText(String  text){
        try {
                double v = Double.parseDouble(text);
                //千分位
                if (((v/1000))<1){
                    text=String.format("%.1f",v/1f);
                }else{
                    text = String.format("%.1f", ((v / 1000f)))+"k";
                }
                return text;
        }catch(Exception e){
            return text;
        }
    }




}
