package com.hala.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hala.R;
import com.hala.adapter.SimplePagerAdapter;
import com.hala.adapter.TagsAdapter;
import com.hala.base.BaseActivity;
import com.hala.base.Contact;
import com.hala.bean.AnchorBean;
import com.hala.bean.BaseBean;
import com.hala.http.BaseCosumer;
import com.hala.http.RetrofitFactory;
import com.hala.wight.RatingBarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    @BindView(R.id.tv_call)
    TextView tvCall;
    private List<AnchorBean.DataBean.CoversBean> coverDatas=new ArrayList<>();
    private List<AnchorBean.DataBean.TagsBean> tagsDatas=new ArrayList<>();
    private SimplePagerAdapter simplePagerAdapter;
    private TagsAdapter tagsAdapter;
    private int anchorId;


    public  static void startAnchorAc(Context context,int anchorId){
        Intent intent = new Intent(context, AnchorsActivity.class);
        intent.putExtra("anchorId",anchorId);
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
    }

    @Override
    protected void initView() {

        initCovers();
        initTags();
        initData();
    }



    private void initTags() {
        tagsAdapter = new TagsAdapter(R.layout.item_tag, tagsDatas);
        rvTags.setAdapter(tagsAdapter);


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
                        List<AnchorBean.DataBean.TagsBean> tags = data.getTags();
                        tagsDatas.addAll(tags);
                        tagsAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void initCovers() {


        simplePagerAdapter = new SimplePagerAdapter(this, coverDatas);
        vp_cover.setAdapter(simplePagerAdapter);


    }


    @OnClick({R.id.toolbar, R.id.tv_call})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                break;
            case R.id.tv_call:
                OneToOneActivity.docallOneToOneActivity(this,14);
                break;
        }
    }
}
