package chat.hala.hala.pay;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import chat.hala.hala.R;


/**
 * Created by oneki on 2017/6/26.
 */

public class DialogCircleProgress extends Dialog {

    public DialogCircleProgress(Context context) {
        super(context, R.style.dialog);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circle_progress);

        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
    }
}
