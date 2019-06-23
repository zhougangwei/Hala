package com.hala.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hala.R;
import com.hala.bean.AnchorInfoBean;
import com.hala.bean.RuleBean;

import java.util.List;

/**
 * Created by kiddo on 2018/1/9.
 */

public class ChargeAdapter extends BaseQuickAdapter<RuleBean.DataBean.RechargeSettingBean, BaseViewHolder> {


    public ChargeAdapter(int layoutIds, List<RuleBean.DataBean.RechargeSettingBean> countryDatas) {
        super(layoutIds, countryDatas);
    }

    @Override
    protected void convert(BaseViewHolder helper, RuleBean.DataBean.RechargeSettingBean item) {
        helper.setText(R.id.tv_coin_num, item.getCoin() + "");
        helper.setText(R.id.tv_money, item.getAmount() + "");
    }
}
