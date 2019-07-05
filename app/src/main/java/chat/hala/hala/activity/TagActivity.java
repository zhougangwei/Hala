package chat.hala.hala.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ScreenUtils;
import com.blankj.utilcode.utils.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.adapter.TagsAdapter;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.bean.AnchorTagBean;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.GsonUtil;
import chat.hala.hala.utils.ResultUtils;
import chat.hala.hala.utils.ToastUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TagActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_save)
    TextView tvSave;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_tag;
    }


    @BindView(R.id.rv_choose)
    RecyclerView rvChoose;
    @BindView(R.id.rv_all)
    RecyclerView rvAll;
    private Paint mPaint;
    private TagsAdapter allTagsAdapter;
    private TagsAdapter chooseAdapter;


    List<AnchorTagBean.DataBean> allDatas = new ArrayList<>();
    List<AnchorTagBean.DataBean> chooseDatas = new ArrayList<>();


    @Override
    protected void beforeInitView() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(SizeUtils.sp2px(this, 14));
        mPaint.setStyle(Paint.Style.FILL);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        tvTitle.setText("Select Tags");
        tvSave.setBackground(getResources().getDrawable(R.drawable.bg_rec_15blue));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 100);
        rvAll.setLayoutManager(gridLayoutManager);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 100);
        rvAll.setLayoutManager(gridLayoutManager);
        rvChoose.setLayoutManager(gridLayoutManager2);

        allTagsAdapter = new TagsAdapter(R.layout.item_choose_all_tag, allDatas);
        chooseAdapter = new TagsAdapter(R.layout.item_choose_tag, chooseDatas);
        rvAll.setAdapter(allTagsAdapter);
        rvChoose.setAdapter(chooseAdapter);

        allTagsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AnchorTagBean.DataBean dataBean = allDatas.get(position);
                if (dataBean.isChoose()) {
                    dataBean.setChoose(false);
                    allTagsAdapter.notifyDataSetChanged();
                    chooseDatas.remove(dataBean);
                    chooseAdapter.notifyDataSetChanged();
                    return;
                }
                if (chooseDatas.size() > 5) {
                    ToastUtils.showToast(TagActivity.this, "Choose 5 tags at most!");
                    return;
                }
                dataBean.setChoose(true);
                allTagsAdapter.notifyDataSetChanged();
                chooseDatas.add(dataBean);
                chooseAdapter.notifyDataSetChanged();

            }
        });
        chooseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AnchorTagBean.DataBean dataBean = chooseDatas.get(position);
                dataBean.setChoose(false);
                chooseDatas.remove(position);
                chooseAdapter.notifyDataSetChanged();
                allTagsAdapter.notifyDataSetChanged();
            }
        });


        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int width = ScreenUtils.getScreenWidth(TagActivity.this) - SizeUtils.dp2px(TagActivity.this, 39);
                int itemWidth = getTextWidth(mPaint, allDatas.get(position).getContent()) + SizeUtils.dp2px(TagActivity.this, 30);
                return Math.min(100, itemWidth * 100 / width + 1);
            }
        });
        gridLayoutManager2.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int width = ScreenUtils.getScreenWidth(TagActivity.this) - SizeUtils.dp2px(TagActivity.this, 39);
                int itemWidth = getTextWidth(mPaint, chooseDatas.get(position).getContent()) + SizeUtils.dp2px(TagActivity.this, 30);
                return Math.min(100, itemWidth * 100 / width + 1);
            }
        });


        RetrofitFactory.getInstance()
                .getAnchorTag()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AnchorTagBean>() {
                    @Override
                    public void accept(AnchorTagBean anchorTagBean) throws Exception {
                        if (ResultUtils.cheekSuccess(anchorTagBean)) {
                            allDatas.clear();
                            allDatas.addAll(anchorTagBean.getData());
                            allTagsAdapter.notifyDataSetChanged();
                        }
                    }
                });

    }

    private int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }


    @OnClick({R.id.iv_back, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_save:
                Intent intent = new Intent();
                intent.putExtra("tags", GsonUtil.parseListToJson(chooseDatas));
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
