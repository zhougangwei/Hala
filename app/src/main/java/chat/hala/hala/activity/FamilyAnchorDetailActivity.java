package chat.hala.hala.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.TimeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.bean.FamilyAnchorDetailBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.ResultUtils;
import chat.hala.hala.utils.TimeUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FamilyAnchorDetailActivity extends BaseActivity {


    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.imageView5)
    ImageView imageView5;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_head)
    TextView tvHead;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.tv_total_time)
    TextView tvTotalTime;
    @BindView(R.id.textView25)
    TextView textView25;
    @BindView(R.id.tv_total_income)
    TextView tvTotalIncome;
    @BindView(R.id.textView26)
    TextView textView26;
    @BindView(R.id.iv_choose_time)
    ImageView ivChooseTime;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_during_total_time)
    TextView tvDuringTotalTime;
    @BindView(R.id.tv_during_total_income)
    TextView tvDuringTotalIncome;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv_today_more)
    ImageView tvTodayMore;
    @BindView(R.id.tv_today_total_time)
    TextView tvTodayTotalTime;
    @BindView(R.id.tv_today_total_income)
    TextView tvTodayTotalIncome;
    private int memberId;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_family_anchor_detail;
    }

    @Override
    protected void beforeInitView() {
        Intent intent = getIntent();
        memberId = intent.getIntExtra("memberId", 0);
    }

    @Override
    protected void initView() {

        RetrofitFactory.getInstance().getFamilyAnchorDetail(memberId, TimeUtil.getTodayStart(),  TimeUtil.getTodayEnd()
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<FamilyAnchorDetailBean>() {
                    @Override
                    public void onGetData(FamilyAnchorDetailBean bean) {
                        if (ResultUtils.cheekSuccess(bean)) {
                            tvTotalIncome.setText(bean.getData().getSumAnchorWorth());
                            tvTotalTime.setText(bean.getData().getSumLiveTimes()+"");
                            tvTodayTotalIncome.setText(bean.getData().getZoneAnchorWorth());
                            tvTodayTotalTime.setText(bean.getData().getZoneLiveTimes()+"");
                            tvHead.setText(bean.getData().getName());
                            Glide.with(FamilyAnchorDetailActivity.this).load(bean.getData().getMediaUrl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHead);
                        }
                    }
                });



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_cancel)
    public void onViewClicked() {
    }
}
