package chat.hala.hala.wight;

import com.chad.library.adapter.base.loadmore.LoadMoreView;

import chat.hala.hala.R;

/**
 * @author wjy on 2019/8/14/014.
 */
public class EmptyLoadMoreView extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.empty;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.iv;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.iv;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.iv;
    }
}
