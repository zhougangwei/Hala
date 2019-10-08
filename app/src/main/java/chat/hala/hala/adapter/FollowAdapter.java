package chat.hala.hala.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chat.hala.hala.R;
import chat.hala.hala.bean.OneToOneListBean;

/**
 * Created by kiddo on 2018/1/9.
 */

public class FollowAdapter extends BaseQuickAdapter<OneToOneListBean.DataBean.ListBean, BaseViewHolder> {



    public FollowAdapter(int layoutIds, List<OneToOneListBean.DataBean.ListBean> countryDatas) {
        super(layoutIds, countryDatas);
    }

    @Override
    protected void convert(BaseViewHolder helper, OneToOneListBean.DataBean.ListBean item) {
        helper.setText(R.id.tv_online_state,item.isOnline()?R.string.online:R.string.offlIine);
        helper.setText(R.id.tv_name,item.getNickname());
        helper.setText(R.id.tv_content,item.getIntroduction());
        helper.setText(R.id.tv_cost,item.getSetting()==null?"":item.getSetting().getChatCpm()+"");
        ImageView imageView = (ImageView) helper.getView(R.id.iv_bg);
        Glide.with(mContext).load(item.getAlbum().get(0).getMediaUrl())
                .apply(RequestOptions.placeholderOf(imageView.getDrawable()))
                .into(imageView);
    }
}
