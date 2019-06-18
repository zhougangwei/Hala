package com.hala.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hala.R;
import com.hala.adapter.HotCallAdapter;
import com.hala.base.BaseFragment;
import com.hala.base.Contact;
import com.hala.bean.OneToOneListBean;
import com.hala.http.BaseCosumer;
import com.hala.http.RetrofitFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HotFragment extends BaseFragment {


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
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragmeng_hot;
    }

    @Override
    protected void initData() {
        RetrofitFactory.getInstance().getHotOneToOneList(0, 20).subscribeOn(Schedulers.io())
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
