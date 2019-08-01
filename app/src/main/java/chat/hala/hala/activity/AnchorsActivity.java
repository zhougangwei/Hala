package chat.hala.hala.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.ScreenUtils;
import com.blankj.utilcode.utils.SizeUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.adapter.AnchorDataAdapter;
import chat.hala.hala.adapter.AnchorTagsAdapter;
import chat.hala.hala.adapter.SimplePagerAdapter;
import chat.hala.hala.base.Contact;
import chat.hala.hala.base.VideoCallManager;
import chat.hala.hala.bean.AnchorBean;
import chat.hala.hala.bean.AnchorInfoBean;
import chat.hala.hala.bean.AnchorTagBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.GsonUtil;
import chat.hala.hala.wight.RatingBarView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;


public class AnchorsActivity extends SlideBackActivity {

    @BindView(R.id.rl_banner)
    LinearLayout            rlBanner;
    @BindView(R.id.toolbar)
    Toolbar                 toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout            appbar;
    @BindView(R.id.vp_cover)
    ViewPager               vp_cover;
    @BindView(R.id.view)
    View                    view;
    @BindView(R.id.tv_name)
    TextView                tvName;
    @BindView(R.id.rbv)
    RatingBarView           rbv;
    @BindView(R.id.tv_introduction)
    TextView                tvIntroduction;
    @BindView(R.id.tv1)
    TextView                tv1;
    @BindView(R.id.tv_biography)
    TextView                tvBiography;
    @BindView(R.id.tv2)
    TextView                tv2;
    @BindView(R.id.rv_tags)
    RecyclerView            rvTags;

    @BindView(R.id.rv_info)
    RecyclerView      rvInfo;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    @BindView(R.id.tv_call)
    TextView          tvCall;

    @BindView(R.id.tv4)
    TextView  mTv4;
    @BindView(R.id.iv_message)
    ImageView mIvMessage;
    @BindView(R.id.iv_like)
    ImageView mIvLike;
    @BindView(R.id.tv_gender)
    TextView  mTvGender;
    @BindView(R.id.tv_distance)
    TextView  mTvDistance;
    @BindView(R.id.tv_last_active)
    TextView  mTvLastActive;
    @BindView(R.id.tv_score)
    TextView  mTvScore;
    @BindView(R.id.tv_fans)
    TextView  mTvFans;
    @BindView(R.id.tv_voice_call)
    TextView  mTvVoiceCall;

    @BindView(R.id.iv_more)
    ImageView mIvMore;


    private List<AnchorBean.DataBean.CoversBean> coverDatas      = new ArrayList<>();
    private List<AnchorTagBean.DataBean>         tagsDatas       = new ArrayList<>();
    private List<AnchorInfoBean>                 anchorInfoDatas = new ArrayList<>();


    private SimplePagerAdapter  simplePagerAdapter;
    private AnchorTagsAdapter   tagsAdapter;
    private int                 anchorId;
    private int                 anchorIdMemberId;
    private AnchorDataAdapter   mAnchorDataAdapter;
    private Paint               mPaint;
    private int                 mAnchormemberId;
    private AnchorBean.DataBean mAnchorData;
    private PopupWindow         mPopupWindow;


    public static void startAnchorAc(Context context, int anchorId, int anchorIdMemberId) {
        Intent intent = new Intent(context, AnchorsActivity.class);
        intent.putExtra("anchorId", anchorId);
        intent.putExtra("anchorIdMemberId", anchorIdMemberId);
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
        mPaint.setTextSize(SizeUtils.sp2px(this, 14));
        mPaint.setStyle(Paint.Style.FILL);
    }


    private void initTags() {
        tagsAdapter = new AnchorTagsAdapter(R.layout.item_anchor_tag, tagsDatas);
        rvTags.setAdapter(tagsAdapter);
        GridLayoutManager tagGridLayoutManager = new GridLayoutManager(AnchorsActivity.this, 100);
        rvTags.setLayoutManager(tagGridLayoutManager);
        rvTags.setItemAnimator(new DefaultItemAnimator());
        rvTags.setAdapter(tagsAdapter);
        tagGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int width = ScreenUtils.getScreenWidth(AnchorsActivity.this)
                        - SizeUtils.dp2px(AnchorsActivity.this, 28);
                int itemWidth = getTextWidth(mPaint,
                        tagsDatas.get(position).getContent()) +
                        +SizeUtils.dp2px(AnchorsActivity.this, 42);
                LogUtils.e("AnchorsActivity", "itemWidth:" + itemWidth + "--" + position);
                return Math.min(100, itemWidth * 100 / width + 1);
            }
        });


        mAnchorDataAdapter = new AnchorDataAdapter(R.layout.item_anchor_infp, anchorInfoDatas);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(AnchorsActivity.this, LinearLayoutManager.VERTICAL, false);
        rvInfo.setLayoutManager(gridLayoutManager);
        rvInfo.setItemAnimator(new DefaultItemAnimator());
        rvInfo.setAdapter(mAnchorDataAdapter);


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
                    public void onGetData(AnchorBean baseBean) {

                        Log.i("AnchorsActivity", "AnchorBean" + GsonUtil.parseObjectToJson(
                                baseBean
                        ));
                        if (Contact.REPONSE_CODE_SUCCESS != baseBean.getCode()) {
                            return;
                        }
                        mAnchorData = baseBean.getData();

                        mAnchormemberId = mAnchorData.getMemberId();
                        tvName.setText(mAnchorData.getNickname());
                        mTvScore.setText(mAnchorData.getStarLevel() + "");
                        mTvFans.setText("粉丝数目");
                        tvBiography.setText(mAnchorData.getBiography());
                        tvCall.setText(
                                String.format(getString(R.string.video_cost),
                                        mAnchorData.getCpm() + ""));
                        mTvVoiceCall.setText(
                                String.format(getString(R.string.video_cost),
                                        mAnchorData.getCpm() + ""));


                        tvIntroduction.setText(mAnchorData.getIntroduction());
                        List<AnchorBean.DataBean.CoversBean> covers = mAnchorData.getCovers();
                        coverDatas.addAll(covers);
                        simplePagerAdapter.notifyDataSetChanged();

                        List<AnchorTagBean.DataBean> tags = mAnchorData.getTags();
                        tagsDatas.clear();
                        tagsDatas.addAll(tags);
                        tagsAdapter.notifyDataSetChanged();


                        AnchorInfoBean anchorInfoBean = new AnchorInfoBean(getString(R.string.last_login), mAnchorData.isOnline() ? getString(R.string.online) : getString(R.string.offlIine));
                        AnchorInfoBean anchorInfoBean1 = new AnchorInfoBean(getString(R.string.answer_rate), "60%");
                        AnchorInfoBean anchorInfoBean2 = new AnchorInfoBean(getString(R.string.height), mAnchorData.getHeight() + "cm");
                        AnchorInfoBean anchorInfoBean3 = new AnchorInfoBean(getString(R.string.weight), mAnchorData.getWeight() + "kg");
                        AnchorInfoBean anchorInfoBean4 = new AnchorInfoBean(getString(R.string.city), mAnchorData.getCity() + "");
                        AnchorInfoBean anchorInfoBean5 = new AnchorInfoBean(getString(R.string.zodiac), mAnchorData.getZodiac() + "");
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


    @OnClick({R.id.toolbar, R.id.tv_call, R.id.iv_back, R.id.iv_message, R.id.iv_like, R.id.iv_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                break;
            case R.id.tv_call:
                VideoCallManager.gotoCallOrReverse(AnchorsActivity.this, anchorId, anchorIdMemberId);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_message:
                RongIM.getInstance().startPrivateChat(this, mAnchormemberId + "", mAnchorData != null ? mAnchorData.getNickname() : "");
                break;
            case R.id.iv_like:
                break;
            case R.id.iv_more:
                showMore();
                break;

        }
    }

    private void showMore() {

        if (mPopupWindow == null) {
            View contentView = View.inflate(this, R.layout.pop_anchor_more, null);
            TextView tv_report = contentView.findViewById(R.id.tv_report);
            TextView tv_add_black = contentView.findViewById(R.id.tv_add_black);

            tv_report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            tv_add_black.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            mPopupWindow = new PopupWindow(contentView,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT, true);
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable());
            mPopupWindow.setTouchable(true);


        }
        mPopupWindow.showAtLocation(mIvMore, Gravity.TOP | Gravity.END, SizeUtils.dp2px(this, 20), SizeUtils.dp2px(this, 50));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
