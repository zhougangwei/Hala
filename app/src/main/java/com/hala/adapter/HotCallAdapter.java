package com.hala.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hala.bean.OneToOneListBean;


import java.util.List;

/**
 * Created by kiddo on 2018/1/9.
 */

public class HotCallAdapter extends BaseQuickAdapter<OneToOneListBean.DataBean.ListBean, BaseViewHolder> {




    public HotCallAdapter(int layoutIds, List<OneToOneListBean.DataBean.ListBean> countryDatas) {
        super(layoutIds, countryDatas);
    }

    @Override
    protected void convert(BaseViewHolder helper, OneToOneListBean.DataBean.ListBean item) {


    }
}
