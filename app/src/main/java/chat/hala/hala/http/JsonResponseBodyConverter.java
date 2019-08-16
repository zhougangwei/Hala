package chat.hala.hala.http;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;


import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by oneki on 2017/8/29.
 */

public class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    JsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            String data = value.string();
            LogUtils.w("JsonResponseBodyConverter",data);
            JSONObject jsonObject = new JSONObject(data);
            //需要重新登录
            String code = jsonObject.get("code").toString();

            return adapter.fromJson(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            value.close();
        }
        return null;
    }
}
