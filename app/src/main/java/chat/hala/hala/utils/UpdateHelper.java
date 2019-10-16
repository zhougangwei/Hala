package chat.hala.hala.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.VersionBean;
import chat.hala.hala.dialog.VersionUpdateDialog;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xyy on 2018/5/7.
 */

public class UpdateHelper {
    @SuppressLint("CheckResult")
    public void checkUpdate(final Context context) {
        RetrofitFactory.getInstance().getVersion(ToolUtils.getVersionName(context), "android")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<VersionBean>() {
                    @Override
                    public void onGetData(VersionBean baseBean) {
                        if (ResultUtils.cheekSuccess(baseBean)) {
                            VersionBean.DataBean.AndroidBean android = baseBean.getData().getAndroid();
                            //是否需要检查版本更新，一天内点击过稍后提醒则不需要检查
                            String dateStr = SPUtil.getInstance(context).getString(Contact.CHECK_UPDATE_DATE, "1970-01-01 00:00:01");
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                Date beforeDate = formatter.parse(dateStr);
                                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                                if (curDate.getTime() - beforeDate.getTime() < 24 * 60 * 60 * 1000) {
                                    return;
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (!android.isM())     //强制更新
                                return;
                            if (!android.isN())    //需要更新
                                return;
                            VersionUpdateDialog dialog = new VersionUpdateDialog(context, android);
                            dialog.setCancelable(false);
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.show();
                        }
                    }
                });

    }
}
