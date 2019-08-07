package io.rong.imkit.rongyun;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import io.rong.common.RLog;
import io.rong.imkit.R;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.emoticon.AndroidEmoji;
import io.rong.imkit.model.ConversationProviderTag;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIConversation;
import io.rong.imkit.userInfoCache.RongUserInfoManager;
import io.rong.imkit.utils.RongDateUtils;

import io.rong.imkit.widget.provider.PrivateConversationProvider;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.RecallNotificationMessage;


@ConversationProviderTag(conversationType = "private", portraitPosition = 2)
public class MyPrivateConversationProvider extends PrivateConversationProvider {
    private static final String TAG = "MyPrivateConversationProvider ";

    public MyPrivateConversationProvider() {
    }

    public View newView(Context context, ViewGroup viewGroup) {
        View result = LayoutInflater.from(context).inflate(R.layout.rc_item_base_conversation, (ViewGroup) null);
        MyPrivateConversationProvider.ViewHolder holder = new MyPrivateConversationProvider.ViewHolder();
        holder.title = (TextView) result.findViewById(R.id.rc_conversation_title);
        holder.time = (TextView) result.findViewById(R.id.rc_conversation_time);
        holder.content = (TextView) result.findViewById(R.id.rc_conversation_content);
        holder.notificationBlockImage = (ImageView) result.findViewById(R.id.rc_conversation_msg_block);
        holder.readStatus = (ImageView) result.findViewById(R.id.rc_conversation_status);
        result.setTag(holder);
        return result;
    }

    private void handleMentionedContent(final MyPrivateConversationProvider.ViewHolder holder, final View view, final UIConversation data) {

        final SpannableStringBuilder builder = new SpannableStringBuilder();
        final String preStr = view.getContext().getString(R.string.rc_message_content_mentioned);
        if (holder.content.getWidth() > 60) {
            CharSequence cutStr = TextUtils.ellipsize(preStr + " " + data.getConversationContent(), holder.content.getPaint(), (float) (holder.content.getWidth() - 60), TextUtils.TruncateAt.END);
            SpannableString string = new SpannableString(cutStr);
            string.setSpan(new ForegroundColorSpan(view.getContext().getResources().getColor(R.color.rc_mentioned_color)), 0, preStr.length(), 33);
            builder.append(string);
            AndroidEmoji.ensure(builder);
            holder.content.setText(builder, TextView.BufferType.SPANNABLE);
        } else {
            holder.content.post(new Runnable() {
                public void run() {
                    if (holder.content.getWidth() > 60) {
                        CharSequence cutStr = TextUtils.ellipsize(preStr + " " + data.getConversationContent(), holder.content.getPaint(), (float) (holder.content.getWidth() - 40), TextUtils.TruncateAt.END);
                        SpannableString strx = new SpannableString(cutStr);
                        strx.setSpan(new ForegroundColorSpan(view.getContext().getResources().getColor(R.color.rc_mentioned_color)), 0, preStr.length(), 33);
                        builder.append(strx);
                    } else {
                        SpannableString str = new SpannableString(preStr + " " + data.getConversationContent());
                        str.setSpan(new ForegroundColorSpan(view.getContext().getResources().getColor(R.color.rc_mentioned_color)), 0, preStr.length(), 33);
                        builder.append(str);
                    }

                    AndroidEmoji.ensure(builder);
                    holder.content.setText(builder, TextView.BufferType.SPANNABLE);
                }
            });
        }

    }

    private void handleDraftContent(final MyPrivateConversationProvider.ViewHolder holder, final View view, final UIConversation data) {

        final SpannableStringBuilder builder = new SpannableStringBuilder();
        final String preStr = view.getContext().getString(R.string.rc_message_content_draft);
        if (holder.content.getWidth() > 60) {
            CharSequence cutStr = TextUtils.ellipsize(preStr + " " + data.getDraft(), holder.content.getPaint(), (float) (holder.content.getWidth() - 60), TextUtils.TruncateAt.END);
            SpannableString string = new SpannableString(cutStr);
            string.setSpan(new ForegroundColorSpan(view.getContext().getResources().getColor(R.color.rc_draft_color)), 0, preStr.length(), 33);
            builder.append(string);
            AndroidEmoji.ensure(builder);
            holder.content.setText(builder, TextView.BufferType.SPANNABLE);
        } else {
            holder.content.post(new Runnable() {
                public void run() {
                    if (holder.content.getWidth() > 60) {
                        CharSequence cutStr = TextUtils.ellipsize(preStr + " " + data.getDraft(), holder.content.getPaint(), (float) (holder.content.getWidth() - 60), TextUtils.TruncateAt.END);
                        SpannableString strx = new SpannableString(cutStr);
                        strx.setSpan(new ForegroundColorSpan(view.getContext().getResources().getColor(R.color.rc_draft_color)), 0, preStr.length(), 33);
                        builder.append(strx);
                    } else {
                        SpannableString str = new SpannableString(preStr + " " + data.getDraft());
                        str.setSpan(new ForegroundColorSpan(view.getContext().getResources().getColor(R.color.rc_draft_color)), 0, preStr.length(), 33);
                        builder.append(str);
                    }

                    AndroidEmoji.ensure(builder);
                    holder.content.setText(builder, TextView.BufferType.SPANNABLE);
                }
            });
        }

    }

    private void handleCommonContent(final MyPrivateConversationProvider.ViewHolder holder, UIConversation data) {

        if (holder.content.getWidth() > 60 && data.getConversationContent() != null) {
            CharSequence cutStr = TextUtils.ellipsize(data.getConversationContent(), holder.content.getPaint(), (float) (holder.content.getWidth() - 60), TextUtils.TruncateAt.END);
            holder.content.setText(cutStr, TextView.BufferType.SPANNABLE);
        } else {
            final CharSequence cutStr = data.getConversationContent();
            holder.content.post(new Runnable() {
                public void run() {
                    if (holder.content.getWidth() > 60 && cutStr != null) {
                        CharSequence str = TextUtils.ellipsize(cutStr, holder.content.getPaint(), (float) (holder.content.getWidth() - 60), TextUtils.TruncateAt.END);
                        holder.content.setText(str, TextView.BufferType.SPANNABLE);
                    } else {
                        holder.content.setText(cutStr);
                    }

                }
            });
        }

    }

    public void bindView(View view, int position, UIConversation data) {
        MyPrivateConversationProvider.ViewHolder holder = (MyPrivateConversationProvider.ViewHolder) view.getTag();
        ProviderTag tag = null;
        if (data == null) {
            holder.title.setText((CharSequence) null);
            holder.time.setText((CharSequence) null);
            holder.content.setText((CharSequence) null);
        } else {
            holder.title.setText(data.getUIConversationTitle());
            String time = RongDateUtils.getConversationListFormatDate(data.getUIConversationTime(), view.getContext());
            holder.time.setText(time);
            if (TextUtils.isEmpty(data.getDraft()) && !data.getMentionedFlag()) {
                boolean readRec = false;

                try {
                    readRec = view.getResources().getBoolean(R.bool.rc_read_receipt);
                } catch (Resources.NotFoundException var10) {
                    RLog.e("MyPrivateConversationProvider ", "rc_read_receipt not configure in rc_config.xml");
                    var10.printStackTrace();
                }

                if (readRec) {
                    if (data.getSentStatus() == Message.SentStatus.READ && data.getConversationSenderId().equals(RongIM.getInstance().getCurrentUserId()) && !(data.getMessageContent() instanceof RecallNotificationMessage)) {
                        holder.readStatus.setImageDrawable(RongContext.getInstance().getResources().getDrawable(R.drawable.ic_item_read));
                    } else {
                        holder.readStatus.setImageDrawable(RongContext.getInstance().getResources().getDrawable(R.drawable.ic_item_unread));
                    }
                }

                this.handleCommonContent(holder, data);
            } else {
                if (data.getMentionedFlag()) {
                    this.handleMentionedContent(holder, view, data);
                } else {
                    this.handleDraftContent(holder, view, data);
                }
                holder.readStatus.setImageDrawable(RongContext.getInstance().getResources().getDrawable(R.drawable.ic_item_unread));
            }

            if (RongContext.getInstance() != null && data.getMessageContent() != null) {
                tag = RongContext.getInstance().getMessageProviderTag(data.getMessageContent().getClass());
            }

            if (data.getSentStatus() != null && (data.getSentStatus() == Message.SentStatus.FAILED || data.getSentStatus() == Message.SentStatus.SENDING) && tag != null && tag.showWarning() && data.getConversationSenderId() != null && data.getConversationSenderId().equals(RongIM.getInstance().getCurrentUserId())) {
                Bitmap bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.rc_conversation_list_msg_send_failure);
                int width = bitmap.getWidth();
                Drawable drawable = null;
                if (data.getSentStatus() == Message.SentStatus.FAILED && TextUtils.isEmpty(data.getDraft())) {
                    drawable = view.getContext().getResources().getDrawable(R.drawable.rc_conversation_list_msg_send_failure);
                } else if (data.getSentStatus() == Message.SentStatus.SENDING && TextUtils.isEmpty(data.getDraft())) {
                    drawable = view.getContext().getResources().getDrawable(R.drawable.rc_conversation_list_msg_sending);
                }

                if (drawable != null) {
                    drawable.setBounds(0, 0, width, width);
                    holder.content.setCompoundDrawablePadding(10);
                    holder.content.setCompoundDrawables(drawable, (Drawable) null, (Drawable) null, (Drawable) null);
                }
            } else {
                holder.content.setCompoundDrawables((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
            }

            Conversation.ConversationNotificationStatus status = data.getNotificationStatus();
            if (status != null && status.equals(Conversation.ConversationNotificationStatus.DO_NOT_DISTURB)) {
                holder.notificationBlockImage.setVisibility(0);
            } else {
                holder.notificationBlockImage.setVisibility(8);
            }
        }

    }

    public Spannable getSummary(UIConversation data) {
        return null;
    }

    public String getTitle(String userId) {
        UserInfo userInfo = RongUserInfoManager.getInstance().getUserInfo(userId);
        return userInfo == null ? userId : userInfo.getName();
    }

    public Uri getPortraitUri(String userId) {
        UserInfo userInfo = RongUserInfoManager.getInstance().getUserInfo(userId);
        return userInfo == null ? null : userInfo.getPortraitUri();
    }

    protected class ViewHolder {
        public TextView title;
        public TextView time;
        public TextView content;
        public ImageView notificationBlockImage;
        public ImageView readStatus;

        protected ViewHolder() {
        }
    }
}