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

import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.ReverseBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.GsonUtil;
import chat.hala.hala.utils.ToastUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ReverseDialog extends Dialog {

    Context mContext;
    @BindView(R.id.tv_reverse)
    TextView tvReverse;

    int anchorId;
    String videoOrAudio;
    private String TAG ="ReverseDialog";

    public ReverseDialog(@NonNull Context context, int anchorId,  String videoOrAudio) {
        super(context);
        this.mContext = context;
        this.anchorId = anchorId;
        this.videoOrAudio = videoOrAudio;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_start_reserve);
        ButterKnife.bind(this);
    }


    private void gotoReverse() {
        RetrofitFactory.getInstance().reserveAnchor(anchorId,videoOrAudio)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<ReverseBean>() {
                    @Override
                    public void onGetData(ReverseBean reverseBean) {
                        LogUtils.e(TAG, "onGetData: "+ GsonUtil.parseObjectToJson(reverseBean));
                         if(Contact.REPONSE_CODE_REVERSE_SAME==reverseBean.getCode()){
                            ToastUtils.showToast(getContext(),"你已预约过该主播");
                        }
                        if (Contact.REPONSE_CODE_REVERSE_NOMONEY==reverseBean.getCode()){
                            ToastUtils.showToast(getContext(),"余额不足");
                        }
                        if (Contact.REPONSE_CODE_SUCCESS != reverseBean.getCode()) {
                            dismiss();
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
