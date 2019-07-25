package chat.hala.hala.fragment;

import android.content.Context;
import android.support.v4.view.ViewPager;
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
import chat.hala.hala.base.BaseFragment;
import chat.hala.hala.wight.ScaleTransitionPagerTitleView;

public class HomeFragment extends BaseFragment {

    private static final int HOT = 0;
    private static final int NEW = 1;

    @BindView(R.id.magic_indicator)
    MagicIndicator mTab;

    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.tv_hot)
    TextView tvHot;
    @BindView(R.id.iv_hot)
    ImageView ivHot;
    @BindView(R.id.tv_new)
    TextView tvNew;
    @BindView(R.id.iv_new)
    ImageView ivNew;

    String[] titles;


    @Override
    protected void initView() {
        titles=new String[]{getString(R.string.hot),getString(R.string.newnew)};
        CommonNavigator commonNavigator7 = new CommonNavigator(getActivity());

        commonNavigator7.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(titles[index]);
                simplePagerTitleView.setNormalColor(getActivity().getResources().getColor(R.color.black));
                simplePagerTitleView.setSelectedColor(getActivity().getResources().getColor(R.color.black));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vp.setCurrentItem(index);
                    }
                });

                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 4));
                indicator.setLineWidth(UIUtil.dip2px(context, 12));
                indicator.setRoundRadius(UIUtil.dip2px(context, 2));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(getResources().getColor(R.color.linepager_indicator_color1),getResources().getColor(R.color.linepager_indicator_color2));

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




    @OnClick({R.id.tv_hot, R.id.tv_new})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_hot:
                vp.setCurrentItem(0, false);
                setChecked(HOT);
                break;
            case R.id.tv_new:
                vp.setCurrentItem(1, false);
                setChecked(NEW);
                break;
        }
    }

    private void setChecked(int type) {
        switch (type) {
            case HOT:
                tvHot.setTextSize(26f);
                tvNew.setTextSize(17f);
                ivHot.setVisibility(View.VISIBLE);
                ivNew.setVisibility(View.INVISIBLE);
                break;
            case NEW:
                tvNew.setTextSize(26f);
                tvHot.setTextSize(17f);
                ivNew.setVisibility(View.VISIBLE);
                ivHot.setVisibility(View.INVISIBLE);
                break;
        }

    }
}
