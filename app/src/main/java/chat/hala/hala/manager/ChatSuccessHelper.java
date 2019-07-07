package chat.hala.hala.manager;

import android.app.Activity;

import chat.hala.hala.dialog.ReverseSuccessDialog;
import chat.hala.hala.rxbus.RxBus;
import chat.hala.hala.rxbus.event.ReverseSuccessEvent;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @ 创建者   zhou
 * @ 创建时间   2019/7/7 0007 22:03
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2019/7/7 0007$
 * @ 更新描述  ${TODO}
 */
public class ChatSuccessHelper {
    public static void receiveChatSuccess(final Activity activity){
        final RxBus rxBus = RxBus.getIntanceBus();
        Disposable disposable = rxBus.doSubscribe(ReverseSuccessEvent.class, new Consumer<ReverseSuccessEvent>() {
            @Override
            public void accept(ReverseSuccessEvent uiEvent) throws Exception {
                new ReverseSuccessDialog(activity,"Congratulations！Just finish a call").show();
                rxBus.unSubscribe(this);
            }
        });
        rxBus.addSubscription(activity,disposable);
    }
}
