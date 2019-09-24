package chat.hala.hala.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
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
import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.base.Contact;
import chat.hala.hala.base.VideoCallManager;
import chat.hala.hala.bean.AnchorBean;
import chat.hala.hala.bean.AnchorInfoBean;
import chat.hala.hala.bean.AnchorTagBean;
import chat.hala.hala.bean.BaseBean;
import chat.hala.hala.dialog.ReportDialog;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.GsonUtil;
import chat.hala.hala.utils.ResultUtils;
import chat.hala.hala.utils.TimeUtil;
import chat.hala.hala.utils.ToastUtils;
import chat.hala.hala.wight.RatingBarView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;


public class AnchorsActivity extends SlideBackActivity {

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
    @BindView(R.id.tv_introduction)         //他说
            TextView tvIntroduction;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv_biography)
    TextView tvBiography;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.rv_tags)
    RecyclerView rvTags;

    @BindView(R.id.tv7)
    TextView tv7;
    @BindView(R.id.tv8)
    TextView tv8;
    @BindView(R.id.tv_cost)
    TextView tvCost;


    @BindView(R.id.rv_info)
    RecyclerView rvInfo;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    @BindView(R.id.tv_call)
    TextView tvCall;

    @BindView(R.id.tv4)
    TextView mTv4;
    @BindView(R.id.tv5)
    TextView mTv5;
    @BindView(R.id.iv_message)
    ImageView mIvMessage;
    @BindView(R.id.iv_like)
    ImageView mIvLike;
    @BindView(R.id.tv_gender)
    TextView mTvGender;
    @BindView(R.id.tv_distance)
    TextView mTvDistance;
    @BindView(R.id.tv_last_active)
    TextView mTvLastActive;
    @BindView(R.id.tv_score)
    TextView mTvScore;
    @BindView(R.id.tv_fans)
    TextView mTvFans;
    @BindView(R.id.tv_voice_call)
    TextView mTvVoiceCall;

    @BindView(R.id.iv_more)
    ImageView mIvMore;
    @BindView(R.id.iv_edit)
    ImageView mIvEdit;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;


    private List<AnchorBean.DataBean.AlbumBean> coverDatas = new ArrayList<>();
    private List<AnchorTagBean.DataBean> tagsDatas = new ArrayList<>();
    private List<AnchorInfoBean> anchorInfoDatas = new ArrayList<>();


    private SimplePagerAdapter simplePagerAdapter;
    private AnchorTagsAdapter tagsAdapter;
    private int anchorId;       //主播Id
    private int memberId;       //主播Id或者主播的用户表Id或者用户Id
    private AnchorDataAdapter mAnchorDataAdapter;
    private Paint mPaint;
    private int mAnchormemberId;
    private AnchorBean.DataBean mAnchorData;
    private PopupWindow mPopupWindow;

    private boolean blockState;     //拉黑状态
    private boolean followState;    //关注状态

    public static final int ANCHOR_AC = 0;
    public static final int EDIT_AC = 1;
    private int fromAc;
    private TextView tvAddBlack;


    public static void startAnchorAc(Context context, int anchorId, int anchorIdMemberId) {
        Intent intent = new Intent(context, AnchorsActivity.class);
        intent.putExtra("anchorId", anchorId);
        intent.putExtra("memberId", anchorIdMemberId);
        intent.putExtra("fromAc", ANCHOR_AC);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_anchors;
    }

    @Override
    protected void beforeInitView() {
        Intent intent = getIntent();
        fromAc = intent.getIntExtra("fromAc", ANCHOR_AC);
        anchorId = intent.getIntExtra("anchorId", 0);
        memberId = intent.getIntExtra("memberId", 0);
    }

    @Override
    protected void initView() {
        if(AvchatInfo.isAnchor()){
            rlBottom.setVisibility(View.GONE);
        }
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


    public boolean isAnchor() {
        return anchorId != 0;
    }


    private void getAuchor() {
        RetrofitFactory.getInstance()
                .getAnchorData(isAnchor() ? "anchor" : "member", isAnchor() ? anchorId : memberId)
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
                        mTvScore.setText(mAnchorData.getMarking() + "");
                        mTvFans.setText(mAnchorData.getFansCount() + "");
                        followState = baseBean.getData().isFollowing();
                        blockState = baseBean.getData().isBlocking();

                        if (mAnchorData.isFollowing()) {
                            mIvLike.setImageDrawable(AnchorsActivity.this.getResources().getDrawable(R.drawable.ic_like));
                        } else {
                            mIvLike.setImageDrawable(AnchorsActivity.this.getResources().getDrawable(R.drawable.ic_dislike));
                        }


                        if (isAnchor()) {                //查看过来的 查看用户的的界面
                            tvBiography.setVisibility(View.VISIBLE);
                            tvBiography.setText(mAnchorData.getIntroduction());
                            tvCall.setText(
                                    String.format(getString(R.string.video_cost),
                                            mAnchorData.getSetting().getVideoCpm() + ""));
                            mTvVoiceCall.setText(
                                    String.format(getString(R.string.voice_cost),
                                            mAnchorData.getSetting().getAudioCpm() + ""));
                        } else {
                            //隐藏Ta说
                            tv1.setVisibility(View.GONE);
                            tvBiography.setVisibility(View.GONE);
                            // 显示用户花费
                            tv3.setVisibility(View.GONE);
                            rvTags.setVisibility(View.GONE);
                            tvCost.setVisibility(View.VISIBLE);
                            tvCost.setText(mAnchorData.getSpent() + "");
                            tv7.setVisibility(View.VISIBLE);
                            tv8.setVisibility(View.VISIBLE);
                            //显示关注
                            mTv5.setText("关注");
                            mTvScore.setText(mAnchorData.getFollowingCount() + "");
                            mTv4.setVisibility(View.GONE);
                            mTvFans.setVisibility(View.GONE);
                        }

                        if (fromAc == EDIT_AC) {            //编辑用户
                            mIvEdit.setVisibility(View.VISIBLE);
                            mIvMore.setVisibility(View.GONE);
                            mIvMessage.setVisibility(View.GONE);
                            mIvLike.setVisibility(View.GONE);
                            tvCall.setVisibility(View.GONE);
                            mTvVoiceCall.setVisibility(View.GONE);
                        }
                        tvIntroduction.setText(mAnchorData.getAutograph());
                        List<AnchorBean.DataBean.AlbumBean> covers = mAnchorData.getAlbum();
                        coverDatas.addAll(covers);
                        simplePagerAdapter.notifyDataSetChanged();
                        List<AnchorTagBean.DataBean> tags = mAnchorData.getTags();
                        if (tags != null && tags.size() > 0) {
                            tagsDatas.clear();
                            tagsDatas.addAll(tags);
                            tagsAdapter.notifyDataSetChanged();
                        }
                        mTvLastActive.setText(String.format(getResources().getString(R.string.many_mins_active),
                                mAnchorData.getLastActiveMinuteGap() + "分钟"
                        ));
                        mTvDistance.setText(String.format(getResources().getString(R.string.distance_km),
                                mAnchorData.getLastActiveMinuteGap() + ""
                        ));

                        AnchorInfoBean anchorInfoBean = new AnchorInfoBean("账号", mAnchorData.getCharacterId());
                        AnchorInfoBean anchorInfoBean1 = new AnchorInfoBean("应答率", mAnchorData.getAnswerRate());
                        AnchorInfoBean anchorInfoBean2 = new AnchorInfoBean("身高体重", mAnchorData.getHeight() + "cm/" + mAnchorData.getWeight() + "kg");
                        AnchorInfoBean anchorInfoBean3 = new AnchorInfoBean("现居地", mAnchorData.getResidentialPlace());
                        AnchorInfoBean anchorInfoBean4 = new AnchorInfoBean("星座", TimeUtil.getConstellation(mAnchorData.getBirthDate()));
                        AnchorInfoBean anchorInfoBean5 = new AnchorInfoBean("注册日期", mAnchorData.getCreatedAtDate());
                        AnchorInfoBean anchorInfoBean6 = new AnchorInfoBean("个性签名", mAnchorData.getAutograph());


                        anchorInfoDatas.add(anchorInfoBean);
                        if (anchorId != 0) {                                //如果不是主播就没这个
                            anchorInfoDatas.add(anchorInfoBean1);
                            anchorInfoDatas.add(anchorInfoBean2);
                        }
                        anchorInfoDatas.add(anchorInfoBean3);
                        anchorInfoDatas.add(anchorInfoBean4);
                        anchorInfoDatas.add(anchorInfoBean5);
                        anchorInfoDatas.add(anchorInfoBean6);
                        mAnchorDataAdapter.notifyDataSetChanged();

                    }
                });
    }

    private void initCovers() {
        simplePagerAdapter = new SimplePagerAdapter(AnchorsActivity.this, coverDatas);
        vp_cover.setAdapter(simplePagerAdapter);

    }


    @OnClick({R.id.toolbar, R.id.tv_call, R.id.iv_back, R.id.iv_message, R.id.iv_like, R.id.iv_more, R.id.iv_edit, R.id.tv_voice_call})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                break;
            case R.id.tv_call:
                VideoCallManager.gotoCallAnchor(AnchorsActivity.this, VideoCallManager.VIDEO_CALL, anchorId, memberId);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_message:
                RongIM.getInstance().startPrivateChat(this, mAnchormemberId + "", mAnchorData != null ? mAnchorData.getNickname() : "");
                break;
            case R.id.iv_like:
                if (followState) {
                    addFollow("unfollow");
                } else {
                    addFollow("follow");
                }
                break;
            case R.id.iv_more:
                showMore();
                break;
            case R.id.iv_edit:
                editUser();
                break;
            case R.id.tv_voice_call:
                VideoCallManager.gotoCallAnchor(AnchorsActivity.this, VideoCallManager.AUDIO_CALL, anchorId, memberId);
                break;
        }
    }

    private void editUser() {
        startActivity(new Intent(this, EditUserActivity.class));
    }


    private void addFollow(String followOrUnFollow) {
        RetrofitFactory.getInstance().addFollow(followOrUnFollow, memberId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<BaseBean>() {
                    @Override
                    public void onGetData(BaseBean baseBean) {
                        if (ResultUtils.cheekSuccess(baseBean)) {
                            followState = !followState;
                            if (followState) {
                                mIvLike.setImageDrawable(AnchorsActivity.this.getResources().getDrawable(R.drawable.ic_like));
                                ToastUtils.showToast(AnchorsActivity.this, "关注成功!");
                            } else {
                                mIvLike.setImageDrawable(AnchorsActivity.this.getResources().getDrawable(R.drawable.ic_dislike));
                                ToastUtils.showToast(AnchorsActivity.this, "取消关注成功!");
                            }
                        }
                    }
                });
    }

    //拉黑或者不拉黑
    private void addBlock() {
        String state = "unblock";
        if (blockState) {
            state = "unblock";
            tvAddBlack.setText("取消黑名单");
        } else {
            state = "block";
            tvAddBlack.setText("加入黑名单");
        }
        RetrofitFactory.getInstance().addBlock(state, memberId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<BaseBean>() {
                    @Override
                    public void onGetData(BaseBean baseBean) {
                        blockState = !blockState;
                        if (blockState) {
                            ToastUtils.showToast(AnchorsActivity.this, "拉黑成功!");
                            if (tvAddBlack != null) {
                                tvAddBlack.setText("取消黑名单");
                            }
                        } else {
                            if (tvAddBlack != null) {
                                tvAddBlack.setText("加入黑名单");
                            }
                            ToastUtils.showToast(AnchorsActivity.this, "取消拉黑成功!");
                        }
                    }
                });
    }


    private void showMore() {

        if (mPopupWindow == null) {
            View contentView = View.inflate(this, R.layout.pop_anchor_more, null);
            TextView tv_report = contentView.findViewById(R.id.tv_report);
            tvAddBlack = contentView.findViewById(R.id.tv_add_black);

            if (blockState) {

                tvAddBlack.setText("取消黑名单");
            } else {

                tvAddBlack.setText("加入黑名单");
            }
            tv_report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ReportDialog(AnchorsActivity.this, memberId).show();
                }
            });

            tvAddBlack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addBlock();
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


}
