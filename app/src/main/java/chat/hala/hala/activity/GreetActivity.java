package chat.hala.hala.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.utils.ToastUtils;

public class GreetActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.et_bio)
    EditText etBio;
    @BindView(R.id.tv_num)
    TextView tvNum;

    String greet;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_greet;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        etBio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (s.toString().trim().length() > 40) {
                        etBio.setText(s.toString().substring(0, 40));
                        etBio.setSelection(40);
                        tvNum.setText(s.toString().trim().length()+"/"+40);
                        ToastUtils.showToast(GreetActivity.this, "您最多能输入40个字");
                    }
                }catch (Exception e){
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @OnClick({R.id.iv_back, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_save:
                greet = etBio.getText().toString();
                if (greet == null || greet.length() == 0) {
                    ToastUtils.showToast(this, "未填写!");
                    return;
                }
                if (greet.length() > 40) {
                    ToastUtils.showToast(this, "超出字数限制!");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("greet", greet);
                setResult(RESULT_OK, intent);
                break;
        }
    }
}
