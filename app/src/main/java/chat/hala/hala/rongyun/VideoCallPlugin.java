package chat.hala.hala.rongyun;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import chat.hala.hala.rongyun.customizemessage.CustomizeVideoMessage;
import io.rong.imkit.R;
import io.rong.imkit.RongExtension;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

public class VideoCallPlugin implements IPluginModule {

  private static final String TAG = "VideoCallPlugin";

  @Override
  public Drawable obtainDrawable(Context context) {
    return ContextCompat.getDrawable(context, R.drawable.rc_ext_plugin_image_selector);
  }

  @Override
  public String obtainTitle(Context context) {
    return "视频通话";
  }

  @Override
  public void onClick(Fragment currentFragment, RongExtension extension) {

   CustomizeVideoMessage myTextMessage = CustomizeVideoMessage.obtain(CustomizeVideoMessage.VIDEO_CALL);
    //TextMessage myTextMessage = TextMessage.obtain("我是消息内容");

    Message myMessage = Message.obtain(((ConversationFragment)currentFragment).getTargetId(), Conversation.ConversationType.PRIVATE, myTextMessage);
    RongIM.getInstance().sendMessage(myMessage, null, null, new IRongCallback.ISendMessageCallback() {
      @Override
      public void onAttached(Message message) {
        Log.e(TAG, "onAttached: "+message.getTargetId());
      }
      @Override
      public void onSuccess(Message message) {
        Log.e(TAG, "onSuccess: "+message.getTargetId());
      }
      @Override
      public void onError(Message message, RongIMClient.ErrorCode errorCode) {
        Log.e(TAG, "onError: "+errorCode);
      }
    });
    // TODO: 2019/8/8/008 进行打电话
   //VideoCallManager.gotoCallOrReverse(currentFragment.getActivity(), Integer.parseInt(((ConversationFragment)currentFragment).getTargetId()));
  }

  @Override
  public void onActivityResult(int i, int i1, Intent intent) {

  }

}