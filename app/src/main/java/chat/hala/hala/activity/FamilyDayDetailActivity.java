package chat.hala.hala.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.adapter.FamilyDayDetailAdapter;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.bean.BaseBean;
import chat.hala.hala.bean.FamilyBeanB;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.CalculateUtils;
import chat.hala.hala.utils.GsonUtil;
import chat.hala.hala.utils.ResultUtils;
import chat.hala.hala.utils.TimeUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FamilyDayDetailActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.imageView6)
    ImageView imageView6;
    @BindView(R.id.tv2)
    TextView tv2;

    @BindView(R.id.tv_today_open_anchor_num)
    TextView tvTodayOpenAnchorNum;
    @BindView(R.id.tv_today_open_anchor_total_time)
    TextView tvTodayOpenAnchorTotalTime;
    @BindView(R.id.tv_today_anchor_income)
    TextView tvTodayAnchorIncome;
    @BindView(R.id.tv_today_family_income)
    TextView tvTodayFamilyIncome;
    @BindView(R.id.rv)
    RecyclerView rv;

    private FamilyDayDetailAdapter familyDayDetailAdapter;


    private List<BaseBean> datas =new ArrayList<>();
    @Override
    protected int getContentViewId() {
        return R.layout.activity_family_day_detail;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        familyDayDetailAdapter = new FamilyDayDetailAdapter(R.layout.item_family_day_detail,datas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(familyDayDetailAdapter);
        tvTitle.setText("统计");
        initData();

    }

    private void initData() {
        RetrofitFactory.getInstance().getFamilyAnchorDayDetail(TimeUtil.getTodayStart(), TimeUtil.getTodayEnd())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<BaseBean>() {
                    @Override
                    public void onGetData(BaseBean baseBean) {
                        LogUtils.e("Fammmm", GsonUtil.parseObjectToJson(baseBean));
                    }
                });
        RetrofitFactory.getInstance().getFamilyManageB(TimeUtil.getTodayStart(), TimeUtil.getTodayEnd()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FamilyBeanB>() {
                    @Override
                    public void accept(FamilyBeanB familyBeanB) throws Exception {
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
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
