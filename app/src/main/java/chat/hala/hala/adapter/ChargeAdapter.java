package chat.hala.hala.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import chat.hala.hala.R;

import chat.hala.hala.bean.RuleBean;

import java.util.List;

/**
 * Created by kiddo on 2018/1/9.
 */

public class ChargeAdapter extends BaseQuickAdapter<RuleBean.DataBean.MainlandRechargeSettingBean, BaseViewHolder> {


    public ChargeAdapter(int layoutIds, List<RuleBean.DataBean.MainlandRechargeSettingBean> countryDatas) {
        super(layoutIds, countryDatas);
    }

    @Override
    protected void convert(BaseViewHolder helper, RuleBean.DataBean.MainlandRechargeSettingBean item) {
        helper.setText(R.id.tv_coin_num, item.getCoin() + "币");
        helper.setText(R.id.tv_money_cost, "¥"+item.getAmount() + "");
        if(item.getClicked()){
            helper.setTextColor(R.id.tv_coin_num, Color.parseColor("#FFFF5F5D"));
            helper.setTextColor(R.id.tv_money_cost, Color.parseColor("#FFFF5F5D"));
            helper.setBackgroundRes(R.id.cl_item,R.drawable.bg_item_charge);
        }else{
            helper.setTextColor(R.id.tv_coin_num, Color.parseColor("#FF898989"));
            helper.setTextColor(R.id.tv_money_cost, Color.parseColor("#FF898989"));
            helper.setBackgroundRes(R.id.cl_item,R.drawable.bg_item_charge_unclicked);
        }
    }
}
