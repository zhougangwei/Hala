package chat.hala.hala.adapter;

import android.graphics.Color;

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



            String args[]={"#EFA45C",
            "#FDD857",
           "#F5628C",
           "#4A90E2",
           "#4EE84C",
           "#BC5CEF",
           "#34DA24",
           "#24CDDA",
           "#247ADA",
           "#DA24B4"};

    public AnchorTagsAdapter(int layoutIds, List<AnchorTagBean.DataBean> countryDatas) {
        super(layoutIds, countryDatas);
    }

    @Override
    protected void convert(BaseViewHolder helper, AnchorTagBean.DataBean item) {
        helper.setText(R.id.tv_content, item.getContent());
        helper.setBackgroundColor(R.id.iv, Color.parseColor(args[new Random().nextInt(args.length)]));
    }
}
