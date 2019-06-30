package chat.hala.hala.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ScreenUtils;
import com.blankj.utilcode.utils.SizeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.adapter.AnchorDataAdapter;
import chat.hala.hala.adapter.AnchorTagsAdapter;
import chat.hala.hala.adapter.SimplePagerAdapter;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.base.Contact;
import chat.hala.hala.base.VideoCallManager;
import chat.hala.hala.bean.AnchorBean;
import chat.hala.hala.bean.AnchorInfoBean;
import chat.hala.hala.bean.AnchorTagBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.wight.RatingBarView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class AnchorsActivity extends BaseActivity {

    @BindView(R.id.rl_banner)
    LinearLayout rlBanner;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.vp_cover)
    ViewPager vp_cover;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.rbv)
    RatingBarView rbv;
    @BindView(R.id.tv_introduction)
    TextView tvIntroduction;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv_biography)
    TextView tvBiography;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.rv_tags)
    RecyclerView rvTags;

    @BindView(R.id.rv_info)
    RecyclerView rvInfo;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    @BindView(R.id.tv_call)
    TextView tvCall;
    private List<AnchorBean.DataBean.CoversBean> coverDatas      =new ArrayList<>();
    private List<AnchorTagBean.DataBean>   tagsDatas       =new ArrayList<>();
    private List<AnchorInfoBean>                 anchorInfoDatas =new ArrayList<>();



    private SimplePagerAdapter simplePagerAdapter;
    private AnchorTagsAdapter tagsAdapter;
    private int anchorId;
    private int anchorIdMemberId;
    private AnchorDataAdapter mAnchorDataAdapter;
    private Paint mPaint;


    public  static void startAnchorAc(Context context,int anchorId,int anchorIdMemberId){
        Intent intent = new Intent(context, AnchorsActivity.class);
        intent.putExtra("anchorId",anchorId);
        intent.putExtra("anchorIdMemberId",anchorIdMemberId);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_anchors;
    }

    @Override
    protected void beforeInitView() {
        Intent intent = getIntent();
        anchorId = intent.getIntExtra("anchorId", 0);
        anchorIdMemberId = intent.getIntExtra("anchorIdMemberId", 0);
    }

    @Override
    protected void initView() {

        initPaint();
        initCovers();
        initTags();
        initData();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(SizeUtils.sp2px(this, 13));
        mPaint.setStyle(Paint.Style.FILL);
    }


    private void initTags() {
        tagsAdapter = new AnchorTagsAdapter(R.layout.item_anchor_tag, tagsDatas);
        rvTags.setAdapter(tagsAdapter);
        GridLayoutManager tagGridLayoutManager = new GridLayoutManager(AnchorsActivity.this, 100);
        rvTags.setLayoutManager(tagGridLayoutManager);
        rvTags.setItemAnimator(new DefaultItemAnimator());
        rvTags.setAdapter(mAnchorDataAdapter);
        tagGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int width = ScreenUtils.getScreenWidth(AnchorsActivity.this)
                        - SizeUtils.dp2px(AnchorsActivity.this, 28);
                int itemWidth = getTextWidth(mPaint,
                        tagsDatas.get(position).getContent() +
                        + SizeUtils.dp2px(AnchorsActivity.this, 57));
                return Math.min(100,itemWidth * 100 / width + 1);
            }
        });









        mAnchorDataAdapter = new AnchorDataAdapter(R.layout.item_anchor_infp, anchorInfoDatas);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(AnchorsActivity.this, 100);
        rvInfo.setLayoutManager(gridLayoutManager);
        rvInfo.setItemAnimator(new DefaultItemAnimator());
        rvInfo.setAdapter(mAnchorDataAdapter);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int width = ScreenUtils.getScreenWidth(AnchorsActivity.this)
                        - SizeUtils.dp2px(AnchorsActivity.this, 28);
                int itemWidth = getTextWidth(mPaint,
                        anchorInfoDatas.get(position).getName() +
                                    " " + anchorInfoDatas.get(position).getContent())
                            + SizeUtils.dp2px(AnchorsActivity.this, 67);
                return Math.min(100,itemWidth * 100 / width + 1);
            }
        });

    }

    private int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    private void initData() {
        getAuchor();
    }
    private void getAuchor() {
        RetrofitFactory.getInstance()
                .getAnchorData(anchorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<AnchorBean>() {
                    @Override
                    public void onNext(AnchorBean baseBean) {
                        if (Contact.REPONSE_CODE_SUCCESS!=baseBean.getCode()) {
                            return;
                        }
                        AnchorBean.DataBean data = baseBean.getData();
                        tvName.setText(data.getNickname());
                        rbv.setStar(data.getStarLevel(),false);
                        tvBiography.setText(data.getBiography());
                        tvIntroduction.setText(data.getIntroduction());
                        List<AnchorBean.DataBean.CoversBean> covers = data.getCovers();
                        coverDatas.addAll(covers);
                        simplePagerAdapter.notifyDataSetChanged();

                        List<AnchorTagBean.DataBean> tags = data.getTags();
                        tagsDatas.clear();
                        tagsDatas.addAll(tags);
                        tagsAdapter.notifyDataSetChanged();

                        AnchorInfoBean anchorInfoBean = new AnchorInfoBean("Last login:", data.isOnline() ? getString(R.string.online) : getString(R.string.offlIine));
                        AnchorInfoBean anchorInfoBean1 = new AnchorInfoBean("Answer rate:", "60%");
                        AnchorInfoBean anchorInfoBean2 = new AnchorInfoBean("Height:", data.getHeight() + "cm");
                        AnchorInfoBean anchorInfoBean3 = new AnchorInfoBean("Weight:", data.getWeight() + "kg");
                        AnchorInfoBean anchorInfoBean4 = new AnchorInfoBean("City:", data.getCity() + "");
                        AnchorInfoBean anchorInfoBean5 = new AnchorInfoBean("Zodiac:", data.getZodiac() + "");
                        anchorInfoDatas.add(anchorInfoBean);
                        anchorInfoDatas.add(anchorInfoBean1);
                        anchorInfoDatas.add(anchorInfoBean2);
                        anchorInfoDatas.add(anchorInfoBean3);
                        anchorInfoDatas.add(anchorInfoBean4);
                        anchorInfoDatas.add(anchorInfoBean5);
                        mAnchorDataAdapter.notifyDataSetChanged();

                    }
                });
    }

    private void initCovers() {
        simplePagerAdapter = new SimplePagerAdapter(AnchorsActivity.this, coverDatas);
        vp_cover.setAdapter(simplePagerAdapter);

    }


    @OnClick({R.id.toolbar, R.id.tv_call,R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                break;
            case R.id.tv_call:
                VideoCallManager.gotoCallOrReverse(AnchorsActivity.this,anchorId,anchorIdMemberId);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
