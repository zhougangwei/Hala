package chat.hala.hala.wight;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import chat.hala.hala.R;
import chat.hala.hala.wight.banner.loader.ImageLoader;


public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path)
                .apply(RequestOptions.placeholderOf(R.drawable.bg_rec_place))
                .into(imageView);
    }
}
