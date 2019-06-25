package com.hala.activity;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hala.R;
import com.hala.adapter.EditHeadAdapter;
import com.hala.base.BaseActivity;
import com.hala.glide.MyGlideEngine;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FeedBackActivity extends BaseActivity {

    EditHeadAdapter mAdapter;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private List<EditHeadAdapter.UserHead> mList;
    private static final int REQUEST_CODE_CHOOSE = 224;
    private List<String> uriList=new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        mList = new ArrayList<>();
        mList.add(new EditHeadAdapter.UserHead("", true));
        mAdapter = new EditHeadAdapter( mList);
        mAdapter.setEnableLoadMore(false);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mList.get(position).isAdd()) {
                    Matisse.from(FeedBackActivity.this)
                            .choose(MimeType.of(MimeType.PNG, MimeType.JPEG))//图片类型
                            .countable(true)//true:选中后显示数字;false:选中后显示对号
                            .maxSelectable(5)//可选的最大数
                            .capture(true)//选择照片时，是否显示拍照
                            .captureStrategy(new CaptureStrategy(true, getPackageName() + ".fileprovider"))//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                            .imageEngine(new MyGlideEngine())//图片加载引擎
                            .forResult(REQUEST_CODE_CHOOSE);//
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode==REQUEST_CODE_CHOOSE){
            List<String> strings = Matisse.obtainPathResult(data);
            if (strings!=null) {
                uriList.clear();
                uriList.addAll(strings) ;
                for (String s : uriList) {
                    mList.add(new EditHeadAdapter.UserHead(s,false));
                }
                mAdapter.notifyDataSetChanged();
            }



        }

    }
}
