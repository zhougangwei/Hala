package chat.hala.hala.rongyun;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import chat.hala.hala.base.VideoCallManager;
import io.rong.imkit.R;
import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.IPluginModule;

public class VoiceCallPlugin implements IPluginModule {

  @Override
  public Drawable obtainDrawable(Context context) {
    return ContextCompat.getDrawable(context, R.drawable.rc_ext_plugin_image_selector);
  }

  @Override
  public String obtainTitle(Context context) {
    return "语音";
  }

  @Override
  public void onClick(Fragment currentFragment, RongExtension extension) {
    // TODO: 2019/8/8/008 进行打电话
     // VideoCallManager.gotoCallOrReverse(currentFragment.getActivity(), anchorId, anchorIdMemberId);

  }

  @Override
  public void onActivityResult(int i, int i1, Intent intent) {

  }

}