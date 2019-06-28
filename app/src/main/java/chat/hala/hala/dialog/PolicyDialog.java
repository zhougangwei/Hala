package chat.hala.hala.dialog;

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

import butterknife.BindView;
import butterknife.OnClick;
import chat.hala.hala.R;

public class PolicyDialog extends Dialog {

    Context mContext;
    @BindView(R.id.tv_agree)
    TextView tvAgree;

    public PolicyDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;

    }

    private OnClickListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_policy);
    }

    public PolicyDialog setListener(PolicyDialog.OnClickListener mListener) {
        this.mListener = mListener;
        return this;
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

    @OnClick({R.id.tv_agree,R.id.iv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_agree:
                if (mListener!=null) {
                    mListener.onClickConfirm();
                }
                dismiss();
                break;
            case R.id.iv_close:
                dismiss();
                break;
        }



    }

    public interface OnClickListener {
        void onClickConfirm();
        void onClickCancel();
    }


}
