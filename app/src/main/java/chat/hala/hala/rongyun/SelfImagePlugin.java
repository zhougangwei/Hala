package chat.hala.hala.rongyun;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.zhihu.matisse.Matisse;

import java.util.List;

import chat.hala.hala.base.Contact;
import chat.hala.hala.manager.ChoosePicManager;
import io.rong.imkit.R;
import io.rong.imkit.RongExtension;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.ImageMessage;

public class SelfImagePlugin implements IPluginModule {

  private static final String TAG = "VideoCallPlugin";
  private Fragment fragment;




  @Override
  public Drawable obtainDrawable(Context context) {
    return ContextCompat.getDrawable(context, R.drawable.ic_video_call);
  }

  @Override
  public String obtainTitle(Context context) {
    return "视频通话";
  }

  @Override
  public void onClick(Fragment currentFragment, RongExtension extension) {
    if (currentFragment!=null&&fragment==null) {
      fragment=currentFragment;
    }
    ChoosePicManager.choosePic(currentFragment.getActivity(),1);
  }


  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == -1) {
      if (requestCode == Contact.REQUEST_BIO) {
        List<Uri> strings = Matisse.obtainResult(data);
        ImageMessage obtain = ImageMessage.obtain(strings.get(0), strings.get(0));
        Message myMessage = Message.obtain(((ConversationFragment)fragment).getTargetId(), Conversation.ConversationType.PRIVATE, obtain);
        RongIM.getInstance().sendImageMessage(myMessage,"", "", new RongIMClient.SendImageMessageCallback()
        {
          @Override
          public void onAttached(Message message) {

          }

          @Override
          public void onError(Message message, RongIMClient.ErrorCode errorCode) {

          }

          @Override
          public void onSuccess(Message message) {

          }

          @Override
          public void onProgress(Message message, int i) {

          }
        });
      }
    }
  }
}