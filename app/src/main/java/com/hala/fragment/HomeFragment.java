package com.hala.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hala.R;
import com.hala.adapter.HomeAdapter;
import com.hala.base.BaseFragment;
import com.hala.base.Contact;
import com.hala.bean.OneToOneListBean;
import com.hala.http.BaseCosumer;
import com.hala.http.RetrofitFactory;
import com.hala.wight.NoScrollViewPager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends BaseFragment {


    private static final int HOT = 0;
    private static final int NEW = 1;
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
    Unbinder unbinder1;

    @Override
    protected void initView() {
        HomeAdapter homeAdapter = new HomeAdapter(getChildFragmentManager());
        vp.setAdapter(homeAdapter);
        vp.setOffscreenPageLimit(3);
        vp.setCurrentItem(0, false);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    setChecked(HOT);
                }else if(position==1){
                    setChecked(NEW);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

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
                ivNew.setVisibility(View.GONE);
                break;
            case NEW:
                tvNew.setTextSize(26f);
                tvHot.setTextSize(17f);
                ivNew.setVisibility(View.VISIBLE);
                ivHot.setVisibility(View.GONE);
                break;
        }

    }
}
