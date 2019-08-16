package chat.hala.hala.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.renderscript.ScriptC;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.blankj.utilcode.utils.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.activity.FeedBackActivity;
import chat.hala.hala.adapter.ReportAdapter;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.BaseBean;
import chat.hala.hala.bean.ReportBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.ProxyPostHttpRequest;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.ResultUtils;
import chat.hala.hala.utils.ToastUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ReportDialog extends Dialog {



    @BindView(R.id.rv)
    RecyclerView rv;



    Context mContext;
    private ReportAdapter randomAnchorAdapter;
    private List<ReportBean.DataBean> mRanodmList = new ArrayList<>();
    private final int memberId;

    public ReportDialog(@NonNull Context context,  int memberId) {
        super(context);
        this.mContext = context;
        this.memberId = memberId;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_report);
        ButterKnife.bind(this);
        randomAnchorAdapter = new ReportAdapter(R.layout.item_report, mRanodmList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(randomAnchorAdapter);
        randomAnchorAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ReportBean.DataBean dataBean = mRanodmList.get(position);
                gotoReport(dataBean.getId());
            }
        });
        initData();
    }

    private void gotoReport(int id) {
        RetrofitFactory.getInstance().feedBack(ProxyPostHttpRequest.getInstance().report("report",id,memberId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<BaseBean>() {
                    @Override
                    public void onGetData(BaseBean baseBean) {
                        if (ResultUtils.cheekSuccess(baseBean)) {
                            ToastUtils.showToast(mContext,"举报成功!");
                            dismiss();
                        }
                    }
                });
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
                    }//
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setGravity(Gravity.BOTTOM);

        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width =  ScreenUtils.getScreenWidth(mContext) ;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(lp);
    }
    @OnClick(R.id.tv_cancel)
    public void onClick() {
        dismiss();
    }


}
