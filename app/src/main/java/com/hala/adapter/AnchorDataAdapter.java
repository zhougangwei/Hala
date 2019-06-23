package com.hala.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hala.bean.AnchorInfoBean;

import java.util.List;

/**
 * Created by kiddo on 2018/1/9.
 */

public class AnchorDataAdapter extends BaseQuickAdapter<AnchorInfoBean, BaseViewHolder> {




    public AnchorDataAdapter(int layoutIds, List<AnchorInfoBean> countryDatas) {
        super(layoutIds, countryDatas);
    }

    @Override
    protected void convert(BaseViewHolder helper, AnchorInfoBean item) {
        /*helper.setText(R.id.text, item.getTitle());*/
        /*helper.setImageResource(R.id.icon, item.getImageResource());*/
        // 加载网络图片




    }
}
