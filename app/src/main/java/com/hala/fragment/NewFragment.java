package com.hala.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hala.R;
import com.hala.activity.AnchorsActivity;
import com.hala.activity.OneToOneActivity;
import com.hala.adapter.HotCallAdapter;
import com.hala.base.BaseFragment;
import com.hala.base.Contact;
import com.hala.bean.OneToOneListBean;
import com.hala.http.BaseCosumer;
import com.hala.http.RetrofitFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewFragment extends BaseFragment {


    @BindView(R.id.rv)
    RecyclerView rv;

    List<OneToOneListBean.DataBean.ContentBean> mHotOnetoOneList=new ArrayList<>();

    @Override
    protected void initView() {
        HotCallAdapter hotCallAdapter = new HotCallAdapter(R.layout.item_hot_list,mHotOnetoOneList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(hotCallAdapter);
        hotCallAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AnchorsActivity.startAnchorAc(getActivity(),mHotOnetoOneList.get(position).getId());
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragmeng_hot;
    }

    @Override
    protected void initData() {
        RetrofitFactory.getInstance().getNewOneToOneList(0, 20).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<OneToOneListBean>() {
                    @Override
                    public void onNext(OneToOneListBean oneToOneListBean) {
                        if (Contact.REPONSE_CODE_SUCCESS != oneToOneListBean.getCode()) {
                            return;
                        }
                        mHotOnetoOneList.clear();
                        List<OneToOneListBean.DataBean.ContentBean> content = oneToOneListBean.getData().getContent();
                        if (content!=null&&content.size()>0) {
                            mHotOnetoOneList.addAll(content);
                        }
                    }
                });
    }
}
