package chat.hala.hala.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import chat.hala.hala.bean.CountryBean;

import java.util.List;

/**
 * Created by kiddo on 2018/1/9.
 */

public class CountryAdapter extends BaseQuickAdapter<CountryBean, BaseViewHolder> {




    public CountryAdapter(int layoutIds, List<CountryBean> countryDatas) {
        super(layoutIds, countryDatas);
    }

    @Override
    protected void convert(BaseViewHolder helper, CountryBean item) {
        /*helper.setText(R.id.text, item.getTitle());*/
        /*helper.setImageResource(R.id.icon, item.getImageResource());*/
        // 加载网络图片

    }
}
