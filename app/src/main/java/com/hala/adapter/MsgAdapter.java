package com.hala.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hala.R;
import com.hala.bean.CountryBean;
import com.hala.bean.MessageUnreadBean;
import com.hala.bean.MsgBean;

import java.util.List;

/**
 * Created by kiddo on 2018/1/9.
 */

public class MsgAdapter extends BaseQuickAdapter<MessageUnreadBean.DataBean, BaseViewHolder> {




    public MsgAdapter(int layoutIds, List<MessageUnreadBean.DataBean> countryDatas) {
        super(layoutIds, countryDatas);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageUnreadBean.DataBean item) {
        helper.setText(R.id.tv_name, item.getTitle());
        helper.setText(R.id.tv_content, item.getBrief());
        ImageView imageView = (ImageView) helper.getView(R.id.iv_head);
        Glide.with(mContext).load(item.getIconUrl())
                .apply(RequestOptions.placeholderOf(imageView.getDrawable()))
                .into(imageView);



    }
}
