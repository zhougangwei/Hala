package chat.hala.hala.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import chat.hala.hala.R;
import chat.hala.hala.activity.WebviewActivity2;
import chat.hala.hala.bean.AdBean;
import chat.hala.hala.bean.OneToOneListBean;
import chat.hala.hala.wight.GlideImageLoader;
import chat.hala.hala.wight.banner.Banner;
import chat.hala.hala.wight.banner.BannerConfig;
import chat.hala.hala.wight.banner.listener.OnBannerListener;

/**
 * Created by kiddo on 2018/1/9.
 */

public class HotCallAdapter extends BaseMultiItemQuickAdapter<OneToOneListBean.DataBean.ListBean, BaseViewHolder> {


    private List<AdBean.DataBean> imagesList;
    private Banner banner;
    private Context context;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *  @param data       A new list is created out of this one to avoid mutable list
     * @param imagesList
     */
    public HotCallAdapter(List<OneToOneListBean.DataBean.ListBean> data, List<AdBean.DataBean> imagesList, Context context) {
        super(data);
        addItemType(OneToOneListBean.DataBean.ListBean.BANNER, R.layout.item_hotfragment_header);
        addItemType(OneToOneListBean.DataBean.ListBean.NORMAL, R.layout.item_hot_list);
        this.imagesList = imagesList;
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, OneToOneListBean.DataBean.ListBean item) {
        switch (helper.getItemViewType()) {
            case OneToOneListBean.DataBean.ListBean.BANNER:
                if(banner==null){
                    banner = helper.getConvertView().findViewById(R.id.banner);
                }
                if (imagesList != null) {
                    banner();
                }
                break;
            case OneToOneListBean.DataBean.ListBean.NORMAL:
                helper.setText(R.id.tv_online_state, item.isOnline() ? R.string.online : R.string.offlIine);


                if(item.isOnline()){
                    TextView tvState = helper.getView(R.id.tv_online_state);
                    Drawable drawable = context.getResources().getDrawable(R.drawable.ic_onlinee);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
                    tvState.setCompoundDrawables(drawable, null, null, null);
                }else {
                    TextView tvState = helper.getView(R.id.tv_online_state);
                    Drawable drawable = context.getResources().getDrawable(R.drawable.ic_off_linee);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
                    tvState.setCompoundDrawables(drawable, null, null, null);
                }

                if(!item.isAvailable()){
                    helper.setText(R.id.tv_online_state,"通话");
                    TextView tvState = helper.getView(R.id.tv_online_state);
                    Drawable drawable = context.getResources().getDrawable(R.drawable.ic_in_call);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
                    tvState.setCompoundDrawables(drawable, null, null, null);
                }


                helper.setText(R.id.tv_name, item.getNickname());
                helper.setText(R.id.tv_content, item.getIntroduction());
                Typeface typeFace = Typeface.createFromAsset(context.getAssets(), "fonts/dinbold.ttf");
                TextView tvCost = helper.getView(R.id.tv_cost);
                tvCost.setTypeface(typeFace);
                helper.setText(R.id.tv_cost, item.getSetting().getVideoCpm() + "");

                ImageView imageView = (ImageView) helper.getView(R.id.iv_bg);
                Glide.with(mContext).load(item.getAlbum().get(0).getMediaUrl())
                        .apply(RequestOptions.placeholderOf(imageView.getDrawable()))
                        .into(imageView);
                break;
        }
    }
        private void banner () {
            List<String> uriList = new ArrayList<>();
            for (int i = 0; i < imagesList.size(); i++) {
                String mediaUrl = imagesList.get(i).getMediaUrl();
                uriList.add(mediaUrl);
            }
            banner.setIndicatorGravity(BannerConfig.RIGHT)
                    .setImages(uriList)
                    .setImageLoader(new GlideImageLoader())
                    .setOnBannerListener(new OnBannerListener() {
                        @Override
                        public void OnBannerClick(int position) {
                            AdBean.DataBean dataBean = imagesList.get(position);
                            String pointTo = dataBean.getPointTo();
                            WebviewActivity2.startActivity(mContext,pointTo,dataBean.getLocate());
                        }
                    })
                    .start();

        }
    }
