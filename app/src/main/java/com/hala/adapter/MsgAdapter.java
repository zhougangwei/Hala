package com.hala.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hala.bean.CountryBean;
import com.hala.bean.MsgBean;

import java.util.List;

/**
 * Created by kiddo on 2018/1/9.
 */

public class MsgAdapter extends BaseQuickAdapter<MsgBean, BaseViewHolder> {




    public MsgAdapter(int layoutIds, List<MsgBean> countryDatas) {
        super(layoutIds, countryDatas);
    }

    @Override
    protected void convert(BaseViewHolder helper, MsgBean item) {


    }
}
