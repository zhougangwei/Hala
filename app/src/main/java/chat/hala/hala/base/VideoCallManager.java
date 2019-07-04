package chat.hala.hala.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import com.tbruyelle.rxpermissions2.RxPermissions;

import chat.hala.hala.R;
import chat.hala.hala.activity.OneToOneActivity;
import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.bean.AnchorStateBean;
import chat.hala.hala.bean.CallBean;
import chat.hala.hala.bean.MediaToken;
import chat.hala.hala.dialog.CommonDialog;
import chat.hala.hala.dialog.ReverseDialog;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.ResultUtils;
import chat.hala.hala.utils.ToastUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class VideoCallManager {

    public static String TAG = "VideoCallManager";

    @SuppressLint("CheckResult")
    public static void gotoCallOrReverse(final Activity activity, final int anchorId, final int anchorMemberId) {
        final RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.setLogging(true);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest
                .permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            RetrofitFactory.getInstance().getAnchorState(anchorId)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Consumer<AnchorStateBean>() {
                                        @Override
                                        public void accept(AnchorStateBean anchorStateBean) throws Exception {
                                            if (Contact.REPONSE_CODE_SUCCESS != anchorStateBean.code) {
                                                return;
                                            }
                                            if (!anchorStateBean.getData().isOnline()) {
                                                ToastUtils.showToast(activity, activity.getString(R.string.anchor_is_not_aviliable));
                                                new ReverseDialog(activity, anchorId).show();
                                                return;
                                            }
                                            if (!anchorStateBean.getData().isAvailable()) {
                                                new ReverseDialog(activity, anchorId).show();
                                            } else {
                                                RetrofitFactory.getInstance().callAnchor(anchorId)
                                                        .subscribeOn(Schedulers.io())
                                                        .observeOn(AndroidSchedulers.mainThread())
                                                        .subscribe(new BaseCosumer<CallBean>() {
                                                            @Override
                                                            public void onNext(final CallBean callBean) {
                                                                if(true){
                                                                    /*
                                                                    * test
                                                                    * */
                                                                    CallBean callBean1 = new CallBean();
                                                                    CallBean.DataBean dataBean = new CallBean.DataBean();
                                                                    dataBean.setChannel(anchorId+"");
                                                                    dataBean.setCallId(anchorId);
                                                                    callBean1.setData(dataBean);
                                                                    getMediaToken(callBean1, activity, anchorId, anchorMemberId);
                                                                    return;
                                                                }




                                                               /* if(true){
                                                                    CallBean callBean1 = new CallBean();
                                                                    CallBean.DataBean dataBean = new CallBean.DataBean();
                                                                    dataBean.setChannel(anchorId+"");
                                                                    dataBean.setCallId(anchorId);
                                                                    callBean1.setData(dataBean);
                                                                    getMediaToken(callBean1, activity, anchorId, anchorMemberId);
                                                                    return;
                                                                }*/

                                                                if (ResultUtils.cheekSuccess(callBean)) {
                                                                    getMediaToken(callBean, activity, anchorId, anchorMemberId);
                                                                } else if (ResultUtils.isNoMoney(callBean)) {
                                                                    new CommonDialog(activity)
                                                                            .setMsg("你没钱了")
                                                                            .setListener(new CommonDialog.OnClickListener() {
                                                                                @Override
                                                                                public void onClickConfirm() {
                                                                                    CallBean callBean1 = new CallBean();
                                                                                    CallBean.DataBean dataBean = new CallBean.DataBean();
                                                                                    dataBean.setChannel(callBean.getData().getChannel());
                                                                                    dataBean.setCallId(callBean.getData().getCallId());
                                                                                    callBean1.setData(dataBean);
                                                                                    getMediaToken(callBean1, activity, anchorId, anchorMemberId);
                                                                                }

                                                                                @Override
                                                                                public void onClickCancel() {
                                                                                }
                                                                            })
                                                                            .show();
                                                                }
                                                            }
                                                        });
                                        }
                                        }
                                    });
                        }
                    }
                });


    }

    private static void getMediaToken(final CallBean callBean, final Activity activity, final int anchorId, final int anchorMemberId) {
        RetrofitFactory.getInstance().getMediaToken(callBean.getData().getChannel()).subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<MediaToken>() {
                    @Override
                    public void onNext(MediaToken mediaToken) {
                        if (ResultUtils.cheekSuccess(mediaToken)) {
                            Log.e(TAG, "mediaToken" + mediaToken);
                            AvchatInfo.setMediaToken(mediaToken.getData().getAgora_media_token());
                            OneToOneActivity.docallOneToOneActivity(activity, anchorId, anchorMemberId, callBean.getData().getChannel(),
                                    callBean.getData().getCallId());
                        }

                    }
                });
    }

}
