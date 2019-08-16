package chat.hala.hala.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chat.hala.hala.R;
import chat.hala.hala.bean.AnchorTagBean;

/**
 * Created by kiddo on 2018/1/9.
 */

public class TagsAdapter extends BaseMultiItemQuickAdapter<AnchorTagBean.DataBean, BaseViewHolder> {
    public static final int HEAD=0;
    public static final int TEXT=1;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public TagsAdapter(List<AnchorTagBean.DataBean> data) {
            super(data);
            addItemType(TagsAdapter.HEAD, R.layout.tag_text);
            addItemType(TagsAdapter.TEXT, R.layout.item_choose_tag);
    }

    @Override
    protected void convert(BaseViewHolder helper, AnchorTagBean.DataBean item) {
        switch (helper.getItemViewType()) {
            case TagsAdapter.TEXT:
                helper.setText(R.id.tv_content,item.getContent());
                if(item.isChoose()){
                    helper.setTextColor(R.id.tv_content,mContext.getResources().getColor(R.color.white));
                    helper.setBackgroundRes(R.id.tv_content,R.drawable.bg_rec_3blue);
                }else{
                    helper.setTextColor(R.id.tv_content, Color.parseColor("#666666"));
                    helper.setBackgroundRes(R.id.tv_content,R.drawable.bg_rec_3grey);
                }
                break;
            case TagsAdapter.HEAD:
                helper.setText(R.id.tv_content,item.getContent());
                break;
        }



    }
}
