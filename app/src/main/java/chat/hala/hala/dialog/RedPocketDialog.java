package chat.hala.hala.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chat.hala.hala.R;

public class RedPocketDialog extends Dialog {

    Context mContext;
    String coins;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_from)
    TextView tvFrom;
    @BindView(R.id.tv_coin)
    TextView tvCoin;
    @BindView(R.id.cl)
    ConstraintLayout cl;
    @BindView(R.id.gp)
    Group gp;


    private String TAG = "RedPocketDialog";

    public RedPocketDialog(@NonNull Context context, String coins) {
        super(context);
        this.mContext = context;
        this.coins = coins;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_redpocket);
        ButterKnife.bind(this);
        tvCoin.setText(String.format(mContext.getResources().getString(R.string.form_user_red_pocket),coins));
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


    @OnClick(R.id.cl)
    public void onViewClicked() {
        gp.setVisibility(View.VISIBLE);
        cl.setBackground(mContext.getResources().getDrawable(R.drawable.ic_red_pocket_opened));
    }
}
