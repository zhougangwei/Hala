package chat.hala.hala.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import chat.hala.hala.R;

import chat.hala.hala.bean.OneToOneListBean;

import java.util.List;

/**
 * Created by kiddo on 2018/1/9.
 */

public class RandomAnchorAdapter extends BaseQuickAdapter<OneToOneListBean.DataBean.ListBean, BaseViewHolder> {




    public RandomAnchorAdapter(int layoutIds, List<OneToOneListBean.DataBean.ListBean> countryDatas) {
        super(layoutIds, countryDatas);
    }

    @Override
    protected void convert(BaseViewHolder helper, final OneToOneListBean.DataBean.ListBean item) {
        helper.setText(R.id.tv_name,item.getNickname());
        ImageView imageView = (ImageView) helper.getView(R.id.iv_head);
        Glide.with(mContext).load(item.getAlbum().get(0).getMediaUrl())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()).placeholder(imageView.getDrawable()))
                .into(imageView);
        helper.addOnClickListener(R.id.tv_call);



    }
}
