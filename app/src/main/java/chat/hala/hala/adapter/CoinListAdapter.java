package chat.hala.hala.adapter;

import android.widget.ImageView;

import com.blankj.utilcode.utils.TimeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.SimpleDateFormat;
import java.util.List;

import chat.hala.hala.R;
import chat.hala.hala.bean.CoinListBean;
import chat.hala.hala.utils.TimeUtil;


public class CoinListAdapter extends BaseQuickAdapter<CoinListBean.DataBean.TransactionsBean.ListBean, BaseViewHolder> {


    public static final String RECHARGE = "recharge";
    public static final String CALLING = "call";
    public static final String RESERVATION = "reservation";
    public static final String RESERVE_RETURN = "reservation_return";


    public CoinListAdapter(int layoutIds, List<CoinListBean.DataBean.TransactionsBean.ListBean> countryDatas) {
        super(layoutIds, countryDatas);
    }

    @Override
    protected void convert(BaseViewHolder helper, CoinListBean.DataBean.TransactionsBean.ListBean item) {
        helper.setText(R.id.tv_name, item.getInfo().getName());
        String content = "";
        String startedAt = item.getDatetime();
        helper.setText(R.id.tv_hour, TimeUtils.date2String(TimeUtils.string2Date(startedAt,new SimpleDateFormat("yyyy-MM-dd HH:mm")), new SimpleDateFormat("HH:mm")));
        helper.setText(R.id.tv_week,  TimeUtil.getTextTime(startedAt));
        helper.setText(R.id.tv_cost,(item.getFigure()));
        switch (item.getCategory()) {
            case RECHARGE:
                helper.setTextColor(R.id.tv_cost,mContext.getResources().getColor(R.color.coin_gain));
                break;
            case CALLING:
                helper.setTextColor(R.id.tv_cost,mContext.getResources().getColor(R.color.coin_cost));
                break;
            case RESERVATION:
                helper.setTextColor(R.id.tv_cost,mContext.getResources().getColor(R.color.coin_cost));

                break;
            case RESERVE_RETURN:
                helper.setTextColor(R.id.tv_cost,mContext.getResources().getColor(R.color.coin_gain));
                break;
        }
        helper.setText(R.id.tv_content, item.getCategoryTrans());
        ImageView imageView = (ImageView) helper.getView(R.id.iv_head);
        Glide.with(mContext).load(item.getInfo().getAvatarUrl())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()).placeholder(imageView.getDrawable()))
                .into(imageView);


    }
}
