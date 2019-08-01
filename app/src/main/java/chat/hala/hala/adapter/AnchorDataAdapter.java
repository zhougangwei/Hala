package chat.hala.hala.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import chat.hala.hala.R;
import chat.hala.hala.bean.AnchorInfoBean;

import java.util.List;

/**
 * Created by kiddo on 2018/1/9.
 */

public class AnchorDataAdapter extends BaseQuickAdapter<AnchorInfoBean, BaseViewHolder> {




    public AnchorDataAdapter(int layoutIds, List<AnchorInfoBean> countryDatas) {
        super(layoutIds, countryDatas);
    }

    @Override
    protected void convert(BaseViewHolder helper, AnchorInfoBean item) {
        /*helper.setText(R.id.text, item.getTitle());*/
        /*helper.setImageResource(R.id.icon, item.getImageResource());*/
        // 加载网络图片
        helper.setText(R.id.tv_name,item.getName()+":");
        helper.setText(R.id.tv_content,item.getContent());



    }
}
