package chat.hala.hala.rongyun;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;

import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.plugin.CombineLocationPlugin;
import io.rong.imkit.plugin.DefaultLocationPlugin;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.plugin.ImagePlugin;
import io.rong.imkit.widget.adapter.MessageListAdapter;

/**
 * @author wjy on 2019/8/8/008.
 */
public class ConversationFragmentEx extends ConversationFragment {
    public MessageListAdapter onResolveAdapter(Context context) {
        return new MyMessageListAdapter(context);
    }


}
