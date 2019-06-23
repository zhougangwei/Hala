package com.hala.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hala.R;


import java.util.Collections;
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
        private boolean isNetImg;
        private String picPath;
        private boolean isAdd;

        public UserHead(boolean isNetImg, String picPath,boolean isAdd) {
            this.isNetImg = isNetImg;
            this.picPath = picPath;
            this.isAdd = isAdd;
        }

        public boolean isNetImg() {
            return isNetImg;
        }

        public void setNetImg(boolean netImg) {
            isNetImg = netImg;
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
