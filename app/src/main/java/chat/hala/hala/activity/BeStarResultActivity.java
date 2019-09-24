package chat.hala.hala.activity;

import android.content.Intent;
import android.support.constraint.Group;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.bean.BeStarResultBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.ResultUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BeStarResultActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_examine_state)
    ImageView ivExamineState;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_examine_again)
    TextView tvExamineAgain;

    @BindView(R.id.group_prepare)
    Group groupPrepare;
    @BindView(R.id.group_result)
    Group groupResult;




    @Override
    protected int getContentViewId() {
        return R.layout.activity_be_star_result;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        tvTitle.setText(R.string.to_be_star);
        getBestarState();

    }

    private void getBestarState() {
        RetrofitFactory.getInstance().
                getBeStarState()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<BeStarResultBean>() {
                    @Override
                    public void onGetData(BeStarResultBean beStarResultBean) {
                        if (ResultUtils.cheekSuccess(beStarResultBean)) {
                            switch (beStarResultBean.getData().getState()) {
                                case BeStarResultBean.BESTAR_OPEN:
                                    showExaminePrepare();
                                    break;
                                case BeStarResultBean.BESTAR_WAITING:
                                    showExamineWait();
                                    break;
                                case BeStarResultBean.BESTAR_PASS:
                                    showExamineSuccess();
                                    break;
                                case BeStarResultBean.BESTAR_REJECTED:
                                    showExamineFail();
                                    break;
                            }
                        }
                    }
                });
    }

    private void showExaminePrepare() {
        groupResult.setVisibility(View.GONE);
        groupPrepare.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.iv_back, R.id.tv_examine_again,R.id.tv_start_examine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_examine_again:
                startActivity(new Intent(this, ApplyAnchorActivity.class));
                break;
            case R.id.tv_start_examine:
                startActivity(new Intent(this, ApplyAnchorActivity.class));
                break;

        }
    }


    public void showExamineSuccess(){
        groupResult.setVisibility(View.VISIBLE);
        groupPrepare.setVisibility(View.GONE);
        tvState.setText("恭喜您，认证通过！");
        tvContent.setText("开始为你的时间创造价值吧！ 请联系官方微信wx:12345");
        ivExamineState.setImageDrawable(getResources().getDrawable(R.drawable.ic_examine_success));
        tvExamineAgain.setVisibility(View.GONE);
    }

    public void showExamineFail(){
        groupResult.setVisibility(View.VISIBLE);
        groupPrepare.setVisibility(View.GONE);
        tvState.setText("很抱歉，审核失败");
        tvContent.setText("所填信息出现问题，请重新提交申请 如有问题，请联系在线客服！");
        ivExamineState.setImageDrawable(getResources().getDrawable(R.drawable.ic_examine_fail));
        tvExamineAgain.setVisibility(View.VISIBLE);
    }
    public void showExamineWait(){
        groupResult.setVisibility(View.VISIBLE);
        groupPrepare.setVisibility(View.GONE);
        tvState.setText("审核中");
        tvContent.setText("请耐心等待，主播申请资料正在审核中");
        ivExamineState.setImageDrawable(getResources().getDrawable(R.drawable.ic_examine_wait));
        tvExamineAgain.setVisibility(View.GONE);
    }

}
