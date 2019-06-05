package com.hala.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hala.bean.AnchorBean;
import com.hala.bean.CountryBean;

import java.util.List;

/**
 * Created by kiddo on 2018/1/9.
 */

public class TagsAdapter extends BaseQuickAdapter<AnchorBean.DataBean.TagsBean, BaseViewHolder> {




    public TagsAdapter(int layoutIds, List<AnchorBean.DataBean.TagsBean> countryDatas) {
        super(layoutIds, countryDatas);
    }

    @Override
    protected void convert(BaseViewHolder helper, AnchorBean.DataBean.TagsBean item) {
        /*helper.setText(R.id.text, item.getTitle());*/
        /*helper.setImageResource(R.id.icon, item.getImageResource());*/
        // 加载网络图片




    }
}
