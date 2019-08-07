package chat.hala.hala.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.base.BaseActivity;

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

    @Override
    protected int getContentViewId() {
        return R.layout.activity_be_star_result;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        tvTitle.setText(R.string.to_be_star);
        getBestarState();

    }

    private void getBestarState() {
        /*RetrofitFactory.getInstance().
                getBeStarState(){

        }*/
    }

    @OnClick({R.id.iv_back, R.id.tv_examine_again})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_examine_again:
                startActivity(new Intent(this, PrepareBeAnchorActivity.class));
                break;
        }
    }


    public void showExamineSuccess(){
        ivExamineState.setImageDrawable(getResources().getDrawable(R.drawable.ic_examine_success));
        tvExamineAgain.setVisibility(View.GONE);
    }

    public void showExamineFail(){
        ivExamineState.setImageDrawable(getResources().getDrawable(R.drawable.ic_examine_fail));
        tvExamineAgain.setVisibility(View.VISIBLE);
    }
    public void showExamineWait(){
        ivExamineState.setImageDrawable(getResources().getDrawable(R.drawable.ic_examine_wait));
        tvExamineAgain.setVisibility(View.GONE);
    }

}
