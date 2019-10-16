package chat.hala.hala.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.blankj.utilcode.utils.ScreenUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.VersionBean;
import chat.hala.hala.service.DownloadServiceSe;
import chat.hala.hala.utils.SPUtil;
import io.reactivex.functions.Consumer;

/**
 * Created by xyy on 2018/5/3.
 */

public class VersionUpdateDialog extends Dialog {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.msg)
    TextView msg;
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.cutline)
    View cutline;
    @BindView(R.id.confirm)
    TextView confirm;
    private Context mContext;
    private VersionBean.DataBean.AndroidBean mVersionInfo;

    public VersionUpdateDialog(@NonNull Context context, VersionBean.DataBean.AndroidBean versionInfo) {
        super(context);
        mContext = context;
        mVersionInfo = versionInfo;
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
        title.setText("V" + mVersionInfo.getV());
        String message = mVersionInfo.getDesc();
        message = message.replace("#", "\n");
        msg.setText(message);
    }

    @OnClick({R.id.confirm, R.id.cancel})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.confirm:
                goUpdate();

                break;
            case R.id.cancel:
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SPUtil.getInstance(mContext).setString(Contact.CHECK_UPDATE_DATE, formatter.format(curDate));
                dismiss();
                break;
        }
    }

    private void goUpdate() {
        final RxPermissions rxPermissions = new RxPermissions((Activity) mContext);
        rxPermissions.request(Manifest
                .permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Intent intent = new Intent(getContext(), DownloadServiceSe.class);
                            intent.putExtra("url", mVersionInfo.getL());
                            getContext().startService(intent);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }

}
