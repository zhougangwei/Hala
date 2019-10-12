package chat.hala.hala.wight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * @author wjy on 2019/10/11/011.
 */
public class NoScrollView extends ScrollView {
    public NoScrollView(Context context) {
        super(context);
    }

    public NoScrollView(Context context, AttributeSet attrs) {
        super(context, null);
    }

    public NoScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, null, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }


}
