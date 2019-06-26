package chat.hala.hala.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import chat.hala.hala.R;
import chat.hala.hala.bean.OneToOneListBean;


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
        helper.setText(R.id.tv_online_state,item.isOnline()?"Online":"Offline");
        helper.setText(R.id.tv_name,item.getNickname());
        helper.setText(R.id.tv_content,item.getBiography());
        helper.setText(R.id.tv_cost,item.getCpm()+" /min");
        ImageView imageView = (ImageView) helper.getView(R.id.iv_bg);
        Glide.with(mContext).load(item.getAvatarUrl())
                .apply(RequestOptions.placeholderOf(imageView.getDrawable()))
                .into(imageView);
    }
}
