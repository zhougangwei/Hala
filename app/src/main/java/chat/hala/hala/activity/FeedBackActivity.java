package chat.hala.hala.activity;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import chat.hala.hala.R;
import chat.hala.hala.adapter.EditHeadAdapter;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.manager.ChoosePicManager;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FeedBackActivity extends BaseActivity {

    EditHeadAdapter mAdapter;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private List<EditHeadAdapter.UserHead> mList;
    private static final int REQUEST_CODE_CHOOSE = 224;
    private List<String> uriList=new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        mList = new ArrayList<>();
        mList.add(new EditHeadAdapter.UserHead("", true));
        mAdapter = new EditHeadAdapter( mList);
        mAdapter.setEnableLoadMore(false);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mList.get(position).isAdd()) {
                    ChoosePicManager.choosePic(FeedBackActivity.this,5);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode==REQUEST_CODE_CHOOSE){
            List<String> strings = Matisse.obtainPathResult(data);
            if (strings!=null) {
                uriList.clear();
                uriList.addAll(strings) ;
                for (String s : uriList) {
                    mList.add(new EditHeadAdapter.UserHead(s,false));
                }
                mAdapter.notifyDataSetChanged();
            }



        }

    }
}
