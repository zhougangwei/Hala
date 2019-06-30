package chat.hala.hala.adapter;

import android.widget.ImageView;

import com.blankj.utilcode.utils.TimeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import chat.hala.hala.R;

import chat.hala.hala.bean.CoinListBean;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class CoinListAdapter extends BaseQuickAdapter<CoinListBean.DataBean.TransactionsBean.ListBean, BaseViewHolder> {


    public static final String RECHARGE = "recharge";
    public static final String CALLING = "calling";
    public static final String RESERVATION = "reservation";
    public static final String RESERVE_RETURN = "reserve_return";


    public CoinListAdapter(int layoutIds, List<CoinListBean.DataBean.TransactionsBean.ListBean> countryDatas) {
        super(layoutIds, countryDatas);
    }

    @Override
    protected void convert(BaseViewHolder helper, CoinListBean.DataBean.TransactionsBean.ListBean item) {
        helper.setText(R.id.tv_name, item.getInfo().getName());
        String content = "";
        String startedAt = item.getDatetime();

        helper.setText(R.id.tv_hour, TimeUtils.date2String(TimeUtils.string2Date(startedAt,new SimpleDateFormat("yyyy-MM-dd HH:mm")), new SimpleDateFormat("HH:mm")));
        helper.setText(R.id.tv_cost,(item.getDigit()>0?"+":"")+item.getDigit()+"");
        switch (item.getCategory()) {
            case RECHARGE:
                content="RECHARGE";
                helper.setTextColor(R.id.tv_cost,mContext.getResources().getColor(R.color.coin_gain));
                break;
            case CALLING:
                content="Calling Fee";
                helper.setTextColor(R.id.tv_cost,mContext.getResources().getColor(R.color.coin_cost));
                break;
            case RESERVATION:
                content="Reservation Charge";
                helper.setTextColor(R.id.tv_cost,mContext.getResources().getColor(R.color.coin_cost));
                break;
            case RESERVE_RETURN:
                content="Reservation Return";
                helper.setTextColor(R.id.tv_cost,mContext.getResources().getColor(R.color.coin_gain));
                break;
        }
        helper.setText(R.id.tv_content, content);
        ImageView imageView = (ImageView) helper.getView(R.id.iv_head);
        Glide.with(mContext).load(item.getInfo().getAvatarUrl())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()).placeholder(imageView.getDrawable()).placeholder(imageView.getDrawable()))
                .into(imageView);


    }
}
