package chat.hala.hala.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.adapter.EditHeadAdapter;
import chat.hala.hala.avchat.QiniuInfo;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.bean.FeedBackBean;
import chat.hala.hala.bean.QiNiuToken;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.ProxyPostHttpRequest;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.http.UploadPicManger;
import chat.hala.hala.manager.ChoosePicManager;
import chat.hala.hala.utils.ResultUtils;
import chat.hala.hala.utils.ToastUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FeedBackActivity extends BaseActivity {

    EditHeadAdapter mAdapter;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.iv_back)
    ImageView    ivBack;
    @BindView(R.id.tv_title)
    TextView     tvTitle;
    @BindView(R.id.tv_save)
    TextView     tvSave;
    @BindView(R.id.et_content)
    EditText     etContent;
    @BindView(R.id.iv_screen)
    ImageView    ivScreen;



    private List<EditHeadAdapter.UserHead> mList;

    private              List<String> uriList             = new ArrayList<>();
    private String mediaurl;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        tvSave.setText(R.string.Submit);
        tvTitle.setText(R.string.feedback);
        mList = new ArrayList<>();
        mList.add(new EditHeadAdapter.UserHead("", true));
        mAdapter = new EditHeadAdapter(mList);
        mAdapter.setEnableLoadMore(false);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mList.get(position).isAdd()) {
                    ChoosePicManager.choosePic(FeedBackActivity.this, 1);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ChoosePicManager.REQUEST_CODE_CHOOSE) {
            List<String> strings = Matisse.obtainPathResult(data);
            if (strings != null && strings.size() > 0) {
                uriList.clear();
                uriList.addAll(strings);
                for (String s : uriList) {
                    mList.add(new EditHeadAdapter.UserHead(s, false));
                }
                mediaurl = uriList.get(0);
                Glide.with(this).load(mediaurl).into(ivScreen);
                mAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_save,R.id.iv_screen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_save:
                upQiniu();
                break;
            case R.id.iv_screen:
                ChoosePicManager.choosePic(FeedBackActivity.this, 1);
                break;
        }
    }

    private void gotoSave() {



        String descr = etContent.getText().toString();
        String category = "general";
        RetrofitFactory.getInstance()
                .feedBack(ProxyPostHttpRequest.getInstance().feedBack(descr, mediaurl, category))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<FeedBackBean>() {
                    @Override
                    public void onGetData(FeedBackBean feedBackBean) {
                        if (ResultUtils.cheekSuccess(feedBackBean)) {
                            ToastUtils.showToast(FeedBackActivity.this,"FeedBack Success!!");
                            finish();
                        }
                    }
                });

    }

    private boolean upQiniu() {
        QiNiuToken.DataBean.StarchatfeedbackBean starchatfeedbackBean = QiniuInfo.getmStarchatfeedbackBean();
        if (starchatfeedbackBean == null) {
            return true;
        }
        new UploadPicManger().uploadImageArray(uriList, 0, starchatfeedbackBean.getToken(), starchatfeedbackBean.getUrl(), new UploadPicManger.QiNiuUploadCompletionHandler() {
            @Override
            public void uploadSuccess(String path, List<String> paths) {
                gotoSave();
            }
            @Override
            public void uploadFailure() {
                gotoSave();
            }
        });
        return false;
    }
}
