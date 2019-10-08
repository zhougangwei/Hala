package chat.hala.hala.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chat.hala.hala.R;
import chat.hala.hala.bean.BaseBean;
import chat.hala.hala.bean.OneToOneListBean;

/**
 * Created by kiddo on 2018/1/9.
 */

public class FamilyDayDetailAdapter extends BaseQuickAdapter<BaseBean, BaseViewHolder> {



    public FamilyDayDetailAdapter(int layoutIds, List<BaseBean> countryDatas) {
        super(layoutIds, countryDatas);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseBean item) {

    }
}
