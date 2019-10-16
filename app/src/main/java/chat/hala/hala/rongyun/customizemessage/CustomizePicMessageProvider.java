package chat.hala.hala.rongyun.customizemessage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import chat.hala.hala.R;
import chat.hala.hala.activity.ConversationActivity;
import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.dialog.CommonDialog;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.manager.MoneyHelper;
import dalvik.system.BaseDexClassLoader;
import io.rong.imageloader.core.ImageLoader;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.AsyncImageView;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;
import io.rong.message.ImageMessage;
import io.rong.message.utils.BitmapUtil;

@ProviderTag(
        messageContent = ImageMessage.class,
        showProgress = false,
        showReadState = true
)
public class CustomizePicMessageProvider extends IContainerItemProvider.MessageProvider<ImageMessage> {


    private static final String TAG = "CustomizePicMessageProvider";
    private Context context;

    public CustomizePicMessageProvider() {
    }

    public View newView(Context context, ViewGroup group) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.rc_item_image_message, (ViewGroup) null);
        CustomizePicMessageProvider.ViewHolder holder = new CustomizePicMessageProvider.ViewHolder();
        holder.message = (TextView) view.findViewById(R.id.rc_msg);
        holder.img = (AsyncImageView) view.findViewById(R.id.rc_img);
        view.setTag(holder);
        return view;
    }

    public void onItemClick(final View view, int position, final ImageMessage content, final UIMessage message) {
      /*  new AlertDialog.Builder(context).
                setView().
                create().show();*/
      if(!AvchatInfo.isAnchor()&&message.getMessageDirection() == Message.MessageDirection.RECEIVE){
          new CommonDialog(context)
                  .setMsg("查看此私密照片将向对方支付200金豆")
                  .setListener(new CommonDialog.OnClickListener() {
                      @Override
                      public void onClickConfirm() {
                          payMoney(view,content,message);
                      }
                      @Override
                      public void onClickCancel() {
                      }
                  })
                  .show();
      }else{
          Intent intent = new Intent("io.rong.imkit.intent.action.picturepagerview");
          intent.setPackage(view.getContext().getPackageName());
          intent.putExtra("message", message.getMessage());
          view.getContext().startActivity(intent);
      }
    }

    private void payMoney(final View view, final ImageMessage content, final UIMessage message) {
        MoneyHelper.costPicMoney(((ConversationActivity)context).getAnchorId()+"", new MoneyHelper.PayBack() {
            @Override
            public void success() {
                if (content != null) {
                    Intent intent = new Intent("io.rong.imkit.intent.action.picturepagerview");
                    intent.setPackage(view.getContext().getPackageName());
                    intent.putExtra("message", message.getMessage());
                    view.getContext().startActivity(intent);
                }
            }
            @Override
            public void fail() {

            }
        });
    }

    public void bindView(View v, int position, ImageMessage content, UIMessage message) {
        CustomizePicMessageProvider.ViewHolder holder = (CustomizePicMessageProvider.ViewHolder) v.getTag();
        if (message.getMessageDirection() == Message.MessageDirection.SEND) {
            v.setBackgroundResource(R.drawable.rc_ic_bubble_no_right);
        } else {
            v.setBackgroundResource(R.drawable.rc_ic_bubble_no_left);
        }
        if (content.isDestruct()) {
            Bitmap bitmap = ImageLoader.getInstance().loadImageSync(content.getThumUri().toString());
            if (bitmap != null) {
                Bitmap blurryBitmap = BitmapUtil.getBlurryBitmap(v.getContext(), bitmap, 5.0F, 0.25F);
                holder.img.setBitmap(blurryBitmap);
            }

        } else {
         /*   Glide.with(v).load(content.getThumUri())
                    .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 8)))
                    .into(holder.img);*/
            holder.img.setResource(content.getThumUri());
            /*Bitmap bitmap = ImageLoader.getInstance().loadImageSync(content.getThumUri().toString());
            if (bitmap != null) {
                Bitmap blurryBitmap = BitmapUtil.getBlurryBitmap(v.getContext(), bitmap, 5.0F, 0.25F);
                holder.img.setBitmap(blurryBitmap);
            }*/
        }
        int progress = message.getProgress();
        Message.SentStatus status = message.getSentStatus();
        if (status.equals(Message.SentStatus.SENDING) && progress < 100) {
            holder.message.setText(progress + "%");
            holder.message.setVisibility(View.VISIBLE);
        } else {
            holder.message.setVisibility(View.GONE);
        }

    }

    public Spannable getContentSummary(ImageMessage data) {
        return null;
    }

    public Spannable getContentSummary(Context context, ImageMessage data) {
        return new SpannableString(context.getString(R.string.rc_message_content_image));
    }

    private static class ViewHolder {
        AsyncImageView img;
        TextView message;

        private ViewHolder() {
        }
    }


}