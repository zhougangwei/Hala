package chat.hala.hala.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import chat.hala.hala.R;

import java.util.List;

/**
 * Created by xyy on 2018/10/30.
 */

public  class EditHeadAdapter extends BaseQuickAdapter<EditHeadAdapter.UserHead, BaseViewHolder> {


    public EditHeadAdapter( List<UserHead> data) {
        super( R.layout.item_user_head_layout, data);
    }

    @Override
    public int getItemCount() {
        int count = super.getItemCount();
        if (count > 8)
            count = 8;
        return count;
    }

    @Override
    protected void convert(BaseViewHolder helper, UserHead item) {
        if (item.isAdd) {
            Glide.with(mContext)
                    .load(R.drawable.ic_add_pic)
                    .into((ImageView) helper.getView(R.id.iv_head));
        } else {
            Glide.with(mContext)
                    .load(item.getPicPath())
                    .into((ImageView) helper.getView(R.id.iv_head));
        }
    }





    public static class UserHead {
         String picPath;
        private boolean isAdd;

        public UserHead( String picPath,boolean isAdd) {
            this.picPath = picPath;
            this.isAdd = isAdd;
        }


        public String getPicPath() {
            return picPath;
        }

        public void setPicPath(String picPath) {
            this.picPath = picPath;
        }

        public boolean isAdd() {
            return isAdd;
        }

        public void setAdd(boolean add) {
            isAdd = add;
        }
    }
}
