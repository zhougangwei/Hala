package chat.hala.hala.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

import com.blankj.utilcode.utils.LogUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.nio.file.Path;

import chat.hala.hala.R;
import chat.hala.hala.activity.ChargeActivity;
import chat.hala.hala.activity.OneToOneActivity;
import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.bean.AnchorStateBean;
import chat.hala.hala.bean.CallBean;
import chat.hala.hala.bean.MediaToken;
import chat.hala.hala.dialog.CommonDialog;
import chat.hala.hala.dialog.ReverseDialog;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.manager.ChatSuccessHelper;
import chat.hala.hala.utils.ResultUtils;
import chat.hala.hala.utils.ToastUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class VideoCallManager {

    public static String TAG = "VideoCallManager";
    public static String VIDEO_CALL = "video";
    public static String AUDIO_CALL = "audio";


    /**
     * @param activity
     * @param anchorId 主播id
     * @param
     */
    @SuppressLint("CheckResult")
    public static void gotoCallAnchor(final Activity activity, final String videoOrAudio, final int anchorId, final int otherId) {
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
                                    .subscribe(new BaseCosumer<AnchorStateBean>() {
                                        @Override
                                        public void onGetData(AnchorStateBean anchorStateBean)  {
                                            if (Contact.REPONSE_CODE_SUCCESS != anchorStateBean.code) {
                                                return;
                                            }
                                            if (!anchorStateBean.getData().isOnline()) {
                                                ToastUtils.showToast(activity, activity.getString(R.string.anchor_is_not_aviliable));
                                                new ReverseDialog(activity, anchorId, videoOrAudio).show();
                                                return;
                                            }
                                            if (!anchorStateBean.getData().isAvailable()) {
                                                new ReverseDialog(activity, anchorId, videoOrAudio).show();
                                            } else {
                                                RetrofitFactory.getInstance().callAnchor(anchorId, AvchatInfo.getMemberId(), videoOrAudio)
                                                        .subscribeOn(Schedulers.io())
                                                        .observeOn(AndroidSchedulers.mainThread())
                                                        .subscribe(new BaseCosumer<CallBean>() {
                                                            @Override
                                                            public void onGetData(final CallBean callBean) {
                                                                if (ResultUtils.cheekSuccess(callBean)) {
                                                                    getMediaToken(callBean, activity, anchorId, otherId, VideoCallManager.VIDEO_CALL.equals(videoOrAudio));
                                                                } else if (ResultUtils.isNoMoney(callBean)) {
                                                                    new CommonDialog(activity)
                                                                            .setMsg("余额不足")
                                                                            .setListener(new CommonDialog.OnClickListener() {
                                                                                @Override
                                                                                public void onClickConfirm() {
                                                                                    activity.startActivity(new Intent(activity, ChargeActivity.class));
                                                                                }

                                                                                @Override
                                                                                public void onClickCancel() {
                                                                                }
                                                                            })
                                                                            .show();
                                                                } else if (ResultUtils.isInCall(callBean)) {
                                                                    ToastUtils.showToast(activity, "对方正在通话中!");
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


    /**
     * @param activity
     * @param anchorId 主播id
     * @param
     */
    @SuppressLint("CheckResult")
    public static void gotoReplyReverse(final Activity activity, final int reservationId, final String videoOrAudio, final int anchorId, final int otherId) {
        final RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.setLogging(true);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest
                .permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        RetrofitFactory.getInstance().replyMember(reservationId)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new BaseCosumer<CallBean>() {
                                    @Override
                                    public void onGetData(final CallBean callBean) {
                                        if (ResultUtils.cheekSuccess(callBean)) {
                                            getMediaToken(callBean, activity, anchorId, otherId, VideoCallManager.VIDEO_CALL.equals(videoOrAudio));
                                        } else if (ResultUtils.isInCall(callBean)) {
                                            ToastUtils.showToast(activity, "对方正在通话中!");
                                        }
                                    }
                                });
                    }
                });
    }

    /**
     * @param activity
     * @param anchorId 主播id
     * @param
     */
    @SuppressLint("CheckResult")
    public static void gotoLoot(final Activity activity, final String videoOrAudio, final int anchorId, final int otherId, final int lootId) {
        final RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.setLogging(true);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest
                .permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            RetrofitFactory.getInstance().callAnchor(anchorId, otherId, videoOrAudio)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new BaseCosumer<CallBean>() {
                                        @Override
                                        public void onGetData(final CallBean callBean) {
                                            if (ResultUtils.cheekSuccess(callBean)) {
                                                getMediaToken(callBean, activity, anchorId, otherId, lootId, VideoCallManager.VIDEO_CALL.equals(videoOrAudio));
                                            } else if (ResultUtils.isInCall(callBean)) {
                                                ToastUtils.showToast(activity, "对方正在通话中!");
                                            }

                                        }
                                    });
                        }
                    }
                });
    }


    private static void getMediaToken(CallBean callBean, Activity activity, final int anchorId, final int otherId, boolean enableVideo) {
        getMediaToken(callBean, activity, anchorId, otherId, 0, enableVideo);
    }


    // TODO: 2019/9/17/017  receiveChatSuccess要改 没取消监听
    private static void getMediaToken(final CallBean callBean, final Activity activity, final int anchorId, final int otherId, final int lootId, final boolean enableVideo) {
        RetrofitFactory.getInstance().getMediaToken(callBean.getData().getChannel()).subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<MediaToken>() {
                    @Override
                    public void onGetData(MediaToken mediaToken) {
                        if (ResultUtils.cheekSuccess(mediaToken)) {
                            LogUtils.e(TAG, "mediaToken" + mediaToken);
                            AvchatInfo.setMediaToken(mediaToken.getData().getAgora_media_token());
                            ChatSuccessHelper.receiveChatSuccess(activity);
                            OneToOneActivity.docallOneToOneActivity(activity, anchorId, otherId, callBean.getData().getChannel(),
                                    callBean.getData().getCallId(), lootId, enableVideo);
                        }

                    }
                });
    }

}
