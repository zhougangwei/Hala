package chat.hala.hala.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import butterknife.ButterKnife;
import chat.hala.hala.R;

import chat.hala.hala.adapter.RandomAnchorAdapter;
import chat.hala.hala.base.Contact;
import chat.hala.hala.base.VideoCallManager;
import chat.hala.hala.bean.OneToOneListBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;

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
        ButterKnife.bind(this);
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
                        VideoCallManager.gotoCallOrReverse((Activity) mContext, mRanodmList.get(position).getAnchorId(),mRanodmList.get(position).getMemberId());
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
