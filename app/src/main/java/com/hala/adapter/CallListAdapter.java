package com.hala.adapter;

import android.graphics.Color;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hala.R;
import com.hala.bean.CallListBean;
import com.hala.bean.MessageUnreadBean;

import java.util.List;



public class CallListAdapter extends BaseQuickAdapter<CallListBean.DataBean.ContentBean, BaseViewHolder> {


    public static final String SUCCESS="success";
    public static final String MISSED="missed";
    public static final String NO_ANSWER="no_answer";


    public CallListAdapter(int layoutIds, List<CallListBean.DataBean.ContentBean> countryDatas) {
        super(layoutIds, countryDatas);
    }

    @Override
    protected void convert(BaseViewHolder helper, CallListBean.DataBean.ContentBean item) {
        helper.setText(R.id.tv_name, item.getTargetInfo().getName());
        String content="";
        switch (item.getState()) {
            case SUCCESS:
                content ="Call duration 40 "+item.getDurationSeconds()+"seconds" +"("+item.getWorth()+" coins)";
                break;
            case  MISSED:
                content ="Missed call";
                helper.setTextColor(R.id.tv_content, mContext.getResources().getColor(R.color.pink));
                break;
            case   NO_ANSWER:
                content="No answered";
                helper.setTextColor(R.id.tv_content, mContext.getResources().getColor(R.color.pink));
                break;
        }
        helper.setText(R.id.tv_content, content);
        ImageView imageView = (ImageView) helper.getView(R.id.iv_head);
        Glide.with(mContext).load(item.getTargetInfo().getAvatarUrl())
                .placeholder(imageView.getDrawable())
                .into(imageView);



    }
}
