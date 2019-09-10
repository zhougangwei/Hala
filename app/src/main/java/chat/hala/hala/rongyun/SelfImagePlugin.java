package chat.hala.hala.rongyun;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhihu.matisse.Matisse;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import chat.hala.hala.base.Contact;
import chat.hala.hala.manager.ChoosePicManager;
import io.rong.imkit.R;
import io.rong.imkit.RongExtension;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.manager.SendImageManager;
import io.rong.imkit.manager.SendMediaManager;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.plugin.ImagePlugin;
import io.rong.imkit.plugin.image.PictureSelectorActivity;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.ImageMessage;
import io.rong.message.SightMessage;

public class SelfImagePlugin implements IPluginModule {

  private static final String TAG = "VideoCallPlugin";
  private Fragment fragment;




  @Override
  public Drawable obtainDrawable(Context context) {
    return ContextCompat.getDrawable(context, R.drawable.ic_video_call);
  }

  @Override
  public String obtainTitle(Context context) {
    return "发送图片";
  }

  @Override
  public void onClick(Fragment currentFragment, RongExtension extension) {
    if (currentFragment!=null&&fragment==null) {
      fragment=currentFragment;
    }
    Intent intent = new Intent(currentFragment.getActivity(), PictureSelectorActivity.class);
    extension.startActivityForPluginResult(intent, 23, this);
  }


  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == -1) {
      if (requestCode == 23) {
        String mediaList = data.getStringExtra("android.intent.extra.RETURN_RESULT");
        Gson gson = new Gson();
        Type entityType = (new TypeToken<LinkedHashMap<String, Integer>>() {
        }).getType();
        LinkedHashMap<String, Integer> selectedMedias = (LinkedHashMap)gson.fromJson(mediaList, entityType);
        Iterator var3 = selectedMedias.entrySet().iterator();
        while(var3.hasNext()) {
          Map.Entry<String, Integer> media = (Map.Entry)var3.next();
          int mediaType = (Integer)media.getValue();
          String mediaUri = (String)media.getKey();
          switch(mediaType) {
            case 1:
              ImageMessage obtain = ImageMessage.obtain(Uri.parse(mediaUri), Uri.parse(mediaUri));
              obtain.setDestruct(true);
              obtain.setDestructTime(5);
              Message myMessage = Message.obtain(((ConversationFragment)fragment).getTargetId(), Conversation.ConversationType.PRIVATE, obtain);
              myMessage.setReadTime(3000);
              RongIM.getInstance().sendImageMessage(myMessage,"", "", new RongIMClient.SendImageMessageCallback()
              {
                @Override
                public void onAttached(Message message) {
                  Log.e(TAG, "onAttached: " );
                }
                @Override
                public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                  Log.e(TAG, "onError: " );
                }
                @Override
                public void onSuccess(Message message) {
                  Log.e(TAG, "onSuccess: " );
                }
                @Override
                public void onProgress(Message message, int i) {
                  Log.e(TAG, "onProgress: " );
                }
              });
              break;
          }
        }
      }
    }
  }
}