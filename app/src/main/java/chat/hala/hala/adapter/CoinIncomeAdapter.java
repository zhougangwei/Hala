package chat.hala.hala.adapter;

import android.graphics.Color;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chat.hala.hala.R;
import chat.hala.hala.bean.CoinListBean;

/**
 * Created by kiddo on 2018/1/9.
 */

public class CoinIncomeAdapter extends BaseQuickAdapter<CoinListBean.DataBean.TransactionsBean.ListBean, BaseViewHolder> {


    public CoinIncomeAdapter(int layoutIds, List<CoinListBean.DataBean.TransactionsBean.ListBean> countryDatas) {
        super(layoutIds, countryDatas);
    }

    @Override
    protected void convert(BaseViewHolder helper, CoinListBean.DataBean.TransactionsBean.ListBean item) {
        helper.setText(R.id.tv_cost, String.format(mContext.getResources().getString(R.string.star_coin),item.getFigure()));
        if(item.getFigure().contains("\\-")){
            helper.setTextColor(R.id.tv_cost, Color.parseColor("#21C089"));
        }else{
            helper.setTextColor(R.id.tv_cost, Color.parseColor("#FF7C3B"));
        }
        helper.setText(R.id.tv_time, item.getDate() );
        ImageView view = (ImageView) helper.getView(R.id.iv_head);

        Glide.with(mContext).load(item.getInfo().getAvatarUrl())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()).placeholder(view.getDrawable()))
                .into(view);

        helper.setText(R.id.tv_name, item.getInfo().getName());
        helper.setText(R.id.tv_cate, item.getCategoryTrans());

    }
}
