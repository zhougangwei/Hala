package chat.hala.hala.adapter;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

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
        if(item.isOnline()){
        TextView tvState = helper.getView(R.id.tv_online_state);
        Drawable drawable = mContext.getResources().getDrawable(R.drawable.ic_onlinee);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
        tvState.setCompoundDrawables(drawable, null, null, null);
    }else {
        TextView tvState = helper.getView(R.id.tv_online_state);
        Drawable drawable = mContext.getResources().getDrawable(R.drawable.ic_off_linee);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
        tvState.setCompoundDrawables(drawable, null, null, null);
    }
        Typeface typeFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/dinbold.ttf");
        TextView tvCost = helper.getView(R.id.tv_cost);
        tvCost.setTypeface(typeFace);
        helper.setText(R.id.tv_cost,item.getSetting()==null?"":item.getSetting().getVideoCpm()+"");

        helper.setText(R.id.tv_online_state,item.isOnline()?R.string.online:R.string.offlIine);
        helper.setText(R.id.tv_name,item.getNickname());
        helper.setText(R.id.tv_content,item.getIntroduction());

        ImageView imageView = (ImageView) helper.getView(R.id.iv_bg);
        Glide.with(mContext).load(item.getAlbum().get(0).getMediaUrl())
                .apply(RequestOptions.placeholderOf(imageView.getDrawable()))
                .into(imageView);

        if(item.getAnchorId()==0){
            helper.setVisible(R.id.tv_online_state,false);
            helper.setVisible(R.id.tv_content,false);
            helper.setVisible(R.id.tv_cost,false);
        }

    }
}
