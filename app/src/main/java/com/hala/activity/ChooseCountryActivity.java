package com.hala.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.hala.R;
import com.hala.adapter.CountryAdapter;
import com.hala.base.BaseActivity;
import com.hala.bean.CountryBean;
import com.hala.utils.AssetUtils;
import com.hala.utils.GsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kiddo on 2018/1/9.
 */

public class ChooseCountryActivity extends BaseActivity {

    RecyclerView rvCountry;
    private CountryAdapter adapter;

    List<CountryBean> countryDatas = new ArrayList<>();


    private void initDatas() {
        String json = AssetUtils.getJson(this, "country.json");
        List<CountryBean> objects = GsonUtil.parseJsonToList(json, new TypeToken<List<CountryBean>>() {
        }.getType());
        countryDatas.addAll(objects);
        adapter.notifyDataSetChanged();
    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_choose_country;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvCountry.setLayoutManager(layoutManager);
        adapter = new CountryAdapter(R.layout.item_country,countryDatas) ;
        rvCountry.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("countryName",countryDatas.get(position).getCountryName());
                intent.putExtra("countryCode",Integer.parseInt(countryDatas.get(position).getCountryCode()));
                setResult(RESULT_OK);
                finish();
            }
        });
        initDatas();
    }
}
