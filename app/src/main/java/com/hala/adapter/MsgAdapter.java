package com.hala.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hala.bean.CountryBean;

import java.util.List;

/**
 * Created by kiddo on 2018/1/9.
 */

public class MsgAdapter extends BaseQuickAdapter<CountryBean, BaseViewHolder> {




    public MsgAdapter(int layoutIds, List<CountryBean> countryDatas) {
        super(layoutIds, countryDatas);
    }

    @Override
    protected void convert(BaseViewHolder helper, CountryBean item) {


    }
}
