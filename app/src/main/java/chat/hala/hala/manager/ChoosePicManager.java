package chat.hala.hala.manager;

import android.Manifest;
import android.app.Activity;

import chat.hala.hala.base.Contact;
import chat.hala.hala.glide.MyGlideEngine;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import io.reactivex.functions.Consumer;

public class ChoosePicManager {

    public static final int REQUEST_CODE_CHOOSE = Contact.REQUEST_CODE_CHOOSE;

    public static void choosePic(final Activity activity, final int count) {
        final RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.setLogging(true);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest
                .permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Matisse.from(activity)
                                    .choose(MimeType.of(MimeType.PNG, MimeType.JPEG))//图片类型
                                    .countable(true)//true:选中后显示数字;false:选中后显示对号
                                    .maxSelectable(count)//可选的最大数
                                    .capture(true)//选择照片时，是否显示拍照
                                    .captureStrategy(new CaptureStrategy(true, activity.getPackageName() + ".fileprovider"))//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                                    .imageEngine(new MyGlideEngine())//图片加载引擎
                                    .forResult(REQUEST_CODE_CHOOSE);//
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }


}
