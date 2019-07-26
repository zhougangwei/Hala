package chat.hala.hala.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import chat.hala.hala.base.Contact;

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
    public static String getProcessName(Context var0) {
        String var1;
        if ((var1 = getProcessFromFile()) == null) {
            var1 = getProcessNameByAM(var0);
        }

        return var1;
    }
    private static String getProcessFromFile() {
        BufferedReader var0 = null;
        boolean var8 = false;

        String var14;
        label97: {
            try {
                var8 = true;
                int var1 = Process.myPid();
                var14 = "/proc/" + var1 + "/cmdline";
                var0 = new BufferedReader(new InputStreamReader(new FileInputStream(var14), "iso-8859-1"));
                StringBuilder var2 = new StringBuilder();

                while((var1 = var0.read()) > 0) {
                    var2.append((char)var1);
                }

                var14 = var2.toString();
                var8 = false;
                break label97;
            } catch (Exception var12) {
                var8 = false;
            } finally {
                if (var8) {
                    if (var0 != null) {
                        try {
                            var0.close();
                        } catch (IOException var10) {
                            var10.printStackTrace();
                        }
                    }

                }
            }

            if (var0 != null) {
                try {
                    var0.close();
                } catch (IOException var9) {
                    var9.printStackTrace();
                }
            }

            return null;
        }

        try {
            var0.close();
        } catch (IOException var11) {
            var11.printStackTrace();
        }

        return var14;
    }

    private static String getProcessNameByAM(Context var0) {
        String var1 = null;
        ActivityManager var5;
        if ((var5 = (ActivityManager)var0.getSystemService("activity")) == null) {
            return null;
        } else {
            while(true) {
                List var2;
                if ((var2 = var5.getRunningAppProcesses()) != null) {
                    Iterator var6 = var2.iterator();

                    while(var6.hasNext()) {
                        ActivityManager.RunningAppProcessInfo var3;
                        if ((var3 = (ActivityManager.RunningAppProcessInfo)var6.next()).pid == Process.myPid()) {
                            var1 = var3.processName;
                            break;
                        }
                    }
                }

                if (!TextUtils.isEmpty(var1)) {
                    return var1;
                }

                try {
                    Thread.sleep(100L);
                } catch (InterruptedException var4) {
                    var4.printStackTrace();
                }
            }
        }
    }

    public static String getLanguage() {
        String language = Locale.getDefault().getLanguage();
        if (language.equals("zh") || language.equals("CN") || language.equals("zh-CN")) {
            return "zh";
        }else if(language.equals("th")){
            return "th";
        }else if(language.equals("ar")){
            return "ar";
        }else {
            return "en";
        }
    }
}
