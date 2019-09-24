package chat.hala.hala.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.view.ActionMode;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import butterknife.BindView;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.adapter.HomeAdapter;
import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.base.BaseFragment;
import chat.hala.hala.wight.ScaleTransitionPagerTitleView;

public class HomeFragment extends BaseFragment {

    private static final int HOT = 0;
    private static final int NEW = 1;

    @BindView(R.id.magic_indicator)
    MagicIndicator mTab;
    @BindView(R.id.vp)
    ViewPager vp;
    String[] titles;


    @Override
    protected void initView() {
        if(AvchatInfo.isAnchor()){
            titles=new String[]{getString(R.string.suggest),getString(R.string.hot),getString(R.string.newnew)};
        }else{
            titles=new String[]{getString(R.string.hot),getString(R.string.newnew)};
        }
        CommonNavigator commonNavigator7 = new CommonNavigator(getActivity());
        commonNavigator7.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(titles[index]);
                simplePagerTitleView.setNormalColor(Color.parseColor("#323333"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#353535"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vp.setCurrentItem(index);
                    }
                });

                simplePagerTitleView.setTypeface(Typeface.DEFAULT , Typeface.BOLD );
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 4));
                indicator.setLineWidth(UIUtil.dip2px(context, 7));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
             //   indicator.setColors(getResources().getColor(R.color.linepager_indicator_color1),getResources().getColor(R.color.linepager_indicator_color2));
                indicator.setColors(Color.parseColor("#FF4066"));
                return indicator;
            }
        });
        mTab.setNavigator(commonNavigator7);
        HomeAdapter homeAdapter = new HomeAdapter(getChildFragmentManager());
        vp.setAdapter(homeAdapter);
        vp.setOffscreenPageLimit(3);
        vp.setCurrentItem(0, false);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mTab.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
            @Override
            public void onPageSelected(int position) {
                mTab.onPageSelected(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                mTab.onPageScrollStateChanged(state);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragmeng_home;
    }

    @Override
    protected void initData() {



    }





}
