package chat.hala.hala.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.SizeUtils;

import chat.hala.hala.R;
import chat.hala.hala.adapter.HomeAdapter;
import chat.hala.hala.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

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




    @Override
    protected void initView() {
        HomeAdapter homeAdapter = new HomeAdapter(getChildFragmentManager());
        vp.setAdapter(homeAdapter);
        vp.setOffscreenPageLimit(3);
        vp.setCurrentItem(0, false);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                tvHot.setTextSize(26f);
                tvNew.setTextSize(17f);




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
