package com.hala.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hala.R;
import com.hala.bean.BaseBean;

import java.util.List;

public class HomeAdapter extends BaseQuickAdapter<BaseBean, BaseViewHolder> {
    public HomeAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseBean item) {
        /*helper.setText(R.id.text, item.getTitle());*/
        /*helper.setImageResource(R.id.icon, item.getImageResource());*/
        // 加载网络图片

    }
}