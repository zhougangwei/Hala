package chat.hala.hala.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import chat.hala.hala.R;
import chat.hala.hala.base.BaseFragment;
import chat.hala.hala.bean.BaseBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.ProxyPostHttpRequest;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.ResultUtils;
import chat.hala.hala.utils.ToastUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wjy on 2019/9/10/010.
 */
public class ApplyChatFragment extends BaseFragment {
    @BindView(R.id.tv_money)
    EditText etMoney ;
    @BindView(R.id.tv_qiuliao)
    TextView tvQiuliao;
    @BindView(R.id.tv_video_qiuliao)
    TextView tvVideoQiuliao;
    @BindView(R.id.tv_audio_qiuliao)
    TextView tvAudioQiuliao;
    Unbinder unbinder;

    public static final String VIDEO="video";
    public static final String AUDIO="audio";
    private String category;
    private String money;


    @Override
    protected void initView() {

    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragmeng_apply_chat;
    }

    @Override
    protected void initData() {

    }



    @OnClick({R.id.tv_qiuliao, R.id.tv_video_qiuliao, R.id.tv_audio_qiuliao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_qiuliao:
                startQiuliao();
                break;
            case R.id.tv_video_qiuliao:
                setQiuliao(VIDEO);
                break;
            case R.id.tv_audio_qiuliao:
                setQiuliao(AUDIO);
                break;
        }
    }

    private void setQiuliao(String type) {
        category =type;
        switch (type) {
            case VIDEO:
                tvVideoQiuliao.setSelected(true);
                tvAudioQiuliao.setSelected(false);
                break;
            case AUDIO:
                tvAudioQiuliao.setSelected(true);
                tvVideoQiuliao.setSelected(false);
                break;
        }
        ToastUtils.showToast(getActivity(),category);
    }

    private void startQiuliao() {
        if(TextUtils.isEmpty(category)){
            return;
        }
        money=etMoney.getText().toString();
        if(TextUtils.isEmpty(money)){
            return;
        }
        RetrofitFactory.getInstance()
                .startQiuliao(ProxyPostHttpRequest.getInstance().startQiuliao(category, Integer.parseInt(money)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<BaseBean>() {
                    @Override
                    public void onGetData(BaseBean baseBean) {
                        if (ResultUtils.cheekSuccess(baseBean)) {
                            ToastUtils.showToast(getActivity(),"开始抢聊！");
                        }
                    }
                });
    }
}
