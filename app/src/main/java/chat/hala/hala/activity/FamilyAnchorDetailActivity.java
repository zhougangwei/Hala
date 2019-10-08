package chat.hala.hala.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.blankj.utilcode.utils.TimeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.fantasy.doubledatepicker.DoubleDateSelectDialog;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.bean.FamilyAnchorDetailBean;
import chat.hala.hala.bean.FamilyBeanA;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.CalculateUtils;
import chat.hala.hala.utils.ResultUtils;
import chat.hala.hala.utils.TimeUtil;
import cn.qqtheme.framework.picker.DateTimePicker;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FamilyAnchorDetailActivity extends BaseActivity {

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
    private String startAt;
    private String endAt;
    private DoubleDateSelectDialog mDoubleTimeSelectDialog;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_family_anchor_detail;
    }

    @Override
    protected void beforeInitView() {
        Intent intent = getIntent();
        memberId = intent.getIntExtra("memberId", 0);
        startAt = TimeUtil.getTodayStart();
        endAt = TimeUtil.getTodayEnd();
    }

    @Override
    protected void initView() {
        tvTitle.setText("主播管理");
        tvStartTime.setText(TimeUtils.getCurTimeString(new SimpleDateFormat("yyyy-MM-dd")));
        tvEndTime.setText(TimeUtils.getCurTimeString(new SimpleDateFormat("yyyy-MM-dd")));

        RetrofitFactory.getInstance().getFamilyAnchorDetail(memberId, TimeUtil.getTodayStart(),  TimeUtil.getTodayEnd()
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<FamilyAnchorDetailBean>() {
                    @Override
                    public void onGetData(FamilyAnchorDetailBean bean) {
                        if (ResultUtils.cheekSuccess(bean)) {
                            FamilyAnchorDetailBean.DataBean data = bean.getData();
                            tvTotalIncome.setText(
                                    CalculateUtils.getMoney(data.getSumAnchorWorth())+"豆"
                                    );
                            tvTotalTime.setText(
                                    CalculateUtils.getTime(data.getSumLiveTimes())
                           );
                            tvTodayTotalIncome.setText(
                                   "时长:"+ CalculateUtils.getMoney(data.getZoneAnchorWorth())
                                 );
                            tvTodayTotalTime.setText(
                                    "收益:"+  CalculateUtils.getMoney(data.getZoneLiveTimes())
                                    );
                            tvHead.setText(bean.getData().getName());
                            Glide.with(FamilyAnchorDetailActivity.this).load(bean.getData().getMediaUrl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHead);
                        }
                    }
                });
        getDuringData(startAt,endAt);
    }

    private void getDuringData(String startAt, String endAt) {
        RetrofitFactory.getInstance().getFamilyAnchorDetail(memberId, startAt,  endAt
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<FamilyAnchorDetailBean>() {
                    @Override
                    public void onGetData(FamilyAnchorDetailBean bean) {
                        if (ResultUtils.cheekSuccess(bean)) {
                            FamilyAnchorDetailBean.DataBean data = bean.getData();
                            tvDuringTotalIncome.setText(
                                    "收益总计:"
                                    +
                                    CalculateUtils.getMoney(data.getSumAnchorWorth())
                            );
                            tvDuringTotalTime.setText(
                                    "时长总计:"+
                                    CalculateUtils.getTime(data.getSumLiveTimes())
                            );
                        }
                    }
                });
    }
    @OnClick({R.id.iv_back,R.id.iv_choose_time,R.id.tv_today_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case   R.id.iv_choose_time:
                showCustomTimePicker();
                break;
            case  R.id.tv_today_more:
                goTodayMore();
                break;
        }


    }

    private void goTodayMore() {
        startActivity(new Intent(this,FamilyDayDetailActivity.class));
    }

    public void showCustomTimePicker() {
        String allowedSmallestTime = "2019-9-12";
        String allowedBiggestTime = TimeUtils.getCurTimeString(new SimpleDateFormat("yyyy-MM-dd"));
        if (mDoubleTimeSelectDialog == null) {
            mDoubleTimeSelectDialog = new DoubleDateSelectDialog(this, allowedSmallestTime, allowedBiggestTime, allowedBiggestTime);
            mDoubleTimeSelectDialog.setOnDateSelectFinished(new DoubleDateSelectDialog.OnDateSelectFinished() {
                @Override
                public void onSelectFinished(String startTime, String endTime) {
                    startAt=startTime +" 00:00:00";
                    endAt=endTime+" 23:59:59";
                    tvStartTime.setText(startTime);
                    tvEndTime.setText(endTime);
                }
            });
            mDoubleTimeSelectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                }
            });
        }
        if (!mDoubleTimeSelectDialog.isShowing()) {
            mDoubleTimeSelectDialog.show();
        }
    }
}
