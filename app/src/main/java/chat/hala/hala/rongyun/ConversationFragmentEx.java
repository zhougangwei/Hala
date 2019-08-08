package chat.hala.hala.rongyun;

import android.content.Context;

import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.widget.adapter.MessageListAdapter;

/**
 * @author wjy on 2019/8/8/008.
 */
public class ConversationFragmentEx extends ConversationFragment {
    public MessageListAdapter onResolveAdapter(Context context) {
        return new MyMessageListAdapter(context);
    }
}
