package chat.hala.hala.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.TimeUtils;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import chat.hala.hala.R;
import chat.hala.hala.base.BaseFragment;
import chat.hala.hala.bean.FamilyBeanA;
import chat.hala.hala.bean.FamilyBeanB;
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
    private String startAt;
    private String endAt;


    @Override
    protected void initView() {
        startAt = TimeUtil.getTodayStart();
        endAt = TimeUtil.getTodayEnd();
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
                .subscribe(new Consumer<FamilyBeanA>() {
                    @Override
                    public void accept(FamilyBeanA familyBeanA) throws Exception {
                        if (ResultUtils.cheekSuccess(familyBeanA)) {
                            FamilyBeanA.DataBean data = familyBeanA.getData();
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
                            Glide.with(getActivity()).load(familyBeanA.getData().getMediaUrl()).into(ivHead);
                        }
                    }
                });

        RetrofitFactory.getInstance().getFamilyManageB(TimeUtil.getTodayStart(), TimeUtil.getTodayEnd()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FamilyBeanB>() {
                    @Override
                    public void accept(FamilyBeanB familyBeanB) throws Exception {
                        if (ResultUtils.cheekSuccess(familyBeanB)) {
                            FamilyBeanB.DataBean data = familyBeanB.getData();
                            tvTodayOpenAnchorNum.setText("开播人数:"+CalculateUtils.getMoney(data.getAnthorNumber()));
                            tvTodayAnchorIncome.setText("主播收益:"+CalculateUtils.getMoney(data.getSumAnchorWorth()));
                            tvTodayFamilyIncome.setText("家族收益:"+
                                    CalculateUtils.getMoney(data.getSumLeaderWorth())
                            );
                            tvTodayOpenAnchorTotalTime.setText("累计时长:"+
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
                            tvDuringOpenAnchorNum.setText("开播人数:"+CalculateUtils.getMoney(data.getAnthorNumber()));
                            tvDuringAnchorIncome.setText("主播收益:"+CalculateUtils.getMoney(data.getSumAnchorWorth()));
                            tvDuringFamilyIncome.setText("家族收益:"+
                                    CalculateUtils.getMoney(data.getSumLeaderWorth())
                            );
                            tvDuringOpenAnchorTotalTime.setText("累计时长:"+
                                    CalculateUtils.getTime(data.getSumLiveTimes())
                            );//总价格
                        }
                    }
                });

    }


}
