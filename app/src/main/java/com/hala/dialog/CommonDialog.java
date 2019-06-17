package com.hala.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.blankj.utilcode.utils.ScreenUtils;
import com.hala.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommonDialog extends Dialog {


    @BindView(R.id.title)
    TextView tvTitle;
    @BindView(R.id.msg)
    TextView tvMsg;
    @BindView(R.id.cancel)
    TextView tvCancel;
    @BindView(R.id.cutline)
    View cutline;
    @BindView(R.id.confirm)
    TextView tvConfirm;
    private Context mContext;

    private String mTitle, mMsg;
    private String mCancel;
    private String mConfirm;

    private OnClickListener mListener;

    public CommonDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        mCancel = mContext.getString(R.string.cancle);
        mConfirm = mContext.getString(R.string.confirm);
    }

    public CommonDialog setTitle(String title) {
        mTitle = title;
        return this;
    }

    public CommonDialog setMsg(String msg) {
        mMsg = msg;
        return this;
    }

    public CommonDialog setConfirm(String confirm) {
        mConfirm = confirm;
        return this;
    }

    public CommonDialog setCancel(String cancel) {
        mCancel = cancel;
        return this;
    }


    public CommonDialog setListener(OnClickListener mListener) {
        this.mListener = mListener;
        return this;
    }


    public void setCancelColor(int color) {
        tvCancel.setTextColor(color);
    }

    public void setConfirmColor(int color) {
        tvConfirm.setTextColor(color);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common);
        ButterKnife.bind(this);
        initViews();
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


    private void initViews() {
        setView(tvTitle, mTitle);
        setView(tvMsg, mMsg);
        setView(tvCancel, mCancel);
        setView(tvConfirm, mConfirm);
    }

    private void setView(TextView tv, String content) {
        if (TextUtils.isEmpty(content))
            tv.setVisibility(View.GONE);
        else
            tv.setText(content);
    }

    @OnClick({R.id.cancel, R.id.confirm})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                dismiss();
                if (mListener != null)
                    mListener.onClickCancel();
                break;
            case R.id.confirm:
                dismiss();
                if (mListener != null)
                    mListener.onClickConfirm();
                break;
        }
    }


    public OnClickListener getmListener() {
        return mListener;
    }



    public interface OnClickListener {
        void onClickConfirm();

        void onClickCancel();
    }


}
