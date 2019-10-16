package chat.hala.hala.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chat.hala.hala.R;
import chat.hala.hala.bean.LoginBean;
import chat.hala.hala.bean.OneToOneListBean;
import chat.hala.hala.fragment.FansFragment;

/**
 * Created by kiddo on 2018/1/9.
 */

public class FansAdapter extends BaseQuickAdapter<OneToOneListBean.DataBean.ListBean, BaseViewHolder> {

    public int type;

    public FansAdapter(int layoutIds, List<OneToOneListBean.DataBean.ListBean> countryDatas, int type) {
        super(layoutIds, countryDatas);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, OneToOneListBean.DataBean.ListBean item) {


        helper.setText(R.id.tv_name, item.getNickname());

        helper.setText(R.id.tv_bio, item.getIntroduction());
        ImageView imageView = (ImageView) helper.getView(R.id.iv_head);
        Glide.with(mContext).load(item.getAlbum().get(0).getMediaUrl())
                .apply((RequestOptions.bitmapTransform(new CircleCrop()).placeholder(imageView.getDrawable())))
                .into(imageView);
        switch (type) {
            case FansFragment.FANS:
                helper.setText(R.id.tv_hi, "+关注");
                helper.addOnClickListener(R.id.tv_hi);
                helper.setBackgroundRes(R.id.tv_hi, R.drawable.bg_fans_follow_list);
                break;
            case FansFragment.FOLLOW:
                helper.setText(R.id.tv_hi, "已关注");
                break;
            case FansFragment.FRIENDS:
                helper.setVisible(R.id.tv_hi, false);
                break;
        }
        if(item.getFollowing()){
            helper.setText(R.id.tv_hi, "已关注");
            helper.setBackgroundRes(R.id.tv_hi, R.drawable.bg_fans_list);
        }
    }
}
