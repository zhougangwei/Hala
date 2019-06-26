package chat.hala.hala.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import chat.hala.hala.R;
import chat.hala.hala.bean.AnchorBean;

import java.util.List;

public class SimplePagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<AnchorBean.DataBean.CoversBean> mData;

    public SimplePagerAdapter(Context context , List<AnchorBean.DataBean.CoversBean> list) {
        mContext = context;
        mData = list;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view =  View.inflate(mContext, R.layout.item_base,null);
        ImageView imageview = view.findViewById(R.id.iv);
        Glide.with(mContext).load(mData.get(position).getCoverUrl())
                .into(imageview);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container,position,object); 这一句要删除，否则报错
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
