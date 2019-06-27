package chat.hala.hala.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chat.hala.hala.R;
import chat.hala.hala.bean.AnchorTagBean;

/**
 * Created by kiddo on 2018/1/9.
 */

public class AnchorTagsAdapter extends BaseQuickAdapter<AnchorTagBean.DataBean, BaseViewHolder> {


    public AnchorTagsAdapter(int layoutIds, List<AnchorTagBean.DataBean> countryDatas) {
        super(layoutIds, countryDatas);
    }

    @Override
    protected void convert(BaseViewHolder helper, AnchorTagBean.DataBean item) {
        helper.setText(R.id.tv_content, item.getContent());
        helper.setBackgroundRes(R.id.rl, helper.getAdapterPosition()%2==0?R.drawable.bg_rec_red14:R.drawable.bg_rec_blue14);


    }
}
