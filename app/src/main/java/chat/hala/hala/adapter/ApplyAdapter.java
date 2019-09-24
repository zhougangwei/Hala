package chat.hala.hala.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chat.hala.hala.R;
import chat.hala.hala.base.VideoCallManager;
import chat.hala.hala.bean.ApplyListBean;
import chat.hala.hala.bean.ApplyListBean.DataBean;

/**
 * Created by kiddo on 2018/1/9.
 */

public class ApplyAdapter extends BaseQuickAdapter<ApplyListBean.DataBean.ListBean, BaseViewHolder> {

    public ApplyAdapter(int layoutIds, List<ApplyListBean.DataBean.ListBean> countryDatas) {
        super(layoutIds, countryDatas);
    }

    @Override
    protected void convert(BaseViewHolder helper, ApplyListBean.DataBean.ListBean item) {
        /*helper.setText(R.id.text, item.getTitle());*/
        /*helper.setImageResource(R.id.icon, item.getImageResource());*/
        // 加载网络图片
        helper.setText(R.id.tv_name, item.getMember().getNickname() + ":");
        helper.setText(R.id.tv_content, item.getMember().getAutograph());

        String category = item.getLootChat().getCategory();
        if (VideoCallManager.VIDEO_CALL.equals(category)) {
            helper.setText(R.id.tv_cate, "视频/" + item.getLootChat().getCoin() + "Pa币");
        } else {
            helper.setText(R.id.tv_cate, "语音/" + item.getLootChat().getCoin() + "Pa币");
        }

        if (DataBean.ListBean.State.apply_failed.equals(item.getState())) {
            helper.setText(R.id.tv_state, "已被抢聊");
            helper.setBackgroundRes(R.id.tv_state, R.drawable.bg_apply_chat_failed);
        } else if (DataBean.ListBean.State.apply_wating.equals(item.getState())) {
            helper.setText(R.id.tv_state, "抢聊中…");
            helper.setBackgroundRes(R.id.tv_state, R.drawable.bg_apply_chat_waiting);
        } else if (DataBean.ListBean.State.apply_waited.equals(item.getState())) {
            helper.setText(R.id.tv_state, "立即抢聊");
            helper.setBackgroundRes(R.id.tv_state, R.drawable.bg_apply_chat_waited);
        } else if (DataBean.ListBean.State.apply_successed.equals(item.getState())) {
            helper.setText(R.id.tv_state, "抢聊成功");
            helper.setBackgroundRes(R.id.tv_state, R.drawable.bg_apply_chat_failed);
        }
        helper.addOnClickListener(R.id.tv_state);
        Glide.with(mContext).load(item.getMember().getAlbum().get(0).getMediaUrl()).into((ImageView) helper.getView(R.id.iv_head));

    }
}
