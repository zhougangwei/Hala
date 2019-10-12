package chat.hala.hala.utils;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;

/**
 * @author wjy on 2019/10/8/008.
 */
public class EditUtils {
    public static boolean judeEmpty(Object target) {
        List<Field> list = Arrays.asList(target.getClass().getDeclaredFields());
        for (int i = 0; i < list.size(); i++) {
            Field field = list.get(i);

            try {
              if (field.isAnnotationPresent(EditEmpty.class)) {

                for (Annotation anno : field.getDeclaredAnnotations()) {//获得所有的注解
                    if (anno.annotationType().equals(EditEmpty.class)) {//找到自己的注解
                        String value = ((EditEmpty) anno).value();
                        String mText = ((EditText) field.get(target)).getText().toString();
                        if (TextUtils.isEmpty(mText)) {
                           ToastUtils.showToast((Activity)target,value+"不能为空!");
                            //System.out.println(value+"不能为空!");
                            return false;
                        }
                    }
                }
                return true;
            }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
