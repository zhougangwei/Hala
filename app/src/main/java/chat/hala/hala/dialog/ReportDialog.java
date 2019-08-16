package chat.hala.hala.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.blankj.utilcode.utils.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.adapter.RandomAnchorAdapter;
import chat.hala.hala.adapter.ReportAdapter;
import chat.hala.hala.base.Contact;
import chat.hala.hala.base.VideoCallManager;
import chat.hala.hala.bean.OneToOneListBean;
import chat.hala.hala.bean.ReportBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ReportDialog extends Dialog {



    @BindView(R.id.rv)
    RecyclerView rv;

    @BindView(R.id.tv_message)
    TextView tv_message;


    Context mContext;
    private ReportAdapter randomAnchorAdapter;
    private List<ReportBean.DataBean> mRanodmList = new ArrayList<>();


    public ReportDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_report);
        ButterKnife.bind(this);
        randomAnchorAdapter = new ReportAdapter(R.layout.item_random_anchor, mRanodmList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(randomAnchorAdapter);
        randomAnchorAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        initData();
    }

    private void initData() {
        RetrofitFactory.getInstance().getReportList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<ReportBean>() {
                    @Override
                    public void onGetData(ReportBean oneToOneListBean) {
                        if (Contact.REPONSE_CODE_SUCCESS != oneToOneListBean.getCode()) {
                            return;
                        }
                        mRanodmList.clear();
                        List<ReportBean.DataBean> data = oneToOneListBean.getData();
                        if (data != null && data.size() > 0) {
                            mRanodmList.addAll(data);
                        }
                        randomAnchorAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = 4 * ScreenUtils.getScreenWidth(mContext) / 5;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(lp);
    }
    @OnClick(R.id.iv_close)
    public void onClick() {
        dismiss();
    }


}
