package chat.hala.hala.adapter;

import android.widget.ImageView;

import com.blankj.utilcode.utils.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chat.hala.hala.R;
import chat.hala.hala.bean.OneToOneListBean;

/**
 * Created by kiddo on 2018/1/9.
 */

public class HotCallAdapter extends BaseQuickAdapter<OneToOneListBean.DataBean.ListBean, BaseViewHolder> {




    public HotCallAdapter(int layoutIds, List<OneToOneListBean.DataBean.ListBean> countryDatas) {
        super(layoutIds, countryDatas);
    }

    @Override
    protected void convert(BaseViewHolder helper, OneToOneListBean.DataBean.ListBean item) {
        helper.setText(R.id.tv_online_state,item.isOnline()?R.string.online:R.string.offlIine);
        helper.setText(R.id.tv_name,item.getNickname());
        helper.setText(R.id.tv_content,item.getBiography());
        helper.setText(R.id.tv_cost,item.getCpm()+" /min");
        ImageView imageView = (ImageView) helper.getView(R.id.iv_bg);
        LogUtils.e(TAG, "convert: "+item.getAvatarUrl());
        Glide.with(mContext).load(item.getCovers()==null?null:item.getCovers().get(0).getCoverUrl())
                .apply(RequestOptions.placeholderOf(imageView.getDrawable()))
                .into(imageView);
    }
}
