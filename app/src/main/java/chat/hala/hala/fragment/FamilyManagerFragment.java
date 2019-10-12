package chat.hala.hala.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ScreenUtils;
import com.blankj.utilcode.utils.TimeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.fantasy.doubledatepicker.DoubleDateSelectDialog;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import chat.hala.hala.R;
import chat.hala.hala.activity.FamilyDayDetailActivity;
import chat.hala.hala.base.BaseFragment;
import chat.hala.hala.bean.BaseBean;
import chat.hala.hala.bean.FamilyBeanA;
import chat.hala.hala.bean.FamilyBeanB;
import chat.hala.hala.bean.FamilyCodeBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.CalculateUtils;
import chat.hala.hala.utils.ResultUtils;
import chat.hala.hala.utils.TimeUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FamilyManagerFragment extends BaseFragment {


    public static String TAG = "FamilyManagerFragment";
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.tv_family_name)
    TextView tvFamilyName;
    @BindView(R.id.tv_anchor_num)
    TextView tvAnchorNum;
    @BindView(R.id.tv_family_total_time)
    TextView tvFamilyTotalTime;
    @BindView(R.id.tv_anchor_income)
    TextView tvAnchorIncome;
    @BindView(R.id.tv_family_income)
    TextView tvFamilyIncome;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.iv_choose_time)
    ImageView ivChooseTime;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_during_open_anchor_num)
    TextView tvDuringOpenAnchorNum;
    @BindView(R.id.tv_during_open_anchor_total_time)
    TextView tvDuringOpenAnchorTotalTime;
    @BindView(R.id.tv_during_anchor_income)
    TextView tvDuringAnchorIncome;
    @BindView(R.id.tv_during_family_income)
    TextView tvDuringFamilyIncome;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv_today_more)
    ImageView tvTodayMore;
    @BindView(R.id.tv_today_open_anchor_num)
    TextView tvTodayOpenAnchorNum;
    @BindView(R.id.tv_today_open_anchor_total_time)
    TextView tvTodayOpenAnchorTotalTime;
    @BindView(R.id.tv_today_anchor_income)
    TextView tvTodayAnchorIncome;
    @BindView(R.id.tv_today_family_income)
    TextView tvTodayFamilyIncome;
    @BindView(R.id.cl)
    ConstraintLayout cl;
    Unbinder unbinder;
    private String startAt;
    private String endAt;
    private AlertDialog alertDialog;
    private TextView tvCode;
    private DoubleDateSelectDialog mDoubleTimeSelectDialog;

    @Override
    protected void initView() {
        startAt = TimeUtil.getTodayStart();
        endAt = TimeUtil.getTodayEnd();
        tvStartTime.setText(TimeUtils.getCurTimeString(new SimpleDateFormat("yyyy-MM-dd")));
        tvEndTime.setText(TimeUtils.getCurTimeString(new SimpleDateFormat("yyyy-MM-dd")));

    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_manager_family;
    }




    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        RetrofitFactory.getInstance().getFamilyManageA().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<FamilyBeanA>() {

                    @Override
                    public void onGetData(FamilyBeanA familyBeanA)  {
                        if (ResultUtils.cheekSuccess(familyBeanA)) {
                            FamilyBeanA.DataBean data = familyBeanA.getData();
                            tvFamilyName.setText(data.getFamilyName());
                            tvId.setText(data.getFamilyId()+"");
                            tvFamilyTotalTime.setText(
                                    CalculateUtils.getTime(data.getSumDurSeconds())
                            );     //总时间
                            tvFamilyIncome.setText(
                                    CalculateUtils.getMoney(data.getCommission())
                            );        //总佣金
                            tvAnchorNum.setText(
                                    CalculateUtils.getMoney(data.getFamilyNumbers())
                            );        //人数
                            tvAnchorIncome.setText(
                                    CalculateUtils.getMoney(data.getSumWorth())
                            );//总价格

                            Glide.with(getActivity())
                                    .load(familyBeanA.getData().getMediaUrl())
                                    .apply(RequestOptions.bitmapTransform(new CircleCrop())).
                                    into(ivHead);
                        }
                    }
                });

        RetrofitFactory.getInstance().getFamilyManageB(TimeUtil.getTodayStart(), TimeUtil.getTodayEnd()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<FamilyBeanB>() {
                    @Override
                    public void onGetData(FamilyBeanB familyBeanB) {
                        if (ResultUtils.cheekSuccess(familyBeanB)) {
                            FamilyBeanB.DataBean data = familyBeanB.getData();
                            tvTodayOpenAnchorNum.setText("开播人数:" + CalculateUtils.getMoney(data.getAnthorNumber()));
                            tvTodayAnchorIncome.setText("主播收益:" + CalculateUtils.getMoney(data.getSumAnchorWorth()));
                            tvTodayFamilyIncome.setText("家族收益:" +
                                    CalculateUtils.getMoney(data.getSumLeaderWorth())
                            );
                            tvTodayOpenAnchorTotalTime.setText("累计时长:" +
                                    CalculateUtils.getTime(data.getSumLiveTimes())
                            );//总价格
                        }
                    }
                });

        getDuringData(startAt, endAt);


    }

    private void getDuringData(String startAt, String endAt) {
        RetrofitFactory.getInstance().getFamilyManageB(startAt, endAt).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FamilyBeanB>() {
                    @Override
                    public void accept(FamilyBeanB familyBeanB) throws Exception {
                        if (ResultUtils.cheekSuccess(familyBeanB)) {
                            FamilyBeanB.DataBean data = familyBeanB.getData();
                            tvDuringOpenAnchorNum.setText("开播人数:" + CalculateUtils.getMoney(data.getAnthorNumber()));
                            tvDuringAnchorIncome.setText("主播收益:" + CalculateUtils.getMoney(data.getSumAnchorWorth()));
                            tvDuringFamilyIncome.setText("家族收益:" +
                                    CalculateUtils.getMoney(data.getSumLeaderWorth())
                            );
                            tvDuringOpenAnchorTotalTime.setText("累计时长:" +
                                    CalculateUtils.getTime(data.getSumLiveTimes())
                            );//总价格
                        }
                    }
                });

    }


    @OnClick({R.id.tv_invite, R.id.iv_choose_time,R.id.tv_today_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_invite:
                startInvite();
                break;
            case R.id.iv_choose_time:
                showCustomTimePicker();
                break;
            case R.id.tv_today_more:
                goTodayMore();
                break;
        }
    }

    public void showCustomTimePicker() {

        if (mDoubleTimeSelectDialog == null) {
            String allowedSmallestTime = "2019-9-12";
            String allowedBiggestTime = TimeUtils.getCurTimeString(new SimpleDateFormat("yyyy-MM-dd"));
            mDoubleTimeSelectDialog = new DoubleDateSelectDialog(getActivity(), allowedSmallestTime, allowedBiggestTime, allowedBiggestTime);
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
    private void goTodayMore() {
        startActivity(new Intent(getActivity(), FamilyDayDetailActivity.class));
    }
    private void startInvite() {
        RetrofitFactory.getInstance().createFamilyCode(7)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<FamilyCodeBean>() {


                    @Override
                    public void onGetData(FamilyCodeBean baseBean) {
                        if (ResultUtils.cheekSuccess(baseBean)) {
                            String data = baseBean.getData();
                            if (alertDialog == null) {
                                View view = View.inflate(getActivity(), R.layout.dialog_family_invite, null);
                                tvCode = view.findViewById(R.id.tv_code);
                                alertDialog = new AlertDialog.Builder(getActivity())
                                        .setView(view)
                                        .create();
                                tvCode.setText(data);
                                alertDialog.show();
                                Window window = alertDialog.getWindow();
                                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                WindowManager.LayoutParams lp = window.getAttributes();
                                lp.width = 4 * ScreenUtils.getScreenWidth(getActivity()) / 5;
                                lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                                window.setAttributes(lp);
                            } else {
                                tvCode.setText(data);
                                alertDialog.show();
                            }
                        }
                    }
                });


    }
}
