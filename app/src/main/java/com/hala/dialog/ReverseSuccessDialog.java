package com.hala.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hala.R;
import com.hala.activity.AnchorsActivity;
import com.hala.activity.OneToOneActivity;
import com.hala.adapter.HotCallAdapter;
import com.hala.adapter.RandomAnchorAdapter;
import com.hala.base.Contact;
import com.hala.bean.OneToOneListBean;
import com.hala.http.BaseCosumer;
import com.hala.http.RetrofitFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ReverseSuccessDialog extends Dialog {


    @BindView(R.id.rv)
    RecyclerView rv;


    Context mContext;
    private RandomAnchorAdapter randomAnchorAdapter;
    private List<OneToOneListBean.DataBean.ListBean> mRanodmList = new ArrayList<>();


    public ReverseSuccessDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_reserve_success);
        randomAnchorAdapter = new RandomAnchorAdapter(R.layout.item_random_anchor, mRanodmList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(randomAnchorAdapter);

        randomAnchorAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_call:
                        OneToOneActivity.docallOneToOneActivity(mContext, mRanodmList.get(position).getAnchorId(),mRanodmList.get(position).getMemberId());
                        dismiss();
                        break;
                }
            }
        });

        initData();
    }

    private void initData() {
        RetrofitFactory.getInstance().getRandOneToOneList(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<OneToOneListBean>() {
                    @Override
                    public void onNext(OneToOneListBean oneToOneListBean) {
                        if (Contact.REPONSE_CODE_SUCCESS != oneToOneListBean.getCode()) {
                            return;
                        }
                        mRanodmList.clear();
                        List<OneToOneListBean.DataBean.ListBean> content = oneToOneListBean.getData().getList();
                        if (content != null && content.size() > 0) {
                            mRanodmList.addAll(content);
                        }


                        randomAnchorAdapter.notifyDataSetChanged();
                    }
                });
    }


    @OnClick(R.id.iv_close)
    public void onClick() {
        dismiss();
    }
}
