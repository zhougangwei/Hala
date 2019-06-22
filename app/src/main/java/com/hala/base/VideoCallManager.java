package com.hala.base;

import android.annotation.SuppressLint;
import android.app.Activity;

import com.hala.R;
import com.hala.activity.MyCallListActivity;
import com.hala.activity.OneToOneActivity;
import com.hala.bean.AnchorStateBean;
import com.hala.dialog.ReverseDialog;
import com.hala.http.RetrofitFactory;
import com.hala.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class VideoCallManager {

    @SuppressLint("CheckResult")
    public static void gotoCallOrReverse(final Activity activity, final int anchorId, final int anchorMemberId){
        RetrofitFactory.getInstance().getAnchorState(anchorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AnchorStateBean>() {
                    @Override
                    public void accept(AnchorStateBean anchorStateBean) throws Exception {
                        if (Contact.REPONSE_CODE_SUCCESS!=anchorStateBean.code) {
                            return;
                        }
                        if (!anchorStateBean.getData().isOnline()) {
                            ToastUtils.showToast(activity,activity.getString(R.string.anchor_is_not_aviliable));
                            return;
                        }
                        if (!anchorStateBean.getData().isAvailable()) {
                            new ReverseDialog(activity,anchorId).show();
                        }else{
                            OneToOneActivity.docallOneToOneActivity(activity,anchorId,anchorMemberId);
                        }
                    }
                });
    }



}
