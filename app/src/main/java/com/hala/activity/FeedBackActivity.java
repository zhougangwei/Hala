package com.hala.activity;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.hala.R;
import com.hala.adapter.EditHeadAdapter;
import com.hala.base.BaseActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class FeedBackActivity extends BaseActivity {

    EditHeadAdapter mAdapter;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private List<EditHeadAdapter.UserHead> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ButterKnife.bind(this);

    }

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
        mList.add(new EditHeadAdapter.UserHead(false, "", true));
        mAdapter = new EditHeadAdapter( mList);
        mAdapter.setEnableLoadMore(false);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

      //  recyclerView.addOnItemTouchListener(touchListener);

    }
    /*private SimpleClickListener<EditHeadAdapter> touchListener = new SimpleClickListener<EditHeadAdapter>() {
        @Override
        public void onItemClick(EditHeadAdapter adapter, View view, final int position) {
            //最后一张图片进行添加
            if (mList.get(position).isAdd()) {
                addPic();
            } else {

                IosBottomDialog.Builder builderHead = new IosBottomDialog.Builder(AnchorsUserActivity.this);
                //无标题，只有操作
                builderHead.setTitle(getString(R.string.title_replace_or_del), Color.parseColor("#aaaaaa"));
                builderHead.addOption(AnchorsUserActivity.this.getString(R.string.delete_has_blank),
                        Color.parseColor("#ff0000"), new IosBottomDialog.OnOptionClickListener() {
                            @Override
                            public void onOptionClick() {
                                if (mList.size() <= 2) {
                                    ToastUtils.showToast(AnchorsUserActivity.this, getString(R.string.keep_one));
                                    return;
                                }
                                mAdapter.remove(position);
                            }
                        }).addOption(AnchorsUserActivity.this.getString(R.string.photo),
                        Color.parseColor("#0077ff"), new IosBottomDialog.OnOptionClickListener() {
                            @Override
                            public void onOptionClick() {
                                RxPermissions rxPermissions = new RxPermissions(AnchorsUserActivity.this);
                                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                                        .subscribe(new Observer<Boolean>() {
                                            @Override
                                            public void onSubscribe(Disposable d) {

                                            }

                                            @Override
                                            public void onNext(Boolean aBoolean) {
                                                if (aBoolean) {
                                                    replacePosition = position;
                                                    Matisse.from(AnchorsUserActivity.this)
                                                            .choose(MimeType.of(MimeType.PNG, MimeType.JPEG))
                                                            .capture(true)
                                                            .captureStrategy(new CaptureStrategy(true, getPackageName()+".fileprovider"))
                                                            .showSingleMediaType(true)
                                                            .countable(true)
                                                            .maxSelectable(1)
                                                            .imageEngine(new GlideEngine())
                                                            .forResult(REQUEST_LOCAL_IMG);
                                                } else {
                                                    ToastUtils.showToast(AnchorsUserActivity.this,
                                                            getString(R.string.modify_user_no_add_permission));
                                                }
                                            }

                                            @Override
                                            public void onError(Throwable e) {

                                            }

                                            @Override
                                            public void onComplete() {

                                            }
                                        });
                            }
                        })
                        .create().show();
            }
        }


        @Override
        public void onItemLongClick(EditHeadAdapter adapter, View view, int position) {

        }

        @Override
        public void onItemChildClick(EditHeadAdapter adapter, View view, int position) {

        }

        @Override
        public void onItemChildLongClick(EditHeadAdapter adapter, View view, int position) {

        }
    };*/
}
