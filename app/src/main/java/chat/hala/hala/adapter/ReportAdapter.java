package chat.hala.hala.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chat.hala.hala.R;
import chat.hala.hala.bean.OneToOneListBean;
import chat.hala.hala.bean.ReportBean;

/**
 * Created by kiddo on 2018/1/9.
 */

public class ReportAdapter extends BaseQuickAdapter<ReportBean.DataBean, BaseViewHolder> {




    public ReportAdapter(int layoutIds, List<ReportBean.DataBean> countryDatas) {
        super(layoutIds, countryDatas);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ReportBean.DataBean item) {
        helper.setText(R.id.tv_item,item.getTitle());
    }
}
