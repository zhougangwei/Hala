package chat.hala.hala.rongyun.customizemessage;

import android.os.Parcel;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.common.RLog;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;


@MessageTag(value = "SC:MyPicMessage", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class CustomizePicMessage extends MessageContent {
    private String extra;//消息属性，可随意定义


    public static CustomizePicMessage obtain(String extra) {
        CustomizePicMessage model = new CustomizePicMessage();
        model.setExtra(extra);
        return model;
    }

    public CustomizePicMessage() {
    }

    public CustomizePicMessage(String extra) {
        this.setExtra(extra);
    }


    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("extra", this.getExtra());
        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        }

        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException var3) {
            RLog.e("TextMessage", "UnsupportedEncodingException ", var3);
            return null;
        }
    }


    public CustomizePicMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {

        }
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            if (jsonObj.has("extra"))
                extra = jsonObj.optString("extra");

        } catch (JSONException e) {
        }

    }


    //给消息赋值。
    public CustomizePicMessage(Parcel in) {
        extra = ParcelUtils.readFromParcel(in);//该类为工具类，消息属性

        //这里可继续增加你消息的属性
    }

    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     */
    public static final Creator<CustomizePicMessage> CREATOR = new Creator<CustomizePicMessage>() {

        @Override
        public CustomizePicMessage createFromParcel(Parcel source) {
            return new CustomizePicMessage(source);
        }

        @Override
        public CustomizePicMessage[] newArray(int size) {
            return new CustomizePicMessage[size];
        }
    };

    /**
     * 描述了包含在 Parcelable 对象排列信息中的特殊对象的类型。
     *
     * @return 一个标志位，表明Parcelable对象特殊对象类型集合的排列。
     */
    public int describeContents() {
        return 0;
    }

    /**
     * 将类的数据写入外部提供的 Parcel 中。
     *
     * @param dest  对象被写入的 Parcel。
     * @param flags 对象如何被写入的附加标志。
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, extra);//该类为工具类，对消息中属性进行序列化

    }




    public String getExtra() {
        return extra;
    }

    public void setExtra(String type) {
        this.extra = type;
    }
}