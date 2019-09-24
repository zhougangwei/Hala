package chat.hala.hala.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import chat.hala.hala.R;
import chat.hala.hala.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class BioActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title)
    TextView  mTvTitle;
    @BindView(R.id.tv_save)
    TextView  mTvSave;
    @BindView(R.id.et_bio)
    EditText  mEtBio;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_bio;
    }

    @Override
    protected void beforeInitView() {
    }

    @Override
    protected void initView() {
        mTvTitle.setText("个人宣言");
    }


    @OnClick({R.id.iv_back, R.id.tv_save, R.id.et_bio})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_save:
                Intent intent = new Intent();
                intent.putExtra("bio",mEtBio.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.et_bio:
                break;
        }
    }
}
