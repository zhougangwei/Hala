package chat.hala.hala.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.blankj.utilcode.utils.SizeUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import butterknife.BindView;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.adapter.FansOrFollowAdapter;
import chat.hala.hala.base.BaseActivity;

public class FollowOrFansActivity extends BaseActivity {

    private static final String TAG = "FollowOrFansActivity";
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.vp)
    ViewPager vp;

    private int type;

    private String[] titles=new String[]{"关注","粉丝","好友"};
    @Override
    protected int getContentViewId() {
        return R.layout.activity_follow_or_fans;
    }

    @Override
    protected void beforeInitView() {
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);

        magicIndicator.setBackgroundResource(R.color.white);
        FansOrFollowAdapter homeAdapter = new FansOrFollowAdapter(getSupportFragmentManager());
        vp.setAdapter(homeAdapter);

        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                clipPagerTitleView.setText(titles[index]);
                clipPagerTitleView.setTextSize(SizeUtils.sp2px(FollowOrFansActivity.this,17));
                clipPagerTitleView.setTextColor(Color.parseColor("#FFA7A7A7"));
                clipPagerTitleView.setClipColor(Color.parseColor("#FFFF6962"));
                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vp.setCurrentItem(index);
                    }
                });
                return clipPagerTitleView;
            }
            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 2));
                indicator.setLineWidth(UIUtil.dip2px(context, 15));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                //   indicator.setColors(getResources().getColor(R.color.linepager_indicator_color1),getResources().getColor(R.color.linepager_indicator_color2));
                indicator.setColors(Color.parseColor("#FFFF6962"));
                return indicator;
            }
        });

        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, vp);
        vp.setCurrentItem(type);
    }



    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
