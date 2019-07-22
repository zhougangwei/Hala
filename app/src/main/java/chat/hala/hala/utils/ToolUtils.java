package chat.hala.hala.utils;

import android.content.Context;

public class ToolUtils {
    public static boolean isMainProcess(Context var0) {
        if (var0 == null) {
            return false;
        } else {
            String var1 = var0.getApplicationContext().getPackageName();
            String var2 = getProcessName(var0);
            return var1.equals(var2);
        }
    }
}
