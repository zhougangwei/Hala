package chat.hala.hala.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.adapter.FamilyManagerAdapter;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.wight.NoScrollViewPager;

public class FamilyManagerActivity extends BaseActivity {


    @BindView(R.id.vp)
    NoScrollViewPager vp;
    String[] titles;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.iv1)
    ImageView iv1;
    @BindView(R.id.iv2)
    ImageView iv2;
    @BindView(R.id.tv_family_manager)
    TextView tvFamilyManager;
    @BindView(R.id.tv_anchor_manager)
    TextView tvAnchorManager;
    private FamilyManagerAdapter familyManagerAdapter;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_family_manager;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {

        titles = new String[]{"家族管理", "主播管理"};
        familyManagerAdapter = new FamilyManagerAdapter(getSupportFragmentManager());
        vp.setAdapter(familyManagerAdapter);
        vp.setOffscreenPageLimit(3);
        vp.setCurrentItem(0);
        iv1.setVisibility(View.VISIBLE);
        iv2.setVisibility(View.GONE);
        tvAnchorManager.setTextColor(Color.WHITE);
        tvFamilyManager.setTextColor(Color.WHITE);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_family_manager, R.id.tv_anchor_manager})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_family_manager:
                vp.setCurrentItem(0);
                iv1.setVisibility(View.VISIBLE);
                iv2.setVisibility(View.GONE);
                tvAnchorManager.setTextColor(Color.WHITE);
                tvFamilyManager.setTextColor(Color.WHITE);
                tvFamilyManager.setTypeface(Typeface.DEFAULT , Typeface.BOLD );
                tvAnchorManager.setTypeface(Typeface.DEFAULT , Typeface.NORMAL );
                break;
            case R.id.tv_anchor_manager:
                vp.setCurrentItem(1);
                iv1.setVisibility(View.GONE);
                iv2.setVisibility(View.VISIBLE);
                tvAnchorManager.setTextColor(Color.BLACK);
                tvFamilyManager.setTextColor(Color.BLACK);
                tvFamilyManager.setTypeface(Typeface.DEFAULT , Typeface.NORMAL );
                tvAnchorManager.setTypeface(Typeface.DEFAULT , Typeface.BOLD );
                break;
        }
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
