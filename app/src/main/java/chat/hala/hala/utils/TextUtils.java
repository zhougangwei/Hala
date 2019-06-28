package chat.hala.hala.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {

    public static SpannableStringBuilder matcherSearchTitle(String text,String text2, String target) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
        CharacterStyle span = null;

        Pattern p = Pattern.compile(target);
        Matcher m = p.matcher(text);
        Matcher m2 = p.matcher(text2);


        while (m.find()) {
            span = new ForegroundColorSpan(Color.parseColor("#472EB4"));
            spannable.setSpan(span, m.start(), m.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        while (m2.find()) {
            span = new ForegroundColorSpan(Color.parseColor("#472EB4"));
            spannable.setSpan(span, m2.start(), m2.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        }



        return spannable;
    }

    public static SpannableStringBuilder matcherSearchTitle(String text, String target) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
        CharacterStyle span = null;

        Pattern p = Pattern.compile(target);
        Matcher m = p.matcher(text);
        while (m.find()) {
            span = new ForegroundColorSpan(Color.parseColor("#472EB4"));
            spannable.setSpan(span, m.start(), m.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return spannable;
    }

    public static void makeTextViewCLick(TextView textView, String regex, int color, boolean isBold, Context context, IdViewListener clickListener) {

        //ds.setColor()设定的是span超链接的文本颜色，而不是点击后的颜色，
        //点击后的背景颜色(HighLightColor)属于TextView的属性，
        //Android4.0以上默认是淡绿色，低版本的是黄色。解决方法就是通过重新设置文字背景为透明色
        if (android.text.TextUtils.isEmpty(regex)){
            return;
        }
        CharSequence text = textView.getText();
        textView.setHighlightColor(context.getResources().getColor(android.R.color.transparent));
        SpannableString spanableInfo = new SpannableString(text);
        String str = text.toString();
        geStart(str, regex, 0, spanableInfo, new Clickable(clickListener,regex,color,isBold));
        textView.setText(spanableInfo);
        textView.setMovementMethod(LinkMovementClickMethod.getInstance());
    }


    private static void geStart(String str, String regex, int start, SpannableString spanableInfo,Clickable clickable) {
        int   start2 = str.indexOf(regex, start );
        if (start2 != -1) {
            spanableInfo.setSpan(clickable, start2, start2 + regex.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            geStart(str, regex, start2 + regex.length(), spanableInfo,clickable);
        }
    }
}
