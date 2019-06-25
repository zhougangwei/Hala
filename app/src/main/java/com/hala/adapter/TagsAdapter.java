package com.hala.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hala.R;
import com.hala.bean.AnchorTagBean;

import java.util.List;

/**
 * Created by kiddo on 2018/1/9.
 */

public class TagsAdapter extends BaseQuickAdapter<AnchorTagBean.DataBean, BaseViewHolder> {




    public TagsAdapter(int layoutIds, List<AnchorTagBean.DataBean> countryDatas) {
        super(layoutIds, countryDatas);
    }

    @Override
    protected void convert(BaseViewHolder helper, AnchorTagBean.DataBean item) {
            helper.setText(R.id.tv_content,item.getContent());
    }
}
