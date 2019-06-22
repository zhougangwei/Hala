package com.hala.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.blankj.utilcode.utils.ScreenUtils;
import com.hala.R;
import com.hala.base.Contact;
import com.hala.bean.ReverseBean;
import com.hala.http.BaseCosumer;
import com.hala.http.RetrofitFactory;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ReverseDialog extends Dialog {

    Context mContext;
    @BindView(R.id.tv_reverse)
    TextView tvReverse;

    int anchorId;

    public ReverseDialog(@NonNull Context context, int anchorId) {
        super(context);
        this.mContext = context;
        this.anchorId = anchorId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_start_reserve);

    }


    private void gotoReverse() {
        RetrofitFactory.getInstance().reserveAnchor(anchorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<ReverseBean>() {
                    @Override
                    public void onNext(ReverseBean reverseBean) {
                        if (Contact.REPONSE_CODE_SUCCESS != reverseBean.getCode()) {
                            return;
                        }
                        showReverseSuccessDialog();
                        dismiss();
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = 4 * ScreenUtils.getScreenWidth(mContext) / 5;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(lp);
    }

    private void showReverseSuccessDialog() {
        new ReverseSuccessDialog(mContext).show();
    }




    @OnClick({R.id.tv_reverse, R.id.iv_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_reverse:
                gotoReverse();
                break;
            case R.id.iv_close:
                dismiss();
                break;
        }
    }
}
