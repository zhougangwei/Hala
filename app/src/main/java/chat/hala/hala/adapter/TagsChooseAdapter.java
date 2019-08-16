package chat.hala.hala.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chat.hala.hala.R;
import chat.hala.hala.bean.AnchorTagBean;
import chat.hala.hala.bean.TagBean;

/**
 * Created by kiddo on 2018/1/9.
 */

public class TagsChooseAdapter extends BaseQuickAdapter<AnchorTagBean.DataBean, BaseViewHolder> {


    public TagsChooseAdapter(int layoutIds, List<AnchorTagBean.DataBean> countryDatas) {
        super(layoutIds, countryDatas);
    }

    @Override
    protected void convert(BaseViewHolder helper, AnchorTagBean.DataBean item) {
        helper.setText(R.id.tv_content,item.getContent());
        if(item.isChoose()){
            helper.setTextColor(R.id.tv_content,mContext.getResources().getColor(R.color.white));
            helper.setBackgroundRes(R.id.tv_content,R.drawable.bg_rec_3blue);
        }else{
            helper.setTextColor(R.id.tv_content,mContext.getResources().getColor(R.color.black));
            helper.setBackgroundRes(R.id.tv_content,R.drawable.bg_rec_3grey);
        }

    }
}
