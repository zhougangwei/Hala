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
import chat.hala.hala.bean.ChargeListBean;
import chat.hala.hala.bean.CoinListBean;
import chat.hala.hala.utils.TimeUtil;

/**
 * Created by kiddo on 2018/1/9.
 */

public class ChargeListAdapter extends BaseQuickAdapter<ChargeListBean.DataBean.TransactionsBean.ListBean, BaseViewHolder> {
    public int type;

    public final String unknown = "unknown";
    public final String apple_pay = "apple_pay";
    public final String google_pay = "google_pay";
    public final String credit_card = "credit_card";
    public final String ebay = "ebay";
    public final String alipay = "alipay";
    public final String wechat = "wechat";
    public final String admin = "admin";

    public ChargeListAdapter(int layoutIds, List<ChargeListBean.DataBean.TransactionsBean.ListBean> countryDatas, int type) {
        super(layoutIds, countryDatas);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, ChargeListBean.DataBean.TransactionsBean.ListBean item) {
        helper.setText(R.id.tv_name, "金额:¥" + item.getAmount());
        helper.setText(R.id.tv_cost, "+" + item.getCoin() + "Pa币");
        helper.setText(R.id.tv_time, "充值时间:" + TimeUtil.dealDateFormat4(item.getUpdatedAt()));
        if (alipay.equals(item.getThrough())) {
            helper.setText(R.id.tv_cate, "支付宝充值");
        } else if (admin.equals(item.getThrough())) {
            helper.setText(R.id.tv_cate, "后台充值");
        }
    }
}
