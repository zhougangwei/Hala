package chat.hala.hala.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;
import java.util.Random;

import chat.hala.hala.R;
import chat.hala.hala.bean.AnchorTagBean;

/**
 * Created by kiddo on 2018/1/9.
 */

public class AnchorTagsAdapter extends BaseQuickAdapter<AnchorTagBean.DataBean, BaseViewHolder> {



            String args[]={
   "#EA607F", "#82D07A", "#F2A200", "#66C2AD",
    "#8CCACE", "#00CEF8", "#BD6BEE", "#F4D14D",
    "#E9B4CD", "#C368A4", "#9DE1F6", "#F8555A",
    "#C0D500", "#71BCA1", "#F29FB8", "#80B175"};

    public AnchorTagsAdapter(int layoutIds, List<AnchorTagBean.DataBean> countryDatas) {
        super(layoutIds, countryDatas);
    }

    @Override
    protected void convert(BaseViewHolder helper, AnchorTagBean.DataBean item) {
        helper.setText(R.id.tv_content, item.getContent());
        View view = helper.getView(R.id.rl);
        GradientDrawable  mGrad = (GradientDrawable)view.getBackground();
        mGrad.setColor(Color.parseColor(args[new Random().nextInt(args.length)]));
    }
}
