package chat.hala.hala.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.blankj.utilcode.utils.TimeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chat.hala.hala.R;
import chat.hala.hala.bean.CallListBean;
import chat.hala.hala.utils.TimeUtil;


public class CallListAdapter extends BaseQuickAdapter<CallListBean.DataBean.ListBean, BaseViewHolder> {

    public static final int MYCALL = 1;
    public static final int REVERSE = 2;


    /*
     * 这三个是通话的 //state枚举值分为success通话，missed，no_answer
     * */
    public static final String SUCCESS = "success";
    public static final String MISSED = "missed";
    public static final String NO_ANSWER = "no_answer";

    /*
     * 这三个是预约的 有一个上面Success 公用 in_process进行中，success已通话，failed未回拨
     * */
    public static final String IN_PROCESS = "in_process";
    public static final String FAILED = "failed";


    public int type;        //call还是reverse

    public CallListAdapter(int layoutIds, List<CallListBean.DataBean.ListBean> countryDatas,int type) {
        super(layoutIds, countryDatas);
        this.type=type;
    }

    @Override
    protected void convert(BaseViewHolder helper, CallListBean.DataBean.ListBean item) {
        helper.setText(R.id.tv_name, item.getTargetInfo().getName());
        String content = "";
        switch (item.getState()) {
            case SUCCESS:
                if (type==MYCALL){
                    content = "Call duration 40 " + item.getDurationSeconds() + "seconds" + "(" + item.getWorth() + " coins)";
                }else{
                    content="Success";
                }
                break;
            case MISSED:
                content = "Missed call";
                helper.setTextColor(R.id.tv_content, mContext.getResources().getColor(R.color.pink));
                break;
            case NO_ANSWER:
                content = "No answered";
                helper.setTextColor(R.id.tv_content, mContext.getResources().getColor(R.color.pink));
                break;
            case IN_PROCESS:
                content = "In Process";
                break;
            case FAILED:
                content = "Failed";
                break;

        }
        helper.setText(R.id.tv_content, content);
        String startedAt = item.getStartedAt();
        if (!TextUtils.isEmpty(startedAt)) {
            String hour = TimeUtil.dealDateFormat(startedAt);
            String time = TimeUtil.dealDateFormat2(startedAt);
            helper.setText(R.id.tv_hour, hour);
            String week = TimeUtils.getWeek(time);
            helper.setText(R.id.tv_week,week);
        }

        ImageView imageView = (ImageView) helper.getView(R.id.iv_head);
        Glide.with(mContext).load(item.getTargetInfo().getAvatarUrl())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()).placeholder(imageView.getDrawable()).placeholder(imageView.getDrawable()))
                .into(imageView);


    }
}
